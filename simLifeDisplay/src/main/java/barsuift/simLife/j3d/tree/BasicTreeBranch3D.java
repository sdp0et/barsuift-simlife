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
import java.util.Collection;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import barsuift.simLife.j3d.AppearanceFactory;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.tree.TreeBranch;
import barsuift.simLife.tree.TreeLeaf;

public class BasicTreeBranch3D implements TreeBranch3D {

    private final TreeBranch3DState state;

    private final TreeBranch treeBranch;

    private final Vector3f translationVector;

    private final Point3f endPoint;

    private final Group group;

    /**
     * Creates a 3D tree branch, with data from the model one, and given state.
     * 
     * @param universe3D the 3D universe
     * @param state the branch 3D state
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
        this.endPoint = state.getEndPoint().toPointValue();
        this.treeBranch = treeBranch;
        this.group = new Group();
        group.setCapability(Group.ALLOW_CHILDREN_WRITE);
        group.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        createFullTreeBranch();
    }

    private void createFullTreeBranch() {
        addBranchShape();
        for (TreeLeaf treeLeaf : treeBranch.getLeaves()) {
            addLeaf(treeLeaf.getTreeLeaf3D());
        }
    }

    private void addBranchShape() {
        Shape3D branchShape = new Shape3D();
        LineArray branchLine = createBranchLine();
        Appearance branchAppearance = new Appearance();
        AppearanceFactory.setColorWithColoringAttributes(branchAppearance, ColorConstants.brown);
        branchShape.setGeometry(branchLine);
        branchShape.setAppearance(branchAppearance);
        group.addChild(branchShape);
    }

    private LineArray createBranchLine() {
        LineArray branchLine = new LineArray(2, GeometryArray.COORDINATES);
        branchLine.setCoordinate(0, new Point3f(0, 0, 0));
        branchLine.setCoordinate(1, endPoint);
        return branchLine;
    }

    public void addLeaf(TreeLeaf3D leaf) {
        group.addChild(leaf.getBranchGroup());
    }

    @Override
    public Point3f getEndPoint() {
        return endPoint;
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
        state.setEndPoint(new Tuple3fState(endPoint));
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public List<TreeLeaf3D> getLeaves() {
        List<TreeLeaf3D> result = new ArrayList<TreeLeaf3D>();
        Collection<TreeLeaf> leaves = treeBranch.getLeaves();
        for (TreeLeaf leaf : leaves) {
            result.add(leaf.getTreeLeaf3D());
        }
        return result;
    }

}
