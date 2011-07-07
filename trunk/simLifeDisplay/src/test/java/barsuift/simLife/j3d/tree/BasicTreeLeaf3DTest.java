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
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.LeafEvent;
import barsuift.simLife.tree.MockTreeLeaf;
import static barsuift.simLife.j3d.assertions.AppearanceAssert.assertThat;
import static barsuift.simLife.j3d.assertions.GroupAssert.assertThat;
import static barsuift.simLife.j3d.assertions.Point3fAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;

public class BasicTreeLeaf3DTest {

    private MockTreeLeaf mockLeaf;

    private TreeLeaf3DState leaf3DState;

    private MockUniverse3D mockUniverse3D;

    @BeforeMethod
    protected void setUp() {
        mockLeaf = new MockTreeLeaf();
        leaf3DState = DisplayDataCreatorForTests.createRandomTreeLeaf3DState();
        mockUniverse3D = new MockUniverse3D();
    }

    @AfterMethod
    protected void tearDown() {
        mockLeaf = null;
        leaf3DState = null;
        mockUniverse3D = null;
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void constructor_exception_onNull() {
        new BasicTreeLeaf3D(null);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void init_exception_onNullLeaf() {
        BasicTreeLeaf3D leaf = new BasicTreeLeaf3D(leaf3DState);
        leaf.init(mockUniverse3D, null);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void init_exception_onNullUniverse() {
        BasicTreeLeaf3D leaf = new BasicTreeLeaf3D(leaf3DState);
        leaf.init(null, mockLeaf);
    }

    @Test
    public void testGetState() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);
        assertThat(leaf3D.getState()).isEqualTo(leaf3DState);

        assertThat(leaf3D.getState()).isEqualTo(leaf3DState);
        assertThat(leaf3D.getState()).isSameAs(leaf3DState);
        assertThat(leaf3D.getState().getEndPoint1().toPointValue()).isEqualTo(new Point3f(0.4f, 0, 0));
        leaf3D.increaseSize();
        assertThat(leaf3D.getState()).isEqualTo(leaf3DState);
        assertThat(leaf3D.getState()).isSameAs(leaf3DState);
        assertThat(leaf3D.getState().getEndPoint1().toPointValue()).isEqualTo(new Point3f(0.6f, 0.2f, 0));
    }

    @Test
    public void testSubscribers() {
        mockLeaf.setEfficiency(PercentHelper.getDecimalValue(80));
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);
        assertThat(mockLeaf.countSubscribers()).isEqualTo(1);
        // check the subscriber is the leaf3D
        mockLeaf.deleteSubscriber(leaf3D);
        assertThat(mockLeaf.countSubscribers()).isEqualTo(0);
    }

    @Test
    public void testAppearance() {
        mockLeaf.setEfficiency(PercentHelper.getDecimalValue(80));
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);
        CompilerHelper.compile(leaf3D.getBranchGroup());
        Shape3D leafShape3D = (Shape3D) ((TransformGroup) leaf3D.getBranchGroup().getChild(0)).getChild(0);

