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
package barsuift.simLife;

import junit.framework.TestCase;


public class PlanetParametersTest extends TestCase {

    private PlanetParameters param;

    protected void setUp() throws Exception {
        super.setUp();
        param = new PlanetParameters();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        param = null;
    }

    public void testSetLatitude() {
        param.setLatitude(PlanetParameters.LATITUDE_MIN);
        assertEquals(PlanetParameters.LATITUDE_MIN, param.getLatitude(), 0.0001);
        param.setLatitude(PlanetParameters.LATITUDE_DEFAULT);
        assertEquals(PlanetParameters.LATITUDE_DEFAULT, param.getLatitude(), 0.0001);
        param.setLatitude(PlanetParameters.LATITUDE_MAX);
        assertEquals(PlanetParameters.LATITUDE_MAX, param.getLatitude(), 0.0001);
        try {
            param.setLatitude(PlanetParameters.LATITUDE_MIN - 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            param.setLatitude(PlanetParameters.LATITUDE_MAX + 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testSetEclipticObliquity() {
        param.setEclipticObliquity(PlanetParameters.ECLIPTIC_OBLIQUITY_MIN);
        assertEquals(PlanetParameters.ECLIPTIC_OBLIQUITY_MIN, param.getEclipticObliquity(), 0.0001);
        param.setEclipticObliquity(PlanetParameters.ECLIPTIC_OBLIQUITY_DEFAULT);
        assertEquals(PlanetParameters.ECLIPTIC_OBLIQUITY_DEFAULT, param.getEclipticObliquity(), 0.0001);
        param.setEclipticObliquity(PlanetParameters.ECLIPTIC_OBLIQUITY_MAX);
        assertEquals(PlanetParameters.ECLIPTIC_OBLIQUITY_MAX, param.getEclipticObliquity(), 0.0001);
        try {
            param.setEclipticObliquity(PlanetParameters.ECLIPTIC_OBLIQUITY_MIN - 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            param.setEclipticObliquity(PlanetParameters.ECLIPTIC_OBLIQUITY_MAX + 1);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testResetToDefaults() {
        param.resetToDefaults();
        assertEquals(PlanetParameters.LATITUDE_DEFAULT, param.getLatitude(), 0.0001);
        assertEquals(PlanetParameters.ECLIPTIC_OBLIQUITY_DEFAULT, param.getEclipticObliquity(), 0.0001);
    }

    public void testRandom() {
        param.random();
        assertTrue(param.getLatitude() >= PlanetParameters.LATITUDE_MIN);
        assertTrue(param.getLatitude() <= PlanetParameters.LATITUDE_MAX);
        assertTrue(param.getEclipticObliquity() >= PlanetParameters.ECLIPTIC_OBLIQUITY_MIN);
        assertTrue(param.getEclipticObliquity() <= PlanetParameters.ECLIPTIC_OBLIQUITY_MAX);
    }

    public void testValidity() {
        assertTrue(PlanetParameters.LATITUDE_DEFAULT >= PlanetParameters.LATITUDE_MIN);
        assertTrue(PlanetParameters.LATITUDE_DEFAULT <= PlanetParameters.LATITUDE_MAX);
        assertTrue(PlanetParameters.ECLIPTIC_OBLIQUITY_DEFAULT >= PlanetParameters.ECLIPTIC_OBLIQUITY_MIN);
        assertTrue(PlanetParameters.ECLIPTIC_OBLIQUITY_DEFAULT <= PlanetParameters.ECLIPTIC_OBLIQUITY_MAX);
    }

}
