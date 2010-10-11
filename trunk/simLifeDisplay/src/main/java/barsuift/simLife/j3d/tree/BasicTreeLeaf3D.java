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

import java.math.BigDecimal;
import java.util.Observable;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Node;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import barsuift.simLife.j3d.AppearanceFactory;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.Tuple3dState;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.j3d.util.AreaHelper;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.j3d.util.NormalHelper;
import barsuift.simLife.j3d.util.PointHelper;
import barsuift.simLife.j3d.util.ProjectionHelper;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.LeafUpdateMask;
import barsuift.simLife.tree.TreeLeaf;

public class BasicTreeLeaf3D implements TreeLeaf3D {

    private static final Color3f SPECULAR_COLOR = new Color3f(0.05f, 0.05f, 0.05f);

    private static final Color3f DIFFUSE_COLOR = new Color3f(0.15f, 0.15f, 0.15f);

    private static final int MAX_INCREASE_FACTOR = 10;



    private final TreeLeaf3DState state;

    /**
     * Leaf attach point, relative to the branch part
     */
    private Point3d leafAttachPoint;

    /**
     * End point 1 at the creation of the leaf (its birth end point 1). The point is relative to the attach point.
     */
    private final Point3d initialEndPoint1;

    /**
     * End point 2 at the creation of the leaf (its birth end point 2). The point is relative to the attach point.
     */
    private final Point3d initialEndPoint2;

    /**
     * Current end point 1. The point is relative to the attach point.
     */
    private Point3d endPoint1;

    /**
     * Current end point 2. The point is relative to the attach point.
     */
    private Point3d endPoint2;

    /**
     * Leaf rotation (in radian)
     */
    private double rotation;



    private TriangleArray leafGeometry;

    private final Shape3D leafShape3D;

    private final Universe3D universe3D;

    private boolean maxSizeReached;

    private final Point3d maxEndPoint1;

    private final Point3d maxEndPoint2;

    private double area;

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
        this.state = state;
        this.leafAttachPoint = state.getLeafAttachPoint().toPointValue();
        this.initialEndPoint1 = state.getInitialEndPoint1().toPointValue();
        this.initialEndPoint2 = state.getInitialEndPoint2().toPointValue();
        this.endPoint1 = state.getEndPoint1().toPointValue();
        this.endPoint2 = state.getEndPoint2().toPointValue();
        this.rotation = state.getRotation();


        maxEndPoint1 = computeMaxEndPoint(initialEndPoint1);
        maxEndPoint2 = computeMaxEndPoint(initialEndPoint2);
        leaf.addObserver(this);
        leafShape3D = new Shape3D();
        createLeafGeometry();
        setColor(leaf.getEfficiency());
        leafShape3D.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        maxSizeReached = false;
    }

    private void setColor(BigDecimal efficiency) {
        Color3f leafColor = new Color3f(ColorConstants.brownYellow);
        leafColor.interpolate(ColorConstants.green, efficiency.floatValue());

        Appearance appearance = new Appearance();
        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_NONE);
        AppearanceFactory.setColorWithMaterial(appearance, leafColor, DIFFUSE_COLOR, SPECULAR_COLOR);

        leafShape3D.setAppearance(appearance);
    }

    private void createLeafGeometry() {
        leafGeometry = new TriangleArray(3, GeometryArray.COORDINATES | GeometryArray.NORMALS);
        leafGeometry.setCapability(TriangleArray.ALLOW_COORDINATE_WRITE);
        leafGeometry.setCoordinate(0, new Point3d(0, 0, 0));
        resetGeometryPoints();
        leafShape3D.setGeometry(leafGeometry);

        Vector3f polygonNormal = NormalHelper.computeNormal(new Point3d(0, 0, 0), endPoint1, endPoint2);
        leafGeometry.setNormal(0, polygonNormal);
        leafGeometry.setNormal(1, polygonNormal);
        leafGeometry.setNormal(2, polygonNormal);
    }

    public double getArea() {
        return area;
    }

    @Override
    public boolean isMaxSizeReached() {
        if (maxSizeReached) {
            // optimization : if the max size is reached, no need to compute it again
            return true;
        }
        if (!PointHelper.areAlmostEquals(endPoint1, maxEndPoint1)) {
            return false;
        }
        if (!PointHelper.areAlmostEquals(endPoint2, maxEndPoint2)) {
            return false;
        }
        maxSizeReached = true;
        return true;
    }

    private Point3d computeMaxEndPoint(Point3d initialEndPoint) {
        double maxX = MAX_INCREASE_FACTOR * initialEndPoint.getX();
        double maxY = MAX_INCREASE_FACTOR * initialEndPoint.getY();
        double maxZ = MAX_INCREASE_FACTOR * initialEndPoint.getZ();
        return new Point3d(maxX, maxY, maxZ);
    }

    @Override
    public void increaseSize() {
        if (isMaxSizeReached()) {
            return;
        }
        endPoint1.x += initialEndPoint1.x;
        endPoint1.y += initialEndPoint1.y;
        endPoint1.z += initialEndPoint1.z;

        endPoint2.x += initialEndPoint2.x;
        endPoint2.y += initialEndPoint2.y;
        endPoint2.z += initialEndPoint2.z;

        resetGeometryPoints();
    }

    @Override
    public Point3d getAttachPoint() {
        return leafAttachPoint;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    /**
     * Set the startPoint, endPoint1 and endPoint2 points to the leafGeometry
     */
    private void resetGeometryPoints() {
        leafGeometry.setCoordinates(1, new Point3d[] { endPoint1, endPoint2 });
        this.area = AreaHelper.computeArea(leafGeometry);
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (LeafUpdateMask.isFieldSet((Integer) arg, LeafUpdateMask.EFFICIENCY_MASK)) {
            TreeLeaf leaf = (TreeLeaf) observable;
            setColor(leaf.getEfficiency());
        }
        if (LeafUpdateMask.isFieldSet((Integer) arg, LeafUpdateMask.FALL_MASK)) {
            fall();
        }
    }

    private void fall() {
        Transform3D globalTransform = new Transform3D();
        leafShape3D.getLocalToVworld(globalTransform);
        Vector3d translationVector = new Vector3d();
        globalTransform.get(translationVector);
        // project the leaf attach point to the ground
        leafAttachPoint = ProjectionHelper.getProjectionPoint(new Point3d(translationVector));
        // the rotation is now global and not related to the tree or the branch
        rotation = TransformerHelper.getRotationFromTransform(globalTransform, Axis.Y);
        // send the leaf Branch group, which contains a TG for the translation along the branch part
        universe3D.getPhysics().getGravity().fall((BranchGroup) leafShape3D.getParent().getParent());
    }

    @Override
    public TreeLeaf3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setLeafAttachPoint(new Tuple3dState(leafAttachPoint));
        state.setInitialEndPoint1(new Tuple3dState(initialEndPoint1));
        state.setInitialEndPoint2(new Tuple3dState(initialEndPoint2));
        state.setEndPoint1(new Tuple3dState(endPoint1));
        state.setEndPoint2(new Tuple3dState(endPoint2));
        state.setRotation(rotation);
    }

    @Override
    public Node getNode() {
        return leafShape3D;
    }

}
