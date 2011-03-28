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
import barsuift.simLife.j3d.tree.TreeBranchPart3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.universe.MockUniverse;


public class BasicTreeBranchPartTest extends TestCase {

    private MockUniverse universe;

    private TreeBranchPartState branchPartState;

    private BasicTreeBranchPart branchPart;

    protected void setUp() throws Exception {
        super.setUp();
        universe = new MockUniverse();
        branchPartState = CoreDataCreatorForTests.createSpecificTreeBranchPartState();
        branchPart = new BasicTreeBranchPart(universe, branchPartState);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        universe = null;
        branchPartState = null;
        branchPart = null;
    }

    public void testSubscribers() {
        for (TreeLeaf leaf : branchPart.getLeaves()) {
            // the leaf is subscribed by its 3D counterpart and by the branch part
            assertEquals(2, leaf.countSubscribers());
            leaf.deleteSubscriber(branchPart);
            // check the branch part is actually one of the subscribers
            assertEquals(1, leaf.countSubscribers());
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

    public void testCollectSolarEnergy() {
        ((MockSun3D) universe.getEnvironment().getSky().getSun().getSun3D()).setBrightness(PercentHelper
                .getDecimalValue(70));
        assertEquals(5, branchPart.getNbLeaves());
        branchPart.collectSolarEnergy();

        // as computed in BasicTreeLeafTest#testCollectSolarEnergy
        // -> freeEnergy in leaves should be 5.2848
        // total collected energy = 5 * 5.2848 = 26.424
        // energy = 26.424 * 0.50 + 10 = 23.212
        // free energy = 26.424 - 13.212 + 3 = 16.212

        assertEquals(23.212f, branchPart.getEnergy().floatValue(), 0.00001f);
        assertEquals(16.212f, branchPart.collectFreeEnergy().floatValue(), 0.00001f);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), branchPart.collectFreeEnergy());
    }

    public void testGetState() {
        assertEquals(branchPartState, branchPart.getState());
        assertSame(branchPartState, branchPart.getState());
        BigDecimal energy = branchPart.getState().getEnergy();
        branchPart.collectSolarEnergy();
        assertEquals(branchPartState, branchPart.getState());
        assertSame(branchPartState, branchPart.getState());
        // the energy should have change in the state
        assertFalse(energy.equals(branchPart.getState().getEnergy()));
    }

    public void testFallingLeaf() {
        TreeLeafState firstLeafState = branchPartState.getLeaveStates().get(0);
        firstLeafState.setEfficiency(PercentHelper.getDecimalValue(10));
        branchPart = new BasicTreeBranchPart(universe, branchPartState);
        int nbLeaves = branchPart.getNbLeaves();

        for (TreeLeaf leaf : branchPart.getLeaves()) {
            leaf.age();
        }

        assertEquals("one leaf should have been removed", nbLeaves - 1, branchPart.getNbLeaves());
        for (TreeLeaf leaf : branchPart.getLeaves()) {
            // make sure the first leaf is not contained anymore in the list of leaves of the branch part
            assertFalse(firstLeafState.equals(leaf.getState()));
        }
        // simulate one leaf is aging, but not falling
        branchPart.update((Publisher) branchPart.getLeaves().toArray()[0], LeafEvent.EFFICIENCY);
        assertEquals("no leaf should have been removed", nbLeaves - 1, branchPart.getNbLeaves());
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        leaveStates.add(leafState1);
        leaveStates.add(leafState2);
        TreeBranchPart3DState part3D = new TreeBranchPart3DState(new Tuple3fState(3.5f, 0, 0));
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        partState.setBranchPart3DState(part3D);
        // the branch part is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3f boundsStartPoint = new Point3f(0.8f, 0, 0);
        Point3f boundsEndPoint = new Point3f(1.2f, 0, 0);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
        Point3f pointForNewLeaf = part.computeAttachPointForNewLeaf();
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        leaveStates.add(leafState1);
        leaveStates.add(leafState2);
        TreeBranchPart3DState part3D = new TreeBranchPart3DState(new Tuple3fState(7, 0, 0));
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        partState.setBranchPart3DState(part3D);
        // the branch part is along the X axis
        // it starts at 0
        // the leaves are at 1 and 5
        // it ends at 7
        // 0 ...... 1 ...... ...... ...... ...... 5 ...... ...... 7
        // the new leaf should be created around 3 (+/- 10% * distance -> 0.4)
        Point3f boundsStartPoint = new Point3f(2.6f, 0, 0);
        Point3f boundsEndPoint = new Point3f(3.4f, 0, 0);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
        Point3f pointForNewLeaf = part.computeAttachPointForNewLeaf();
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        leaveStates.add(leafState1);
        leaveStates.add(leafState2);
        TreeBranchPart3DState part3D = new TreeBranchPart3DState(new Tuple3fState(6, 0, 0));
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        partState.setBranchPart3DState(part3D);
        // the branch part is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 6
        // 0 ...... ...... 2 ...... 3 ...... ...... ...... 6
        // the new leaf should be created around 4.5 (+/- 10% * distance -> 0.3)
        Point3f boundsStartPoint = new Point3f(4.2f, 0, 0);
        Point3f boundsEndPoint = new Point3f(4.8f, 0, 0);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
        Point3f pointForNewLeaf = part.computeAttachPointForNewLeaf();
        PointTestHelper.assertPointIsWithinBounds(pointForNewLeaf, boundsStartPoint, boundsEndPoint);
    }

