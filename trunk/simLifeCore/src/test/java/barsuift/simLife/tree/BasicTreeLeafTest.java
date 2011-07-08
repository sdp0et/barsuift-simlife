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

import org.fest.assertions.Delta;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.MockEnvironment;
import barsuift.simLife.environment.MockSky;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.universe.MockUniverse;

import static org.fest.assertions.Assertions.assertThat;

public class BasicTreeLeafTest {

    private BasicTreeLeaf leaf;

    private PublisherTestHelper publisherHelper;

    private MockUniverse universe;

    private TreeLeafState leafState;

    private MockSun3D mockSun3D;

    @BeforeMethod
    protected void setUp() {
        leafState = CoreDataCreatorForTests.createSpecificTreeLeafState();

        BigDecimal lightRate = PercentHelper.getDecimalValue(70);
        mockSun3D = new MockSun3D();
        mockSun3D.setBrightness(lightRate);
        MockSun mockSun = new MockSun();
        mockSun.setSun3D(mockSun3D);
        MockSky mockSky = new MockSky();
        mockSky.setSun(mockSun);
        MockEnvironment mockEnv = new MockEnvironment();
        mockEnv.setSky(mockSky);
        universe = new MockUniverse();
        universe.setEnvironment(mockEnv);

        leaf = new BasicTreeLeaf(leafState);
        leaf.init(universe);
        publisherHelper = new PublisherTestHelper();
    }

    @AfterMethod
    protected void tearDown() {
        mockSun3D = null;
        leaf = null;
        publisherHelper = null;
        universe = null;
        leafState = null;
    }

