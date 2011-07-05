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

import junit.framework.TestCase;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.helper.ColorTestHelper;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.VectorTestHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.time.SimLifeDate;


public class BasicSun3DTest extends TestCase {

    private Sun3DState sun3DState;

    private BasicSun3D sun3D;

    private DirectionalLight sunLight;

    private PublisherTestHelper publisherHelper;

    private MockUniverse3D universe3D;

    protected void setUp() throws Exception {
        super.setUp();
        sun3DState = DisplayDataCreatorForTests.createRandomSun3DState();
        universe3D = new MockUniverse3D();
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);
        publisherHelper = new PublisherTestHelper();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sun3DState = null;
        sun3D = null;
        sunLight = null;
        publisherHelper = null;
        universe3D = null;
    }

    public void testBasicSun3D() {
        sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        SimLifeDate date = new SimLifeDate();
        date.setMinuteOfDay(10);
        universe3D.setDate(date);
        sun3D = new BasicSun3D(sun3DState, universe3D);

        // The earth rotation is automatic, the value from the state is not used. the value is recomputed from the date
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);
        assertEquals((float) (2 * Math.PI / 144), sun3D.getEarthRevolution(), 0.0001);
        assertTrue(sun3D.isEarthRotationTaskAutomatic());
        assertTrue(sun3D.isEarthRevolutionTaskAutomatic());
    }

    /**
     * Test that the sun3D is a publisher : it notifies its subscribers when the earth rotation change
     */
    public void testPublisher() {
        publisherHelper.addSubscriberTo(sun3D);

        sun3D.setEarthRevolution((float) Math.PI);
        assertEquals(3, publisherHelper.nbUpdated());
        assertEquals(SunUpdateCode.EARTH_REVOLUTION, publisherHelper.getUpdateObjects().get(0));
        assertEquals(SunUpdateCode.BRIGHTNESS, publisherHelper.getUpdateObjects().get(1));
        assertEquals(SunUpdateCode.COLOR, publisherHelper.getUpdateObjects().get(2));

        publisherHelper.reset();

        sun3D.setEarthRotation((float) Math.PI);
        assertEquals(3, publisherHelper.nbUpdated());
        assertEquals(SunUpdateCode.EARTH_ROTATION, publisherHelper.getUpdateObjects().get(0));
        assertEquals(SunUpdateCode.BRIGHTNESS, publisherHelper.getUpdateObjects().get(1));
        assertEquals(SunUpdateCode.COLOR, publisherHelper.getUpdateObjects().get(2));
    }

    public void testUpdateBrightness() {
        setToZenith();

        assertEquals(1f, sun3D.getBrightness().floatValue(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertEquals(0.5f, sun3D.getBrightness().floatValue(), 0.0001);

        sun3D.setEarthRotation(0f);
        assertEquals(0f, sun3D.getBrightness().floatValue(), 0.0001);
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
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);
    }


    public void testUpdateColor() {
        setToZenith();

        Color3f actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(1f, 1, 1), actualSunColor);

        sun3D.setEarthRotation((float) Math.PI / 2);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(0.5f, 0.0f, 0.0f), actualSunColor);

        sun3D.setEarthRotation(0f);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(0.0f, 0.0f, 0.0f), actualSunColor);


        sun3DState.setEarthRotation((float) Math.PI);
        sun3DState.setLatitude((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(
                new Color3f(1f, (float) Math.sqrt(Math.sqrt(2) / 2), (float) Math.sqrt(Math.sqrt(2) / 2)),
                actualSunColor);
    }

    public void testUpdateEarthRotation1() {
        // latitude = equator
        sun3DState.setLatitude(0);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        sun3D.setEarthRotation(0f);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 1, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sun rise
        sun3D.setEarthRotation((float) Math.PI / 2);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sun at its zenith
        sun3D.setEarthRotation((float) Math.PI);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -1, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 5/8
        sun3D.setEarthRotation((float) (5 * Math.PI / 4));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sunset
        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testUpdateEarthRotation2() {
        // latitude = 45�
        sun3DState.setLatitude((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        sun3D.setEarthRotation(0f);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, (float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sun rise
        sun3D.setEarthRotation((float) Math.PI / 2);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // noon
        sun3D.setEarthRotation((float) Math.PI);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -(float) Math.sqrt(2) / 2, (float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sunset
        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testUpdateEarthRotation3() {
        // latitude = 90�
        sun3DState.setLatitude((float) Math.PI / 2);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        sun3D.setEarthRotation(0f);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 0, -1);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sun rise
        sun3D.setEarthRotation((float) Math.PI / 2);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // noon
        sun3D.setEarthRotation((float) Math.PI);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 0, 1);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 5/8
        sun3D.setEarthRotation((float) (5 * Math.PI / 4));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, 0, (float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sunset
        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testUpdateEarthRevolution() {
        sun3DState.setEclipticObliquity((float) Math.PI / 2);
        // latitude = 90�
        sun3DState.setLatitude((float) Math.PI / 2);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // time of year = wim solstice
        sun3D.setEarthRevolution(0f);
        Color3f wimColor = new Color3f();
        sunLight.getColor(wimColor);
        assertEquals(0f, sun3D.getBrightness().floatValue(), 0.0001);

        // time of year = sprim equinox
        sun3D.setEarthRevolution((float) Math.PI / 2);
        Color3f sprimColor = new Color3f();
        sunLight.getColor(sprimColor);
        assertEquals(0.5f, sun3D.getBrightness().floatValue(), 0.0001);

        // time of year = sum solstice
        sun3D.setEarthRevolution((float) Math.PI);
        Color3f sumColor = new Color3f();
        sunLight.getColor(sumColor);
        assertEquals(1f, sun3D.getBrightness().floatValue(), 0.0001);

        // time of year = tom equinox
        sun3D.setEarthRevolution((float) (3 * Math.PI / 2));
        Color3f tomColor = new Color3f();
        sunLight.getColor(tomColor);
        assertEquals(0.5f, sun3D.getBrightness().floatValue(), 0.0001);

        ColorTestHelper.assertEquals(new Color3f(0, 0, 0), wimColor);
        ColorTestHelper.assertEquals(new Color3f(1, 1, 1), sumColor);
        ColorTestHelper.assertEquals(sprimColor, tomColor);
        assertFalse(wimColor.equals(sprimColor));
        assertFalse(wimColor.equals(tomColor));
    }

    public void testComputeSunHeightLatitude0() {
        // latitude = equator
        sun3DState.setLatitude(0);
        sun3DState.setEclipticObliquity((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // /////////////////////////////
        // time of year = sprim equinox
        sun3D.setEarthRevolution((float) Math.PI / 2);

        sun3D.setEarthRotation(0f);
        assertEquals(-1f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertEquals(-Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI);
        assertEquals(1f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertEquals(-1f, sun3D.getHeight(), 0.0001);

        // /////////////////////////////
        // time of year = sum solstice
        sun3D.setEarthRevolution((float) Math.PI);

        sun3D.setEarthRotation(0f);
        assertEquals(-Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertEquals(-0.5f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertEquals(0.5, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI);
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertEquals(-Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);
    }

    public void testComputeSunHeightLatitude45() {
        // latitude = middle of the planet
        sun3DState.setLatitude((float) Math.PI / 4);
        sun3DState.setEclipticObliquity((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // /////////////////////////////
        // time of year = sprim equinox
        sun3D.setEarthRevolution((float) Math.PI / 2);

        sun3D.setEarthRotation(0f);
        assertEquals(-Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertEquals(-0.5, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertEquals(0.5, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI);
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertEquals(-Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        // /////////////////////////////
        // time of year = sum solstice
        sun3D.setEarthRevolution((float) Math.PI);

        sun3D.setEarthRotation(0f);
        assertEquals(0, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertEquals(0.5f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI);
        assertEquals(1f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertEquals(0.5f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertEquals(0f, sun3D.getHeight(), 0.0001);
    }

    public void testComputeSunHeightLatitude90() {
        // latitude = north pole
        sun3DState.setLatitude((float) Math.PI / 2);
        sun3DState.setEclipticObliquity((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // /////////////////////////////
        // time of year = sprim equinox
        sun3D.setEarthRevolution((float) Math.PI / 2);

        sun3D.setEarthRotation(0f);
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI);
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertEquals(0f, sun3D.getHeight(), 0.0001);

        // /////////////////////////////
        // time of year = sum solstice
        sun3D.setEarthRevolution((float) Math.PI);

        sun3D.setEarthRotation(0f);
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 4);
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI / 2);
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) Math.PI);
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);

        sun3D.setEarthRotation((float) (2 * Math.PI));
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);
    }

    public void testGetWhiteFactor() {
        setToZenith();
        assertEquals(1f, sun3D.getHeight(), 0.0001);
        assertEquals(1f, sun3D.getWhiteFactor(), 0.001f);

        // time of day = sun rise
        sun3D.setEarthRotation((float) Math.PI / 2);
        assertEquals(0f, sun3D.getHeight(), 0.0001);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);

        // time of day = midnight
        sun3D.setEarthRotation(0f);
        assertEquals(-1f, sun3D.getHeight(), 0.0001);
        assertEquals(1f, sun3D.getWhiteFactor(), 0.001f);

        // time of day = sunset
        sun3D.setEarthRotation((float) (3 * Math.PI / 2));
        assertEquals(0f, sun3D.getHeight(), 0.0001);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);

        // time of day = middle of the morning
        sun3D.setEarthRotation((float) (3 * Math.PI / 4));
        assertEquals(Math.sqrt(2) / 2, sun3D.getHeight(), 0.0001);
        assertEquals(Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor(), 0.001f);
    }

    public void testGetState() {
        sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        SimLifeDate date = new SimLifeDate();
        date.setMinuteOfDay(10);
        universe3D.setDate(date);
        sun3D = new BasicSun3D(sun3DState, universe3D);

        assertEquals(sun3DState, sun3D.getState());
        assertSame(sun3DState, sun3D.getState());
        assertEquals((float) (Math.PI / 4), sun3D.getState().getLatitude(), 0.0001);
        assertEquals((float) (Math.PI / 3), sun3D.getState().getEclipticObliquity(), 0.0001);
        // The earth rotation is automatic, the value from the state is not used. the value is recomputed from the date
        assertEquals((float) Math.PI, sun3D.getState().getEarthRotation(), 0.0001);
        assertEquals((float) (2 * Math.PI / 144), sun3D.getState().getEarthRevolution(), 0.0001);
        assertTrue(sun3D.getState().isEarthRotationTaskAutomatic());
        assertTrue(sun3D.getState().isEarthRevolutionTaskAutomatic());

        sun3D.setEarthRotation(0.47f);
        sun3D.setEarthRevolution(0.39f);
        sun3D.setEarthRevolutionTaskAutomatic(false);
        sun3D.setEarthRotationTaskAutomatic(false);

        assertEquals(sun3DState, sun3D.getState());
        assertSame(sun3DState, sun3D.getState());
        assertEquals((float) (Math.PI / 4), sun3D.getState().getLatitude(), 0.0001);
        assertEquals((float) (Math.PI / 3), sun3D.getState().getEclipticObliquity(), 0.0001);
        assertEquals(0.47f, sun3D.getState().getEarthRotation(), 0.0001);
        assertEquals(0.39f, sun3D.getState().getEarthRevolution(), 0.0001);
        assertFalse(sun3D.getState().isEarthRotationTaskAutomatic());
        assertFalse(sun3D.getState().isEarthRevolutionTaskAutomatic());
    }

    public void testGetEarthRotation() {
        sun3D.setEarthRotation(0.3f);
        assertEquals(0.3f, sun3D.getEarthRotation(), 0.0001);
    }

    public void testGetEarthRevolution() {
        sun3D.setEarthRevolution(0.3f);
        assertEquals(0.3f, sun3D.getEarthRevolution(), 0.0001);
    }

    public void testAdjustEarthRotation() {
        sun3DState.setEarthRotationTaskAutomatic(false);
        sun3DState.setEarthRotation((float) Math.PI);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);

        sun3DState.setEarthRotation((float) (3 * Math.PI));
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);

        sun3DState.setEarthRotation((float) (5 * Math.PI));
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);

        sun3DState.setEarthRotation(-(float) Math.PI);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);

        sun3DState.setEarthRotation(-(float) (3 * Math.PI));
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);


        sun3D.setEarthRotation((float) Math.PI);
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);

        sun3D.setEarthRotation((float) (3 * Math.PI));
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);

        sun3D.setEarthRotation((float) (5 * Math.PI));
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);

        sun3D.setEarthRotation(-(float) Math.PI);
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);

        sun3D.setEarthRotation(-(float) (3 * Math.PI));
        assertEquals((float) Math.PI, sun3D.getEarthRotation(), 0.0001);
    }


    public void testAdjustEarthRevolution() {
        sun3DState.setEarthRevolutionTaskAutomatic(false);
        sun3DState.setEarthRevolution((float) Math.PI);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);

        sun3DState.setEarthRevolution((float) (3 * Math.PI));
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);

        sun3DState.setEarthRevolution((float) (5 * Math.PI));
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);

        sun3DState.setEarthRevolution(-(float) Math.PI);
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);

        sun3DState.setEarthRevolution(-(float) (3 * Math.PI));
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);


        sun3D.setEarthRevolution((float) Math.PI);
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);

        sun3D.setEarthRevolution((float) (3 * Math.PI));
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);

        sun3D.setEarthRevolution((float) (5 * Math.PI));
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);

        sun3D.setEarthRevolution(-(float) Math.PI);
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);

        sun3D.setEarthRevolution(-(float) (3 * Math.PI));
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0001);
    }

    public void testSetEarthRotationTaskAutomatic() {
        sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertTrue(sun3D.isEarthRotationTaskAutomatic());
        assertTrue(sun3D.getEarthRotationTask().isAutomatic());

        sun3D.setEarthRotationTaskAutomatic(true);
        assertTrue(sun3D.isEarthRotationTaskAutomatic());
        assertTrue(sun3D.getEarthRotationTask().isAutomatic());

        sun3D.setEarthRotationTaskAutomatic(false);
        assertFalse(sun3D.isEarthRotationTaskAutomatic());
        assertFalse(sun3D.getEarthRotationTask().isAutomatic());
    }

    public void testSetEarthRevolutionTaskAutomatic() {
        sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        sun3D = new BasicSun3D(sun3DState, universe3D);
        assertTrue(sun3D.isEarthRevolutionTaskAutomatic());
        assertTrue(sun3D.getEarthRevolutionTask().isAutomatic());

        sun3D.setEarthRevolutionTaskAutomatic(true);
        assertTrue(sun3D.isEarthRevolutionTaskAutomatic());
        assertTrue(sun3D.getEarthRevolutionTask().isAutomatic());

        sun3D.setEarthRevolutionTaskAutomatic(false);
        assertFalse(sun3D.isEarthRevolutionTaskAutomatic());
        assertFalse(sun3D.getEarthRevolutionTask().isAutomatic());
    }

}