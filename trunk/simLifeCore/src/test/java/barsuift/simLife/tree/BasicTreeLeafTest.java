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
import barsuift.simLife.environment.MockSky;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
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
        MockSky mockSky = new MockSky();
        mockSky.setSun(mockSun);
        MockEnvironment mockEnv = new MockEnvironment();
        mockEnv.setSky(mockSky);
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

        assertEquals(0.80f, leaf.getEfficiency().floatValue(), 0.0000000001f);
        assertEquals(14.43520013f, leaf.getEnergy().floatValue(), 0.00001f);
        assertEquals(5.2848f, leaf.collectFreeEnergy().floatValue(), 0.00001f);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), leaf.collectFreeEnergy());

        leafState.setEnergy(new BigDecimal("99"));
        leaf = new BasicTreeLeaf(universe, leafState);
        leaf.collectSolarEnergy();
        // the energy would be more than 100, so it will be capped
        assertEquals(100f, leaf.getEnergy().floatValue(), 0.00001f);
    }

    public void testAge() {
        publisherHelper.addSubscriberTo(leaf);
        assertEquals(0, publisherHelper.nbUpdated());

        // efficiency before aging = 0.80
        assertEquals(0.80f, leaf.getEfficiency().floatValue(), 0.0000000001f);

        leaf.age();

        // /efficiency after aging = 0.80 * 0.95 = 0.76
        assertEquals(0.76f, leaf.getEfficiency().floatValue(), 0.0000000001f);

        assertEquals(1, publisherHelper.nbUpdated());
        Object updateParam = publisherHelper.getUpdateObjects().get(0);
        assertEquals(LeafEvent.EFFICIENCY, updateParam);
    }

    public void testImproveEfficiency() {
        publisherHelper.addSubscriberTo(leaf);
        assertEquals(0.80f, leaf.getEfficiency().floatValue(), 0.0000000001f);
        assertEquals(10f, leaf.getEnergy().floatValue(), 0.00001f);

        leaf.improveEfficiency();
        assertEquals(0.90f, leaf.getEfficiency().floatValue(), 0.0000000001f);
        assertEquals(0f, leaf.getEnergy().floatValue(), 0.00001f);

        leafState.setEnergy(new BigDecimal("35"));
        leaf = new BasicTreeLeaf(universe, leafState);
        leaf.improveEfficiency();
        assertEquals(1.00f, leaf.getEfficiency().floatValue(), 0.0000000001f);
        assertEquals(15f, leaf.getEnergy().floatValue(), 0.00001f);
    }

    public void testFall() {
        // make sure the leaf only has 10% efficiency (limit before falling)
        leafState.setEfficiency(PercentHelper.getDecimalValue(10));
        leaf = new BasicTreeLeaf(universe, leafState);
        publisherHelper.addSubscriberTo(leaf);

        assertFalse(leaf.isTooWeak());
        assertEquals(0, universe.getPhysics().getGravity().getFallingLeaves().size());

        leaf.age();

        assertTrue(leaf.getEfficiency().floatValue() < 0.1f);
        assertTrue(leaf.isTooWeak());

        assertEquals(2, publisherHelper.nbUpdated());
        // first the age method notifies about efficiency
        Object updateParam1 = publisherHelper.getUpdateObjects().get(0);
        assertEquals(LeafEvent.EFFICIENCY, updateParam1);
        // then the fall method notifies about fall
        Object updateParam2 = publisherHelper.getUpdateObjects().get(1);
        assertEquals(MobileEvent.FALLING, updateParam2);

        assertEquals(1, universe.getPhysics().getGravity().getFallingLeaves().size());
        assertTrue(universe.getPhysics().getGravity().getFallingLeaves().contains(leaf));
    }

    public void testUpdate() {
        TreeLeaf3D leaf3D = leaf.getTreeLeaf3D();
        // the leaf should be a subscriber of the leaf3D
        assertEquals(1, leaf3D.countSubscribers());
        // assert the leaf is really one of the subscribers of the leaf3D
        leaf3D.deleteSubscriber(leaf);
        assertEquals(0, leaf3D.countSubscribers());


        publisherHelper.addSubscriberTo(leaf);

        // test with wrong argument
        leaf.update(leaf3D, MobileEvent.FALLING);
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // test with wrong argument
        leaf.update(leaf3D, null);
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // test with wrong argument
        leaf.update(leaf3D, LeafEvent.EFFICIENCY);
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // test with good argument
        leaf.update(leaf3D, MobileEvent.FALLEN);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(MobileEvent.FALLEN, publisherHelper.getUpdateObjects().get(0));
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
