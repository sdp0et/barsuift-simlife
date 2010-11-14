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

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import junit.framework.TestCase;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.Tuple3dState;
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.util.TransformerHelper;


public class TreeLeaf3DStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomTreeLeaf3DState() {
        TreeLeaf3DStateFactory factory = new TreeLeaf3DStateFactory();
        Point3d leafAttachPoint = new Point3d(0.32, 0.33, 0.34);
        double rotation = Randomizer.randomRotation();
        Transform3D transform = TransformerHelper.getTranslationTransform3D(new Vector3d(leafAttachPoint));
        Transform3D rotationT3D = TransformerHelper.getRotationTransform3D(rotation, Axis.Y);
        transform.mul(rotationT3D);

        TreeLeaf3DState leaf3DState = factory.createRandomTreeLeaf3DState(transform);

        Transform3D actualTransform = leaf3DState.getTransform().toTransform3D();
        Tuple3dState actualInitialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Tuple3dState actualInitialEndPoint2 = leaf3DState.getInitialEndPoint2();
        Tuple3dState actualEndPoint1 = leaf3DState.getEndPoint1();
        Tuple3dState actualEndPoint2 = leaf3DState.getEndPoint2();

        // test transform
        Vector3d actualTranslation = new Vector3d();
        actualTransform.get(actualTranslation);
        double actualRotation = TransformerHelper.getRotationFromTransform(actualTransform, Axis.Y);
        assertEquals(leafAttachPoint, new Point3d(actualTranslation));
        assertEquals(rotation, actualRotation, 0.000001);

        // test initial point 2 position
        assertTrue("Wrong X position for end point 1.", actualInitialEndPoint1.getX() > 0 - 0.02 - 0.01);
        assertTrue("Wrong X position for end point 1.", actualInitialEndPoint1.getX() < 0 - 0.02 + 0.01);
        assertTrue("Wrong Y position for end point 1.", actualInitialEndPoint1.getY() > 0 - 0.04 - 0.01);
        assertTrue("Wrong Y position for end point 1.", actualInitialEndPoint1.getY() < 0 - 0.04 + 0.01);
        assertTrue("Wrong Z position for end point 1.", actualInitialEndPoint1.getZ() > 0 - 0.01);
        assertTrue("Wrong Z position for end point 1.", actualInitialEndPoint1.getZ() < 0 + 0.01);
        // test initial point 3 position
        assertTrue("Wrong X position for end point 2.", actualInitialEndPoint2.getX() > 0 + 0.02 - 0.01);
        assertTrue("Wrong X position for end point 2.", actualInitialEndPoint2.getX() < 0 + 0.02 + 0.01);
        assertTrue("Wrong Y position for end point 2.", actualInitialEndPoint2.getY() > 0 - 0.04 - 0.01);
        assertTrue("Wrong Y position for end point 2.", actualInitialEndPoint2.getY() < 0 - 0.04 + 0.01);
        assertTrue("Wrong Z position for end point 2.", actualInitialEndPoint2.getZ() > 0 - 0.01);
        assertTrue("Wrong Z position for end point 2.", actualInitialEndPoint2.getZ() < 0 + 0.01);

