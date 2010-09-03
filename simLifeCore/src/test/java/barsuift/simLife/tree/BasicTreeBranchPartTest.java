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
import java.util.Observable;

import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.tree.TreeBranchPart3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.time.ObservableTestHelper;
import barsuift.simLife.universe.MockUniverse;


public class BasicTreeBranchPartTest extends TestCase {

    private MockUniverse universe;

    private TreeBranchPartState branchPartState;

    private BasicTreeBranchPart branchPart;

    private TreeLeafState firstLeafState;

    protected void setUp() throws Exception {
        super.setUp();
        universe = new MockUniverse();
        branchPartState = CoreDataCreatorForTests.createSpecificTreeBranchPartState();
        firstLeafState = branchPartState.getLeaveStates().get(0);
        firstLeafState.setEfficiency(PercentHelper.getDecimalValue(10));
        branchPart = new BasicTreeBranchPart(universe, branchPartState);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        firstLeafState = null;
        universe = null;
        branchPartState = null;
        branchPart = null;
    }

    public void testObservers() {
        for (TreeLeaf leaf : branchPart.getLeaves()) {
            // the leaf is observed by its 3D counterpart and by the branch part
            assertEquals(2, leaf.countObservers());
            leaf.deleteObserver(branchPart);
            // check the branch part is actually one of the observers
            assertEquals(1, leaf.countObservers());
        }
    }

