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

import java.util.Observable;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import barsuift.simLife.Percent;
import barsuift.simLife.j3d.AppearanceFactory;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.AreaHelper;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.j3d.util.NormalHelper;
import barsuift.simLife.j3d.util.PointHelper;
import barsuift.simLife.j3d.util.ProjectionHelper;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.LeafUpdateCode;
import barsuift.simLife.tree.TreeLeaf;

public class BasicTreeLeaf3D implements TreeLeaf3D {

    private static final int MAX_INCREASE_FACTOR = 10;

    private TriangleArray leafGeometry;

    private Shape3D leafShape3D;

    private TreeLeaf3DState state;

    private Universe3D universe3D;

    private final BranchGroup branchGroup;

    public BasicTreeLeaf3D(Universe3D universe3D, TreeLeaf3DState state, TreeLeaf leaf) {
        if (universe3D == null) {
            throw new IllegalArgumentException("Null universe 3D");
        }
        if (leaf == null) {
            throw new IllegalArgumentException("Null tree leaf");
        }
        if (state == null) {
            throw new IllegalArgumentException("Null tree leaf 3D state");
        }
        this.universe3D = universe3D;
        this.state = new TreeLeaf3DState(state);
        leaf.addObserver(this);
        leafShape3D = new Shape3D();
        this.branchGroup = new BranchGroup();
        branchGroup.addChild(leafShape3D);
        createLeafGeometry();
        setColor(leaf.getEfficiency());
        leafShape3D.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        branchGroup.setCapability(BranchGroup.ALLOW_DETACH);
    }

    private void setColor(Percent efficiency) {
        Color3f leafColor = new Color3f(ColorConstants.brownYellow);
        leafColor.interpolate(ColorConstants.green, efficiency.getValue().floatValue());

        Appearance appearance = new Appearance();
        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_NONE);
        AppearanceFactory.setColorWithMaterial(appearance, leafColor, new Color3f(0.15f, 0.15f, 0.15f), new Color3f(
                0.05f, 0.05f, 0.05f));

        leafShape3D.setAppearance(appearance);
    }

    private void createLeafGeometry() {
        leafGeometry = new TriangleArray(3, GeometryArray.COORDINATES | GeometryArray.NORMALS);
        leafGeometry.setCapability(TriangleArray.ALLOW_COORDINATE_WRITE);
        resetGeometryPoints();
        leafShape3D.setGeometry(leafGeometry);

        Vector3f polygonNormal = NormalHelper.computeNormal(new Point3d(0, 0, 0), state.getEndPoint1().toPointValue(),
                state.getEndPoint2().toPointValue());
        leafGeometry.setNormal(0, polygonNormal);
        leafGeometry.setNormal(1, polygonNormal);
        leafGeometry.setNormal(2, polygonNormal);
    }

    public double getArea() {
        return AreaHelper.computeArea(leafGeometry);
    }

    @Override
    public boolean isMaxSizeReached() {
        Point3dState initialEndPoint1 = state.getInitialEndPoint1();
        double maxX1 = MAX_INCREASE_FACTOR * initialEndPoint1.getX();
        double maxY1 = MAX_INCREASE_FACTOR * initialEndPoint1.getY();
        double maxZ1 = MAX_INCREASE_FACTOR * initialEndPoint1.getZ();
        Point3d actualEndPoint1 = state.getEndPoint1().toPointValue();
        boolean areAlmostEquals1 = PointHelper.areAlmostEquals(actualEndPoint1, new Point3d(maxX1, maxY1, maxZ1));
        if (!areAlmostEquals1) {
            return false;
        }
        Point3dState initialEndPoint2 = state.getInitialEndPoint2();
        double maxX2 = MAX_INCREASE_FACTOR * initialEndPoint2.getX();
        double maxY2 = MAX_INCREASE_FACTOR * initialEndPoint2.getY();
        double maxZ2 = MAX_INCREASE_FACTOR * initialEndPoint2.getZ();
        Point3d actualEndPoint2 = state.getEndPoint2().toPointValue();
        boolean areAlmostEquals2 = PointHelper.areAlmostEquals(actualEndPoint2, new Point3d(maxX2, maxY2, maxZ2));
        if (!areAlmostEquals2) {
            return false;
        }
        return true;
    }

    @Override
    public void increaseSize() {
        if (isMaxSizeReached()) {
            return;
        }
        state.getEndPoint1().setX(state.getEndPoint1().getX() + state.getInitialEndPoint1().getX());
        state.getEndPoint1().setY(state.getEndPoint1().getY() + state.getInitialEndPoint1().getY());
        state.getEndPoint1().setZ(state.getEndPoint1().getZ() + state.getInitialEndPoint1().getZ());

        state.getEndPoint2().setX(state.getEndPoint2().getX() + state.getInitialEndPoint2().getX());
        state.getEndPoint2().setY(state.getEndPoint2().getY() + state.getInitialEndPoint2().getY());
        state.getEndPoint2().setZ(state.getEndPoint2().getZ() + state.getInitialEndPoint2().getZ());

        resetGeometryPoints();
    }

    public Point3d getAttachPoint() {
        return state.getLeafAttachPoint().toPointValue();
    }

    /**
     * Set the startPoint, endPoint1 and endPoint2 points to the leafGeometry
     */
    private void resetGeometryPoints() {
        leafGeometry.setCoordinate(0, new Point3d(0, 0, 0));
        leafGeometry.setCoordinate(1, state.getEndPoint1().toPointValue());
        leafGeometry.setCoordinate(2, state.getEndPoint2().toPointValue());
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (arg == LeafUpdateCode.efficiency) {
            TreeLeaf leaf = (TreeLeaf) observable;
            setColor(leaf.getEfficiency());
        }
        if (arg == LeafUpdateCode.fall) {
            fall();
        }
    }

    private void fall() {
        Transform3D globalTransform = new Transform3D();
        branchGroup.getLocalToVworld(globalTransform);
        Vector3d translationVector = new Vector3d();
        globalTransform.get(translationVector);
        Point3d projectionPoint = ProjectionHelper.getProjectionPoint(new Point3d(translationVector));
        state.setLeafAttachPoint(new Point3dState(projectionPoint));
        double angle = TransformerHelper.getRotationFromTransform(globalTransform, Axis.Y);
        state.setRotation(angle);
        universe3D.getPhysics().getGravity().fall((BranchGroup) branchGroup.getParent().getParent());
    }

    @Override
    public TreeLeaf3DState getState() {
        return new TreeLeaf3DState(state);
    }

    @Override
    public BranchGroup getBranchGroup() {
        return branchGroup;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicTreeLeaf3D other = (BasicTreeLeaf3D) obj;
        if (state == null) {
            if (other.state != null)
                return false;
        } else
            if (!state.equals(other.state))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "BasicTreeLeaf3D [state=" + state + "]";
    }

}
