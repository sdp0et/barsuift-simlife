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

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.universe.MockUniverse;

public class BasicTreeBranchTest extends TestCase {

    private MockUniverse universe;

    private TreeBranchState branchState;

    private BasicTreeBranch branch;

    protected void setUp() throws Exception {
        super.setUp();
        universe = new MockUniverse();
        branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branch = new BasicTreeBranch(universe, branchState);
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

    public void testBasicTreeBranch() {
        assertEquals(branchState.getLeavesStates().size(), branch.getNbLeaves());
        assertEquals(branchState.getLeavesStates().size(), branch.getLeaves().size());
        try {
            new BasicTreeBranch(null, branchState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranch(universe, null);
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
        branch = new BasicTreeBranch(universe, branchState);
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

    private void setLeafStateTransform(TreeLeaf3DState leaf3DState, Vector3f translation) {
        Transform3D transform1 = new Transform3D();
        transform1.setTranslation(translation);
        leaf3DState.setTransform(new Transform3DState(transform1));
    }


    public void testComputeAttachPointForNewleafState1() {
        // create object states
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        setLeafStateTransform(leaf3DState1, new Vector3f(2, 0, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        setLeafStateTransform(leaf3DState2, new Vector3f(3, 0, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(2);
        leavesStates.add(leafState1);
        leavesStates.add(leafState2);
        TreeBranch3DState branch3DState = new TreeBranch3DState(new Tuple3fState(), new Tuple3fState(3.5f, 0, 0));
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        branchState.setBranch3DState(branch3DState);
        // the branch is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3f boundsStartPoint = new Point3f(0.8f, 0, 0);
        Point3f boundsEndPoint = new Point3f(1.2f, 0, 0);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        Point3f pointForNewLeaf = branch.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    public void testComputeAttachPointForNewleafState2() {
        // create object states
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        setLeafStateTransform(leaf3DState1, new Vector3f(1, 0, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        setLeafStateTransform(leaf3DState2, new Vector3f(5, 0, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(2);
        leavesStates.add(leafState1);
        leavesStates.add(leafState2);
        TreeBranch3DState branch3DState = new TreeBranch3DState(new Tuple3fState(), new Tuple3fState(7, 0, 0));
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        branchState.setBranch3DState(branch3DState);
        // the branch is along the X axis
        // it starts at 0
        // the leaves are at 1 and 5
        // it ends at 7
        // 0 ...... 1 ...... ...... ...... ...... 5 ...... ...... 7
        // the new leaf should be created around 3 (+/- 10% * distance -> 0.4)
        Point3f boundsStartPoint = new Point3f(2.6f, 0, 0);
        Point3f boundsEndPoint = new Point3f(3.4f, 0, 0);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        Point3f pointForNewLeaf = branch.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    public void testComputeAttachPointForNewLeaf3() {
        // create object states
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        setLeafStateTransform(leaf3DState1, new Vector3f(2, 0, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        setLeafStateTransform(leaf3DState2, new Vector3f(3, 0, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(2);
        leavesStates.add(leafState1);
        leavesStates.add(leafState2);
        TreeBranch3DState branch3DState = new TreeBranch3DState(new Tuple3fState(), new Tuple3fState(6, 0, 0));
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        branchState.setBranch3DState(branch3DState);
        // the branch is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 6
        // 0 ...... ...... 2 ...... 3 ...... ...... ...... 6
        // the new leaf should be created around 4.5 (+/- 10% * distance -> 0.3)
        Point3f boundsStartPoint = new Point3f(4.2f, 0, 0);
        Point3f boundsEndPoint = new Point3f(4.8f, 0, 0);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        Point3f pointForNewLeaf = branch.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    /**
     * Test if the leaves are not created in the order based on the distance to the branch start.
     */
    public void testComputeAttachPointForNewLeaf4() {
        // create object states
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        setLeafStateTransform(leaf3DState1, new Vector3f(2, 0, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        setLeafStateTransform(leaf3DState2, new Vector3f(3, 0, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(2);
        // add the leaves in the "wrong" order
        leavesStates.add(leafState2);
        leavesStates.add(leafState1);
        TreeBranch3DState branch3DState = new TreeBranch3DState(new Tuple3fState(), new Tuple3fState(3.5f, 0, 0));
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        branchState.setBranch3DState(branch3DState);
        // the branch is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3f boundsStartPoint = new Point3f(0.8f, 0, 0);
        Point3f boundsEndPoint = new Point3f(1.2f, 0, 0);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        Point3f pointForNewLeaf = branch.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    public void testCreateOneNewLeaf() {
        // create object states
        TreeBranchState branchState = new TreeBranchState();
        branchState.setEnergy(new BigDecimal(150));
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);

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
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertFalse(branch.canCreateOneNewLeaf());

        branchState.setEnergy(new BigDecimal(150));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertTrue(branch.canCreateOneNewLeaf());

        // add some leaves, but let some room for one leaf
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>();
        for (int i = 0; i < BasicTreeBranch.MAX_NB_LEAVES - 1; i++) {
            leavesStates.add(new TreeLeafState());
        }
        branchState.setLeavesStates(leavesStates);
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertTrue(branch.canCreateOneNewLeaf());

        // add the last possible leaf
        leavesStates.add(new TreeLeafState());
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertFalse(branch.canCreateOneNewLeaf());
    }

    public void testShouldCreateOneNewLeaf() {
        BasicTreeBranch branch;
        TreeBranchState branchState = new TreeBranchState();
        branchState.setEnergy(new BigDecimal(89));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertFalse(branch.shouldCreateOneNewLeaf());

        // 90 = 90 + (0% * 90)
        // so we should have 0% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(90));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertEquals(0, sum);


        // 153 = 90 + (70% * 90)
        // so we should have 70% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(153));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
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
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);

        // 250 > 90 + (100% * 90)
        // so we should have 100% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(250));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
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
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertFalse(branch.canIncreaseOneLeafSize());

        branchState.setEnergy(new BigDecimal(20));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
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
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertFalse(branch.canIncreaseOneLeafSize());

        // reset the end point 1 of leaf 1, so that it can be increased again
        branchState.getLeavesStates().get(0).getLeaf3DState()
                .setEndPoint1(branchState.getLeavesStates().get(0).getLeaf3DState().getInitialEndPoint1());
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertTrue(branch.canIncreaseOneLeafSize());
    }

    public void testShouldIncreaseOneLeafSize() {
        BasicTreeBranch branch;
        TreeBranchState branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branchState.setEnergy(new BigDecimal(19));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        assertFalse(branch.shouldIncreaseOneLeafSize());

        // 20 = 20 + (0% * 80)
        // so we should have 0% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(20));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(0, sum);


        // 76 = 20 + (70% * 80)
        // so we should have 70% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(76));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
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
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);

        // 250 > 20 + (100% * 80)
        // so we should have 100% odd to create a new leaf
        branchState.setEnergy(new BigDecimal(250));
        branch = new BasicTreeBranch(new MockUniverse(), branchState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = branch.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);
    }

    public void testIncreaseOneLeafSize() {
        TreeLeafState firstLeafState = branchState.getLeavesStates().get(0);
        Point3f firstInitialEndPoint1 = firstLeafState.getLeaf3DState().getInitialEndPoint1().toPointValue();
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
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);

        branch.increaseOneLeafSize();


        PointTestHelper.assertPointEquals(firstInitialEndPoint1, ((TreeLeaf) branch.getLeaves().toArray()[0])
                .getTreeLeaf3D().getState().getEndPoint1().toPointValue());
        assertEquals(new BigDecimal(130), branch.getEnergy());

        branch.increaseOneLeafSize();

        Point3f expectedEndPoint = new Point3f(firstInitialEndPoint1.getX() * 2, firstInitialEndPoint1.getY() * 2,
                firstInitialEndPoint1.getZ() * 2);
        PointTestHelper.assertPointEquals(expectedEndPoint, ((TreeLeaf) branch.getLeaves().toArray()[0])
                .getTreeLeaf3D().getState().getEndPoint1().toPointValue());
        assertEquals(new BigDecimal(110), branch.getEnergy());
    }

    public void testGetRandomLeafToIncrease1() {
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        leaf3DState1.setEndPoint1(new Tuple3fState(2, 0, 0));
        leaf3DState1.setEndPoint2(new Tuple3fState(0, 2, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        leaf3DState2.setEndPoint1(new Tuple3fState(2, 0, 0));
        leaf3DState2.setEndPoint2(new Tuple3fState(0, 4, 0));
        TreeLeaf3DState leaf3DState3 = new TreeLeaf3DState();
        leaf3DState3.setEndPoint1(new Tuple3fState(3, 0, 0));
        leaf3DState3.setEndPoint2(new Tuple3fState(0, 4, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        TreeLeafState leafState3 = new TreeLeafState();
        leafState3.setLeaf3DState(leaf3DState3);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(3);
        leavesStates.add(leafState1);
        leavesStates.add(leafState2);
        leavesStates.add(leafState3);
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        // area1 = 2
        // area2 = 4
        // area3 = 6
        // sum (area) = 12
        // diffArea1 = 12 - 2 = 10
        // diffArea2 = 12 - 4 = 8
        // diffArea3 = 12 - 6 = 6
        // sum (diffArea) = 24
        // ratio1 = 10 / 24 = 41.66%
        // ratio2 = 8 / 24 = 33.33%
        // ratio3 = 6 / 24 = 25.00%

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf leaf = branch.getRandomLeafToIncrease();
            if (leaf.getState() == leafState1) {
                sum1++;
            }
            if (leaf.getState() == leafState2) {
                sum2++;
            }
            if (leaf.getState() == leafState3) {
                sum3++;
            }
        }
        assertTrue("sum1=" + sum1, sum1 > 310);
        assertTrue("sum1=" + sum1, sum1 < 510);
        assertTrue("sum2=" + sum2, sum2 > 280);
        assertTrue("sum2=" + sum2, sum2 < 386);
        assertTrue("sum3=" + sum3, sum3 > 200);
        assertTrue("sum3=" + sum3, sum3 < 300);
    }

    /**
     * Test with one leaf at its maximum size
     */
    public void testGetRandomLeafToIncrease2() {
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        leaf3DState1.setEndPoint1(new Tuple3fState(2, 0, 0));
        leaf3DState1.setEndPoint2(new Tuple3fState(0, 2, 0));
        leaf3DState1.setInitialEndPoint1(new Tuple3fState(0.2f, 0, 0));
        leaf3DState1.setInitialEndPoint2(new Tuple3fState(0, 0.2f, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        leaf3DState2.setEndPoint1(new Tuple3fState(2, 0, 0));
        leaf3DState2.setEndPoint2(new Tuple3fState(0, 4, 0));
        TreeLeaf3DState leaf3DState3 = new TreeLeaf3DState();
        leaf3DState3.setEndPoint1(new Tuple3fState(3, 0, 0));
        leaf3DState3.setEndPoint2(new Tuple3fState(0, 4, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        TreeLeafState leafState3 = new TreeLeafState();
        leafState3.setLeaf3DState(leaf3DState3);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(3);
        leavesStates.add(leafState1);
        leavesStates.add(leafState2);
        leavesStates.add(leafState3);
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        // area1 = 2 (not taken into account, because leaf is already at its maximum size)
        // area2 = 4
        // area3 = 6
        // sum (area) = 10
        // diffArea2 = 10 - 4 = 6
        // diffArea3 = 10 - 6 = 4
        // sum (diffArea) = 10
        // ratio2 = 6 / 10 = 60%
        // ratio3 = 4 / 10 = 40%

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf leaf = branch.getRandomLeafToIncrease();
            if (leaf.getState() == leafState1) {
                sum1++;
            }
            if (leaf.getState() == leafState2) {
                sum2++;
            }
            if (leaf.getState() == leafState3) {
                sum3++;
            }
        }
        assertEquals(0, sum1);
        assertTrue("sum2=" + sum2, sum2 > 540);
        assertTrue("sum2=" + sum2, sum2 < 660);
        assertTrue("sum3=" + sum3, sum3 > 340);
        assertTrue("sum3=" + sum3, sum3 < 460);
    }

    /**
     * Test with all but one leaves at their maximum sizes
     */
    public void testGetRandomLeafToIncrease3() {
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        leaf3DState1.setEndPoint1(new Tuple3fState(2, 0, 0));
        leaf3DState1.setEndPoint2(new Tuple3fState(0, 2, 0));
        leaf3DState1.setInitialEndPoint1(new Tuple3fState(0.2f, 0, 0));
        leaf3DState1.setInitialEndPoint2(new Tuple3fState(0, 0.2f, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        leaf3DState2.setEndPoint1(new Tuple3fState(2, 0, 0));
        leaf3DState2.setEndPoint2(new Tuple3fState(0, 4, 0));
        leaf3DState2.setInitialEndPoint1(new Tuple3fState(0.2f, 0, 0));
        leaf3DState2.setInitialEndPoint2(new Tuple3fState(0, 0.4f, 0));
        TreeLeaf3DState leaf3DState3 = new TreeLeaf3DState();
        leaf3DState3.setEndPoint1(new Tuple3fState(3, 0, 0));
        leaf3DState3.setEndPoint2(new Tuple3fState(0, 4, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        TreeLeafState leafState3 = new TreeLeafState();
        leafState3.setLeaf3DState(leaf3DState3);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(3);
        leavesStates.add(leafState1);
        leavesStates.add(leafState2);
        leavesStates.add(leafState3);
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        // area1 = 2 (not taken into account, because leaf is already at its maximum size)
        // area2 = 4 (not taken into account, because leaf is already at its maximum size)
        // area3 = 6
        // sum (area) = 6
        // diffArea3 = 6 - 6 = 0
        // sum (diffArea) = 0
        // ratio3 = 0 / 0 = 100%

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf leaf = branch.getRandomLeafToIncrease();
            if (leaf.getState() == leafState1) {
                sum1++;
            }
            if (leaf.getState() == leafState2) {
                sum2++;
            }
            if (leaf.getState() == leafState3) {
                sum3++;
            }
        }
        assertEquals(0, sum1);
        assertEquals(0, sum2);
        assertEquals(1000, sum3);
    }

    /**
     * Test with 3 leaves, one of which has 0 for area
     */
    public void testGetRandomLeafToIncrease4() {
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        leaf3DState1.setEndPoint1(new Tuple3fState(0, 0, 0));
        leaf3DState1.setEndPoint2(new Tuple3fState(0, 0, 0));
        // this is to ensure the leaf is not considered to have reached its maximum size
        leaf3DState1.setInitialEndPoint1(new Tuple3fState(0.2f, 0, 0));
        leaf3DState1.setInitialEndPoint2(new Tuple3fState(0, 0.2f, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        leaf3DState2.setEndPoint1(new Tuple3fState(2, 0, 0));
        leaf3DState2.setEndPoint2(new Tuple3fState(0, 4, 0));
        TreeLeaf3DState leaf3DState3 = new TreeLeaf3DState();
        leaf3DState3.setEndPoint1(new Tuple3fState(3, 0, 0));
        leaf3DState3.setEndPoint2(new Tuple3fState(0, 4, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        TreeLeafState leafState3 = new TreeLeafState();
        leafState3.setLeaf3DState(leaf3DState3);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(3);
        leavesStates.add(leafState1);
        leavesStates.add(leafState2);
        leavesStates.add(leafState3);
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        // area1 = 0
        // area2 = 4
        // area3 = 6
        // sum (area) = 10
        // diffArea1 = 10 - 0 = 10
        // diffArea2 = 10 - 4 = 6
        // diffArea3 = 10 - 6 = 4
        // sum (diffArea) = 20
        // ratio1 = 10 / 20 = 50%
        // ratio2 = 6 / 20 = 30%
        // ratio3 = 4 / 20 = 20%

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf leaf = branch.getRandomLeafToIncrease();
            if (leaf.getState() == leafState1) {
                sum1++;
            }
            if (leaf.getState() == leafState2) {
                sum2++;
            }
            if (leaf.getState() == leafState3) {
                sum3++;
            }
        }
        assertTrue("sum1=" + sum1, sum1 > 450);
        assertTrue("sum1=" + sum1, sum1 < 550);
        assertTrue("sum2=" + sum2, sum2 > 240);
        assertTrue("sum2=" + sum2, sum2 < 360);
        assertTrue("sum3=" + sum3, sum3 > 150);
        assertTrue("sum3=" + sum3, sum3 < 250);
    }

    /**
     * Test with 2 leaves, one of which has 0 for area
     */
    public void testGetRandomLeafToIncrease5() {
        TreeLeaf3DState leaf3DState1 = new TreeLeaf3DState();
        leaf3DState1.setEndPoint1(new Tuple3fState(0, 0, 0));
        leaf3DState1.setEndPoint2(new Tuple3fState(0, 0, 0));
        // this is to ensure the leaf is not considered to have reached its maximum size
        leaf3DState1.setInitialEndPoint1(new Tuple3fState(0.2f, 0, 0));
        leaf3DState1.setInitialEndPoint2(new Tuple3fState(0, 0.2f, 0));
        TreeLeaf3DState leaf3DState2 = new TreeLeaf3DState();
        leaf3DState2.setEndPoint1(new Tuple3fState(2, 0, 0));
        leaf3DState2.setEndPoint2(new Tuple3fState(0, 4, 0));
        TreeLeafState leafState1 = new TreeLeafState();
        leafState1.setLeaf3DState(leaf3DState1);
        TreeLeafState leafState2 = new TreeLeafState();
        leafState2.setLeaf3DState(leaf3DState2);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(2);
        leavesStates.add(leafState1);
        leavesStates.add(leafState2);
        TreeBranchState branchState = new TreeBranchState();
        branchState.setLeavesStates(leavesStates);
        BasicTreeBranch branch = new BasicTreeBranch(new MockUniverse(), branchState);
        // area1 = 0
        // area2 = 4
        // sum (area) = 4
        // diffArea1 = 4 - 0 = 4
        // diffArea2 = 4 - 4 = 0
        // sum (diffArea) = 4
        // ratio1 = 4 / 4 = 100%
        // ratio2 = 0 / 4 = 0%

        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf leaf = branch.getRandomLeafToIncrease();
            if (leaf.getState() == leafState1) {
                sum1++;
            }
            if (leaf.getState() == leafState2) {
                sum2++;
            }
        }
        assertEquals(1000, sum1);
        assertEquals(0, sum2);
    }

}
