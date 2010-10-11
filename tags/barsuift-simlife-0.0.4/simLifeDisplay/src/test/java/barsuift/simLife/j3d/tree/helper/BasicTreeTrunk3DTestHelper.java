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
import barsuift.simLife.j3d.tree.BasicTreeTrunk3D;

import com.sun.j3d.utils.geometry.Cylinder;

public final class BasicTreeTrunk3DTestHelper extends Assert {

    private BasicTreeTrunk3DTestHelper() {
        // private constructor to enforce static access
    }

    @SuppressWarnings("rawtypes")
    public static void checkTrunk3D(BasicTreeTrunk3D trunk3D, float expectedHeight, float expectedRadius,
            Point3d expectedLowerBottom, Point3d expectedUpperBottom, Point3d expectedMovedLowerBottom,
            Point3d expectedMovedUpperBottom, Point3d expectedLowerTop, Point3d expectedUpperTop,
            Point3d expectedMovedLowerTop, Point3d expectedMovedUpperTop) {

        Cylinder trunkCylinder = trunk3D.getTrunk();
        assertEquals(expectedHeight, trunkCylinder.getHeight());
        assertEquals(expectedRadius, trunkCylinder.getRadius());

        TransformGroup trunkTransformGroup = (TransformGroup) trunk3D.getGroup().getChild(0);
        Enumeration transformChilds = trunkTransformGroup.getAllChildren();
        // check at least one element
        assertTrue(transformChilds.hasMoreElements());
        SceneGraphObject child = (SceneGraphObject) transformChilds.nextElement();
        assertEquals(trunkCylinder, child);
        // check only one element
        assertFalse(transformChilds.hasMoreElements());

        // get transform object
        Transform3D transform3D = new Transform3D();
        trunkTransformGroup.getTransform(transform3D);

        // check bottom of trunk
        Shape3D bottom = trunkCylinder.getShape(Cylinder.BOTTOM);
        BoundingBox bottomBounds = (BoundingBox) bottom.getBounds();
        Point3d lowerBottom = new Point3d();
        bottomBounds.getLower(lowerBottom);
        Point3d movedLowerBottom = new Point3d();
        transform3D.transform(lowerBottom, movedLowerBottom);
        Point3d upperBottom = new Point3d();
        bottomBounds.getUpper(upperBottom);
        Point3d movedUpperBottom = new Point3d();
        transform3D.transform(upperBottom, movedUpperBottom);
        PointTestHelper.assertPointEquals(expectedLowerBottom, lowerBottom, 0.02, 0.0001, 0.02);
        PointTestHelper.assertPointEquals(expectedMovedLowerBottom, movedLowerBottom, 0.02, 0.0001, 0.02);
        PointTestHelper.assertPointEquals(expectedUpperBottom, upperBottom, 0.02, 0.0001, 0.02);
        PointTestHelper.assertPointEquals(expectedMovedUpperBottom, movedUpperBottom, 0.02, 0.0001, 0.02);

        // check top of trunk
        Shape3D top = trunkCylinder.getShape(Cylinder.TOP);
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
