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

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Group;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.tree.MockTreeTrunk;

import com.sun.j3d.utils.geometry.Cylinder;

import static barsuift.simLife.j3d.assertions.AppearanceAssert.assertThat;
import static barsuift.simLife.j3d.assertions.BoundingBoxAssert.assertThat;
import static barsuift.simLife.j3d.assertions.CylinderAssert.assertThat;
import static barsuift.simLife.j3d.assertions.GroupAssert.assertThat;
import static barsuift.simLife.j3d.assertions.Transform3DAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;

public class BasicTreeTrunk3DTest {

    private TreeTrunk3DState trunk3DState;

    private MockTreeTrunk mockTrunk;

    private MockUniverse3D mockUniverse3D;

    @BeforeMethod
    protected void setUp() {
        TreeTrunk3DStateFactory stateFactory = new TreeTrunk3DStateFactory();
        trunk3DState = stateFactory.createRandomTreeTrunk3DState();
        mockTrunk = new MockTreeTrunk();
        mockUniverse3D = new MockUniverse3D();
    }

    @AfterMethod
    protected void tearDown() {
        trunk3DState = null;
        mockTrunk = null;
        mockUniverse3D = null;
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void constructor_exception() {
        new BasicTreeTrunk3D(null);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void init_exception_nullTrunk() {
        BasicTreeTrunk3D trunk3D = new BasicTreeTrunk3D(trunk3DState);
        trunk3D.init(mockUniverse3D, null);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void init_exception_nullUniverse() {
        BasicTreeTrunk3D trunk3D = new BasicTreeTrunk3D(trunk3DState);
        trunk3D.init(null, mockTrunk);
    }

    @Test
    public void testGetState() {
        BasicTreeTrunk3D trunk3D = new BasicTreeTrunk3D(trunk3DState);
        trunk3D.init(mockUniverse3D, mockTrunk);
        assertThat(trunk3D.getState()).isEqualTo(trunk3DState);
        assertThat(trunk3D.getState()).isSameAs(trunk3DState);
    }

    @Test
    public void testCreateTrunk() {
        float radius = 0.5f;
        float height = 4;
        mockTrunk.setHeight(height);
        mockTrunk.setRadius(radius);
        BasicTreeTrunk3D trunk3D = new BasicTreeTrunk3D(trunk3DState);
        trunk3D.init(mockUniverse3D, mockTrunk);

        Cylinder trunkCylinder = trunk3D.getTrunk();
        assertThat(trunkCylinder).hasRadius(radius);
        assertThat(trunkCylinder).hasHeight(height);

        Group trunkGroup = trunk3D.getGroup();
        assertThat(trunkGroup).hasExactlyOneTransformGroup();
        TransformGroup trunkTg = assertThat(trunkGroup).getSingleTransformGroup();

        assertThat(trunkTg).hasExactlyOne(Cylinder.class);
        Cylinder trunkCylinderChild = (Cylinder) trunkTg.getChild(0);
        assertThat(trunkCylinderChild).isEqualTo(trunkCylinder);

        Transform3D transform3D = new Transform3D();
        trunkTg.getTransform(transform3D);
        assertThat(transform3D).hasTranslation(new Vector3f(0, height / 2, 0));

        Delta bigDelta = Delta.delta(0.02f);
        Delta smallDelta = Delta.delta(0.0001f);

        Point3d expectedLowerBottom = new Point3d(-0.5, -2, -0.5);
        Point3d expectedUpperBottom = new Point3d(0.5, -2, 0.5);
        BoundingBox bottomBounds = (BoundingBox) trunkCylinder.getShape(Cylinder.BOTTOM).getBounds();
        assertThat(bottomBounds).hasLowerBound(expectedLowerBottom, bigDelta, smallDelta, bigDelta);
        assertThat(bottomBounds).hasUpperBound(expectedUpperBottom, bigDelta, smallDelta, bigDelta);

        Point3d expectedLowerTop = new Point3d(-0.5, 2, -0.5);
        Point3d expectedUpperTop = new Point3d(0.5, 2, 0.5);
        BoundingBox topBounds = (BoundingBox) trunkCylinder.getShape(Cylinder.TOP).getBounds();
        assertThat(topBounds).hasLowerBound(expectedLowerTop, bigDelta, smallDelta, bigDelta);
        assertThat(topBounds).hasUpperBound(expectedUpperTop, bigDelta, smallDelta, bigDelta);

        Appearance appearance = trunkCylinder.getAppearance();
        assertThat(appearance).hasAmbientColor(ColorConstants.brown);
        assertThat(appearance).hasSpecularColor(new Color3f(0.05f, 0.05f, 0.05f));
        assertThat(appearance).hasDiffuseColor(new Color3f(0.15f, 0.15f, 0.15f));
    }

}
