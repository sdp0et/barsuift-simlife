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
package barsuift.simLife.j3d.landscape;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class BasicLandscape3DTest {

    private BasicLandscape3D landscape3D;

    @BeforeMethod
    protected void setUp() {
        Landscape3DState state = DisplayDataCreatorForTests.createSpecificLandscape3DState();
        landscape3D = new BasicLandscape3D(state);
    }

    @AfterMethod
    protected void tearDown() {
        landscape3D = null;
    }

    @Test
    public void testGetVertexIndix() {
        AssertJUnit.assertEquals(0, landscape3D.getVertexIndix(0, 0));
        AssertJUnit.assertEquals(1, landscape3D.getVertexIndix(1, 0));
        AssertJUnit.assertEquals(2, landscape3D.getVertexIndix(0, 1));
        AssertJUnit.assertEquals(3, landscape3D.getVertexIndix(1, 1));

        // the following points are not in landscape
        // we don't actually care about the returned values. The only assertion is that this method won't fail.
        landscape3D.getVertexIndix(-1, 0);
        landscape3D.getVertexIndix(0, -1);
        landscape3D.getVertexIndix(2, 0);
        landscape3D.getVertexIndix(0, 2);

    }

    @Test
    public void testInLandscape() {
        float epsilon = 0.00001f;

        AssertJUnit.assertFalse(landscape3D.inLandscape(0, -1));
        AssertJUnit.assertFalse(landscape3D.inLandscape(epsilon, -1));

        AssertJUnit.assertFalse(landscape3D.inLandscape(0, -epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(epsilon, -epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1 - epsilon, -epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1, -epsilon));

        AssertJUnit.assertFalse(landscape3D.inLandscape(-1, 0));
        AssertJUnit.assertFalse(landscape3D.inLandscape(-epsilon, 0));
        AssertJUnit.assertTrue(landscape3D.inLandscape(0, 0));
        AssertJUnit.assertTrue(landscape3D.inLandscape(epsilon, 0));
        AssertJUnit.assertTrue(landscape3D.inLandscape(1 - epsilon, 0));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1, 0));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1 + epsilon, 0));
        AssertJUnit.assertFalse(landscape3D.inLandscape(2, 0));

        AssertJUnit.assertFalse(landscape3D.inLandscape(-1, epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(-epsilon, epsilon));
        AssertJUnit.assertTrue(landscape3D.inLandscape(0, epsilon));
        AssertJUnit.assertTrue(landscape3D.inLandscape(epsilon, epsilon));
        AssertJUnit.assertTrue(landscape3D.inLandscape(1 - epsilon, epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1, epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1 + epsilon, epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(2, epsilon));

        AssertJUnit.assertFalse(landscape3D.inLandscape(-1, 1 - epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(-epsilon, 1 - epsilon));
        AssertJUnit.assertTrue(landscape3D.inLandscape(0, 1 - epsilon));
        AssertJUnit.assertTrue(landscape3D.inLandscape(epsilon, 1 - epsilon));
        AssertJUnit.assertTrue(landscape3D.inLandscape(1 - epsilon, 1 - epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1, 1 - epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1 + epsilon, 1 - epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(2, 1 - epsilon));

        AssertJUnit.assertFalse(landscape3D.inLandscape(-1, 1));
        AssertJUnit.assertFalse(landscape3D.inLandscape(-epsilon, 1));
        AssertJUnit.assertFalse(landscape3D.inLandscape(0, 1));
        AssertJUnit.assertFalse(landscape3D.inLandscape(epsilon, 1));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1 - epsilon, 1));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1, 1));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1 + epsilon, 1));
        AssertJUnit.assertFalse(landscape3D.inLandscape(2, 1));

        AssertJUnit.assertFalse(landscape3D.inLandscape(0, 1 + epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(epsilon, 1 + epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1 - epsilon, 1 + epsilon));
        AssertJUnit.assertFalse(landscape3D.inLandscape(1, 1 + epsilon));

        AssertJUnit.assertFalse(landscape3D.inLandscape(0, 2));
    }

    @Test
    public void testGetHeight() {
        float epsilon = 0.00001f;
        float delta = 0.001f;

        AssertJUnit.assertEquals(0.0, landscape3D.getHeight(0.0f, 0.0f), delta);
        AssertJUnit.assertEquals(0.2, landscape3D.getHeight(0.5f, 0.0f), delta);
        AssertJUnit.assertEquals(0.4, landscape3D.getHeight(1.0f - epsilon, 0.0f), delta);

        AssertJUnit.assertEquals(0.3, landscape3D.getHeight(0.0f, 0.5f), delta);
        AssertJUnit.assertEquals(0.5, landscape3D.getHeight(0.5f, 0.5f), delta);
        AssertJUnit.assertEquals(0.8, landscape3D.getHeight(1.0f - epsilon, 0.5f), delta);

        AssertJUnit.assertEquals(0.6, landscape3D.getHeight(0.0f, 1.0f - epsilon), delta);
        AssertJUnit.assertEquals(0.9, landscape3D.getHeight(0.5f, 1.0f - epsilon), delta);
        AssertJUnit.assertEquals(1.2, landscape3D.getHeight(1.0f - epsilon, 1.0f - epsilon), delta);

        AssertJUnit.assertEquals(0.28, landscape3D.getHeight(0.4f, 0.2f), delta);
        AssertJUnit.assertEquals(0.32, landscape3D.getHeight(0.2f, 0.4f), delta);

        AssertJUnit.assertEquals(0.6, landscape3D.getHeight(0.8f, 0.4f), delta);
        AssertJUnit.assertEquals(0.68, landscape3D.getHeight(0.4f, 0.8f), delta);


        // when the position is not in the landscape, we simply return 0
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(-1, 0));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(0, -1));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(2, 0));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(0, 2));

        AssertJUnit.assertEquals(0f, landscape3D.getHeight(0, -epsilon));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(1, -epsilon));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(-epsilon, 0));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(1f + epsilon, 0));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(-epsilon, 1));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(1 + epsilon, 1));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(0, 1 + epsilon));
        AssertJUnit.assertEquals(0f, landscape3D.getHeight(1, 1 + epsilon));
    }

}
