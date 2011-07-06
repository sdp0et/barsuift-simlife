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
package barsuift.simLife.j3d.environment;

import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.fest.assertions.Delta;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.time.SimLifeDate;

import static org.fest.assertions.Assertions.assertThat;


public class BasicSun3DTest {

    private Sun3DState sun3DState;

    private BasicSun3D sun3D;

    private DirectionalLight sunLight;

    private PublisherTestHelper publisherHelper;

    private MockUniverse3D universe3D;

    @BeforeMethod
    public void init() {
        sun3DState = DisplayDataCreatorForTests.createRandomSun3DState();
        universe3D = new MockUniverse3D();
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);
        publisherHelper = new PublisherTestHelper();
    }

    @AfterMethod
    public void clean() {
        sun3DState = null;
        sun3D = null;
        sunLight = null;
        publisherHelper = null;
        universe3D = null;
    }

    @Test
    public void basicSun3D() {
        sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        SimLifeDate date = new SimLifeDate();
        date.setMinuteOfDay(10);
        universe3D.setDate(date);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);

        // The earth rotation is automatic, the value from the state is not used. the value is recomputed from the date
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) (2 * Math.PI / 144), Delta.delta(0.0001));
        assertThat(sun3D.isEarthRotationTaskAutomatic()).isTrue();
        assertThat(sun3D.isEarthRevolutionTaskAutomatic()).isTrue();
    }

    /**
     * Test that the sun3D is a publisher : it notifies its subscribers when the earth rotation change
     */
    @Test
    public void testPublisher() {
        publisherHelper.addSubscriberTo(sun3D);

        sun3D.setEarthRevolution((float) Math.PI);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(3);
        assertThat(publisherHelper.getUpdateObjects().get(0)).isEqualTo(SunUpdateCode.EARTH_REVOLUTION);
        assertThat(publisherHelper.getUpdateObjects().get(1)).isEqualTo(SunUpdateCode.BRIGHTNESS);
        assertThat(publisherHelper.getUpdateObjects().get(2)).isEqualTo(SunUpdateCode.COLOR);

        publisherHelper.reset();

        sun3D.setEarthRotation((float) Math.PI);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(3);
        assertThat(publisherHelper.getUpdateObjects().get(0)).isEqualTo(SunUpdateCode.EARTH_ROTATION);
        assertThat(publisherHelper.getUpdateObjects().get(1)).isEqualTo(SunUpdateCode.BRIGHTNESS);
        assertThat(publisherHelper.getUpdateObjects().get(2)).isEqualTo(SunUpdateCode.COLOR);
    }

    @Test
    public void testUpdateBrightness() {
        setToZenith();

        assertThat(sun3D.getBrightness().floatValue()).isEqualTo(1f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertThat(sun3D.getBrightness().floatValue()).isEqualTo(0.5f, Delta.delta(0.0001));

        sun3D.setEarthRotation(0f);
        assertThat(sun3D.getBrightness().floatValue()).isEqualTo(0f, Delta.delta(0.0001));
    }

    /**
     * set position at perfect zenith
     */
    private void setToZenith() {
        // time of day = noon
        sun3DState.setEarthRotation((float) Math.PI);
        // time of year = sprim equinox
        sun3DState.setEarthRevolution((float) Math.PI / 2);
        // latitude = equator
        sun3DState.setLatitude(0);
        sun3DState.setEarthRevolutionTaskAutomatic(false);
        sun3DState.setEarthRotationTaskAutomatic(false);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);
    }


    @Test
    public void testUpdateColor() {
        setToZenith();

        Color3f actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertThat(actualSunColor).isEqualTo(new Color3f(1f, 1, 1));

        sun3D.setEarthRotation((float) Math.PI / 2);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertThat(actualSunColor).isEqualTo(new Color3f(0.5f, 0.0f, 0.0f));

        sun3D.setEarthRotation(0f);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertThat(actualSunColor).isEqualTo(new Color3f(0.0f, 0.0f, 0.0f));


        sun3DState.setEarthRotation((float) Math.PI);
        sun3DState.setLatitude((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        AssertJUnit.assertEquals(
                new Color3f(1f, (float) Math.sqrt(Math.sqrt(2) / 2), (float) Math.sqrt(Math.sqrt(2) / 2)),
                actualSunColor);
    }

    @Test
    public void testUpdateEarthRotation1() {
        // latitude = equator
        sun3DState.setLatitude(0);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        sun3D.setEarthRotation(0f);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 1, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // sun rise
        sun3D.setEarthRotation((float) Math.PI / 2);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // sun at its zenith
        sun3D.setEarthRotation((float) Math.PI);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -1, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // 5/8
        sun3D.setEarthRotation((float) (5 * Math.PI / 4));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // sunset
        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);
    }

    @Test
    public void testUpdateEarthRotation2() {
        // latitude = 45°
        sun3DState.setLatitude((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        sun3D.setEarthRotation(0f);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, (float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // sun rise
        sun3D.setEarthRotation((float) Math.PI / 2);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // noon
        sun3D.setEarthRotation((float) Math.PI);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -(float) Math.sqrt(2) / 2, (float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // sunset
        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);
    }

    @Test
    public void testUpdateEarthRotation3() {
        // latitude = 90°
        sun3DState.setLatitude((float) Math.PI / 2);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        sun3D.setEarthRotation(0f);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 0, -1);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // sun rise
        sun3D.setEarthRotation((float) Math.PI / 2);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // noon
        sun3D.setEarthRotation((float) Math.PI);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 0, 1);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // 5/8
        sun3D.setEarthRotation((float) (5 * Math.PI / 4));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, 0, (float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);

        // sunset
        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        assertThat(actualDirection).isEqualTo(expectedDirection);
    }

    @Test
    public void testUpdateEarthRevolution() {
        sun3DState.setEclipticObliquity((float) Math.PI / 2);
        // latitude = 90°
        sun3DState.setLatitude((float) Math.PI / 2);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // time of year = wim solstice
        sun3D.setEarthRevolution(0f);
        Color3f wimColor = new Color3f();
        sunLight.getColor(wimColor);
        assertThat(sun3D.getBrightness().floatValue()).isEqualTo(0f, Delta.delta(0.0001));

        // time of year = sprim equinox
        sun3D.setEarthRevolution((float) Math.PI / 2);
        Color3f sprimColor = new Color3f();
        sunLight.getColor(sprimColor);
        assertThat(sun3D.getBrightness().floatValue()).isEqualTo(0.5f, Delta.delta(0.0001));

        // time of year = sum solstice
        sun3D.setEarthRevolution((float) Math.PI);
        Color3f sumColor = new Color3f();
        sunLight.getColor(sumColor);
        assertThat(sun3D.getBrightness().floatValue()).isEqualTo(1f, Delta.delta(0.0001));

        // time of year = tom equinox
        sun3D.setEarthRevolution((float) (3 * Math.PI / 2));
        Color3f tomColor = new Color3f();
        sunLight.getColor(tomColor);
        assertThat(sun3D.getBrightness().floatValue()).isEqualTo(0.5f, Delta.delta(0.0001));

        assertThat(wimColor).isEqualTo(new Color3f(0, 0, 0));
        assertThat(sumColor).isEqualTo(new Color3f(1, 1, 1));
        assertThat(tomColor).isEqualTo(sprimColor);
        assertThat(sprimColor).isNotEqualTo(wimColor);
        assertThat(tomColor).isNotEqualTo(wimColor);
    }

    @Test
    public void testComputeSunHeightLatitude0() {
        // latitude = equator
        sun3DState.setLatitude(0);
        sun3DState.setEclipticObliquity((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // /////////////////////////////
        // time of year = sprim equinox
        sun3D.setEarthRevolution((float) Math.PI / 2);

        sun3D.setEarthRotation(0f);
        assertThat(sun3D.getHeight()).isEqualTo(-1f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertThat(sun3D.getHeight()).isEqualTo((float) (-Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI);
        assertThat(sun3D.getHeight()).isEqualTo(1f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertThat(sun3D.getHeight()).isEqualTo(-1f, Delta.delta(0.0001));

        // /////////////////////////////
        // time of year = sum solstice
        sun3D.setEarthRevolution((float) Math.PI);

        sun3D.setEarthRotation(0f);
        assertThat(sun3D.getHeight()).isEqualTo((float) (-Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertThat(sun3D.getHeight()).isEqualTo(-0.5f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertThat(sun3D.getHeight()).isEqualTo(0.5f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI);
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertThat(sun3D.getHeight()).isEqualTo((float) (-Math.sqrt(2) / 2), Delta.delta(0.0001));
    }

    @Test
    public void testComputeSunHeightLatitude45() {
        // latitude = middle of the planet
        sun3DState.setLatitude((float) Math.PI / 4);
        sun3DState.setEclipticObliquity((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // /////////////////////////////
        // time of year = sprim equinox
        sun3D.setEarthRevolution((float) Math.PI / 2);

        sun3D.setEarthRotation(0f);
        assertThat(sun3D.getHeight()).isEqualTo((float) (-Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertThat(sun3D.getHeight()).isEqualTo(-0.5f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertThat(sun3D.getHeight()).isEqualTo(0.5f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI);
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertThat(sun3D.getHeight()).isEqualTo((float) (-Math.sqrt(2) / 2), Delta.delta(0.0001));

        // /////////////////////////////
        // time of year = sum solstice
        sun3D.setEarthRevolution((float) Math.PI);

        sun3D.setEarthRotation(0f);
        assertThat(sun3D.getHeight()).isEqualTo(0, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertThat(sun3D.getHeight()).isEqualTo(0.5f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI);
        assertThat(sun3D.getHeight()).isEqualTo(1f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertThat(sun3D.getHeight()).isEqualTo(0.5f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));
    }

    @Test
    public void testComputeSunHeightLatitude90() {
        // latitude = north pole
        sun3DState.setLatitude((float) Math.PI / 2);
        sun3DState.setEclipticObliquity((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // /////////////////////////////
        // time of year = sprim equinox
        sun3D.setEarthRevolution((float) Math.PI / 2);

        sun3D.setEarthRotation(0f);
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI);
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));

        // /////////////////////////////
        // time of year = sum solstice
        sun3D.setEarthRevolution((float) Math.PI);

        sun3D.setEarthRotation(0f);
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) Math.PI);
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));
    }

    @Test
    public void testGetWhiteFactor() {
        setToZenith();
        assertThat(sun3D.getHeight()).isEqualTo(1f, Delta.delta(0.0001));
        assertThat(sun3D.getWhiteFactor()).isEqualTo(1f, Delta.delta(0.001f));

        // time of day = sun rise
        sun3D.setEarthRotation((float) Math.PI / 2);
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));
        assertThat(sun3D.getWhiteFactor()).isEqualTo(0f, Delta.delta(0.001f));

        // time of day = midnight
        sun3D.setEarthRotation(0f);
        assertThat(sun3D.getHeight()).isEqualTo(-1f, Delta.delta(0.0001));
        assertThat(sun3D.getWhiteFactor()).isEqualTo(1f, Delta.delta(0.001f));

        // time of day = sunset
        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertThat(sun3D.getHeight()).isEqualTo(0f, Delta.delta(0.0001));
        assertThat(sun3D.getWhiteFactor()).isEqualTo(0f, Delta.delta(0.001f));

        // time of day = middle of the morning
        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertThat(sun3D.getHeight()).isEqualTo((float) (Math.sqrt(2) / 2), Delta.delta(0.0001));
        assertThat(sun3D.getWhiteFactor()).isEqualTo((float) (Math.sqrt(Math.sqrt(2) / 2)), Delta.delta(0.001f));
    }

    @Test
    public void testGetState() {
        sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        SimLifeDate date = new SimLifeDate();
        date.setMinuteOfDay(10);
        universe3D.setDate(date);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);

        assertThat(sun3D.getState()).isEqualTo(sun3DState);
        assertThat(sun3D.getState()).isSameAs(sun3DState);
        assertThat(sun3D.getState().getLatitude()).isEqualTo((float) (Math.PI / 4), Delta.delta(0.0001));
        assertThat(sun3D.getState().getEclipticObliquity()).isEqualTo((float) (Math.PI / 3), Delta.delta(0.0001));
        // The earth rotation is automatic, the value from the state is not used. the value is recomputed from the date
        assertThat(sun3D.getState().getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));
        assertThat(sun3D.getState().getEarthRevolution()).isEqualTo((float) (2 * Math.PI / 144), Delta.delta(0.0001));
        assertThat(sun3D.getState().isEarthRotationTaskAutomatic()).isTrue();
        assertThat(sun3D.getState().isEarthRevolutionTaskAutomatic()).isTrue();

        sun3D.setEarthRotation(0.47f);
        sun3D.setEarthRevolution(0.39f);
        sun3D.setEarthRevolutionTaskAutomatic(false);
        sun3D.setEarthRotationTaskAutomatic(false);

        assertThat(sun3D.getState()).isEqualTo(sun3DState);
        assertThat(sun3D.getState()).isSameAs(sun3DState);
        assertThat(sun3D.getState().getLatitude()).isEqualTo((float) (Math.PI / 4), Delta.delta(0.0001));
        assertThat(sun3D.getState().getEclipticObliquity()).isEqualTo((float) (Math.PI / 3), Delta.delta(0.0001));
        assertThat(sun3D.getState().getEarthRotation()).isEqualTo(0.47f, Delta.delta(0.0001));
        assertThat(sun3D.getState().getEarthRevolution()).isEqualTo(0.39f, Delta.delta(0.0001));
        assertThat((sun3D.getState().isEarthRotationTaskAutomatic())).isFalse();
        assertThat((sun3D.getState().isEarthRevolutionTaskAutomatic())).isFalse();
    }

    @Test
    public void testGetEarthRotation() {
        sun3D.setEarthRotation(0.3f);
        assertThat(sun3D.getEarthRotation()).isEqualTo(0.3f, Delta.delta(0.0001));
    }

    @Test
    public void testGetEarthRevolution() {
        sun3D.setEarthRevolution(0.3f);
        assertThat(sun3D.getEarthRevolution()).isEqualTo(0.3f, Delta.delta(0.0001));
    }

    @Test
    public void testAdjustEarthRotation() {
        sun3DState.setEarthRotationTaskAutomatic(false);
        sun3DState.setEarthRotation((float) Math.PI);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3DState.setEarthRotation((float) (3 * Math.PI));
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3DState.setEarthRotation((float) (5 * Math.PI));
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3DState.setEarthRotation(-(float) Math.PI);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3DState.setEarthRotation(-(float) (3 * Math.PI));
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));


        sun3D.setEarthRotation((float) Math.PI);
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (3 * Math.PI));
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3D.setEarthRotation((float) (5 * Math.PI));
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3D.setEarthRotation(-(float) Math.PI);
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3D.setEarthRotation(-(float) (3 * Math.PI));
        assertThat(sun3D.getEarthRotation()).isEqualTo((float) Math.PI, Delta.delta(0.0001));
    }


    @Test
    public void testAdjustEarthRevolution() {
        sun3DState.setEarthRevolutionTaskAutomatic(false);
        sun3DState.setEarthRevolution((float) Math.PI);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3DState.setEarthRevolution((float) (3 * Math.PI));
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3DState.setEarthRevolution((float) (5 * Math.PI));
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3DState.setEarthRevolution(-(float) Math.PI);
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3DState.setEarthRevolution(-(float) (3 * Math.PI));
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));


        sun3D.setEarthRevolution((float) Math.PI);
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3D.setEarthRevolution((float) (3 * Math.PI));
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3D.setEarthRevolution((float) (5 * Math.PI));
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3D.setEarthRevolution(-(float) Math.PI);
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));

        sun3D.setEarthRevolution(-(float) (3 * Math.PI));
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0001));
    }

    @Test
    public void testSetEarthRotationTaskAutomatic() {
        sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.isEarthRotationTaskAutomatic()).isTrue();
        assertThat(sun3D.getEarthRotationTask().isAutomatic()).isTrue();

        sun3D.setEarthRotationTaskAutomatic(true);
        assertThat(sun3D.isEarthRotationTaskAutomatic()).isTrue();
        assertThat(sun3D.getEarthRotationTask().isAutomatic()).isTrue();

        sun3D.setEarthRotationTaskAutomatic(false);
        assertThat((sun3D.isEarthRotationTaskAutomatic())).isFalse();
        assertThat((sun3D.getEarthRotationTask().isAutomatic())).isFalse();
    }

    @Test
    public void testSetEarthRevolutionTaskAutomatic() {
        sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        sun3D = new BasicSun3D(sun3DState);
        sun3D.init(universe3D);
        assertThat(sun3D.isEarthRevolutionTaskAutomatic()).isTrue();
        assertThat(sun3D.getEarthRevolutionTask().isAutomatic()).isTrue();

        sun3D.setEarthRevolutionTaskAutomatic(true);
        assertThat(sun3D.isEarthRevolutionTaskAutomatic()).isTrue();
        assertThat(sun3D.getEarthRevolutionTask().isAutomatic()).isTrue();

        sun3D.setEarthRevolutionTaskAutomatic(false);
        assertThat((sun3D.isEarthRevolutionTaskAutomatic())).isFalse();
        assertThat((sun3D.getEarthRevolutionTask().isAutomatic())).isFalse();
    }

}
