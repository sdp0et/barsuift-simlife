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
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.fest.assertions.Delta;
import org.testng.annotations.Test;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.util.TransformerHelper;
import static barsuift.simLife.j3d.assertions.Point3fAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;


public class TreeLeaf3DStateFactoryTest {

    @Test
    public void testCreateRandomTreeLeaf3DState() {
        TreeLeaf3DStateFactory factory = new TreeLeaf3DStateFactory();
        Point3f leafAttachPoint = new Point3f(0.32f, 0.33f, 0.34f);
        double rotation = Randomizer.randomRotation();
        Transform3D transform = TransformerHelper.getTranslationTransform3D(new Vector3f(leafAttachPoint));
        Transform3D rotationT3D = TransformerHelper.getRotationTransform3D(rotation, Axis.X);
        transform.mul(rotationT3D);

        TreeLeaf3DState leaf3DState = factory.createRandomTreeLeaf3DState();
        leaf3DState.setTransform(new Transform3DState(transform));

        Transform3D actualTransform = leaf3DState.getTransform().toTransform3D();
        Tuple3fState actualInitialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Tuple3fState actualInitialEndPoint2 = leaf3DState.getInitialEndPoint2();
        Tuple3fState actualEndPoint1 = leaf3DState.getEndPoint1();
        Tuple3fState actualEndPoint2 = leaf3DState.getEndPoint2();

        // test transform
        Vector3f actualTranslation = new Vector3f();
        actualTransform.get(actualTranslation);
        double actualRotation = TransformerHelper.getRotationFromTransform(actualTransform, Axis.X);
        assertThat(new Point3f(actualTranslation)).isEqualTo(leafAttachPoint);
        assertThat(actualRotation).isEqualTo(rotation, Delta.delta(0.00001));

        // test initial point 2 position
        Point3f initialPoint1MinBound = new Point3f(0 + 0.04f - 0.01f, 0 - 0.02f - 0.01f, 0 - 0.01f);
        Point3f initialPoint1MaxBound = new Point3f(0 + 0.04f + 0.01f, 0 - 0.02f + 0.01f, 0 + 0.01f);
        assertThat(actualInitialEndPoint1.toPointValue()).isWithin(initialPoint1MinBound, initialPoint1MaxBound);

        // test initial point 3 position
        Point3f initialPoint2MinBound = new Point3f(0 + 0.04f - 0.01f, 0 + 0.02f - 0.01f, 0 - 0.01f);
        Point3f initialPoint2MaxBound = new Point3f(0 + 0.04f + 0.01f, 0 + 0.02f + 0.01f, 0 + 0.01f);
        assertThat(actualInitialEndPoint2.toPointValue()).isWithin(initialPoint2MinBound, initialPoint2MaxBound);

        // test point 2 position
        Point3f point1MinBound = new Point3f(0 + 0.4f - 0.1f, 0 - 0.2f - 0.1f, 0 - 0.1f);
        Point3f point1MaxBound = new Point3f(0 + 0.4f + 0.1f, 0 - 0.2f + 0.1f, 0 + 0.1f);
        assertThat(actualEndPoint1.toPointValue()).isWithin(point1MinBound, point1MaxBound);

        // test point 3 position
        Point3f point2MinBound = new Point3f(0 + 0.4f - 0.1f, 0 + 0.2f - 0.1f, 0 - 0.1f);
        Point3f point2MaxBound = new Point3f(0 + 0.4f + 0.1f, 0 + 0.2f + 0.1f, 0 + 0.1f);
        assertThat(actualEndPoint2.toPointValue()).isWithin(point2MinBound, point2MaxBound);
    }

    @Test
    public void testCreateNewTreeLeaf3DState() {
        TreeLeaf3DStateFactory factory = new TreeLeaf3DStateFactory();
        Point3f leafAttachPoint = new Point3f(0.32f, 0.33f, 0.34f);
        double rotation = Randomizer.randomRotation();
        Transform3D transform = TransformerHelper.getTranslationTransform3D(new Vector3f(leafAttachPoint));
        Transform3D rotationT3D = TransformerHelper.getRotationTransform3D(rotation, Axis.X);
        transform.mul(rotationT3D);

        TreeLeaf3DState leaf3DState = factory.createNewTreeLeaf3DState();
        leaf3DState.setTransform(new Transform3DState(transform));

        Transform3D actualTransform = leaf3DState.getTransform().toTransform3D();
        Tuple3fState actualInitialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Tuple3fState actualInitialEndPoint2 = leaf3DState.getInitialEndPoint2();
        Tuple3fState actualEndPoint1 = leaf3DState.getEndPoint1();
        Tuple3fState actualEndPoint2 = leaf3DState.getEndPoint2();

        // test transform
        Vector3f actualTranslation = new Vector3f();
        actualTransform.get(actualTranslation);
        double actualRotation = TransformerHelper.getRotationFromTransform(actualTransform, Axis.X);
        assertThat(new Point3f(actualTranslation)).isEqualTo(leafAttachPoint);
        assertThat(actualRotation).isEqualTo(rotation, Delta.delta(0.000001));

        // test initial point 2 position
        Point3f initialPoint1MinBound = new Point3f(0 + 0.04f - 0.01f, 0 - 0.02f - 0.01f, 0 - 0.01f);
        Point3f initialPoint1MaxBound = new Point3f(0 + 0.04f + 0.01f, 0 - 0.02f + 0.01f, 0 + 0.01f);
        assertThat(actualInitialEndPoint1.toPointValue()).isWithin(initialPoint1MinBound, initialPoint1MaxBound);

        // test initial point 3 position
        Point3f initialPoint2MinBound = new Point3f(0 + 0.04f - 0.01f, 0 + 0.02f - 0.01f, 0 - 0.01f);
        Point3f initialPoint2MaxBound = new Point3f(0 + 0.04f + 0.01f, 0 + 0.02f + 0.01f, 0 + 0.01f);
        assertThat(actualInitialEndPoint2.toPointValue()).isWithin(initialPoint2MinBound, initialPoint2MaxBound);

        // test point 2 position
        assertThat(actualEndPoint1.toPointValue()).isEqualTo(actualInitialEndPoint1.toPointValue());
        // test point 3 position
        assertThat(actualEndPoint2.toPointValue()).isEqualTo(actualInitialEndPoint2.toPointValue());
    }

}
