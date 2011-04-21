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
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeBranch;

// TODO 999. ??? the trunk should be a special instance of a branch
public class BasicTree3D implements Tree3D {

    private final Tree3DState state;

    private final Vector3f translationVector;


    private final Tree tree;

    private final BranchGroup branchGroup;

    private final TransformGroup tg;

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
        this.state = state;
        this.translationVector = state.getTranslationVector().toVectorValue();
        this.tree = tree;
        Transform3D translation = TransformerHelper.getTranslationTransform3D(translationVector);
        this.tg = new TransformGroup(translation);
        this.branchGroup = new BranchGroup();
        branchGroup.addChild(tg);
        createTrunkAndBranchesBG();
    }

    private void createTrunkAndBranchesBG() {
        tg.addChild(tree.getTrunk().getTreeTrunk3D().getGroup());

        List<TreeBranch> branches = tree.getBranches();
        for (TreeBranch branch : branches) {
            tg.addChild(branch.getBranch3D().getBranchGroup());
        }
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
        return tree.getTrunk().getTreeTrunk3D();
    }

    @Override
    public Tree3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setTranslationVector(new Tuple3fState(translationVector));
    }

    @Override
    public BranchGroup getBranchGroup() {
        return branchGroup;
    }

}
