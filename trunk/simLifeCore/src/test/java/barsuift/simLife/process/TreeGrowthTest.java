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
package barsuift.simLife.process;

import junit.framework.TestCase;
import barsuift.simLife.UtilDataCreatorForTests;
import barsuift.simLife.tree.MockTree;
import barsuift.simLife.tree.MockTreeBranch;
import barsuift.simLife.tree.MockTreeBranchPart;
import barsuift.simLife.tree.MockTreeLeaf;


public class TreeGrowthTest extends TestCase {

    public void testExecuteCyclicStep() {
        MockTreeLeaf mockLeaf = new MockTreeLeaf();

        MockTreeBranchPart mockBranchPart = new MockTreeBranchPart();
        mockBranchPart.addLeaf(mockLeaf);

        MockTreeBranch mockBranch = new MockTreeBranch();
        mockBranch.addPart(mockBranchPart);

        MockTree mockTree = new MockTree();
        mockTree.addBranch(mockBranch);

        TreeGrowth treeGrowth = new TreeGrowth(UtilDataCreatorForTests.createSpecificCyclicTaskState(), mockTree);
        treeGrowth.executeCyclicStep();

        assertEquals(1, mockLeaf.getNbImproveEfficiencyCalled());

        assertEquals(1, mockBranchPart.getNbGrowCalled());
    }

}
