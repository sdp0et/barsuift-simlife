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
import java.util.List;

import org.fest.assertions.Delta;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.process.Aging;
import barsuift.simLife.process.ConditionalTask;
import barsuift.simLife.process.MockMainSynchronizer;
import barsuift.simLife.process.Photosynthesis;
import barsuift.simLife.process.TreeGrowth;
import barsuift.simLife.universe.MockUniverse;

import static org.fest.assertions.Assertions.assertThat;

public class BasicTreeTest {

    private MockUniverse universe;

    private TreeState treeState;

    private BasicTree tree;

    @BeforeMethod
    protected void setUp() {
        universe = new MockUniverse();
        treeState = CoreDataCreatorForTests.createSpecificTreeState();
        tree = new BasicTree(treeState);
        tree.init(universe);
    }

    @AfterMethod
    protected void tearDown() {
        universe = null;
        treeState = null;
        tree = null;
    }

    @Test
    public void testBasicTree() {
        assertThat(tree.getNbBranches()).isEqualTo(treeState.getBranches().size());
        try {
            BasicTree tree = new BasicTree(treeState);
            tree.init(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTree(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        MockMainSynchronizer synchronizer = (MockMainSynchronizer) universe.getSynchronizer();
        List<ConditionalTask> slowScheduled = synchronizer.getSlowScheduled();
        assertThat(slowScheduled).hasSize(3);
        assertThat(slowScheduled.get(0).getClass()).isEqualTo(Photosynthesis.class);
        assertThat(slowScheduled.get(1).getClass()).isEqualTo(Aging.class);
        assertThat(slowScheduled.get(2).getClass()).isEqualTo(TreeGrowth.class);
    }

    @Test
    public void testGetState() {
        assertThat(tree.getState()).isEqualTo(treeState);
        assertThat(tree.getState()).isSameAs(treeState);
        BigDecimal energy = tree.getState().getEnergy();
        tree.collectSolarEnergy();
        assertThat(tree.getState()).isEqualTo(treeState);
        assertThat(tree.getState()).isSameAs(treeState);
        // the energy should have change in the state
        assertThat(tree.getState().getEnergy()).isNotEqualTo(energy);
    }


    @Test
    public void testCollectSolarEnergy() {
        ((MockSun3D) universe.getEnvironment().getSky().getSun().getSun3D()).setBrightness(PercentHelper
                .getDecimalValue(70));
        tree.collectSolarEnergy();
        assertThat(tree.getNbBranches()).isEqualTo(40);

        // as computed in BasicTreeBranchTest#testCollectSolarEnergy
        // -> freeEnergy in branches = 42.636
        // collected energy from branches = 40 * 42.636 + 10 = 1715.44

        assertThat(tree.getEnergy().floatValue()).isEqualTo(1715.44f, Delta.delta(0.00001f));
        assertThat(tree.collectFreeEnergy().floatValue()).isEqualTo(0f, Delta.delta(0.00001f));
        // can not collect the free energy more than once
        assertThat(tree.collectFreeEnergy()).isEqualTo(new BigDecimal(0));
    }

}
