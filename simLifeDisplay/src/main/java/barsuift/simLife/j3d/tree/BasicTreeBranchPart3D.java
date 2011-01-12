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

import barsuift.simLife.j3d.AppearanceFactory;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.tree.TreeBranchPart;
import barsuift.simLife.tree.TreeLeaf;

public class BasicTreeBranchPart3D implements TreeBranchPart3D {

    private final TreeBranchPart3DState state;

    private final Point3f endPoint;

    private final Group group;

    private final TreeBranchPart branchPart;

    public BasicTreeBranchPart3D(Universe3D universe3D, TreeBranchPart3DState state, TreeBranchPart branchPart) {
        if (universe3D == null) {
            throw new IllegalArgumentException("Null universe 3D");
        }
        if (state == null) {
            throw new IllegalArgumentException("Null tree branch part 3D state");
        }
        if (branchPart == null) {
            throw new IllegalArgumentException("Null tree branch part");
        }
        this.state = state;
        this.endPoint = state.getEndPoint().toPointValue();
        this.branchPart = branchPart;
        this.group = new Group();
        group.setCapability(Group.ALLOW_CHILDREN_WRITE);
        group.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        createFullTreeBranch(branchPart);
    }

    private void createFullTreeBranch(TreeBranchPart branchPart) {
        addBranchShape();
        for (TreeLeaf treeLeaf : branchPart.getLeaves()) {
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

    public Point3f getEndPoint() {
        return endPoint;
    }

    @Override
    public TreeBranchPart3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setEndPoint(new Tuple3fState(endPoint));
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public List<TreeLeaf3D> getLeaves() {
        List<TreeLeaf3D> result = new ArrayList<TreeLeaf3D>();
        Collection<TreeLeaf> leaves = branchPart.getLeaves();
        for (TreeLeaf leaf : leaves) {
            result.add(leaf.getTreeLeaf3D());
        }
        return result;
    }

}
