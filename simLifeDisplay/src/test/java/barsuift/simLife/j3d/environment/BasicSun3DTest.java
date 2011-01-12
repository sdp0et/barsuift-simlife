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
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.VectorTestHelper;
import barsuift.simLife.message.PublisherTestHelper;


public class BasicSun3DTest extends TestCase {

    private MockSun mockSun;

    private BasicSun3D sun3D;

    private DirectionalLight sunLight;

    private PublisherTestHelper publisherHelper;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun();
        sun3D = new BasicSun3D(mockSun);
        sunLight = sun3D.getLight();
        CompilerHelper.compile(sunLight);
        publisherHelper = new PublisherTestHelper();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        sun3D = null;
        sunLight = null;
        publisherHelper = null;
    }

    /**
     * Test that the sun3D is subscribed to the sun
     */
    public void testSubscribers() {
        assertEquals(1, mockSun.countSubscribers());
        // check the subscriber is the sunLight
        mockSun.deleteSubscriber(sun3D);
        assertEquals(0, mockSun.countSubscribers());
    }

    /**
     * Test that the sun3D is a publisher : it notifies its subscribers when the color change
     */
    public void testPublisher() {
        publisherHelper.addSubscriberTo(sun3D);
        // force computation of angles in the sun, and so for color in sun3D
        mockSun.setZenithAngle(1f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);

        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(SunUpdateCode.color, publisherHelper.getUpdateObjects().get(0));
    }

    public void testUpdateBrightness() {
        // set position at perfect zenith
        mockSun.setRiseAngle(0.5f);
        mockSun.setZenithAngle(1f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);

        Color3f actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1, 1, 1), actualSunColor);

        mockSun.setBrightness(PercentHelper.getDecimalValue(40));
        sun3D.update(mockSun, SunUpdateCode.brightness);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(0.4f, 0.4f, 0.4f), actualSunColor);

        mockSun.setBrightness(PercentHelper.getDecimalValue(70));
        sun3D.update(mockSun, SunUpdateCode.brightness);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(0.7f, 0.7f, 0.7f), actualSunColor);
    }


    public void testUpdateColor() {
        Color3f actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1f, (float) Math.sqrt(2) / 2, (float) Math.sqrt(2) / 2), actualSunColor);

        mockSun.setBrightness(PercentHelper.getDecimalValue(50));
        sun3D.update(mockSun, SunUpdateCode.brightness);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(0.5f, (float) (0.5 * Math.sqrt(2) / 2), (float) (0.5 * Math.sqrt(2) / 2)),
                actualSunColor);

        mockSun.setBrightness(PercentHelper.getDecimalValue(100));
        sun3D.update(mockSun, SunUpdateCode.brightness);
        mockSun.setRiseAngle(0.25f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1f, 0f, 0f), actualSunColor);

        mockSun.setRiseAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1f, (float) Math.sqrt(Math.sqrt(2) / 2), (float) Math.sqrt(Math.sqrt(2) / 2)),
                actualSunColor);

        mockSun.setZenithAngle(1f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        actualSunColor = new Color3f();
        sunLight.getColor(actualSunColor);
        assertEquals(new Color3f(1f, 1f, 1f), actualSunColor);
    }

    public void testUpdateRiseAngle1() {
        // rise = Pi/4
        // zenith = Pi/4
        assertEquals(0.375f, mockSun.getRiseAngle(), 0.0001f);
        assertEquals(0.5f, mockSun.getZenithAngle(), 0.0001f);
        Vector3f actualDirection = new Vector3f();
        sunLight.getDirection(actualDirection);
        Vector3f expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, -0.5f, -0.5f);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setRiseAngle(0.25f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setRiseAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -(float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setRiseAngle(0.625f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, -0.5f, -0.5f);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setRiseAngle(0.75f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    /**
     * Test rise angle with zenith angle set to 0
     */
    public void testUpdateRiseAngle2() {
        mockSun.setZenithAngle(0f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        // Pi/4
        assertEquals(0.375f, mockSun.getRiseAngle(), 0.0001f);
        Vector3f actualDirection = new Vector3f();
        sunLight.getDirection(actualDirection);
        Vector3f expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, 0f, (float) -Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 0 Pi
        mockSun.setRiseAngle(0.25f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // Pi/2
        mockSun.setRiseAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, 0, -1);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 3Pi/4
        mockSun.setRiseAngle(0.625f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, 0, -(float) Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // Pi
        mockSun.setRiseAngle(0.75f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    /**
     * Test rise angle with zenith angle set to 100% (Pi/2)
     */
    public void testUpdateRiseAngle3() {
        mockSun.setZenithAngle(1f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        // Pi/4
        assertEquals(0.375f, mockSun.getRiseAngle(), 0.0001f);
        Vector3f actualDirection = new Vector3f();
        sunLight.getDirection(actualDirection);
        Vector3f expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 0 Pi
        mockSun.setRiseAngle(0.25f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // Pi/2
        mockSun.setRiseAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(0, -1, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // 3Pi/4
        mockSun.setRiseAngle(0.625f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-(float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        // Pi
        mockSun.setRiseAngle(0.75f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f(-1, 0, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testUpdateZenithAngle() {
        assertEquals(0.375f, mockSun.getRiseAngle(), 0.0001f);
        assertEquals(0.5f, mockSun.getZenithAngle(), 0.0001f);
        Vector3f actualDirection = new Vector3f();
        sunLight.getDirection(actualDirection);
        Vector3f expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, -0.5f, -0.5f);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setZenithAngle(0f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, 0f, (float) -Math.sqrt(2) / 2);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);

        mockSun.setZenithAngle(1f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        sunLight.getDirection(actualDirection);
        expectedDirection = new Vector3f((float) Math.sqrt(2) / 2, -(float) Math.sqrt(2) / 2, 0);
        expectedDirection.normalize();
        VectorTestHelper.assertVectorEquals(expectedDirection, actualDirection);
    }

    public void testGetWhiteFactor() {
        assertEquals(0.375f, mockSun.getRiseAngle(), 0.0001f);
        assertEquals(0.5f, mockSun.getZenithAngle(), 0.0001f);
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor(), 0.0001f);

        mockSun.setZenithAngle(0f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        mockSun.setRiseAngle(0.25f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.375f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.625f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.75f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);

        mockSun.setZenithAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        mockSun.setRiseAngle(0.25f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.375f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.625f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(2) / 2, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.75f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);


        mockSun.setZenithAngle(1f);
        sun3D.update(mockSun, SunUpdateCode.zenithAngle);
        mockSun.setRiseAngle(0.25f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.375f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.5f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(1f, sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.625f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals((float) Math.sqrt(Math.sqrt(2) / 2), sun3D.getWhiteFactor(), 0.001f);
        mockSun.setRiseAngle(0.75f);
        sun3D.update(mockSun, SunUpdateCode.riseAngle);
        assertEquals(0f, sun3D.getWhiteFactor(), 0.001f);
    }
}
