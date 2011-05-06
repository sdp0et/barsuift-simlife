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
import barsuift.simLife.j3d.tree.Tree3DState;
import barsuift.simLife.j3d.tree.Tree3DStateFactory;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranchesOrganizer;
import barsuift.simLife.process.Aging;
import barsuift.simLife.process.ConditionalTaskState;
import barsuift.simLife.process.ConditionalTaskStateFactory;
import barsuift.simLife.process.Photosynthesis;
import barsuift.simLife.process.TreeGrowth;

public class TreeStateFactory {

    public static final int HEIGHT_RADIUS_RATIO = 16;

    public TreeState createRandomTreeState() {
        int creationMillis = Randomizer.randomBetween(0, 100) * 1000;
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        int nbBranches = Randomizer.randomBetween(20, 40);
        float height = Randomizer.randomBetween(3, 5);
        float radius = height / HEIGHT_RADIUS_RATIO;
        List<TreeBranchState> branchesStates = new ArrayList<TreeBranchState>(nbBranches);
        List<TreeBranch3DState> branches3DStates = new ArrayList<TreeBranch3DState>(nbBranches);
        TreeBranchStateFactory branchStateFactory = new TreeBranchStateFactory();
        for (int i = 0; i < nbBranches; i++) {
            TreeBranchState branchState = branchStateFactory.createRandomBranchState(height);
            branchesStates.add(branchState);
            branches3DStates.add(branchState.getBranch3DState());
        }
        TreeBranchesOrganizer branchesOrganizer = new TreeBranchesOrganizer();
        branchesOrganizer.organizeBranches(branches3DStates, height);

        ConditionalTaskStateFactory taskStateFactory = new ConditionalTaskStateFactory();
        ConditionalTaskState photosynthesis = taskStateFactory.createConditionalTaskState(Photosynthesis.class);
        ConditionalTaskState aging = taskStateFactory.createConditionalTaskState(Aging.class);
        ConditionalTaskState growth = taskStateFactory.createConditionalTaskState(TreeGrowth.class);
        TreeTrunkStateFactory trunkStateFactory = new TreeTrunkStateFactory();
        TreeTrunkState trunkState = trunkStateFactory.createRandomTreeTrunkState(radius, height);

        Tree3DStateFactory tree3DStateFactory = new Tree3DStateFactory();
        Tree3DState tree3dState = tree3DStateFactory.createRandomTree3DState();


        return new TreeState(creationMillis, energy, branchesStates, photosynthesis, aging, growth, trunkState, height,
                tree3dState);
    }

}
