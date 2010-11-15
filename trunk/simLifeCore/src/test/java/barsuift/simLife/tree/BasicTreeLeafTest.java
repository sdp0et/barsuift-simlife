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

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.MockEnvironment;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.universe.MockUniverse;

public class BasicTreeLeafTest extends TestCase {

    private BasicTreeLeaf leaf;

    private PublisherTestHelper publisherHelper;

    private MockUniverse universe;

    private TreeLeafState leafState;

    private MockSun mockSun;

    protected void setUp() throws Exception {
        super.setUp();

        leafState = CoreDataCreatorForTests.createSpecificTreeLeafState();

        BigDecimal lightRate = PercentHelper.getDecimalValue(70);
        mockSun = new MockSun();
        mockSun.setBrightness(lightRate);
        MockEnvironment mockEnv = new MockEnvironment();
        mockEnv.setSun(mockSun);
        universe = new MockUniverse();
        universe.setEnvironment(mockEnv);

        leaf = new BasicTreeLeaf(universe, leafState);
        publisherHelper = new PublisherTestHelper();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        leaf = null;
        publisherHelper = null;
        universe = null;
        leafState = null;
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

    public void testCollectSolarEnergy() {
        leaf.collectSolarEnergy();
        // efficiency at the beginning = 0.80
        // area = 0.08000000238418585
        // collected energy = 0.80 * 0.70 * 150 * 0.08000000238418585 = 6.7200002002716114
        // energy for leaf = 6.7200002002716114 * 0.66 = 4.435200132179263524
        // total energy = 10 + 4.435200132179263524 = 14.435200132179263524 (scale 10 -> 14.43520013)
        // free energy = 6.7200002002716114 - 4.435200132179263524 = 2.284800068092347876
        // total free energy = 3 + 2.284800068092347876 = 5.284800068092347876 (scale 5 -> 5.2848)

        assertEquals(0.80, leaf.getEfficiency().doubleValue(), 0.0000000001);
        assertEquals(14.43520013, leaf.getEnergy().doubleValue(), 0.00001);
        assertEquals(5.2848, leaf.collectFreeEnergy().doubleValue(), 0.00001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), leaf.collectFreeEnergy());

        leafState.setEnergy(new BigDecimal("99"));
        leaf = new BasicTreeLeaf(universe, leafState);
        leaf.collectSolarEnergy();
        // the energy would be more than 100, so it will be capped
        assertEquals(100, leaf.getEnergy().doubleValue(), 0.00001);
    }

    public void testAge() {
        publisherHelper.addSubscriberTo(leaf);
        assertEquals(0, publisherHelper.nbUpdated());

        // efficiency before aging = 0.80
        assertEquals(0.80, leaf.getEfficiency().doubleValue(), 0.0000000001);

        leaf.age();

        // /efficiency after aging = 0.80 * 0.95 = 0.76
        assertEquals(0.76, leaf.getEfficiency().doubleValue(), 0.0000000001);

        assertEquals(1, publisherHelper.nbUpdated());
        int updateParam = (Integer) publisherHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam, LeafUpdateMask.EFFICIENCY_MASK));
    }

    public void testImproveEfficiency() {
        publisherHelper.addSubscriberTo(leaf);
        assertEquals(0.80, leaf.getEfficiency().doubleValue(), 0.0000000001);
        assertEquals(10, leaf.getEnergy().doubleValue(), 0.00001);

        leaf.improveEfficiency();
        assertEquals(0.90, leaf.getEfficiency().doubleValue(), 0.0000000001);
        assertEquals(0, leaf.getEnergy().doubleValue(), 0.00001);

        leafState.setEnergy(new BigDecimal("35"));
        leaf = new BasicTreeLeaf(universe, leafState);
        leaf.improveEfficiency();
        assertEquals(1.00, leaf.getEfficiency().doubleValue(), 0.0000000001);
        assertEquals(15, leaf.getEnergy().doubleValue(), 0.00001);
    }

    public void testFall() {
        // make sure the leaf only has 10% efficiency (limit before falling)
        leafState.setEfficiency(PercentHelper.getDecimalValue(10));
        leaf = new BasicTreeLeaf(universe, leafState);
        publisherHelper.addSubscriberTo(leaf);

        assertFalse(leaf.isTooWeak());
        assertEquals(0, universe.getPhysics().getGravity().getFallingLeaves().size());

        leaf.age();

        assertTrue(leaf.getEfficiency().doubleValue() < 0.1);
        assertTrue(leaf.isTooWeak());

        assertEquals(2, publisherHelper.nbUpdated());
        // first the age method notifies about efficiency
        int updateParam1 = (Integer) publisherHelper.getUpdateObjects().get(0);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam1, LeafUpdateMask.EFFICIENCY_MASK));
        // then the fall method notifies about fall
        int updateParam2 = (Integer) publisherHelper.getUpdateObjects().get(1);
        assertTrue(LeafUpdateMask.isFieldSet(updateParam2, LeafUpdateMask.FALLING_MASK));

        assertEquals(1, universe.getPhysics().getGravity().getFallingLeaves().size());
        assertTrue(universe.getPhysics().getGravity().getFallingLeaves().contains(leaf));
    }

    public void testGetState() {
        assertEquals(leafState, leaf.getState());
        assertSame(leafState, leaf.getState());
        BigDecimal energy = leaf.getState().getEnergy();
        BigDecimal efficiency = leaf.getState().getEfficiency();
        leaf.improveEfficiency();
        assertEquals(leafState, leaf.getState());
        assertSame(leafState, leaf.getState());
        // the energy and efficiency should have change in the state
        assertFalse(energy.equals(leaf.getState().getEnergy()));
        assertFalse(efficiency.equals(leaf.getState().getEfficiency()));
    }

}
