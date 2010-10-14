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

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.TransformGroup;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.MockEnvironment;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.j3d.universe.physic.MockGravity;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.universe.MockUniverse;

public class BasicTreeLeafTest extends TestCase {

    private BasicTreeLeaf leaf;

    private PublisherTestHelper publisherHelper;

    private MockUniverse universe;

    private TreeLeafState leafState;

    private BranchGroup bg;

    private MockSun mockSun;

    protected void setUp() throws Exception {
        super.setUp();

        leafState = CoreDataCreatorForTests.createSpecificTreeLeafState();

        BigDecimal lightRate = PercentHelper.getDecimalValue(70);
        mockSun = new MockSun();
        mockSun.setLuminosity(lightRate);
        MockEnvironment mockEnv = new MockEnvironment();
        mockEnv.setSun(mockSun);
        universe = new MockUniverse();
        universe.setEnvironment(mockEnv);

        leaf = new BasicTreeLeaf(universe, leafState);
        publisherHelper = new PublisherTestHelper();
        attachLeaf3DIn3dStructure();
    }

    private void attachLeaf3DIn3dStructure() {
        Node leafNode = leaf.getTreeLeaf3D().getNode();
        bg = new BranchGroup();
        TransformGroup tg = new TransformGroup();
        bg.addChild(tg);
        tg.addChild(leafNode);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        leaf = null;
        publisherHelper = null;
        universe = null;
        leafState = null;
        bg = null;
    }

    public void testBasicTreeLeaf() {
        try {
            new BasicTreeLeaf(null, leafState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeLeaf(universe, null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testSpendTime1() {
        publisherHelper.addSubscriber(leaf);

        assertEquals(0, publisherHelper.nbUpdated());

        leaf.spendTime();
        // efficiency after aging = 0.80 * 0.95 = 0.76
        // area = 0.08000000238418585
        // collected energy = 0.76 * 0.70 * 150 * 0.08000000238418585 = 6.38400019025803083
        // energy for leaf = 6.38400019025803083 * 0.66 = 4.2134401255703003478
        // total energy = 10 + 4.2134401255703003478 = 14.2134401255703003478
        // free energy = 6.384 - 4.2134401255703003478 = 2.1705598744296996522
        // total free energy = 3 + 2.1705598744296996522 = 5.1705598744296996522 (scale 5 -> 5.17056)
        // efficiency after useEnergy : 0.76 + 0.142134401255703003478 = 0.902134401255703003478 (scale 10 ->
        // 0.9021344013)
        // energy after useEnergy = 0 (scale 5 -> 0)
        // age = 16

        assertEquals(1, publisherHelper.nbUpdated());
        int updateParam = (Integer) publisherHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));

        assertEquals(0.9021344013, leaf.getEfficiency().doubleValue(), 0.0000000001);
        assertEquals(0, leaf.getEnergy().doubleValue(), 0.00001);
        assertEquals(5.17056, leaf.collectFreeEnergy().doubleValue(), 0.00001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), leaf.collectFreeEnergy());
        assertEquals(16, leaf.getAge());
    }

    public void testSpendTime2() {
        leafState.setEfficiency(new BigDecimal("0.999999"));
        leaf = new BasicTreeLeaf(universe, leafState);
        publisherHelper.addSubscriber(leaf);

        assertEquals(0, publisherHelper.nbUpdated());

        leaf.spendTime();
        // efficiency after aging = 0.999999 * 0.95 = 0.94999905
        // area = 0.08000000238418585
        // collected energy = 0.94999905 * 0.70 * 150 * 0.08000000238418585 = 7.9799922578223007149614625
        // energy for leaf = 7.9799922578223007149614625 * 0.66 = 5.26679489016271847187456525
        // total energy = 10 + 5.26679489016271847187456525 = 15.26679489016271847187456525
        // free energy = 7.9799922578223007149614625 - 5.26679489016271847187456525 = 2.713197367659582232621581
        // total free energy = 3 + 2.713197367659582232621581 = 5.713197367659582232621581 (scale 5 -> 5.71320)
        // max efficiency to add = 1 - 0.94999905 = 0.05000095
        // efficiency to add = min(0.05000095,0.1526679489016271847187456525) = 0.05000095
        // efficiency after useEnergy : 0.94999905 + 0.05000095 = 1 (scale 10 -> 1)
        // energy after useEnergy = 15.26679489016271847187456525 - 5.000095 = 10.26669989016271847187456525 (scale 5 ->
        // 10.26670)
        // age = 16

        assertEquals(1, publisherHelper.nbUpdated());
        int updateParam = (Integer) publisherHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));