    public void testBasicTreeBranchPart() {
        assertEquals(branchPartState.getLeaveStates().size(), branchPart.getLeaves().size());
        try {
            new BasicTreeBranchPart(null, branchPartState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranchPart(universe, null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testSpendTime() {
        ((MockSun) universe.getEnvironment().getSun()).setLuminosity(PercentHelper.getDecimalValue(70));
        // add mock observers on each leaf
        List<ObservableTestHelper> observerHelpers = new ArrayList<ObservableTestHelper>();
        for (TreeLeaf leaf : branchPart.getLeaves()) {
            ObservableTestHelper observerHelper = new ObservableTestHelper();
            observerHelpers.add(observerHelper);
            observerHelper.addIObserver(leaf);
            assertEquals(0, observerHelper.nbUpdated());
        }

        branchPart.spendTime();

        assertEquals(16, branchPart.getAge());
        // as computed in BasicTreeLeafTest#testSpendTime1
        // -> freeEnergy in leaves should be 5.17056, except for falling leaf 0 (no more in the leaf list)
        // total collected energy = 4 * 5.17056 = 20.68224
        // energy = 20.68224 * 0.50 + 10 = 20.34112
        // free energy = 20.68224 - 10.34112 + 3 = 13.34112
        assertEquals(20.34112, branchPart.getEnergy().doubleValue(), 0.000001);
        assertEquals(13.34112, branchPart.collectFreeEnergy().doubleValue(), 0.000001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), branchPart.collectFreeEnergy());
        int nbFall = 0;
        for (ObservableTestHelper observerHelper : observerHelpers) {
            assertEquals(1, observerHelper.nbUpdated());
            int updateParam = (Integer) observerHelper.getUpdateObjects().get(0);
            if (LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.FALL_MASK)) {
                // the single falling leaf (the first one)
                assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
                assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
                assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));
                assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.FALL_MASK));
                nbFall++;
                assertEquals(1, nbFall);
            } else {
                // all the other leaves
                assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
                assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
                assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));
            }
        }
        // check one leaf has been removed from the list
        assertEquals(branchPartState.getLeaveStates().size() - 1, branchPart.getLeaves().size());
    }

    public void testGetState() {
        assertEquals(branchPartState, branchPart.getState());
    }

    public void testFallingLeaf() {
        branchPart = new BasicTreeBranchPart(universe, branchPartState);
        int nbLeaves = branchPart.getNbLeaves();

        branchPart.spendTime();

        assertEquals("one leaf should have been removed", nbLeaves - 1, branchPart.getNbLeaves());
        for (TreeLeaf leaf : branchPart.getLeaves()) {
            // make sure the first leaf is not contained anymore in the list of leaves of the branch part
            assertFalse(firstLeafState.equals(leaf.getState()));
        }
        // simulate one leaf is aging, but not falling
        branchPart.update((Observable) branchPart.getLeaves().get(0), LeafUpdateMask.AGE_MASK);
        branchPart.update((Observable) branchPart.getLeaves().get(0), LeafUpdateMask.EFFICIENCY_MASK);
        assertEquals("no leaf should have been removed", nbLeaves - 1, branchPart.getNbLeaves());
    }

    public void testComputeAttachPointForNewLeaf1() {
        // create object states
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setLeafAttachPoint(new Point3dState(2, 0, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setLeafAttachPoint(new Point3dState(3, 0, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setLeaf3DState(leaf3D2);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        leaveStates.add(leaf1);
        leaveStates.add(leaf2);
        TreeBranchPart3DState part3D = new TreeBranchPart3DState(new Point3dState(3.5, 0, 0));
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        partState.setBranchPart3DState(part3D);
        // the branch part is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3d boundsStartPoint = new Point3d(0.8, 0, 0);
        Point3d boundsEndPoint = new Point3d(1.2, 0, 0);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
        Point3d pointForNewLeaf = part.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    public void testComputeAttachPointForNewLeaf2() {
        // create object states
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setLeafAttachPoint(new Point3dState(1, 0, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setLeafAttachPoint(new Point3dState(5, 0, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setLeaf3DState(leaf3D2);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        leaveStates.add(leaf1);
        leaveStates.add(leaf2);
        TreeBranchPart3DState part3D = new TreeBranchPart3DState(new Point3dState(7, 0, 0));
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        partState.setBranchPart3DState(part3D);
        // the branch part is along the X axis
        // it starts at 0
        // the leaves are at 1 and 5
        // it ends at 7
        // 0 ...... 1 ...... ...... ...... ...... 5 ...... ...... 7
        // the new leaf should be created around 3 (+/- 10% * distance -> 0.4)
        Point3d boundsStartPoint = new Point3d(2.6, 0, 0);
        Point3d boundsEndPoint = new Point3d(3.4, 0, 0);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
        Point3d pointForNewLeaf = part.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    public void testComputeAttachPointForNewLeaf3() {
        // create object states
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setLeafAttachPoint(new Point3dState(2, 0, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setLeafAttachPoint(new Point3dState(3, 0, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setLeaf3DState(leaf3D2);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        leaveStates.add(leaf1);
        leaveStates.add(leaf2);
        TreeBranchPart3DState part3D = new TreeBranchPart3DState(new Point3dState(6, 0, 0));
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        partState.setBranchPart3DState(part3D);
        // the branch part is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 6
        // 0 ...... ...... 2 ...... 3 ...... ...... ...... 6
        // the new leaf should be created around 4.5 (+/- 10% * distance -> 0.3)
        Point3d boundsStartPoint = new Point3d(4.2, 0, 0);
        Point3d boundsEndPoint = new Point3d(4.8, 0, 0);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
        Point3d pointForNewLeaf = part.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    /**
     * Test if the leaves are not created in the order based on the distance to the branch part start.
     */
    public void testComputeAttachPointForNewLeaf4() {
        // create object states
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setLeafAttachPoint(new Point3dState(2, 0, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setLeafAttachPoint(new Point3dState(3, 0, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setLeaf3DState(leaf3D2);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        // add the leaves in the "wrong" order
        leaveStates.add(leaf2);
        leaveStates.add(leaf1);
        TreeBranchPart3DState part3D = new TreeBranchPart3DState(new Point3dState(3.5, 0, 0));
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        partState.setBranchPart3DState(part3D);
        // the branch part is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3d boundsStartPoint = new Point3d(0.8, 0, 0);
        Point3d boundsEndPoint = new Point3d(1.2, 0, 0);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
        Point3d pointForNewLeaf = part.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    public void testCreateOneNewLeaf() {
        // create object states
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setEnergy(new BigDecimal(150));
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);

        assertEquals(0, part.getNbLeaves());
        assertEquals(0, part.getBranchPart3D().getLeaves().size());

        part.createOneNewLeaf();

        assertEquals(1, part.getNbLeaves());
        assertEquals(1, part.getBranchPart3D().getLeaves().size());
        TreeLeaf newLeaf = part.getLeaves().get(part.getLeaves().size() - 1);
        // there should be 2 observers, one of which is the part itself (the other one is the leaf3D)
        assertEquals(2, newLeaf.countObservers());
        newLeaf.deleteObserver(part);
        assertEquals(1, newLeaf.countObservers());
        assertEquals(new BigDecimal(60), part.getEnergy());
    }

    public void testCanCreateOneNewLeaf() {
        BasicTreeBranchPart part;
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setEnergy(new BigDecimal(89));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertFalse(part.canCreateOneNewLeaf());

        partState.setEnergy(new BigDecimal(150));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertTrue(part.canCreateOneNewLeaf());

        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>();
        leaveStates.add(new TreeLeafState());
        leaveStates.add(new TreeLeafState());
        leaveStates.add(new TreeLeafState());
        partState.setLeaveStates(leaveStates);
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertTrue(part.canCreateOneNewLeaf());

        leaveStates.add(new TreeLeafState());
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertFalse(part.canCreateOneNewLeaf());
    }

    public void testShouldCreateOneNewLeaf() {
        BasicTreeBranchPart part;
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setEnergy(new BigDecimal(89));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertFalse(part.shouldCreateOneNewLeaf());

        // 90 = 90 + (0% * 90)
        // so we should have 0% odd to create a new leaf
        partState.setEnergy(new BigDecimal(90));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = part.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertEquals(0, sum);


        // 153 = 90 + (70% * 90)
        // so we should have 70% odd to create a new leaf
        partState.setEnergy(new BigDecimal(153));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = part.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertTrue(sum > 650);
        assertTrue(sum < 750);

        // 180 = 90 + (100% * 90)
        // so we should have 100% odd to create a new leaf
        partState.setEnergy(new BigDecimal(180));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = part.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);

        // 250 > 90 + (100% * 90)
        // so we should have 100% odd to create a new leaf
        partState.setEnergy(new BigDecimal(250));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = part.shouldCreateOneNewLeaf();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);
    }

    public void testCanIncreaseOneLeafSize() {
        BasicTreeBranchPart part;
        TreeBranchPartState partState = CoreDataCreatorForTests.createSpecificTreeBranchPartState();
        partState.setEnergy(new BigDecimal(19));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertFalse(part.canIncreaseOneLeafSize());

        partState.setEnergy(new BigDecimal(20));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertTrue(part.canIncreaseOneLeafSize());

        // set all the leaves at their maximum size, so that they can not be increased anymore
        for (TreeLeafState leafState : partState.getLeaveStates()) {
            TreeLeaf3DState leaf3dState = leafState.getLeaf3DState();
            Point3dState initialEndPoint1 = leaf3dState.getInitialEndPoint1();
            leaf3dState.setEndPoint1(new Point3dState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                    initialEndPoint1.getZ() * 10));
            Point3dState initialEndPoint2 = leaf3dState.getInitialEndPoint2();
            leaf3dState.setEndPoint2(new Point3dState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                    initialEndPoint2.getZ() * 10));
        }
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertFalse(part.canIncreaseOneLeafSize());

        // reset the end point 1 of leaf 1, so that it can be increased again
        partState.getLeaveStates().get(0).getLeaf3DState()
                .setEndPoint1(partState.getLeaveStates().get(0).getLeaf3DState().getInitialEndPoint1());
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertTrue(part.canIncreaseOneLeafSize());
    }

    public void testShouldIncreaseOneLeafSize() {
        BasicTreeBranchPart part;
        TreeBranchPartState partState = CoreDataCreatorForTests.createSpecificTreeBranchPartState();
        partState.setEnergy(new BigDecimal(19));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        assertFalse(part.shouldIncreaseOneLeafSize());

        // 20 = 20 + (0% * 80)
        // so we should have 0% odd to create a new leaf
        partState.setEnergy(new BigDecimal(20));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = part.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(0, sum);


        // 76 = 20 + (70% * 80)
        // so we should have 70% odd to create a new leaf
        partState.setEnergy(new BigDecimal(76));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = part.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertTrue(sum > 650);
        assertTrue(sum < 750);

        // 100 = 20 + (100% * 80)
        // so we should have 100% odd to create a new leaf
        partState.setEnergy(new BigDecimal(100));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = part.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);

        // 250 > 20 + (100% * 80)
        // so we should have 100% odd to create a new leaf
        partState.setEnergy(new BigDecimal(250));
        part = new BasicTreeBranchPart(new MockUniverse(), partState);
        sum = 0;
        for (int i = 0; i < 1000; i++) {
            boolean result = part.shouldIncreaseOneLeafSize();
            sum += (result ? 1 : 0);
        }
        assertEquals(1000, sum);
    }

    public void testIncreaseOneLeafSize() {
        Point3d firstInitialEndPoint1 = firstLeafState.getLeaf3DState().getInitialEndPoint1().toPointValue();
        branchPartState.setEnergy(new BigDecimal(150));
        // set all the leaves at their maximum size, so that they can not be increased anymore
        for (TreeLeafState leafState : branchPartState.getLeaveStates()) {
            TreeLeaf3DState leaf3dState = leafState.getLeaf3DState();
            Point3dState initialEndPoint1 = leaf3dState.getInitialEndPoint1();
            leaf3dState.setEndPoint1(new Point3dState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                    initialEndPoint1.getZ() * 10));
            Point3dState initialEndPoint2 = leaf3dState.getInitialEndPoint2();
            leaf3dState.setEndPoint2(new Point3dState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                    initialEndPoint2.getZ() * 10));
        }
        // set the first leaf end point to (0,0,0) so that it can be increased
        firstLeafState.getLeaf3DState().setEndPoint1(new Point3dState());
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), branchPartState);

        part.increaseOneLeafSize();


        PointTestHelper.assertPointEquals(firstInitialEndPoint1, part.getLeaves().get(0).getTreeLeaf3D().getState()
                .getEndPoint1().toPointValue());
        assertEquals(new BigDecimal(130), part.getEnergy());

        part.increaseOneLeafSize();

        Point3d expectedEndPoint = new Point3d(firstInitialEndPoint1.getX() * 2, firstInitialEndPoint1.getY() * 2,
                firstInitialEndPoint1.getZ() * 2);
        PointTestHelper.assertPointEquals(expectedEndPoint, part.getLeaves().get(0).getTreeLeaf3D().getState()
                .getEndPoint1().toPointValue());
        assertEquals(new BigDecimal(110), part.getEnergy());
    }

    public void testGetRandomLeafToIncrease1() {
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setEndPoint1(new Point3dState(2, 0, 0));
        leaf3D1.setEndPoint2(new Point3dState(0, 2, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setEndPoint1(new Point3dState(2, 0, 0));
        leaf3D2.setEndPoint2(new Point3dState(0, 4, 0));
        TreeLeaf3DState leaf3D3 = new TreeLeaf3DState();
        leaf3D3.setEndPoint1(new Point3dState(3, 0, 0));
        leaf3D3.setEndPoint2(new Point3dState(0, 4, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setId(new Long(1));
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setId(new Long(2));
        leaf2.setLeaf3DState(leaf3D2);
        TreeLeafState leaf3 = new TreeLeafState();
        leaf3.setId(new Long(3));
        leaf3.setLeaf3DState(leaf3D3);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(3);
        leaveStates.add(leaf1);
        leaveStates.add(leaf2);
        leaveStates.add(leaf3);
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
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
            TreeLeaf leaf = part.getRandomLeafToIncrease();
            if (leaf.getId() == 1) {
                sum1++;
            }
            if (leaf.getId() == 2) {
                sum2++;
            }
            if (leaf.getId() == 3) {
                sum3++;
            }
        }
        assertTrue("sum1=" + sum1, sum1 > 370);
        assertTrue("sum1=" + sum1, sum1 < 460);
        assertTrue("sum2=" + sum2, sum2 > 300);
        assertTrue("sum2=" + sum2, sum2 < 366);
        assertTrue("sum3=" + sum3, sum3 > 220);
        assertTrue("sum3=" + sum3, sum3 < 280);
    }

    /**
     * Test with one leaf at its maximum size
     */
    public void testGetRandomLeafToIncrease2() {
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setEndPoint1(new Point3dState(2, 0, 0));
        leaf3D1.setEndPoint2(new Point3dState(0, 2, 0));
        leaf3D1.setInitialEndPoint1(new Point3dState(0.2, 0, 0));
        leaf3D1.setInitialEndPoint2(new Point3dState(0, 0.2, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setEndPoint1(new Point3dState(2, 0, 0));
        leaf3D2.setEndPoint2(new Point3dState(0, 4, 0));
        TreeLeaf3DState leaf3D3 = new TreeLeaf3DState();
        leaf3D3.setEndPoint1(new Point3dState(3, 0, 0));
        leaf3D3.setEndPoint2(new Point3dState(0, 4, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setId(new Long(1));
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setId(new Long(2));
        leaf2.setLeaf3DState(leaf3D2);
        TreeLeafState leaf3 = new TreeLeafState();
        leaf3.setId(new Long(3));
        leaf3.setLeaf3DState(leaf3D3);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(3);
        leaveStates.add(leaf1);
        leaveStates.add(leaf2);
        leaveStates.add(leaf3);
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
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
            TreeLeaf leaf = part.getRandomLeafToIncrease();
            if (leaf.getId() == 1) {
                sum1++;
            }
            if (leaf.getId() == 2) {
                sum2++;
            }
            if (leaf.getId() == 3) {
                sum3++;
            }
        }
        assertEquals(0, sum1);
        assertTrue("sum2=" + sum2, sum2 > 540);
        assertTrue("sum2=" + sum2, sum2 < 660);
        assertTrue("sum3=" + sum3, sum3 > 360);
        assertTrue("sum3=" + sum3, sum3 < 440);
    }

    /**
     * Test with all but one leaves at their maximum sizes
     */
    public void testGetRandomLeafToIncrease3() {
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setEndPoint1(new Point3dState(2, 0, 0));
        leaf3D1.setEndPoint2(new Point3dState(0, 2, 0));
        leaf3D1.setInitialEndPoint1(new Point3dState(0.2, 0, 0));
        leaf3D1.setInitialEndPoint2(new Point3dState(0, 0.2, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setEndPoint1(new Point3dState(2, 0, 0));
        leaf3D2.setEndPoint2(new Point3dState(0, 4, 0));
        leaf3D2.setInitialEndPoint1(new Point3dState(0.2, 0, 0));
        leaf3D2.setInitialEndPoint2(new Point3dState(0, 0.4, 0));
        TreeLeaf3DState leaf3D3 = new TreeLeaf3DState();
        leaf3D3.setEndPoint1(new Point3dState(3, 0, 0));
        leaf3D3.setEndPoint2(new Point3dState(0, 4, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setId(new Long(1));
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setId(new Long(2));
        leaf2.setLeaf3DState(leaf3D2);
        TreeLeafState leaf3 = new TreeLeafState();
        leaf3.setId(new Long(3));
        leaf3.setLeaf3DState(leaf3D3);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(3);
        leaveStates.add(leaf1);
        leaveStates.add(leaf2);
        leaveStates.add(leaf3);
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
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
            TreeLeaf leaf = part.getRandomLeafToIncrease();
            if (leaf.getId() == 1) {
                sum1++;
            }
            if (leaf.getId() == 2) {
                sum2++;
            }
            if (leaf.getId() == 3) {
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
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setEndPoint1(new Point3dState(0, 0, 0));
        leaf3D1.setEndPoint2(new Point3dState(0, 0, 0));
        // this is to ensure the leaf is not consedered to have reached its maximum size
        leaf3D1.setInitialEndPoint1(new Point3dState(0.2, 0, 0));
        leaf3D1.setInitialEndPoint2(new Point3dState(0, 0.2, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setEndPoint1(new Point3dState(2, 0, 0));
        leaf3D2.setEndPoint2(new Point3dState(0, 4, 0));
        TreeLeaf3DState leaf3D3 = new TreeLeaf3DState();
        leaf3D3.setEndPoint1(new Point3dState(3, 0, 0));
        leaf3D3.setEndPoint2(new Point3dState(0, 4, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setId(new Long(1));
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setId(new Long(2));
        leaf2.setLeaf3DState(leaf3D2);
        TreeLeafState leaf3 = new TreeLeafState();
        leaf3.setId(new Long(3));
        leaf3.setLeaf3DState(leaf3D3);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(3);
        leaveStates.add(leaf1);
        leaveStates.add(leaf2);
        leaveStates.add(leaf3);
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
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
            TreeLeaf leaf = part.getRandomLeafToIncrease();
            if (leaf.getId() == 1) {
                sum1++;
            }
            if (leaf.getId() == 2) {
                sum2++;
            }
            if (leaf.getId() == 3) {
                sum3++;
            }
        }
        assertTrue("sum1=" + sum1, sum1 > 450);
        assertTrue("sum1=" + sum1, sum1 < 550);
        assertTrue("sum2=" + sum2, sum2 > 260);
        assertTrue("sum2=" + sum2, sum2 < 340);
        assertTrue("sum3=" + sum3, sum3 > 160);
        assertTrue("sum3=" + sum3, sum3 < 240);
    }

    /**
     * Test with 2 leaves, one of which has 0 for area
     */
    public void testGetRandomLeafToIncrease5() {
        TreeLeaf3DState leaf3D1 = new TreeLeaf3DState();
        leaf3D1.setEndPoint1(new Point3dState(0, 0, 0));
        leaf3D1.setEndPoint2(new Point3dState(0, 0, 0));
        // this is to ensure the leaf is not consedered to have reached its maximum size
        leaf3D1.setInitialEndPoint1(new Point3dState(0.2, 0, 0));
        leaf3D1.setInitialEndPoint2(new Point3dState(0, 0.2, 0));
        TreeLeaf3DState leaf3D2 = new TreeLeaf3DState();
        leaf3D2.setEndPoint1(new Point3dState(2, 0, 0));
        leaf3D2.setEndPoint2(new Point3dState(0, 4, 0));
        TreeLeafState leaf1 = new TreeLeafState();
        leaf1.setId(new Long(1));
        leaf1.setLeaf3DState(leaf3D1);
        TreeLeafState leaf2 = new TreeLeafState();
        leaf2.setId(new Long(2));
        leaf2.setLeaf3DState(leaf3D2);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        leaveStates.add(leaf1);
        leaveStates.add(leaf2);
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
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
            TreeLeaf leaf = part.getRandomLeafToIncrease();
            if (leaf.getId() == 1) {
                sum1++;
            }
            if (leaf.getId() == 2) {
                sum2++;
            }
        }
        assertEquals(1000, sum1);
        assertEquals(0, sum2);
    }

}
