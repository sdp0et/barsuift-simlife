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
import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.helper.ColorTestHelper;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.VectorTestHelper;
import barsuift.simLife.message.PublisherTestHelper;


public class BasicSun3DTest extends TestCase {

    private MockSun mockSun;

    private Sun3DState sun3DState;

    private BasicSun3D sun3D;

    private DirectionalLight sunLight;

    private PublisherTestHelper publisherHelper;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun();
        sun3DState = DisplayDataCreatorForTests.createRandomSun3DState();
        sun3D = new BasicSun3D(sun3DState, mockSun);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);
        publisherHelper = new PublisherTestHelper();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        sun3DState = null;
        sun3D = null;
        sunLight = null;
        publisherHelper = null;
    }

    /**
     * Test that the sun3D is subscribed to the sun
     */
    public void testSubscribers() {
        assertEquals(1, mockSun.countSubscribers());
        // check the subscriber is the sun3D
        mockSun.deleteSubscriber(sun3D);
        assertEquals(0, mockSun.countSubscribers());
    }

    /**
     * Test that the sun3D is a publisher : it notifies its subscribers when the color change
     */
    public void testPublisher() {
        publisherHelper.addSubscriberTo(sun3D);
        // force computation of angles in the sun, and so for color in sun3D
        mockSun.setEarthRotation(0.5f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);

        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(SunUpdateCode.COLOR, publisherHelper.getUpdateObjects().get(0));
    }

    public void testUpdateBrightness() {
        setToZenith();

        Color3f actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(1, 1, 1), actualSunColor);

        mockSun.setBrightness(PercentHelper.getDecimalValue(40));
        sun3D.update(mockSun, SunUpdateCode.BRIGHTNESS);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(0.4f, 0.4f, 0.4f), actualSunColor);

        mockSun.setBrightness(PercentHelper.getDecimalValue(70));
        sun3D.update(mockSun, SunUpdateCode.BRIGHTNESS);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(0.7f, 0.7f, 0.7f), actualSunColor);
    }

    /**
     * set position at perfect zenith
     */
    private void setToZenith() {
        // time of day = noon
        mockSun.setEarthRotation(0.5f);
        // time of year = sprim equinox
        mockSun.setEarthRevolution(0.25f);
        // latitude = equator
        sun3DState.setLatitude(0);
        sun3D = new BasicSun3D(sun3DState, mockSun);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);
    }


    public void testUpdateColor() {
        setToZenith();

        Color3f actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(1f, 1, 1), actualSunColor);

        mockSun.setBrightness(PercentHelper.getDecimalValue(50));
        sun3D.update(mockSun, SunUpdateCode.BRIGHTNESS);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(0.5f, 0.5f, 0.5f), actualSunColor);

        mockSun.setBrightness(PercentHelper.getDecimalValue(100));
        sun3D.update(mockSun, SunUpdateCode.BRIGHTNESS);
        mockSun.setEarthRotation(0.25f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        ColorTestHelper.assertEquals(new Color3f(1f, 0f, 0f), actualSunColor);


        mockSun.setEarthRotation(0.5f);
        sun3DState.setLatitude((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState, mockSun);
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
        sun3D = new BasicSun3D(sun3DState, mockSun);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        mockSun.setEarthRotation(0f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 1, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sun rise
        mockSun.setEarthRotation(0.25f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sun at its zenith
        mockSun.setEarthRotation(0.5f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -1, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 5/8
        mockSun.setEarthRotation(0.625f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sunset
        mockSun.setEarthRotation(0.75f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testUpdateEarthRotation2() {
        // latitude = 45°
        sun3DState.setLatitude((float) Math.PI / 4);
        sun3D = new BasicSun3D(sun3DState, mockSun);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        mockSun.setEarthRotation(0f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, (float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sun rise
        mockSun.setEarthRotation(0.25f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // noon
        mockSun.setEarthRotation(0.5f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -(float) Math.sqrt(2) / 2, (float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sunset
        mockSun.setEarthRotation(0.75f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testUpdateEarthRotation3() {
        // latitude = 90°
        sun3DState.setLatitude((float) Math.PI / 2);
        sun3D = new BasicSun3D(sun3DState, mockSun);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        Vector3f actualDirection = new Vector3f();
        Vector3f expectedDirection;

        // the sun is at its nadir
        mockSun.setEarthRotation(0f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 0, -1);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sun rise
        mockSun.setEarthRotation(0.25f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // noon
        mockSun.setEarthRotation(0.5f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 0, 1);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 5Pi/8
        mockSun.setEarthRotation(0.625f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, 0, (float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // sunset
        mockSun.setEarthRotation(0.75f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testUpdateEarthRevolution() {
        // latitude = 90°
        sun3DState.setLatitude((float) Math.PI / 2);
        sun3D = new BasicSun3D(sun3DState, mockSun);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);

        // time of year = wim solstice
        mockSun.setEarthRevolution(0f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_REVOLUTION);
        Color3f wimColor = new Color3f();
        sunLight.getColor(wimColor);

        // time of year = sprim equinox
        mockSun.setEarthRevolution(0.25f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_REVOLUTION);
        Color3f sprimColor = new Color3f();
        sunLight.getColor(sprimColor);

        // time of year = sum solstice
        mockSun.setEarthRevolution(0.5f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_REVOLUTION);
        Color3f sumColor = new Color3f();
        sunLight.getColor(sumColor);

        // time of year = tom equinox
        mockSun.setEarthRevolution(0.75f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_REVOLUTION);
        Color3f tomColor = new Color3f();
        sunLight.getColor(tomColor);

        ColorTestHelper.assertEquals(wimColor, sumColor);
        ColorTestHelper.assertEquals(sprimColor, tomColor);
        assertFalse(wimColor.equals(sprimColor));
        assertFalse(sumColor.equals(tomColor));
    }

    // FIXME 000. 002. fix test
    public void testGetWhiteFactor() {
        assertEquals(0.375f, mockSun.getEarthRotation(), 0.0001f);
        assertEquals(0.5f, mockSun.getZenithAngle(), 0.0001f);
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor(), 0.0001f);

        mockSun.setZenithAngle(0f);
        sun3D.update(mockSun, SunUpdateCode.ZENITH_ANGLE);
        mockSun.setEarthRotation(0.25f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.375f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.5f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.625f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.75f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);

        mockSun.setZenithAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.ZENITH_ANGLE);
        mockSun.setEarthRotation(0.25f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.375f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.5f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.625f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.75f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);


        mockSun.setZenithAngle(1f);
        sun3D.update(mockSun, SunUpdateCode.ZENITH_ANGLE);
        mockSun.setEarthRotation(0.25f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.375f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.5f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(1f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.625f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor(), 0.001f);
        mockSun.setEarthRotation(0.75f);
        sun3D.update(mockSun, SunUpdateCode.EARTH_ROTATION);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
    }
}
