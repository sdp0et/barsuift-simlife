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
import javax.media.j3d.Group;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.TreeBranch;
import barsuift.simLife.tree.TreeBranchPart;

public class BasicTreeBranch3D implements TreeBranch3D {

    private final TreeBranch3DState state;

    private final TreeBranch treeBranch;

    private final Vector3f translationVector;

    private final Group group;

    /**
     * Creates a 3D tree branch, with data from the model one, and going towards to given end point. The end point is
     * indicative, and the actual branch end point may differ somehow from the given one, as the branch is created with
     * some randomization.
     * 
     * @param universe3D the 3D universe
     * @param endPoint indicative end point for the branch
     * @param treeBranch tree branch data
     */
    public BasicTreeBranch3D(Universe3D universe3D, TreeBranch3DState state, TreeBranch treeBranch) {
        if (universe3D == null) {
            throw new IllegalArgumentException("Null universe 3D");
        }
        if (state == null) {
            throw new IllegalArgumentException("Null tree branch 3D state");
        }
        if (treeBranch == null) {
            throw new IllegalArgumentException("Null tree branch");
        }
        this.state = state;
        this.translationVector = state.getTranslationVector().toVectorValue();
        this.treeBranch = treeBranch;
        BranchGroup parts = createParts();
        this.group = new Group();
        group.addChild(parts);
    }

    private BranchGroup createParts() {
        BranchGroup branchGroup = new BranchGroup();
        Point3f currentPartStartPoint = new Point3f(0, 0, 0);
        TransformGroup previousTransformGroup = new TransformGroup();
        branchGroup.addChild(previousTransformGroup);
        for (TreeBranchPart branchPart : treeBranch.getParts()) {
            TreeBranchPart3D branchPart3D = branchPart.getBranchPart3D();
            Group branchPartGroup = branchPart3D.getGroup();
            BranchGroup currentBranchGroup = new BranchGroup();
            Vector3f translationVector = new Vector3f(currentPartStartPoint);
            TransformGroup currentTransformGroup = TransformerHelper.getTranslationTransformGroup(translationVector);
            currentBranchGroup.addChild(currentTransformGroup);
            currentTransformGroup.addChild(branchPartGroup);
            previousTransformGroup.addChild(currentBranchGroup);
            // set pointer for next round
            currentPartStartPoint = branchPart3D.getEndPoint();
            previousTransformGroup = currentTransformGroup;
        }
        return branchGroup;
    }

    @Override
    public Point3f getEndPoint() {
        List<TreeBranchPart> parts = treeBranch.getParts();
        return parts.get(parts.size() - 1).getBranchPart3D().getEndPoint();
    }

    public Vector3f getTranslationVector() {
        return translationVector;
    }

    @Override
    public TreeBranch3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setTranslationVector(new Tuple3fState(translationVector));
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public List<TreeBranchPart3D> getBranchParts() {
        List<TreeBranchPart3D> result = new ArrayList<TreeBranchPart3D>();
        List<TreeBranchPart> parts = treeBranch.getParts();
        for (TreeBranchPart treeBranchPart : parts) {
            result.add(treeBranchPart.getBranchPart3D());
        }
        return result;
    }

}
