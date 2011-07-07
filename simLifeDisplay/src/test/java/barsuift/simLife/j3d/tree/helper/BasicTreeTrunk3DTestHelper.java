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

import org.fest.assertions.Delta;

import barsuift.simLife.j3d.tree.BasicTreeTrunk3D;

import com.sun.j3d.utils.geometry.Cylinder;

import static barsuift.simLife.j3d.assertions.Point3dAssert.assertThat;

// TODO convert to FEST assertion
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
        Delta bigDelta = Delta.delta(0.02);
        Delta smallDelta = Delta.delta(0.0001);
        assertThat(lowerBottom).isEqualTo(expectedLowerBottom, bigDelta, smallDelta, bigDelta);
        assertThat(movedLowerBottom).isEqualTo(expectedMovedLowerBottom, bigDelta, smallDelta, bigDelta);
        assertThat(upperBottom).isEqualTo(expectedUpperBottom, bigDelta, smallDelta, bigDelta);
        assertThat(movedUpperBottom).isEqualTo(expectedMovedUpperBottom, bigDelta, smallDelta, bigDelta);

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
        assertThat(lowerTop).isEqualTo(expectedLowerTop, bigDelta, smallDelta, bigDelta);
        assertThat(movedLowerTop).isEqualTo(expectedMovedLowerTop, bigDelta, smallDelta, bigDelta);
        assertThat(upperTop).isEqualTo(expectedUpperTop, bigDelta, smallDelta, bigDelta);
        assertThat(movedUpperTop).isEqualTo(expectedMovedUpperTop, bigDelta, smallDelta, bigDelta);
    }

}
