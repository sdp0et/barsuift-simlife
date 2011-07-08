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

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class LandscapeParametersTest {

    private LandscapeParameters param;

    @BeforeMethod
    protected void setUp() {
        param = new LandscapeParameters();
    }

    @AfterMethod
    protected void tearDown() {
        param = null;
    }

    @Test
    public void setSize() {
        param.setSize(LandscapeParameters.SIZE_MIN);
        assertThat(param.getSize()).isEqualTo(LandscapeParameters.SIZE_MIN);
        param.setSize(LandscapeParameters.SIZE_DEFAULT);
        assertThat(param.getSize()).isEqualTo(LandscapeParameters.SIZE_DEFAULT);
        param.setSize(LandscapeParameters.SIZE_MAX);
        assertThat(param.getSize()).isEqualTo(LandscapeParameters.SIZE_MAX);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void setSize_exception_onTooSmallSize() {
        param.setSize(LandscapeParameters.SIZE_MIN / 2);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void setSize_exception_onTooBigSize() {
        param.setSize(LandscapeParameters.SIZE_MAX * 2);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void setSize_exception_onNonPowerOfTwoSize() {
        param.setSize(3);
    }

    @Test
    public void setMaxHeight() {
        param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_MIN);
        assertThat(param.getMaximumHeight()).isEqualTo(LandscapeParameters.MAX_HEIGHT_MIN, Delta.delta(0.0001));
        param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_DEFAULT);
        assertThat(param.getMaximumHeight()).isEqualTo(LandscapeParameters.MAX_HEIGHT_DEFAULT, Delta.delta(0.0001));
        param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_MAX);
        assertThat(param.getMaximumHeight()).isEqualTo(LandscapeParameters.MAX_HEIGHT_MAX, Delta.delta(0.0001));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setMaxHeight_exception_onTooSmallHeight() {
        param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_MIN - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setMaxHeight_exception_onTooBigHeight() {
        param.setMaximumHeight(LandscapeParameters.MAX_HEIGHT_MAX + 1);
    }

    @Test
    public void setRoughness() {
        param.setRoughness(LandscapeParameters.ROUGHNESS_MIN);
        assertThat(param.getRoughness()).isEqualTo(LandscapeParameters.ROUGHNESS_MIN, Delta.delta(0.0001));
        param.setRoughness(LandscapeParameters.ROUGHNESS_DEFAULT);
        assertThat(param.getRoughness()).isEqualTo(LandscapeParameters.ROUGHNESS_DEFAULT, Delta.delta(0.0001));
        param.setRoughness(LandscapeParameters.ROUGHNESS_MAX);
        assertThat(param.getRoughness()).isEqualTo(LandscapeParameters.ROUGHNESS_MAX, Delta.delta(0.0001));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setRoughness_exception_onTooSmallRoughness() {
        param.setRoughness(LandscapeParameters.ROUGHNESS_MIN - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setRoughness_exception_onTooBigRoughness() {
        param.setRoughness(LandscapeParameters.ROUGHNESS_MIN - 1);
    }

    @Test
    public void setErosion() {
        param.setErosion(LandscapeParameters.EROSION_MIN);
        assertThat(param.getErosion()).isEqualTo(LandscapeParameters.EROSION_MIN, Delta.delta(0.0001));
        param.setErosion(LandscapeParameters.EROSION_DEFAULT);
        assertThat(param.getErosion()).isEqualTo(LandscapeParameters.EROSION_DEFAULT, Delta.delta(0.0001));
        param.setErosion(LandscapeParameters.EROSION_MAX);
        assertThat(param.getErosion()).isEqualTo(LandscapeParameters.EROSION_MAX, Delta.delta(0.0001));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setErosion_exception_onTooSmallErosion() {
        param.setErosion(LandscapeParameters.EROSION_MIN - 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void setErosion_exception_onTooBigErosion() {
        param.setErosion(LandscapeParameters.EROSION_MAX + 1);
    }

    @Test
    public void testResetToDefaults() {
        param.resetToDefaults();
        assertThat(param.getSize()).isEqualTo(LandscapeParameters.SIZE_DEFAULT);
        assertThat(param.getMaximumHeight()).isEqualTo(LandscapeParameters.MAX_HEIGHT_DEFAULT, Delta.delta(0.0001));
        assertThat(param.getRoughness()).isEqualTo(LandscapeParameters.ROUGHNESS_DEFAULT, Delta.delta(0.0001));
        assertThat(param.getErosion()).isEqualTo(LandscapeParameters.EROSION_DEFAULT, Delta.delta(0.0001));
    }

    @Test
    public void testRandom() {
        param.random();
        assertThat(param.getSize()).isGreaterThanOrEqualTo(LandscapeParameters.SIZE_MIN);
        assertThat(param.getSize()).isLessThanOrEqualTo(LandscapeParameters.SIZE_MAX);
        assertThat(param.getMaximumHeight()).isGreaterThanOrEqualTo(LandscapeParameters.MAX_HEIGHT_MIN);
        assertThat(param.getMaximumHeight()).isLessThanOrEqualTo(LandscapeParameters.MAX_HEIGHT_MAX);
        assertThat(param.getRoughness()).isGreaterThanOrEqualTo(LandscapeParameters.ROUGHNESS_MIN);
        assertThat(param.getRoughness()).isLessThanOrEqualTo(LandscapeParameters.ROUGHNESS_MAX);
        assertThat(param.getErosion()).isGreaterThanOrEqualTo(LandscapeParameters.EROSION_MIN);
        assertThat(param.getErosion()).isLessThanOrEqualTo(LandscapeParameters.EROSION_MAX);
    }

    @Test
    public void testValidity() {
        assertThat(LandscapeParameters.SIZE_DEFAULT).isGreaterThanOrEqualTo(LandscapeParameters.SIZE_MIN);
        assertThat(LandscapeParameters.SIZE_DEFAULT).isLessThanOrEqualTo(LandscapeParameters.SIZE_MAX);
        assertThat(LandscapeParameters.MAX_HEIGHT_DEFAULT).isGreaterThanOrEqualTo(LandscapeParameters.MAX_HEIGHT_MIN);
        assertThat(LandscapeParameters.MAX_HEIGHT_DEFAULT).isLessThanOrEqualTo(LandscapeParameters.MAX_HEIGHT_MAX);
        assertThat(LandscapeParameters.ROUGHNESS_DEFAULT).isGreaterThanOrEqualTo(LandscapeParameters.ROUGHNESS_MIN);
        assertThat(LandscapeParameters.ROUGHNESS_DEFAULT).isLessThanOrEqualTo(LandscapeParameters.ROUGHNESS_MAX);
        assertThat(LandscapeParameters.EROSION_DEFAULT).isGreaterThanOrEqualTo(LandscapeParameters.EROSION_MIN);
        assertThat(LandscapeParameters.EROSION_DEFAULT).isLessThanOrEqualTo(LandscapeParameters.EROSION_MAX);
    }

}
