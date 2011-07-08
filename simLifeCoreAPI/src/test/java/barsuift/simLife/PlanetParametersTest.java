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

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static barsuift.simLife.PlanetParameters.ECLIPTIC_OBLIQUITY_DEFAULT;
import static barsuift.simLife.PlanetParameters.ECLIPTIC_OBLIQUITY_MAX;
import static barsuift.simLife.PlanetParameters.ECLIPTIC_OBLIQUITY_MIN;
import static barsuift.simLife.PlanetParameters.LATITUDE_DEFAULT;
import static barsuift.simLife.PlanetParameters.LATITUDE_MAX;
import static barsuift.simLife.PlanetParameters.LATITUDE_MIN;

import static org.fest.assertions.Assertions.assertThat;


public class PlanetParametersTest {

    private PlanetParameters param;

    @BeforeMethod
    protected void setUp() {
        param = new PlanetParameters();
    }

    @AfterMethod
    protected void tearDown() {
        param = null;
    }

    @Test
    public void setLatitude() {
        param.setLatitude(LATITUDE_MIN);
        assertThat(param.getLatitude()).isEqualTo(LATITUDE_MIN, Delta.delta(0.0001));
        param.setLatitude(LATITUDE_DEFAULT);
        assertThat(param.getLatitude()).isEqualTo(LATITUDE_DEFAULT, Delta.delta(0.0001));
        param.setLatitude(LATITUDE_MAX);
        assertThat(param.getLatitude()).isEqualTo(LATITUDE_MAX, Delta.delta(0.0001));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setLatitude_exception_onTooSmallLatitude() {
        param.setLatitude(LATITUDE_MIN - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setLatitude_exception_onTooBigLatitude() {
        param.setLatitude(LATITUDE_MAX + 1);
    }

    @Test
    public void setEclipticObliquity() {
        param.setEclipticObliquity(ECLIPTIC_OBLIQUITY_MIN);
        assertThat(param.getEclipticObliquity()).isEqualTo(ECLIPTIC_OBLIQUITY_MIN, Delta.delta(0.0001));
        param.setEclipticObliquity(ECLIPTIC_OBLIQUITY_DEFAULT);
        assertThat(param.getEclipticObliquity()).isEqualTo(ECLIPTIC_OBLIQUITY_DEFAULT, Delta.delta(0.0001));
        param.setEclipticObliquity(ECLIPTIC_OBLIQUITY_MAX);
        assertThat(param.getEclipticObliquity()).isEqualTo(ECLIPTIC_OBLIQUITY_MAX, Delta.delta(0.0001));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setEclipticObliquity_exception_onTooSmallValue() {
        param.setEclipticObliquity(ECLIPTIC_OBLIQUITY_MIN - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setEclipticObliquity_exception_onTooBigValue() {
        param.setEclipticObliquity(ECLIPTIC_OBLIQUITY_MAX + 1);
    }

    @Test
    public void testResetToDefaults() {
        param.resetToDefaults();
        assertThat(param.getLatitude()).isEqualTo(LATITUDE_DEFAULT, Delta.delta(0.0001));
        assertThat(param.getEclipticObliquity()).isEqualTo(ECLIPTIC_OBLIQUITY_DEFAULT, Delta.delta(0.0001));
    }

    @Test
    public void testRandom() {
        param.random();
        assertThat(param.getLatitude()).isGreaterThanOrEqualTo(LATITUDE_MIN);
        assertThat(param.getLatitude()).isLessThanOrEqualTo(LATITUDE_MAX);
        assertThat(param.getEclipticObliquity()).isGreaterThanOrEqualTo(ECLIPTIC_OBLIQUITY_MIN);
        assertThat(param.getEclipticObliquity()).isLessThanOrEqualTo(ECLIPTIC_OBLIQUITY_MAX);
    }

    @Test
    public void testValidity() {
        assertThat(LATITUDE_DEFAULT).isGreaterThanOrEqualTo(LATITUDE_MIN);
        assertThat(LATITUDE_DEFAULT).isLessThanOrEqualTo(LATITUDE_MAX);
        assertThat(ECLIPTIC_OBLIQUITY_DEFAULT).isGreaterThanOrEqualTo(ECLIPTIC_OBLIQUITY_MIN);
        assertThat(ECLIPTIC_OBLIQUITY_DEFAULT).isLessThanOrEqualTo(ECLIPTIC_OBLIQUITY_MAX);
    }

}