    /**
     * Test if the leaves are not created in the order based on the distance to the branch part start.
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        // add the leaves in the "wrong" order
        leaveStates.add(leafState2);
        leaveStates.add(leafState1);
        TreeBranchPart3DState part3D = new TreeBranchPart3DState(new Tuple3fState(3.5f, 0, 0));
        TreeBranchPartState partState = new TreeBranchPartState();
        partState.setLeaveStates(leaveStates);
        partState.setBranchPart3DState(part3D);
        // the branch part is along the X axis
        // it starts at 0
        // the leaves are at 2 and 3
        // it ends at 3.5
        // 0 ...... ...... 2 ...... 3 ... 3.5
        // the new leaf should be created around 1 (+/- 10% * distance -> 0.2)
        Point3f boundsStartPoint = new Point3f(0.8f, 0, 0);
        Point3f boundsEndPoint = new Point3f(1.2f, 0, 0);
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), partState);
        Point3f pointForNewLeaf = part.computeAttachPointForNewLeaf();
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
        TreeLeaf newLeaf = (TreeLeaf) part.getLeaves().toArray()[part.getLeaves().size() - 1];
        // there should be 2 subscribers, one of which is the part itself (the other one is the leaf3D)
        assertEquals(2, newLeaf.countSubscribers());
        newLeaf.deleteSubscriber(part);
        assertEquals(1, newLeaf.countSubscribers());
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
            Tuple3fState initialEndPoint1 = leaf3dState.getInitialEndPoint1();
            leaf3dState.setEndPoint1(new Tuple3fState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                    initialEndPoint1.getZ() * 10));
            Tuple3fState initialEndPoint2 = leaf3dState.getInitialEndPoint2();
            leaf3dState.setEndPoint2(new Tuple3fState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
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
        TreeLeafState firstLeafState = branchPartState.getLeaveStates().get(0);
        Point3f firstInitialEndPoint1 = firstLeafState.getLeaf3DState().getInitialEndPoint1().toPointValue();
        branchPartState.setEnergy(new BigDecimal(150));
        // set all the leaves at their maximum size, so that they can not be increased anymore
        for (TreeLeafState leafState : branchPartState.getLeaveStates()) {
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
        BasicTreeBranchPart part = new BasicTreeBranchPart(new MockUniverse(), branchPartState);

        part.increaseOneLeafSize();


        PointTestHelper.assertPointEquals(firstInitialEndPoint1, ((TreeLeaf) part.getLeaves().toArray()[0])
                .getTreeLeaf3D().getState().getEndPoint1().toPointValue());
        assertEquals(new BigDecimal(130), part.getEnergy());

        part.increaseOneLeafSize();

        Point3f expectedEndPoint = new Point3f(firstInitialEndPoint1.getX() * 2, firstInitialEndPoint1.getY() * 2,
                firstInitialEndPoint1.getZ() * 2);
        PointTestHelper.assertPointEquals(expectedEndPoint, ((TreeLeaf) part.getLeaves().toArray()[0]).getTreeLeaf3D()
                .getState().getEndPoint1().toPointValue());
        assertEquals(new BigDecimal(110), part.getEnergy());
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(3);
        leaveStates.add(leafState1);
        leaveStates.add(leafState2);
        leaveStates.add(leafState3);
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(3);
        leaveStates.add(leafState1);
        leaveStates.add(leafState2);
        leaveStates.add(leafState3);
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(3);
        leaveStates.add(leafState1);
        leaveStates.add(leafState2);
        leaveStates.add(leafState3);
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(3);
        leaveStates.add(leafState1);
        leaveStates.add(leafState2);
        leaveStates.add(leafState3);
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
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(2);
        leaveStates.add(leafState1);
        leaveStates.add(leafState2);
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
