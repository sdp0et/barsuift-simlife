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

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.AppearanceFactory;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.TreeBranchPart;
import barsuift.simLife.tree.TreeLeaf;

public class BasicTreeBranchPart3D implements TreeBranchPart3D {

    private final Point3d endPoint;

    private final Group group;

    private TreeBranchPart branchPart;

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
        this.branchPart = branchPart;
        this.endPoint = state.getEndPoint().toPointValue();
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
        branchLine.setCoordinate(0, new Point3d(0, 0, 0));
        branchLine.setCoordinate(1, endPoint);
        return branchLine;
    }

    public void addLeaf(TreeLeaf3D leaf) {
        BranchGroup leafBranchGroup = new BranchGroup();
        leafBranchGroup.setCapability(BranchGroup.ALLOW_DETACH);
        leafBranchGroup.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TreeLeaf3DState leaf3DState = leaf.getState();
        Transform3D translation = TransformerHelper.getTranslationTransform3D(new Vector3d(leaf3DState
                .getLeafAttachPoint().toPointValue()));
        Transform3D rotation = TransformerHelper.getRotationTransform3D(leaf3DState.getRotation(), Axis.Y);
        translation.mul(rotation);
        transformGroup.setTransform(translation);
        transformGroup.addChild(leaf.getBranchGroup());
        leafBranchGroup.addChild(transformGroup);
        group.addChild(leafBranchGroup);
    }

    public Point3d getEndPoint() {
        return endPoint;
    }

    @Override
    public TreeBranchPart3DState getState() {
        return new TreeBranchPart3DState(new Point3dState(endPoint));
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public List<TreeLeaf3D> getLeaves() {
        List<TreeLeaf3D> result = new ArrayList<TreeLeaf3D>();
        List<TreeLeaf> leaves = branchPart.getLeaves();
        for (TreeLeaf leaf : leaves) {
            result.add(leaf.getTreeLeaf3D());
        }
        return result;
    }

}
