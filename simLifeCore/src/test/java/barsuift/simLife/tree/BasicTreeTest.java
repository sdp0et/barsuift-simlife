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

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.process.Aging;
import barsuift.simLife.process.ConditionalTask;
import barsuift.simLife.process.MockMainSynchronizer;
import barsuift.simLife.process.Photosynthesis;
import barsuift.simLife.process.TreeGrowth;
import barsuift.simLife.universe.MockUniverse;

public class BasicTreeTest extends TestCase {

    private MockUniverse universe;

    private TreeState treeState;

    private BasicTree tree;

    protected void setUp() throws Exception {
        super.setUp();
        universe = new MockUniverse();
        treeState = CoreDataCreatorForTests.createSpecificTreeState();
        tree = new BasicTree(treeState);
        tree.init(universe);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        universe = null;
        treeState = null;
        tree = null;
    }

    public void testBasicTree() {
        assertEquals(treeState.getBranches().size(), tree.getNbBranches());
        try {
            BasicTree tree = new BasicTree(treeState);
            tree.init(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTree(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        MockMainSynchronizer synchronizer = (MockMainSynchronizer) universe.getSynchronizer();
        List<ConditionalTask> slowScheduled = synchronizer.getSlowScheduled();
        assertEquals(3, slowScheduled.size());
        assertEquals(Photosynthesis.class, slowScheduled.get(0).getClass());
        assertEquals(Aging.class, slowScheduled.get(1).getClass());
        assertEquals(TreeGrowth.class, slowScheduled.get(2).getClass());
    }

    public void testGetState() {
        assertEquals(treeState, tree.getState());
        assertSame(treeState, tree.getState());
        BigDecimal energy = tree.getState().getEnergy();
        tree.collectSolarEnergy();
        assertEquals(treeState, tree.getState());
        assertSame(treeState, tree.getState());
        // the energy should have change in the state
        assertFalse(energy.equals(tree.getState().getEnergy()));
    }


    public void testCollectSolarEnergy() {
        ((MockSun3D) universe.getEnvironment().getSky().getSun().getSun3D()).setBrightness(PercentHelper
                .getDecimalValue(70));
        tree.collectSolarEnergy();
        assertEquals(40, tree.getNbBranches());

        // as computed in BasicTreeBranchTest#testCollectSolarEnergy
        // -> freeEnergy in branches = 42.636
        // collected energy from branches = 40 * 42.636 + 10 = 1715.44

        assertEquals(1715.44f, tree.getEnergy().floatValue(), 0.00001f);
        assertEquals(0f, tree.collectFreeEnergy().floatValue(), 0.00001f);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), tree.collectFreeEnergy());
    }

}
