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
package barsuift.simLife.j3d.tree;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeBranch;

// TODO 999. ??? the trunk should be a special instance of a branch
public class BasicTree3D implements Tree3D {

    private Tree tree;

    private final BranchGroup branchGroup;

    private Point3d translationVector;

    public BasicTree3D(Universe3D universe3D, Tree3DState state, Tree tree) {
        super();
        if (universe3D == null) {
            throw new IllegalArgumentException("Null universe 3D");
        }
        if (state == null) {
            throw new IllegalArgumentException("Null tree 3D state");
        }
        if (tree == null) {
            throw new IllegalArgumentException("Null tree");
        }
        this.tree = tree;
        this.branchGroup = new BranchGroup();
        this.translationVector = state.getTranslationVector().toPointValue();
        createTrunkAndBranchesBG();
    }

    private void createTrunkAndBranchesBG() {
        branchGroup.addChild(tree.getTrunk().getTreeTrunkD().getGroup());

        List<TreeBranch> branches = tree.getBranches();
        for (TreeBranch branch : branches) {
            TreeBranch3D branch3D = branch.getBranch3D();
            BranchGroup branchBG = createBranch(branch3D);
            branchGroup.addChild(branchBG);
        }
    }

    private BranchGroup createBranch(TreeBranch3D branch3D) {
        Vector3d translationVector = new Vector3d(branch3D.getState().getTranslationVector().toPointValue());
        BranchGroup branchBG = new BranchGroup();
        TransformGroup transformGroup = TransformerHelper.getTranslationTransformGroup(translationVector);
        branchBG.addChild(transformGroup);

        transformGroup.addChild(branch3D.getGroup());
        return branchBG;
    }

    @Override
    public List<TreeBranch3D> getBranches() {
        List<TreeBranch3D> branches3D = new ArrayList<TreeBranch3D>();
        for (TreeBranch treeBranch : tree.getBranches()) {
            branches3D.add(treeBranch.getBranch3D());
        }
        return branches3D;
    }

    @Override
    public TreeTrunk3D getTrunk() {
        return tree.getTrunk().getTreeTrunkD();
    }

    @Override
    public Tree3DState getState() {
        return new Tree3DState(new Point3dState(translationVector));
    }

    @Override
    public BranchGroup getBranchGroup() {
        return branchGroup;
    }

}
