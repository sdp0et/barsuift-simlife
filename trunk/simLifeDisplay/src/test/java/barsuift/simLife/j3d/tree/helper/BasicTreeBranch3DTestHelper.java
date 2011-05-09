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
package barsuift.simLife.j3d.tree.helper;

import java.util.Enumeration;

import javax.media.j3d.BoundingBox;
import javax.media.j3d.SceneGraphObject;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import junit.framework.Assert;
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.tree.BasicTreeBranch3D;

import com.sun.j3d.utils.geometry.Cylinder;

public final class BasicTreeBranch3DTestHelper extends Assert {

    private BasicTreeBranch3DTestHelper() {
        // private constructor to enforce static access
    }

    @SuppressWarnings("rawtypes")
    public static void checkTreeBranch3D(BasicTreeBranch3D branch3D, float expectedLength, float expectedRadius,
            Point3d expectedLowerBottom, Point3d expectedUpperBottom, Point3d expectedMovedLowerBottom,
            Point3d expectedMovedUpperBottom, Point3d expectedLowerTop, Point3d expectedUpperTop,
            Point3d expectedMovedLowerTop, Point3d expectedMovedUpperTop) {

        Cylinder branchCylinder = branch3D.getBranchCylinder();
        assertEquals(expectedLength, branchCylinder.getHeight());
        assertEquals(expectedRadius, branchCylinder.getRadius());

        TransformGroup branchTransformGroup = (TransformGroup) branch3D.getBranchGroup().getChild(0);
        TransformGroup cylinderTransformGroup = (TransformGroup) branchTransformGroup.getChild(0);
        Enumeration transformChilds = cylinderTransformGroup.getAllChildren();
        // check at least one element
        assertTrue(transformChilds.hasMoreElements());
        SceneGraphObject child = (SceneGraphObject) transformChilds.nextElement();
        assertEquals(branchCylinder, child);
        // check only one element
        assertFalse(transformChilds.hasMoreElements());

        // get transform object
        Transform3D transform3D = new Transform3D();
        cylinderTransformGroup.getTransform(transform3D);

        // check bottom of branch
        Shape3D bottom = branchCylinder.getShape(Cylinder.BOTTOM);
        BoundingBox bottomBounds = (BoundingBox) bottom.getBounds();
        Point3d lowerBottom = new Point3d();
        bottomBounds.getLower(lowerBottom);
        Point3d movedLowerBottom = new Point3d();
        transform3D.transform(lowerBottom, movedLowerBottom);
        Point3d upperBottom = new Point3d();
        bottomBounds.getUpper(upperBottom);
        Point3d movedUpperBottom = new Point3d();
        transform3D.transform(upperBottom, movedUpperBottom);
        PointTestHelper.assertPointEquals(expectedLowerBottom, lowerBottom, 0.02f, 0.0001f, 0.02f);
        PointTestHelper.assertPointEquals(expectedMovedLowerBottom, movedLowerBottom, 0.02f, 0.0001f, 0.02f);
        PointTestHelper.assertPointEquals(expectedUpperBottom, upperBottom, 0.02f, 0.0001f, 0.02f);
        PointTestHelper.assertPointEquals(expectedMovedUpperBottom, movedUpperBottom, 0.02f, 0.0001f, 0.02f);

        // check top of branch
        Shape3D top = branchCylinder.getShape(Cylinder.TOP);
        BoundingBox topBounds = (BoundingBox) top.getBounds();
        Point3d lowerTop = new Point3d();
        topBounds.getLower(lowerTop);
        Point3d movedLowerTop = new Point3d();
        transform3D.transform(lowerTop, movedLowerTop);
        Point3d upperTop = new Point3d();
        topBounds.getUpper(upperTop);
        Point3d movedUpperTop = new Point3d();
        transform3D.transform(upperTop, movedUpperTop);
        PointTestHelper.assertPointEquals(expectedLowerTop, lowerTop, 0.02, 0.0001, 0.02);
        PointTestHelper.assertPointEquals(expectedMovedLowerTop, movedLowerTop, 0.02, 0.0001, 0.02);
        PointTestHelper.assertPointEquals(expectedUpperTop, upperTop, 0.02, 0.0001, 0.02);
        PointTestHelper.assertPointEquals(expectedMovedUpperTop, movedUpperTop, 0.02, 0.0001, 0.02);
    }

}