        // test point 2 position
        assertTrue("Wrong X position for end point 1.", actualEndPoint1.getX() > 0 - 0.2 - 0.1);
        assertTrue("Wrong X position for end point 1.", actualEndPoint1.getX() < 0 - 0.2 + 0.1);
        assertTrue("Wrong Y position for end point 1.", actualEndPoint1.getY() > 0 - 0.4 - 0.1);
        assertTrue("Wrong Y position for end point 1.", actualEndPoint1.getY() < 0 - 0.4 + 0.1);
        assertTrue("Wrong Z position for end point 1.", actualEndPoint1.getZ() > 0 - 0.1);
        assertTrue("Wrong Z position for end point 1.", actualEndPoint1.getZ() < 0 + 0.1);
        // test point 3 position
        assertTrue("Wrong X position for end point 2.", actualEndPoint2.getX() > 0 + 0.2 - 0.1);
        assertTrue("Wrong X position for end point 2.", actualEndPoint2.getX() < 0 + 0.2 + 0.1);
        assertTrue("Wrong Y position for end point 2.", actualEndPoint2.getY() > 0 - 0.4 - 0.1);
        assertTrue("Wrong Y position for end point 2.", actualEndPoint2.getY() < 0 - 0.4 + 0.1);
        assertTrue("Wrong Z position for end point 2.", actualEndPoint2.getZ() > 0 - 0.1);
        assertTrue("Wrong Z position for end point 2.", actualEndPoint2.getZ() < 0 + 0.1);
    }

    public void testCreateNewTreeLeaf3DState() {
        TreeLeaf3DStateFactory factory = new TreeLeaf3DStateFactory();
        Point3d leafAttachPoint = new Point3d(0.32, 0.33, 0.34);
        double rotation = Randomizer.randomRotation();
        Transform3D transform = TransformerHelper.getTranslationTransform3D(new Vector3d(leafAttachPoint));
        Transform3D rotationT3D = TransformerHelper.getRotationTransform3D(rotation, Axis.Y);
        transform.mul(rotationT3D);

        TreeLeaf3DState leaf3DState = factory.createNewTreeLeaf3DState(transform);

        Transform3D actualTransform = leaf3DState.getTransform().toTransform3D();
        Tuple3dState actualInitialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Tuple3dState actualInitialEndPoint2 = leaf3DState.getInitialEndPoint2();
        Tuple3dState actualEndPoint1 = leaf3DState.getEndPoint1();
        Tuple3dState actualEndPoint2 = leaf3DState.getEndPoint2();

        // test transform
        Vector3d actualTranslation = new Vector3d();
        actualTransform.get(actualTranslation);
        double actualRotation = TransformerHelper.getRotationFromTransform(actualTransform, Axis.Y);
        assertEquals(leafAttachPoint, new Point3d(actualTranslation));
        assertEquals(rotation, actualRotation, 0.000001);

        // test initial point 2 position
        assertTrue("Wrong X position for end point 1.", actualInitialEndPoint1.getX() > 0 - 0.02 - 0.01);
        assertTrue("Wrong X position for end point 1.", actualInitialEndPoint1.getX() < 0 - 0.02 + 0.01);
        assertTrue("Wrong Y position for end point 1.", actualInitialEndPoint1.getY() > 0 - 0.04 - 0.01);
        assertTrue("Wrong Y position for end point 1.", actualInitialEndPoint1.getY() < 0 - 0.04 + 0.01);
        assertTrue("Wrong Z position for end point 1.", actualInitialEndPoint1.getZ() > 0 - 0.01);
        assertTrue("Wrong Z position for end point 1.", actualInitialEndPoint1.getZ() < 0 + 0.01);
        // test initial point 3 position
        assertTrue("Wrong X position for end point 2.", actualInitialEndPoint2.getX() > 0 + 0.02 - 0.01);
        assertTrue("Wrong X position for end point 2.", actualInitialEndPoint2.getX() < 0 + 0.02 + 0.01);
        assertTrue("Wrong Y position for end point 2.", actualInitialEndPoint2.getY() > 0 - 0.04 - 0.01);
        assertTrue("Wrong Y position for end point 2.", actualInitialEndPoint2.getY() < 0 - 0.04 + 0.01);
        assertTrue("Wrong Z position for end point 2.", actualInitialEndPoint2.getZ() > 0 - 0.01);
        assertTrue("Wrong Z position for end point 2.", actualInitialEndPoint2.getZ() < 0 + 0.01);

        // test point 2 position
        PointTestHelper.assertPointEquals(actualInitialEndPoint1.toPointValue(), actualEndPoint1.toPointValue());
        // test point 3 position
        PointTestHelper.assertPointEquals(actualInitialEndPoint2.toPointValue(), actualEndPoint2.toPointValue());
    }

}
