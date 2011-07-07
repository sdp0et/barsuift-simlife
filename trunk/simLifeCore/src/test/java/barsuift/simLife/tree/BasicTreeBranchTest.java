/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.universe.MockUniverse;

// TODO see if there is a reason to create new MockUniverse every time
public class BasicTreeBranchTest extends TestCase {

    private MockUniverse universe;

    private TreeBranchState branchState;

    private BasicTreeBranch branch;

    protected void setUp() throws Exception {
        super.setUp();
        universe = new MockUniverse();
        branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branch = new BasicTreeBranch(branchState);
        branch.init(universe);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        universe = null;
        branchState = null;
        branch = null;
    }

    public void testSubscribers() {
        for (TreeLeaf leaf : branch.getLeaves()) {
            // the leaf is subscribed by its 3D counterpart and by the branch
            assertEquals(2, leaf.countSubscribers());
            leaf.deleteSubscriber(branch);
            // check the branch is actually one of the subscribers
            assertEquals(1, leaf.countSubscribers());
        }
    }

    public void testConstructor() {
        assertEquals(branchState.getLeavesStates().size(), branch.getNbLeaves());
        assertEquals(branchState.getLeavesStates().size(), branch.getLeaves().size());
        try {
            BasicTreeBranch branch = new BasicTreeBranch(branchState);
            branch.init(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranch(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testCollectSolarEnergy() {
        ((MockSun3D) universe.getEnvironment().getSky().getSun().getSun3D()).setBrightness(PercentHelper
                .getDecimalValue(70));
        assertEquals(15, branch.getNbLeaves());
        branch.collectSolarEnergy();

        // check the list of leaves has not changed
        assertEquals(15, branch.getNbLeaves());

        // as computed in BasicTreeLeafTest#testCollectSolarEnergy
        // -> freeEnergy in leaves should be 5.2848
        // total collected energy = 15 * 5.2848 = 79.272
        // energy = 79.272 * 0.50 + 10 = 49.636
        // free energy = 79.272 - 39.636 + 3 = 42.636

        assertEquals(49.636f, branch.getEnergy().floatValue(), 0.00001f);
        assertEquals(42.636f, branch.collectFreeEnergy().floatValue(), 0.00001f);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), branch.collectFreeEnergy());
    }

    public void testGetState() {
        assertEquals(branchState, branch.getState());
        assertSame(branchState, branch.getState());
        BigDecimal energy = branch.getState().getEnergy();
        branch.collectSolarEnergy();
        assertEquals(branchState, branch.getState());
        assertSame(branchState, branch.getState());
        // the energy should have change in the state
        assertFalse(energy.equals(branch.getState().getEnergy()));
    }

    public void testFallingLeaf() {
        TreeLeafState firstLeafState = branchState.getLeavesStates().get(0);
        firstLeafState.setEfficiency(PercentHelper.getDecimalValue(10));
        branch = new BasicTreeBranch(branchState);
        branch.init(universe);
        int nbLeaves = branch.getNbLeaves();

        for (TreeLeaf leaf : branch.getLeaves()) {
            leaf.age();
        }

        assertEquals("one leaf should have been removed", nbLeaves - 1, branch.getNbLeaves());
        for (TreeLeaf leaf : branch.getLeaves()) {
            // make sure the first leaf is not contained anymore in the list of leaves of the branch
            assertFalse(firstLeafState.equals(leaf.getState()));
        }
        // simulate one leaf is aging, but not falling
        branch.update((Publisher) branch.getLeaves().toArray()[0], LeafEvent.EFFICIENCY);
        assertEquals("no leaf should have been removed", nbLeaves - 1, branch.getNbLeaves());
    }



    public void testCreateOneNewLeaf() {
        // create object states
        TreeBranchState branchState = new TreeBranchState();
        branchState.setEnergy(new BigDecimal(150));
        BasicTreeBranch branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());

        assertEquals(0, branch.getNbLeaves());
        assertEquals(0, branch.getBranch3D().getLeaves().size());

        branch.createOneNewLeaf();

        assertEquals(1, branch.getNbLeaves());
        assertEquals(1, branch.getBranch3D().getLeaves().size());
        TreeLeaf newLeaf = (TreeLeaf) branch.getLeaves().toArray()[branch.getLeaves().size() - 1];
        // there should be 2 subscribers, one of which is the branch itself (the other one is the leaf3D)
        assertEquals(2, newLeaf.countSubscribers());
        newLeaf.deleteSubscriber(branch);
        assertEquals(1, newLeaf.countSubscribers());
        assertEquals(new BigDecimal(60), branch.getEnergy());
    }

    public void testCanCreateOneNewLeaf() {
        BasicTreeBranch branch;
        TreeBranchState branchState = new TreeBranchState();
        branchState.setEnergy(new BigDecimal(89));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertFalse(branch.canCreateOneNewLeaf());

        branchState.setEnergy(new BigDecimal(150));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertTrue(branch.canCreateOneNewLeaf());

        // add some leaves, but let some room for one leaf
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>();
        for (int i = 0; i < BasicTreeBranch.MAX_NB_LEAVES - 1; i++) {
            leavesStates.add(new TreeLeafState());
        }
        branchState.setLeavesStates(leavesStates);
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertTrue(branch.canCreateOneNewLeaf());

        // add the last possible leaf
        leavesStates.add(new TreeLeafState());
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertFalse(branch.canCreateOneNewLeaf());
    }

    public void testShouldCreateOneNewLeaf() {
        BasicTreeBranch branch;
        TreeBranchState branchState = new TreeBranchState();
        branchState.setEnergy(new BigDecimal(89));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertFalse(branch.shouldCreateOneNewLeaf());

        // 90 = 90 + (0% * 90)
        // so we should have 0% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(90));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertEquals(0, sum);


        // 153 = 90 + (70% * 90)
        // so we should have 70% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(153));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertTrue(sum > 650);
        assertTrue(sum < 750);

        // 180 = 90 + (100% * 90)
        // so we should have 100% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(180));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);

        // 250 > 90 + (100% * 90)
        // so we should have 100% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(250));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);
    }

    public void testCanIncreaseOneLeafSize() {
        BasicTreeBranch branch;
        TreeBranchState branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branchState.setEnergy(new BigDecimal(19));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertFalse(branch.canIncreaseOneLeafSize());

        branchState.setEnergy(new BigDecimal(20));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertTrue(branch.canIncreaseOneLeafSize());

        // set all the leaves at their maximum size, so that they can not be increased anymore
        for (TreeLeafState leafState : branchState.getLeavesStates()) {
            TreeLeaf3DState leaf3dState = leafState.getLeaf3DState();
            Tuple3fState initialEndPoint1 = leaf3dState.getInitialEndPoint1();
            leaf3dState.setEndPoint1(new Tuple3fState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                    initialEndPoint1.getZ() * 10));
            Tuple3fState initialEndPoint2 = leaf3dState.getInitialEndPoint2();
            leaf3dState.setEndPoint2(new Tuple3fState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                    initialEndPoint2.getZ() * 10));
        }
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertFalse(branch.canIncreaseOneLeafSize());