        Color3f expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.8f);
        assertThat(leafShape3D.getAppearance()).hasAmbientColor(expectedColor);
        assertThat(leafShape3D.getAppearance()).hasSpecularColor(new Color3f(0.05f, 0.05f, 0.05f));
        assertThat(leafShape3D.getAppearance()).hasDiffuseColor(new Color3f(0.15f, 0.15f, 0.15f));

        // check the leaves are not culled (transparent) when seen from behind
        assertThat(leafShape3D.getAppearance().getPolygonAttributes().getCullFace()).isEqualTo(
                PolygonAttributes.CULL_NONE);

        mockLeaf.setEfficiency(PercentHelper.getDecimalValue(75));
        leaf3D.update(mockLeaf, LeafEvent.EFFICIENCY);

        // check that the color has changed as expected
        expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.75f);
        assertThat(leafShape3D.getAppearance()).hasAmbientColor(expectedColor);
        assertThat(leafShape3D.getAppearance()).hasSpecularColor(new Color3f(0.05f, 0.05f, 0.05f));
        assertThat(leafShape3D.getAppearance()).hasDiffuseColor(new Color3f(0.15f, 0.15f, 0.15f));


        mockLeaf.setEfficiency(PercentHelper.getDecimalValue(60));
        // 65536 does not correspond to any valid update code
        leaf3D.update(mockLeaf, 65536);

        // check that the color has NOT changed as expected, because the update code is not the appropriate one
        expectedColor = new Color3f(ColorConstants.brownYellow);
        expectedColor.interpolate(ColorConstants.green, 0.75f); // 75%, not 60%
        assertThat(leafShape3D.getAppearance()).hasAmbientColor(expectedColor);
        assertThat(leafShape3D.getAppearance()).hasSpecularColor(new Color3f(0.05f, 0.05f, 0.05f));
        assertThat(leafShape3D.getAppearance()).hasDiffuseColor(new Color3f(0.15f, 0.15f, 0.15f));
    }

    @Test
    public void testGeometry() {
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);
        BranchGroup bg = leaf3D.getBranchGroup();
        CompilerHelper.compile(bg);

        assertThat(bg).hasExactlyOneTransformGroup();
        TransformGroup transformGroup = (TransformGroup) bg.getChild(0);

        // test translation and rotation
        Transform3D transform3D = new Transform3D();
        transformGroup.getTransform(transform3D);
        assertThat(new Transform3DState(transform3D)).isEqualTo(leaf3DState.getTransform());

        // test one leaf found
        assertThat(transformGroup).hasExactlyOneShape3D();
        Shape3D leafShape3D = (Shape3D) transformGroup.getChild(0);
        assertThat(leafShape3D).isNotNull();

        // test position and geometry
        Geometry leafGeometry = leafShape3D.getGeometry();
        assertThat(leafGeometry instanceof TriangleArray).isTrue();
        TriangleArray leafTriangle = (TriangleArray) leafGeometry;
        assertThat(leafTriangle.getVertexCount()).isEqualTo(3);

        Point3f actualStartPoint = new Point3f();
        Point3f actualEndPoint1 = new Point3f();
        Point3f actualEndPoint2 = new Point3f();
        leafTriangle.getCoordinate(0, actualStartPoint);
        leafTriangle.getCoordinate(1, actualEndPoint1);
        leafTriangle.getCoordinate(2, actualEndPoint2);
        assertThat(actualStartPoint).isEqualTo(new Point3f(0, 0, 0));
        assertThat(actualEndPoint1).isEqualTo(leaf3DState.getEndPoint1().toPointValue());
        assertThat(actualEndPoint2).isEqualTo(leaf3DState.getEndPoint2().toPointValue());
    }

    @Test
    public void testFall() {
        double initialRotation = Randomizer.randomRotation();
        Tuple3fState initialLeafAttachPoint = new Tuple3fState(1, 2, 3);
        Transform3D initialTransform = TransformerHelper.getTranslationTransform3D(initialLeafAttachPoint
                .toVectorValue());
        Transform3D initialRotationT3D = TransformerHelper.getRotationTransform3D(initialRotation, Axis.Y);
        initialTransform.mul(initialRotationT3D);
        leaf3DState.setTransform(new Transform3DState(initialTransform));

        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);

        // add the leaf into a graph with translation and rotation
        Transform3D parentTransform3D = new Transform3D();
        Vector3f parentTranslation = new Vector3f(2, 3, 5);
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
        leaf3D.update(null, MobileEvent.FALLING);

        assertThat(leaf3D.getPosition()).isEqualTo(new Point3f(3 + 2, 2 + 3, -1 + 5));

        TransformGroup tg = (TransformGroup) leaf3D.getBranchGroup().getChild(0);
        Transform3D newTransform = new Transform3D();
        tg.getTransform(newTransform);
        double newRotation = TransformerHelper.getRotationFromTransform(newTransform, Axis.Y);
        assertThat(newRotation).isEqualTo((initialRotation + Math.PI / 2) % (2 * Math.PI), Delta.delta(0.00001));

    }

    @Test
    public void testGetArea() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);
        assertThat(leaf3D.getArea()).isEqualTo(0.08f, Delta.delta(0.000001));
    }

    @Test
    public void testIsMaxSizeReached() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);
        assertThat(leaf3D.isMaxSizeReached()).isFalse();

        Tuple3fState initialEndPoint1 = leaf3DState.getInitialEndPoint1();
        leaf3DState.setEndPoint1(new Tuple3fState(10 * initialEndPoint1.getX(), 10 * initialEndPoint1.getY(),
                10 * initialEndPoint1.getZ()));
        Tuple3fState initialEndPoint2 = leaf3DState.getInitialEndPoint2();
        leaf3DState.setEndPoint2(new Tuple3fState(10 * initialEndPoint2.getX(), 10 * initialEndPoint2.getY(),
                10 * initialEndPoint2.getZ()));
        leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);
        assertThat(leaf3D.isMaxSizeReached()).isTrue();
    }

    @Test
    public void testIncreaseSize() {
        leaf3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        leaf3DState.setEndPoint1(leaf3DState.getInitialEndPoint1());
        leaf3DState.setEndPoint2(leaf3DState.getInitialEndPoint2());
        Tuple3fState initialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Tuple3fState initialEndPoint2 = leaf3DState.getInitialEndPoint2();
        BasicTreeLeaf3D leaf3D = new BasicTreeLeaf3D(leaf3DState);
        leaf3D.init(mockUniverse3D, mockLeaf);
        Shape3D leafShape = (Shape3D) ((TransformGroup) leaf3D.getBranchGroup().getChild(0)).getChild(0);
        Point3f geomEndPoint1 = new Point3f();
        Point3f geomEndPoint2 = new Point3f();

        assertThat(leaf3D.isMaxSizeReached()).isFalse();

        leaf3D.increaseSize();

        // test state
        Point3f endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        Point3f expectedEndPoint1 = new Point3f(initialEndPoint1.getX() * 2, initialEndPoint1.getY() * 2,
                initialEndPoint1.getZ() * 2);
        assertThat(endPoint1).isEqualTo(expectedEndPoint1);
        Point3f endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        Point3f expectedEndPoint2 = new Point3f(initialEndPoint2.getX() * 2, initialEndPoint2.getY() * 2,
                initialEndPoint2.getZ() * 2);
        assertThat(endPoint2).isEqualTo(expectedEndPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        assertThat(geomEndPoint1).isEqualTo(leaf3D.getState().getEndPoint1().toPointValue());
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        assertThat(geomEndPoint2).isEqualTo(leaf3D.getState().getEndPoint1().toPointValue());

        leaf3D.increaseSize();

        // test state
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3f(initialEndPoint1.getX() * 3, initialEndPoint1.getY() * 3,
                initialEndPoint1.getZ() * 3);
        assertThat(endPoint1).isEqualTo(expectedEndPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3f(initialEndPoint2.getX() * 3, initialEndPoint2.getY() * 3,
                initialEndPoint2.getZ() * 3);
        assertThat(endPoint2).isEqualTo(expectedEndPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        assertThat(geomEndPoint1).isEqualTo(leaf3D.getState().getEndPoint1().toPointValue());
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        assertThat(geomEndPoint2).isEqualTo(leaf3D.getState().getEndPoint1().toPointValue());

        // increase up to the max size
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();
        leaf3D.increaseSize();

        // test state
        assertThat(leaf3D.isMaxSizeReached()).isTrue();
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3f(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                initialEndPoint1.getZ() * 10);
        assertThat(endPoint1).isEqualTo(expectedEndPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3f(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                initialEndPoint2.getZ() * 10);
        assertThat(endPoint2).isEqualTo(expectedEndPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        assertThat(geomEndPoint1).isEqualTo(leaf3D.getState().getEndPoint1().toPointValue());
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        assertThat(geomEndPoint2).isEqualTo(leaf3D.getState().getEndPoint1().toPointValue());

        // nothing changes if we increase the leaf again
        leaf3D.increaseSize();

        // test state
        assertThat(leaf3D.isMaxSizeReached()).isTrue();
        endPoint1 = leaf3D.getState().getEndPoint1().toPointValue();
        expectedEndPoint1 = new Point3f(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                initialEndPoint1.getZ() * 10);
        assertThat(endPoint1).isEqualTo(expectedEndPoint1);
        endPoint2 = leaf3D.getState().getEndPoint2().toPointValue();
        expectedEndPoint2 = new Point3f(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                initialEndPoint2.getZ() * 10);
        assertThat(endPoint2).isEqualTo(expectedEndPoint2);
        // test geometry
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint1);
        assertThat(geomEndPoint1).isEqualTo(leaf3D.getState().getEndPoint1().toPointValue());
        ((TriangleArray) leafShape.getGeometry()).getCoordinate(1, geomEndPoint2);
        assertThat(geomEndPoint2).isEqualTo(leaf3D.getState().getEndPoint1().toPointValue());
    }
}