    @Test
    public void testBasicTreeLeaf() {
        try {
            BasicTreeLeaf leaf = new BasicTreeLeaf(leafState);
            leaf.init(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeLeaf(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    @Test
    public void testCollectSolarEnergy() {
        leaf.collectSolarEnergy();
        // efficiency at the beginning = 0.80
        // area = 0.08000000238418585
        // collected energy = 0.80 * 0.70 * 150 * 0.08000000238418585 = 6.7200002002716114
        // energy for leaf = 6.7200002002716114 * 0.66 = 4.435200132179263524
        // total energy = 10 + 4.435200132179263524 = 14.435200132179263524 (scale 10 -> 14.43520013)
        // free energy = 6.7200002002716114 - 4.435200132179263524 = 2.284800068092347876
        // total free energy = 3 + 2.284800068092347876 = 5.284800068092347876 (scale 5 -> 5.2848)

        assertThat(leaf.getEfficiency().floatValue()).isEqualTo(0.80f, Delta.delta(0.0000000001f));
        assertThat(leaf.getEnergy().floatValue()).isEqualTo(14.43520013f, Delta.delta(0.00001f));
        assertThat(leaf.collectFreeEnergy().floatValue()).isEqualTo(5.2848f, Delta.delta(0.00001f));
        // can not collect the free energy more than once
        assertThat(leaf.collectFreeEnergy()).isEqualTo(new BigDecimal(0));

        leafState.setEnergy(new BigDecimal("99"));
        leaf = new BasicTreeLeaf(leafState);
        leaf.init(universe);
        leaf.collectSolarEnergy();
        // the energy would be more than 100, so it will be capped
        assertThat(leaf.getEnergy().floatValue()).isEqualTo(100f, Delta.delta(0.00001f));
    }

    @Test
    public void testAge() {
        publisherHelper.addSubscriberTo(leaf);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);

        // efficiency before aging = 0.80
        assertThat(leaf.getEfficiency().floatValue()).isEqualTo(0.80f, Delta.delta(0.0000000001f));

        leaf.age();

        // /efficiency after aging = 0.80 * 0.95 = 0.76
        assertThat(leaf.getEfficiency().floatValue()).isEqualTo(0.76f, Delta.delta(0.0000000001f));

        assertThat(publisherHelper.nbUpdated()).isEqualTo(1);
        Object updateParam = publisherHelper.getUpdateObjects().get(0);
        assertThat(updateParam).isEqualTo(LeafEvent.EFFICIENCY);
    }

    @Test
    public void testImproveEfficiency() {
        publisherHelper.addSubscriberTo(leaf);
        assertThat(leaf.getEfficiency().floatValue()).isEqualTo(0.80f, Delta.delta(0.0000000001f));
        assertThat(leaf.getEnergy().floatValue()).isEqualTo(10f, Delta.delta(0.00001f));

        leaf.improveEfficiency();
        assertThat(leaf.getEfficiency().floatValue()).isEqualTo(0.90f, Delta.delta(0.0000000001f));
        assertThat(leaf.getEnergy().floatValue()).isEqualTo(0f, Delta.delta(0.00001f));

        leafState.setEnergy(new BigDecimal("35"));
        leaf = new BasicTreeLeaf(leafState);
        leaf.init(universe);
        leaf.improveEfficiency();
        assertThat(leaf.getEfficiency().floatValue()).isEqualTo(1.00f, Delta.delta(0.0000000001f));
        assertThat(leaf.getEnergy().floatValue()).isEqualTo(15f, Delta.delta(0.00001f));
    }

    @Test
    public void testFall() {
        // make sure the leaf only has 10% efficiency (limit before falling)
        leafState.setEfficiency(PercentHelper.getDecimalValue(10));
        leaf = new BasicTreeLeaf(leafState);
        leaf.init(universe);
        publisherHelper.addSubscriberTo(leaf);

        assertThat(leaf.isTooWeak()).isFalse();
        assertThat(universe.getPhysics().getGravity().getFallingLeaves()).isEmpty();

        leaf.age();

        assertThat(leaf.getEfficiency().floatValue()).isLessThan(0.1f);
        assertThat(leaf.isTooWeak()).isTrue();

        assertThat(publisherHelper.nbUpdated()).isEqualTo(2);
        // first the age method notifies about efficiency
        Object updateParam1 = publisherHelper.getUpdateObjects().get(0);
        assertThat(updateParam1).isEqualTo(LeafEvent.EFFICIENCY);
        // then the fall method notifies about fall
        Object updateParam2 = publisherHelper.getUpdateObjects().get(1);
        assertThat(updateParam2).isEqualTo(MobileEvent.FALLING);

        assertThat(universe.getPhysics().getGravity().getFallingLeaves()).hasSize(1);
        assertThat(universe.getPhysics().getGravity().getFallingLeaves()).contains(leaf);
    }

    @Test
    public void testUpdate() {
        TreeLeaf3D leaf3D = leaf.getTreeLeaf3D();
        // the leaf should be a subscriber of the leaf3D
        assertThat(leaf3D.countSubscribers()).isEqualTo(1);
        // assert the leaf is really one of the subscribers of the leaf3D
        leaf3D.deleteSubscriber(leaf);
        assertThat(leaf3D.countSubscribers()).isEqualTo(0);


        publisherHelper.addSubscriberTo(leaf);

        // test with wrong argument
        leaf.update(leaf3D, MobileEvent.FALLING);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // test with wrong argument
        leaf.update(leaf3D, null);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // test with wrong argument
        leaf.update(leaf3D, LeafEvent.EFFICIENCY);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // test with good argument
        leaf.update(leaf3D, MobileEvent.FALLEN);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat(publisherHelper.getUpdateObjects().get(0)).isEqualTo(MobileEvent.FALLEN);
    }

    @Test
    public void testGetState() {
        assertThat(leaf.getState()).isEqualTo(leafState);
        assertThat(leaf.getState()).isSameAs(leafState);
        BigDecimal energy = leaf.getState().getEnergy();
        BigDecimal efficiency = leaf.getState().getEfficiency();
        leaf.improveEfficiency();
        assertThat(leaf.getState()).isEqualTo(leafState);
        assertThat(leaf.getState()).isSameAs(leafState);
        // the energy and efficiency should have change in the state
        assertThat(leaf.getState().getEnergy()).isNotEqualTo(energy);
        assertThat(leaf.getState().getEfficiency()).isNotEqualTo(efficiency);
    }

}
