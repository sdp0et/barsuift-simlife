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
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.tree.helper.BasicTreeTrunk3DTestHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.tree.MockTreeTrunk;
import static barsuift.simLife.j3d.assertions.AppearanceAssert.assertThat;

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
        AssertJUnit.assertEquals(trunk3DState, trunk3D.getState());
        AssertJUnit.assertSame(trunk3DState, trunk3D.getState());
    }

    @Test
    public void testCreateTrunk() {
        float radius = 0.5f;
        float height = 4;
        mockTrunk.setHeight(height);
        mockTrunk.setRadius(radius);
        BasicTreeTrunk3D trunk3D = new BasicTreeTrunk3D(trunk3DState);
        trunk3D.init(mockUniverse3D, mockTrunk);

        Point3d expectedLowerBottom = new Point3d(-0.5, -2, -0.5);
        Point3d expectedUpperBottom = new Point3d(0.5, -2, 0.5);
        Point3d expectedMovedLowerBottom = new Point3d(-0.5, 0, -0.5);
        Point3d expectedMovedUpperBottom = new Point3d(0.5, 0, 0.5);
        Point3d expectedLowerTop = new Point3d(-0.5, 2, -0.5);
        Point3d expectedUpperTop = new Point3d(0.5, 2, 0.5);
        Point3d expectedMovedLowerTop = new Point3d(-0.5, 4, -0.5);
        Point3d expectedMovedUpperTop = new Point3d(0.5, 4, 0.5);
        BasicTreeTrunk3DTestHelper.checkTrunk3D(trunk3D, height, radius, expectedLowerBottom, expectedUpperBottom,
                expectedMovedLowerBottom, expectedMovedUpperBottom, expectedLowerTop, expectedUpperTop,
                expectedMovedLowerTop, expectedMovedUpperTop);

        Appearance appearance = trunk3D.getTrunk().getAppearance();
        assertThat(appearance).hasAmbientColor(ColorConstants.brown);
        assertThat(appearance).hasSpecularColor(new Color3f(0.05f, 0.05f, 0.05f));
        assertThat(appearance).hasDiffuseColor(new Color3f(0.15f, 0.15f, 0.15f));
    }

}
