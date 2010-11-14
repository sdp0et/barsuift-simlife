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

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.Group;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import junit.framework.TestCase;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.Tuple3dState;
import barsuift.simLife.j3d.helper.ColorTestHelper;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.helper.Structure3DHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.LeafUpdateMask;
import barsuift.simLife.tree.MockTreeLeaf;

public class BasicTreeLeaf3DTest extends TestCase {

    private MockTreeLeaf mockLeaf;

    private TreeLeaf3DState leaf3DState;

    private MockUniverse3D mockUniverse3D;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockLeaf = new MockTreeLeaf();
        leaf3DState = DisplayDataCreatorForTests.createRandomTreeLeaf3DState();
        mockUniverse3D = new MockUniverse3D();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        mockLeaf = null;
        leaf3DState = null;
        mockUniverse3D = null;
    }

    public void testConstructor() {
        try {
            new BasicTreeLeaf3D(mockUniverse3D, null, mockLeaf);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, null);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeLeaf3D(null, leaf3DState, mockLeaf);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertEquals(leaf3DState, leaf3D.getState());

        assertEquals(leaf3DState, leaf3D.getState());
        assertSame(leaf3DState, leaf3D.getState());
        assertEquals(0.4, leaf3D.getState().getEndPoint1().getX(), 0.00001);
        assertEquals(0.0, leaf3D.getState().getEndPoint1().getY(), 0.00001);
        assertEquals(0.0, leaf3D.getState().getEndPoint1().getZ(), 0.00001);
        leaf3D.increaseSize();
        assertEquals(leaf3DState, leaf3D.getState());
        assertSame(leaf3DState, leaf3D.getState());
        assertEquals(0.6, leaf3D.getState().getEndPoint1().getX(), 0.00001);
        assertEquals(0.2, leaf3D.getState().getEndPoint1().getY(), 0.00001);
        assertEquals(0.0, leaf3D.getState().getEndPoint1().getZ(), 0.00001);
    }

    public void testSubscribers() {
        mockLeaf.setEfficiency(PercentHelper.getDecimalValue(80));
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertEquals(1, mockLeaf.countSubscribers());
        // check the subscriber is the leaf3D
        mockLeaf.deleteSubscriber(leaf3D);
        assertEquals(0, mockLeaf.countSubscribers());
    }

    public void testAppearance() {
        mockLeaf.setEfficiency(PercentHelper.getDecimalValue(80));
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        CompilerHelper.compile(leaf3D.getBranchGroup());
        Shape3D leafShape3D = (Shape3D) ((TransformGroup) leaf3D.getBranchGroup().getChild(0)).getChild(0);

        Color3f expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.8f);
        ColorTestHelper.testColorFromMaterial(leafShape3D.getAppearance(), expectedColor, new Color3f(0.05f, 0.05f,
                0.05f), new Color3f(0.15f, 0.15f, 0.15f));

        // check the leaves are not culled (transparent) when seen from behind
        assertEquals(PolygonAttributes.CULL_NONE, leafShape3D.getAppearance().getPolygonAttributes().getCullFace());

        mockLeaf.setEfficiency(PercentHelper.getDecimalValue(75));
        leaf3D.update(mockLeaf, LeafUpdateMask.EFFICIENCY_MASK);

        // check that the color has changed as expected
        expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.75f);
        ColorTestHelper.testColorFromMaterial(leafShape3D.getAppearance(), expectedColor, new Color3f(0.05f, 0.05f,
                0.05f), new Color3f(0.15f, 0.15f, 0.15f));


        mockLeaf.setEfficiency(PercentHelper.getDecimalValue(60));
        // 65536 does not correspond to any valid update code
        leaf3D.update(mockLeaf, 65536);

        // check that the color has NOT changed as expected, because the update code is not the appropriate one
        expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.75f); // 75%, not 60%
        ColorTestHelper.testColorFromMaterial(leafShape3D.getAppearance(), expectedColor, new Color3f(0.05f, 0.05f,
                0.05f), new Color3f(0.15f, 0.15f, 0.15f));
    }

    public void testGeometry() {
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        BranchGroup bg = leaf3D.getBranchGroup();
        CompilerHelper.compile(bg);

        Structure3DHelper.assertExactlyOneTransformGroup(bg);
        TransformGroup transformGroup = (TransformGroup) bg.getChild(0);

        // test translation and rotation
        Transform3D transform3D = new Transform3D();
        transformGroup.getTransform(transform3D);
        assertEquals(leaf3DState.getTransform(), new Transform3DState(transform3D));

        // test one leaf found
        Structure3DHelper.assertExactlyOneShape3D(transformGroup);
        Shape3D leafShape3D = (Shape3D) transformGroup.getChild(0);
        assertNotNull(leafShape3D);

        // test position and geometry
        Geometry leafGeometry = leafShape3D.getGeometry();
        assertTrue(leafGeometry instanceof TriangleArray);
        TriangleArray leafTriangle = (TriangleArray) leafGeometry;
        assertEquals(3, leafTriangle.getVertexCount());

        Point3d actualStartPoint = new Point3d();
        Point3d actualEndPoint1 = new Point3d();
        Point3d actualEndPoint2 = new Point3d();
        leafTriangle.getCoordinate(0, actualStartPoint);
        leafTriangle.getCoordinate(1, actualEndPoint1);
        leafTriangle.getCoordinate(2, actualEndPoint2);
        PointTestHelper.assertPointEquals(new Point3d(0, 0, 0), actualStartPoint);
        PointTestHelper.assertPointEquals(leaf3DState.getEndPoint1().toPointValue(), actualEndPoint1);
        PointTestHelper.assertPointEquals(leaf3DState.getEndPoint2().toPointValue(), actualEndPoint2);
    }

    public void testFall() {
        double initialRotation = Randomizer.randomRotation();
        Tuple3dState initialLeafAttachPoint = new Tuple3dState(1, 2, 3);
        Transform3D initialTransform = TransformerHelper.getTranslationTransform3D(initialLeafAttachPoint
                .toVectorValue());
        Transform3D initialRotationT3D = TransformerHelper.getRotationTransform3D(initialRotation, Axis.Y);
        initialTransform.mul(initialRotationT3D);
        leaf3DState.setTransform(new Transform3DState(initialTransform));

        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);

        // add the leaf into a graph with translation and rotation
        Transform3D parentTransform3D = new Transform3D();
        Vector3d parentTranslation = new Vector3d(2, 3, 5);
        parentTransform3D.set(parentTranslation);
        Transform3D parentRotation = new Transform3D();
        parentRotation.rotY(Math.PI / 2);
        parentTransform3D.mul(parentRotation);
        TransformGroup parentTransformGroup = new TransformGroup(parentTransform3D);
        BranchGroup parentBranchGroup = new BranchGroup();
        parentTransformGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
        parentBranchGroup.addChild(parentTransformGroup);
        parentTransformGroup.addChild(leaf3D.getBranchGroup());
        CompilerHelper.addToLocale(parentBranchGroup);

        // call to the fall() method
        leaf3D.update(null, LeafUpdateMask.FALLING_MASK);

        assertEquals(new Point3d(3 + 2, 2 + 3, -1 + 5), leaf3D.getPosition());

        TransformGroup tg = (TransformGroup) leaf3D.getBranchGroup().getChild(0);
        Transform3D newTransform = new Transform3D();
        tg.getTransform(newTransform);
        double newRotation = TransformerHelper.getRotationFromTransform(newTransform, Axis.Y);
        assertEquals(initialRotation + Math.PI / 2, newRotation, 0.000001);

    }

    public void testGetArea() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertEquals(0.08, leaf3D.getArea(), 0.000001);
    }

    public void testIsMaxSizeReached() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertFalse(leaf3D.isMaxSizeReached());

        Tuple3dState initialEndPoint1 = leaf3DState.getInitialEndPoint1();
        leaf3DState.setEndPoint1(new Tuple3dState(10 * initialEndPoint1.getX(), 10 * initialEndPoint1.getY(),
                10 * initialEndPoint1.getZ()));
        Tuple3dState initialEndPoint2 = leaf3DState.getInitialEndPoint2();
        leaf3DState.setEndPoint2(new Tuple3dState(10 * initialEndPoint2.getX(), 10 * initialEndPoint2.getY(),
                10 * initialEndPoint2.getZ()));
        leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        assertTrue(leaf3D.isMaxSizeReached());
    }

    public void testIncreaseSize() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        leaf3DState.setEndPoint1(leaf3DState.getInitialEndPoint1());
        leaf3DState.setEndPoint2(leaf3DState.getInitialEndPoint2());
        Tuple3dState initialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Tuple3dState initialEndPoint2 = leaf3DState.getInitialEndPoint2();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(mockUniverse3D, leaf3DState, mockLeaf);
        Shape3D leafShape = (Shape3D) ((TransformGroup) leaf3D.getBranchGroup().getChild(0)).getChild(0);
        Point3d geomEndPoint1 = new Point3d();
        Point3d geomEndPoint2 = new Point3d();

        assertFalse(leaf3D.isMaxSizeReached());

        leaf3D.increaseSize();

        // test state
        Point3d endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        Point3d expectedEndPoint1 = new Point3d(initialEndPoint1.getX() * 2, initialEndPoint1.getY() * 2,
                initialEndPoint1.getZ() * 2);
        PointTestHelper.assertPointEquals(expectedEndPoint1, endPoint1);
        Point3d endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        Point3d expectedEndPoint2 = new Point3d(initialEndPoint2.getX() * 2, initialEndPoint2.getY() * 2,
                initialEndPoint2.getZ() * 2);
        PointTestHelper.assertPointEquals(expectedEndPoint2, endPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint1);
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint2);

        leaf3D.increaseSize();

        // test state
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3d(initialEndPoint1.getX() * 3, initialEndPoint1.getY() * 3,
                initialEndPoint1.getZ() * 3);
        PointTestHelper.assertPointEquals(expectedEndPoint1, endPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3d(initialEndPoint2.getX() * 3, initialEndPoint2.getY() * 3,
                initialEndPoint2.getZ() * 3);
        PointTestHelper.assertPointEquals(expectedEndPoint2, endPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint1);
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint2);

        // increase up to the max size
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();

        // test state
        assertTrue(leaf3D.isMaxSizeReached());
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3d(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                initialEndPoint1.getZ() * 10);
        PointTestHelper.assertPointEquals(expectedEndPoint1, endPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3d(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                initialEndPoint2.getZ() * 10);
        PointTestHelper.assertPointEquals(expectedEndPoint2, endPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint1);
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint2);

        // nothing changes if we increase the leaf again
        leaf3D.increaseSize();

        // test state
        assertTrue(leaf3D.isMaxSizeReached());
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3d(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                initialEndPoint1.getZ() * 10);
        PointTestHelper.assertPointEquals(expectedEndPoint1, endPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3d(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                initialEndPoint2.getZ() * 10);
        PointTestHelper.assertPointEquals(expectedEndPoint2, endPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint1);
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        PointTestHelper.assertPointEquals(leaf3D.getState().getEndPoint1().toPointValue(), geomEndPoint2);
    }
}
