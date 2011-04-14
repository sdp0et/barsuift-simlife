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
package barsuift.simLife.landscape;

import junit.framework.TestCase;


public class LandscapeParametersTest extends TestCase {

    private LandscapeParameters param;

    protected void setUp() throws Exception {
        super.setUp();
        param = new LandscapeParameters();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        param = null;
    }

    public void testSetSize() {
        param.setSize(LandscapeParameters.SIZE_MIN);
        assertEquals(LandscapeParameters.SIZE_MIN, param.getSize(), 0.0001);
        param.setSize(LandscapeParameters.SIZE_DEFAULT);
        assertEquals(LandscapeParameters.SIZE_DEFAULT, param.getSize(), 0.0001);
        param.setSize(LandscapeParameters.SIZE_MAX);
        assertEquals(LandscapeParameters.SIZE_MAX, param.getSize(), 0.0001);
        try {
            param.setSize(LandscapeParameters.SIZE_MIN - 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            param.setSize(LandscapeParameters.SIZE_MAX + 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            param.setSize(3);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testSetMaxHeight() {
        param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_MIN);
        assertEquals(LandscapeParameters.MAX_HEIGHT_MIN, param.getMaximumHeight(), 0.0001);
        param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_DEFAULT);
        assertEquals(LandscapeParameters.MAX_HEIGHT_DEFAULT, param.getMaximumHeight(), 0.0001);
        param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_MAX);
        assertEquals(LandscapeParameters.MAX_HEIGHT_MAX, param.getMaximumHeight(), 0.0001);
        try {
            param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_MIN - 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_MAX + 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testSetRoughness() {
        param.setRoughness(LandscapeParameters.ROUGHNESS_MIN);
        assertEquals(LandscapeParameters.ROUGHNESS_MIN, param.getRoughness(), 0.0001);
        param.setRoughness(LandscapeParameters.ROUGHNESS_DEFAULT);
        assertEquals(LandscapeParameters.ROUGHNESS_DEFAULT, param.getRoughness(), 0.0001);
        param.setRoughness(LandscapeParameters.ROUGHNESS_MAX);
        assertEquals(LandscapeParameters.ROUGHNESS_MAX, param.getRoughness(), 0.0001);
        try {
            param.setRoughness(LandscapeParameters.ROUGHNESS_MIN - 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            param.setRoughness(LandscapeParameters.ROUGHNESS_MAX + 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testSetErosion() {
        param.setErosion(LandscapeParameters.EROSION_MIN);
        assertEquals(LandscapeParameters.EROSION_MIN, param.getErosion(), 0.0001);
        param.setErosion(LandscapeParameters.EROSION_DEFAULT);
        assertEquals(LandscapeParameters.EROSION_DEFAULT, param.getErosion(), 0.0001);
        param.setErosion(LandscapeParameters.EROSION_MAX);
        assertEquals(LandscapeParameters.EROSION_MAX, param.getErosion(), 0.0001);
        try {
            param.setErosion(LandscapeParameters.EROSION_MIN - 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            param.setErosion(LandscapeParameters.EROSION_MAX + 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testResetToDefaults() {
        param.resetToDefaults();
        assertEquals(LandscapeParameters.SIZE_DEFAULT, param.getSize(), 0.0001);
        assertEquals(LandscapeParameters.MAX_HEIGHT_DEFAULT, param.getMaximumHeight(), 0.0001);
        assertEquals(LandscapeParameters.ROUGHNESS_DEFAULT, param.getRoughness(), 0.0001);
        assertEquals(LandscapeParameters.EROSION_DEFAULT, param.getErosion(), 0.0001);
    }

    public void testRandom() {
        param.random();
        assertTrue(param.getSize() >= LandscapeParameters.SIZE_MIN);
        assertTrue(param.getSize() <= LandscapeParameters.SIZE_MAX);
        assertTrue(param.getMaximumHeight() >= LandscapeParameters.MAX_HEIGHT_MIN);
        assertTrue(param.getMaximumHeight() <= LandscapeParameters.MAX_HEIGHT_MAX);
        assertTrue(param.getRoughness() >= LandscapeParameters.ROUGHNESS_MIN);
        assertTrue(param.getRoughness() <= LandscapeParameters.ROUGHNESS_MAX);
        assertTrue(param.getErosion() >= LandscapeParameters.EROSION_MIN);
        assertTrue(param.getErosion() <= LandscapeParameters.EROSION_MAX);
    }

    public void testValidity() {
        assertTrue(LandscapeParameters.SIZE_DEFAULT >= LandscapeParameters.SIZE_MIN);
        assertTrue(LandscapeParameters.SIZE_DEFAULT <= LandscapeParameters.SIZE_MAX);
        assertTrue(LandscapeParameters.MAX_HEIGHT_DEFAULT >= LandscapeParameters.MAX_HEIGHT_MIN);
        assertTrue(LandscapeParameters.MAX_HEIGHT_DEFAULT <= LandscapeParameters.MAX_HEIGHT_MAX);
        assertTrue(LandscapeParameters.ROUGHNESS_DEFAULT >= LandscapeParameters.ROUGHNESS_MIN);
        assertTrue(LandscapeParameters.ROUGHNESS_DEFAULT <= LandscapeParameters.ROUGHNESS_MAX);
        assertTrue(LandscapeParameters.EROSION_DEFAULT >= LandscapeParameters.EROSION_MIN);
        assertTrue(LandscapeParameters.EROSION_DEFAULT <= LandscapeParameters.EROSION_MAX);
    }

}