        assertEquals(1, leaf.getEfficiency().doubleValue(), 0.0000000001);
        assertEquals(10.26670, leaf.getEnergy().doubleValue(), 0.00001);
        assertEquals(5.71320, leaf.collectFreeEnergy().doubleValue(), 0.00001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), leaf.collectFreeEnergy());
        assertEquals(16, leaf.getAge());
    }

    public void testSpendTime3() {
        leafState.setEfficiency(new BigDecimal("0.991"));
        leaf = new BasicTreeLeaf(universe, leafState);
        publisherHelper.addSubscriber(leaf);

        assertEquals(0, publisherHelper.nbUpdated());

        leaf.spendTime();
        // efficiency after aging = 0.991 * 0.95 = 0.94145
        // area = 0.08000000238418585
        // collected energy = 0.94145 * 0.70 * 150 * 0.08000000238418585 = 7.9081802356821356906625
        // energy for leaf = 7.9081802356821356906625 * 0.66 = 5.21939895555020955583725
        // total energy = 10 + 5.21939895555020955583725 = 15.21939895555020955583725
        // free energy = 7.9081802356821356906625 - 5.21939895555020955583725 = 2.68878128013192613482525
        // total free energy = 3 + 2.68878128013192613482525 = 5.68878128013192613482525 (scale 5 -> 5.68878)
        // max efficiency to add = 1 - 0.94145 = 0.05855
        // efficiency to add = min(0.05855,0.1521939895555020955583725) = 0.05855
        // efficiency after useEnergy : 0.94145 + 0.05855 = 1 (scale 10 -> 1)
        // energy after useEnergy = 15.21939895555020955583725 - 5.855 = 9.36439895555020955583725 (scale 5 -> 9.36440)
        // age = 16

        assertEquals(1, publisherHelper.nbUpdated());
        int updateParam = (Integer) publisherHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));

        assertEquals(1, leaf.getEfficiency().doubleValue(), 0.0000000001);
        assertEquals(9.36440, leaf.getEnergy().doubleValue(), 0.00001);
        assertEquals(5.68878, leaf.collectFreeEnergy().doubleValue(), 0.00001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), leaf.collectFreeEnergy());
        assertEquals(16, leaf.getAge());
    }

    public void testFall() {
        // make sure the leaf only has 10% efficiency (limit before falling)
        leafState.setEfficiency(PercentHelper.getDecimalValue(10));
        leaf = new BasicTreeLeaf(universe, leafState);
        attachLeaf3DIn3dStructure();
        publisherHelper.addSubscriber(leaf);

        assertFalse(leaf.isTooWeak());

        leaf.spendTime();

        assertTrue(leaf.getEfficiency().doubleValue() < 0.1);
        assertTrue(leaf.isTooWeak());

        assertEquals(1, publisherHelper.nbUpdated());
        int updateParam = (Integer) publisherHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.FALL_MASK));

        MockGravity gravity = (MockGravity) universe.getUniverse3D().getPhysics().getGravity();
        assertEquals(bg, gravity.getFallenGroup());
    }

    public void testGetState() {
        assertEquals(leafState, leaf.getState());
        assertSame(leafState, leaf.getState());
        assertEquals(15, leaf.getState().getAge());
        leaf.spendTime();
        assertEquals(leafState, leaf.getState());
        assertSame(leafState, leaf.getState());
        assertEquals(16, leaf.getState().getAge());
    }

}