        // reset the end point 1 of leaf 1, so that it can be increased again
        branchState.getLeavesStates().get(0).getLeaf3DState()
                .setEndPoint1(branchState.getLeavesStates().get(0).getLeaf3DState().getInitialEndPoint1());
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertTrue(branch.canIncreaseOneLeafSize());
    }

    public void testShouldIncreaseOneLeafSize() {
        BasicTreeBranch branch;
        TreeBranchState branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branchState.setEnergy(new BigDecimal(19));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertFalse(branch.shouldIncreaseOneLeafSize());

        // 20 = 20 + (0% * 80)
        // so we should have 0% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(20));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(0, sum);


        // 76 = 20 + (70% * 80)
        // so we should have 70% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(76));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertTrue(sum > 650);
        assertTrue(sum < 750);

        // 100 = 20 + (100% * 80)
        // so we should have 100% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(100));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);

        // 250 > 20 + (100% * 80)
        // so we should have 100% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(250));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);
    }

    public void testIncreaseOneLeafSize() {
        TreeLeafState firstLeafState = branchState.getLeavesStates().get(0);
        branchState.setEnergy(new BigDecimal(150));
        // set all the leaves at their maximum size, so that they can not be increased anymore
        for (TreeLeafState leafState : branchState.getLeavesStates()) {
            TreeLeaf3DState leaf3dState = leafState.getLeaf3DState();
            Tuple3fState initialEndPoint1 = leaf3dState.getInitialEndPoint1();
            leaf3dState.setEndPoint1(new Tuple3fState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                    initialEndPoint1.getZ() * 10));
            Tuple3fState initialEndPoint2 = leaf3dState.getInitialEndPoint2();
            leaf3dState.setEndPoint2(new Tuple3fState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                    initialEndPoint2.getZ() * 10));
        }
        // set the first leaf end point to (0,0,0) so that it can be increased
        firstLeafState.getLeaf3DState().setEndPoint1(new Tuple3fState());
        BasicTreeBranch branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());

        branch.increaseOneLeafSize();
        assertEquals(new BigDecimal(130), branch.getEnergy());

        branch.increaseOneLeafSize();
        assertEquals(new BigDecimal(110), branch.getEnergy());
    }

}
