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
import barsuift.simLife.universe.MockUniverse;

public class BasicTreeBranchTest extends TestCase {

    private MockUniverse universe;

    private TreeBranchState branchState;

    private BasicTreeBranch branch;

    protected void setUp() throws Exception {
        super.setUp();
        universe = new MockUniverse();
        branchState = CoreDataCreatorForTests.createSpecificTreeBranchState();
        branch = new BasicTreeBranch(universe, branchState);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        universe = null;
        branchState = null;
        branch = null;
    }

    public void testBasicTreeBranch() {
        assertEquals(branchState.getBranchPartStates().size(), branch.getNbParts());
        try {
            new BasicTreeBranch(null, branchState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranch(universe, null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testSpendTime() {
        ((MockSun) universe.getEnvironment().getSun()).setLuminosity(PercentHelper.getDecimalValue(70));
        assertEquals(3, branch.getNbParts());

        branch.spendTime();

        // check the list of branch parts has not changed
        assertEquals(3, branch.getNbParts());
        // as computed in BasicTreeLeafTest#testSpendTime1
        // -> freeEnergy in leaves=5.17056
        // -> collected energy in branch part = 5 * 5.17056 = 25.8528
        // energy for branch part = 25.8528 * 0.50 = 12.9264
        // free energy in branch part = 25.8528 - 12.9264 + 3 = 15.9264
        // collected energy from parts = 3 * 15.9264 = 47.7792
        // energy = 47.7792 * 0 + 10 = 10
        // free energy = 47.7792 - 0 + 3 = 50.7792
        assertEquals(10, branch.getEnergy().doubleValue(), 0.00001);
        assertEquals(50.7792, branch.collectFreeEnergy().doubleValue(), 0.00001);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), branch.collectFreeEnergy());
    }

    public void testGetState() {
        assertEquals(branchState, branch.getState());
        assertSame(branchState, branch.getState());
        BigDecimal energy = branch.getState().getEnergy();
        branch.spendTime();
        assertEquals(branchState, branch.getState());
        assertSame(branchState, branch.getState());
        // the energy should have change in the state
        assertFalse(energy.equals(branch.getState().getEnergy()));
    }

}
