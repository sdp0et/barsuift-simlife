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


public class TreeBranchStateFactoryTest extends TestCase {

    private float treeHeight;

    private TreeBranchState branchState;

    private TreeBranchStateFactory factory;

    protected void setUp() throws Exception {
        super.setUp();
        treeHeight = 6;
        factory = new TreeBranchStateFactory();
        branchState = factory.createRandomBranchState(treeHeight);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        treeHeight = 0;
        factory = null;
        branchState = null;
    }

    public void testCreateBranchState() {
        assertNotNull(branchState.getBranch3DState());
        List<TreeLeafState> leavesStates = branchState.getLeavesStates();
        int nbLeaves = leavesStates.size();
        assertTrue(nbLeaves >= 6);
        assertTrue(nbLeaves <= 12);
        assertEquals(branchState.getBranch3DState().getLength() / 40, branchState.getBranch3DState().getRadius(), 0.0001);
        assertTrue(branchState.getCreationMillis() >= 0);
        assertTrue(branchState.getCreationMillis() <= 100000);
        assertTrue(branchState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
        assertTrue(branchState.getFreeEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchState.getFreeEnergy().compareTo(new BigDecimal(50)) <= 0);
    }

    public void testComputeLength() {
        float treeHeight = 12;
        float branchLength = factory.computeLength(treeHeight);
        assertTrue(branchLength >= 3);
        assertTrue(branchLength <= 6);
        treeHeight = 2.4f;
        branchLength = factory.computeLength(treeHeight);
        assertTrue(branchLength >= 0.6);
        assertTrue(branchLength <= 1.2);
    }

}
