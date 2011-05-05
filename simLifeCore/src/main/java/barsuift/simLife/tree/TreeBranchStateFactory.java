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
import java.util.ArrayList;
import java.util.List;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DStateFactory;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeLeavesOrganizer;

public class TreeBranchStateFactory {

    /**
     * Ratio between the tree height and the branch length
     */
    public static final float TREE_HEIGHT_BRANCH_LENGTH_RATIO = 2f;

    /**
     * Ratio between the the branch length and the branch radius
     */
    public static final float BRANCH_LENGTH_RADIUS_RATIO = 40f;

    public TreeBranchState createRandomBranchState(float treeHeight) {
        float length = computeLength(treeHeight);
        float radius = length / BRANCH_LENGTH_RADIUS_RATIO;
        int creationMillis = Randomizer.randomBetween(0, 100) * 1000;
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>();
        List<TreeLeaf3DState> leaves3DStates = new ArrayList<TreeLeaf3DState>();
        TreeLeafStateFactory leafStateFactory = new TreeLeafStateFactory();
        int nbLeaves = Randomizer.randomBetween(6, 12);
        for (int index = 0; index < nbLeaves; index++) {
            TreeLeafState leafState = leafStateFactory.createRandomTreeLeafState();
            leavesStates.add(leafState);
            leaves3DStates.add(leafState.getLeaf3DState());
        }
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        leavesOrganizer.organizeLeaves(leaves3DStates, length);
        TreeBranch3DStateFactory branch3DStateFactory = new TreeBranch3DStateFactory();
        TreeBranch3DState branch3DState = branch3DStateFactory.createRandomTreeBranch3DState(length, radius);

        return new TreeBranchState(creationMillis, energy, freeEnergy, leavesStates, branch3DState);
    }

    protected float computeLength(float treeHeight) {
        float ratio = Randomizer.randomBetween(0.5f, 1);
        float maxBranchLength = treeHeight / TREE_HEIGHT_BRANCH_LENGTH_RATIO;
        return ratio * maxBranchLength;
    }

}
