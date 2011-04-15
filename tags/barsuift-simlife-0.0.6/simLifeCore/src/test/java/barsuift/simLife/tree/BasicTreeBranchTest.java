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
import barsuift.simLife.j3d.environment.MockSun3D;
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

    public void testCollectSolarEnergy() {
        ((MockSun3D) universe.getEnvironment().getSky().getSun().getSun3D()).setBrightness(PercentHelper
                .getDecimalValue(70));
        assertEquals(3, branch.getNbParts());
        branch.collectSolarEnergy();

        // check the list of branch parts has not changed
        assertEquals(3, branch.getNbParts());

        // as computed in BasicTreeBranchPartTest#testCollectSolarEnergy
        // free energy in branch parts = 16.212
        // collected energy from parts = 3 * 16.212 = 48.636
        // energy = 48.636 * 0 + 10 = 10
        // free energy = 48.636 - 0 + 3 = 51.636

        assertEquals(10f, branch.getEnergy().floatValue(), 0.00001f);
        assertEquals(51.636f, branch.collectFreeEnergy().floatValue(), 0.00001f);
        // can not collect the free energy more than once
        assertEquals(new BigDecimal(0), branch.collectFreeEnergy());
    }

    public void testGetState() {
        assertEquals(branchState, branch.getState());
        assertSame(branchState, branch.getState());
        BigDecimal energy = branch.getState().getEnergy();
        branch.collectSolarEnergy();
        assertEquals(branchState, branch.getState());
        assertSame(branchState, branch.getState());
        // the energy should have change in the state
        assertFalse(energy.equals(branch.getState().getEnergy()));
    }

}
