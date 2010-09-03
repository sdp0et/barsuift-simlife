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
import javax.media.j3d.TransformGroup;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.MockEnvironment;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.j3d.universe.physic.MockGravity;
import barsuift.simLife.time.ObservableTestHelper;
import barsuift.simLife.universe.MockUniverse;

public class BasicTreeLeafTest extends TestCase {

    private BasicTreeLeaf leaf;

    private ObservableTestHelper observerHelper;

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
        observerHelper = new ObservableTestHelper();
        attachLeaf3DIn3dStructure();
    }

    private void attachLeaf3DIn3dStructure() {
        BranchGroup leafBranchGroup = leaf.getTreeLeaf3D().getBranchGroup();
        bg = new BranchGroup();
        TransformGroup tg = new TransformGroup();
        bg.addChild(tg);
        tg.addChild(leafBranchGroup);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        leaf = null;
        observerHelper = null;
        universe = null;
        leafState = null;
        bg = null;
    }

    public void testBasicTreeLeaf() {
        try {
            new BasicTreeLeaf(null, leafState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected excpetion
        }
        try {
            new BasicTreeLeaf(universe, null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected excpetion
        }
    }

    public void testSpendTime1() {
        observerHelper.addObserver(leaf);

        assertEquals(0, observerHelper.nbUpdated());

        leaf.spendTime();
        // efficiency after aging = 0.80 * 0.95 = 0.76
        // area = 0.08
        // collected energy = 0.76 * 0.70 * 150 * 0.08 = 6.384
        // energy for leaf = 6.384 * 0.66 = 4.21344
        // total energy = 10 + 4.21344 = 14.21344
        // free energy = 6.384 - 4.21344 = 2.17056
        // total free energy = 3 + 2.17056 = 5.17056
        // efficiency after useEnergy : 0.76 + 0.14421344 = 0.9021344
        // energy after useEnergy = 0
        // age = 16

        assertEquals(1, observerHelper.nbUpdated());
        int updateParam = (Integer) observerHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));

        assertEquals(0.9021344, leaf.getEfficiency().doubleValue(), 0.000001);
        assertEquals(0, leaf.getEnergy().doubleValue(), 0.000001);
        assertEquals(5.17056, leaf.collectFreeEnergy().doubleValue(), 0.000001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), leaf.collectFreeEnergy());
        assertEquals(16, leaf.getAge());
    }

    public void testSpendTime2() {
        leafState.setEfficiency(new BigDecimal("0.999999"));
        leaf = new BasicTreeLeaf(universe, leafState);
        observerHelper.addObserver(leaf);

        assertEquals(0, observerHelper.nbUpdated());

        leaf.spendTime();
        // efficiency after aging = 0.999999 * 0.95 = 0.94999905
        // area = 0.08
        // collected energy = 0.94999905 * 0.70 * 150 * 0.08 = 7.97999202
        // energy for leaf = 7.97999202 * 0.66 = 5.2667947332
        // total energy = 10 + 5.2667947332 = 15.2667947332
        // free energy = 7.97999202 - 5.2667947332 = 2.7131972868
        // total free energy = 3 + 2.7131972868 = 5.7131972868
        // max efficiency to add = 1 - 0.94999905 = 0.05000095
        // efficiency to add = min(0.05000095,0.152667947332) = 0.05000095
        // efficiency after useEnergy : 0.94999905 + 0.05000095 = 1
        // energy after useEnergy = 15.2667947332 - 5.000095 = 10.2666997332
        // age = 16

        assertEquals(1, observerHelper.nbUpdated());
        int updateParam = (Integer) observerHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));

        assertEquals(1, leaf.getEfficiency().doubleValue(), 0.000001);
        assertEquals(10.2666997332, leaf.getEnergy().doubleValue(), 0.000001);
        assertEquals(5.7131972868, leaf.collectFreeEnergy().doubleValue(), 0.000001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), leaf.collectFreeEnergy());
        assertEquals(16, leaf.getAge());
    }

    public void testSpendTime3() {
        leafState.setEfficiency(new BigDecimal("0.991"));
        leaf = new BasicTreeLeaf(universe, leafState);
        observerHelper.addObserver(leaf);

        assertEquals(0, observerHelper.nbUpdated());

        leaf.spendTime();
        // efficiency after aging = 0.991 * 0.95 = 0.94145
        // collected energy = 0.94145 * 0.70 * 150 * 0.08 = 7.90818
        // energy for leaf = 7.90818 * 0.66 = 5.2193988
        // total energy = 10 + 5.2193988 = 15.2193988
        // free energy = 7.90818 - 5.2193988 = 2.6887812
        // total free energy = 3 + 2.6887812 = 5.6887812
        // max efficiency to add = 1 - 0.94145 = 0.05855
        // efficiency to add = min(0.05855,0.152193988) = 0.05855
        // efficiency after useEnergy : 0.94145 + 0.05855 = 1
        // energy after useEnergy = 15.2193988 - 5.855 = 9.3643988
        // age = 16

        assertEquals(1, observerHelper.nbUpdated());
        int updateParam = (Integer) observerHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));

        assertEquals(1, leaf.getEfficiency().doubleValue(), 0.000001);
        assertEquals(9.3643988, leaf.getEnergy().doubleValue(), 0.000001);
        assertEquals(5.6887812, leaf.collectFreeEnergy().doubleValue(), 0.000001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), leaf.collectFreeEnergy());
        assertEquals(16, leaf.getAge());
    }

    public void testFall() {
        // make sure the leaf only has 10% efficiency (limit before falling)
        leafState.setEfficiency(PercentHelper.getDecimalValue(10));
        leaf = new BasicTreeLeaf(universe, leafState);
        attachLeaf3DIn3dStructure();
        observerHelper.addObserver(leaf);

        assertFalse(leaf.isTooWeak());

        leaf.spendTime();

        assertTrue(leaf.getEfficiency().doubleValue() < 0.1);
        assertTrue(leaf.isTooWeak());

        assertEquals(1, observerHelper.nbUpdated());
        int updateParam = (Integer) observerHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.AGE_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.ENERGY_MASK));
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.FALL_MASK));

        MockGravity gravity = (MockGravity) universe.getUniverse3D().getPhysics().getGravity();
        assertEquals(bg, gravity.getFallenGroup());
    }

    public void testGetState() {
        assertEquals(leafState, leaf.getState());
    }

}
