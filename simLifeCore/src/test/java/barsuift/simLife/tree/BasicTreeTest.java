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
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.process.AgingTree;
import barsuift.simLife.process.Photosynthesis;
import barsuift.simLife.process.TreeGrowth;
import barsuift.simLife.time.MockTimeController;
import barsuift.simLife.universe.MockUniverse;

public class BasicTreeTest extends TestCase {

    private MockUniverse universe;

    private TreeState treeState;

    private BasicTree tree;

    protected void setUp() throws Exception {
        super.setUp();
        universe = new MockUniverse();
        treeState = CoreDataCreatorForTests.createSpecificTreeState();
        tree = new BasicTree(universe, treeState);
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
            new BasicTree(null, treeState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTree(universe, null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        MockTimeController timeController = (MockTimeController) universe.getTimeController();
        assertEquals(3, timeController.getNbScheduleCalled());
        assertEquals(3, timeController.getScheduledRunnables().size());
        assertEquals(Photosynthesis.class, timeController.getScheduledRunnables().get(0).getClass());
        assertEquals(AgingTree.class, timeController.getScheduledRunnables().get(1).getClass());
        assertEquals(TreeGrowth.class, timeController.getScheduledRunnables().get(2).getClass());
    }

    public void testGetState() {
        assertEquals(treeState, tree.getState());
        assertSame(treeState, tree.getState());
        BigDecimal energy = tree.getState().getEnergy();
        tree.spendTime();
        assertEquals(treeState, tree.getState());
        assertSame(treeState, tree.getState());
        // the energy should have change in the state
        assertFalse(energy.equals(tree.getState().getEnergy()));
    }


    public void testSpendTime() {
        ((MockSun) universe.getEnvironment().getSun()).setLuminosity(PercentHelper.getDecimalValue(70));
        tree.spendTime();
        assertEquals(40, tree.getNbBranches());
        // as computed in BasicTreeBranchTest#testSpendTime
        // -> freeEnergy in branches=50.7792
        // collected energy from branches = 40 * 50.7792 + 10 = 2041.168
        assertEquals(2041.168, tree.getEnergy().doubleValue(), 0.00001);
    }

}
