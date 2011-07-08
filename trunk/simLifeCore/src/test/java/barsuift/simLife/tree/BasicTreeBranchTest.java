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

import org.fest.assertions.Delta;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.universe.MockUniverse;

import static org.fest.assertions.Assertions.assertThat;

// TODO see if there is a reason to create new MockUniverse every time
public class BasicTreeBranchTest {

    private MockUniverse universe;

    private TreeBranchState branchState;

    private BasicTreeBranch branch;

    @BeforeMethod
    protected void setUp() {
        universe = new MockUniverse();
        branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branch = new BasicTreeBranch(branchState);
        branch.init(universe);
    }

    @AfterMethod
    protected void tearDown() {
        universe = null;
        branchState = null;
        branch = null;
    }

    @Test
    public void testSubscribers() {
        for (TreeLeaf leaf : branch.getLeaves()) {
            // the leaf is subscribed by its 3D counterpart and by the branch
            assertThat(leaf.countSubscribers()).isEqualTo(2);
            leaf.deleteSubscriber(branch);
            // check the branch is actually one of the subscribers
            assertThat(leaf.countSubscribers()).isEqualTo(1);
        }
    }

    @Test
    public void testConstructor() {
        assertThat(branch.getNbLeaves()).isEqualTo(branchState.getLeavesStates().size());
        assertThat(branch.getLeaves()).hasSize(branchState.getLeavesStates().size());
        try {
            BasicTreeBranch branch = new BasicTreeBranch(branchState);
            branch.init(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranch(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    @Test
    public void testCollectSolarEnergy() {
        ((MockSun3D) universe.getEnvironment().getSky().getSun().getSun3D()).setBrightness(PercentHelper
                .getDecimalValue(70));
        assertThat(branch.getNbLeaves()).isEqualTo(15);
        branch.collectSolarEnergy();

        // check the list of leaves has not changed
        assertThat(branch.getNbLeaves()).isEqualTo(15);

        // as computed in BasicTreeLeafTest#testCollectSolarEnergy
        // -> freeEnergy in leaves should be 5.2848
        // total collected energy = 15 * 5.2848 = 79.272
        // energy = 79.272 * 0.50 + 10 = 49.636
        // free energy = 79.272 - 39.636 + 3 = 42.636

        assertThat(branch.getEnergy().floatValue()).isEqualTo(49.636f, Delta.delta(0.00001f));
        assertThat(branch.collectFreeEnergy().floatValue()).isEqualTo(42.636f, Delta.delta(0.00001f));
        // can not collect the free energy more than once
        assertThat(branch.collectFreeEnergy()).isEqualTo(new BigDecimal(0));
    }

    @Test
    public void testGetState() {
        assertThat(branch.getState()).isEqualTo(branchState);
        assertThat(branch.getState()).isSameAs(branchState);
        BigDecimal energy = branch.getState().getEnergy();
        branch.collectSolarEnergy();
        assertThat(branch.getState()).isEqualTo(branchState);
        assertThat(branch.getState()).isSameAs(branchState);
        // the energy should have change in the state
        assertThat(branch.getState().getEnergy()).isNotEqualTo(energy);
    }

    @Test
    public void testFallingLeaf() {
        TreeLeafState firstLeafState = branchState.getLeavesStates().get(0);
        firstLeafState.setEfficiency(PercentHelper.getDecimalValue(10));
        branch = new BasicTreeBranch(branchState);
        branch.init(universe);
        int nbLeaves = branch.getNbLeaves();

        for (TreeLeaf leaf : branch.getLeaves()) {
            leaf.age();
        }

        assertThat(branch.getNbLeaves()).as("one leaf should have been removed").isEqualTo(nbLeaves - 1);
        for (TreeLeaf leaf : branch.getLeaves()) {
            // make sure the first leaf is not contained anymore in the list of leaves of the branch
            assertThat(leaf.getState()).isNotEqualTo(firstLeafState);
        }
        // simulate one leaf is aging, but not falling
        branch.update((Publisher) branch.getLeaves().toArray()[0], LeafEvent.EFFICIENCY);
        assertThat(branch.getNbLeaves()).as("no leaf should have been removed").isEqualTo(nbLeaves - 1);
    }



    @Test
    public void testCreateOneNewLeaf() {
        // create object states
        TreeBranchState branchState = new TreeBranchState();
        branchState.setEnergy(new BigDecimal(150));
        BasicTreeBranch branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());

        assertThat(branch.getNbLeaves()).isEqualTo(0);
        assertThat(branch.getBranch3D().getLeaves()).isEmpty();

        branch.createOneNewLeaf();

        assertThat(branch.getNbLeaves()).isEqualTo(1);
        assertThat(branch.getBranch3D().getLeaves()).hasSize(1);
        TreeLeaf newLeaf = (TreeLeaf) branch.getLeaves().toArray()[branch.getLeaves().size() - 1];
        // there should be 2 subscribers, one of which is the branch itself (the other one is the leaf3D)
        assertThat(newLeaf.countSubscribers()).isEqualTo(2);
        newLeaf.deleteSubscriber(branch);
        assertThat(newLeaf.countSubscribers()).isEqualTo(1);
        assertThat(branch.getEnergy()).isEqualTo(new BigDecimal(60));
    }

    @Test
    public void testCanCreateOneNewLeaf() {
        BasicTreeBranch branch;
        TreeBranchState branchState = new TreeBranchState();
        branchState.setEnergy(new BigDecimal(89));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.canCreateOneNewLeaf()).isFalse();

        branchState.setEnergy(new BigDecimal(150));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.canCreateOneNewLeaf()).isTrue();

        // add some leaves, but let some room for one leaf
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>();
        for (int i = 0; i < BasicTreeBranch.MAX_NB_LEAVES - 1; i++) {
            leavesStates.add(new TreeLeafState());
        }
        branchState.setLeavesStates(leavesStates);
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.canCreateOneNewLeaf()).isTrue();

        // add the last possible leaf
        leavesStates.add(new TreeLeafState());
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.canCreateOneNewLeaf()).isFalse();
    }

    @Test
    public void testShouldCreateOneNewLeaf() {
        BasicTreeBranch branch;
        TreeBranchState branchState = new TreeBranchState();
        branchState.setEnergy(new BigDecimal(89));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.shouldCreateOneNewLeaf()).isFalse();

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
        assertThat(sum).isEqualTo(0);


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
        assertThat(sum).isGreaterThan(650);
        assertThat(sum).isLessThan(750);

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
        assertThat(sum).isEqualTo(1000);

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
        assertThat(sum).isEqualTo(1000);
    }

    @Test
    public void testCanIncreaseOneLeafSize() {
        BasicTreeBranch branch;
        TreeBranchState branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branchState.setEnergy(new BigDecimal(19));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.canIncreaseOneLeafSize()).isFalse();

        branchState.setEnergy(new BigDecimal(20));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.canIncreaseOneLeafSize()).isTrue();

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
        assertThat(branch.canIncreaseOneLeafSize()).isFalse();

        // reset the end point 1 of leaf 1, so that it can be increased again
        branchState.getLeavesStates().get(0).getLeaf3DState()
                .setEndPoint1(branchState.getLeavesStates().get(0).getLeaf3DState().getInitialEndPoint1());
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.canIncreaseOneLeafSize()).isTrue();
    }

    @Test
    public void testShouldIncreaseOneLeafSize() {
        BasicTreeBranch branch;
        TreeBranchState branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branchState.setEnergy(new BigDecimal(19));
        branch = new BasicTreeBranch(branchState);
        branch.init(new MockUniverse());
        assertThat(branch.shouldIncreaseOneLeafSize()).isFalse();

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
        assertThat(sum).isEqualTo(0);


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
        assertThat(sum).isGreaterThan(650);
        assertThat(sum).isLessThan(750);

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
        assertThat(sum).isEqualTo(1000);

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
        assertThat(sum).isEqualTo(1000);
    }

    @Test
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
        assertThat(branch.getEnergy()).isEqualTo(new BigDecimal(130));

        branch.increaseOneLeafSize();
        assertThat(branch.getEnergy()).isEqualTo(new BigDecimal(110));
    }

}
