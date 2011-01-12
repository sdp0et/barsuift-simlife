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
package barsuift.simLife.environment;

import junit.framework.TestCase;
import barsuift.simLife.PercentHelper;
import barsuift.simLife.message.PublisherTestHelper;


public class BasicSunTest extends TestCase {

    private PublisherTestHelper publisherHelper;

    private SunState sunState;

    private BasicSun sun;

    protected void setUp() throws Exception {
        super.setUp();
        sunState = new SunState();
        sun = new BasicSun(sunState);
        publisherHelper = new PublisherTestHelper();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sun = null;
        publisherHelper = null;
    }

    public void testConstructor() {
        try {
            new BasicSun(null);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        assertEquals(sunState, sun.getState());
        assertSame(sunState, sun.getState());
        assertEquals(0.0, sun.getState().getBrightness().doubleValue());
        assertEquals(0.0f, sun.getState().getRiseAngle(), 0.0001);
        assertEquals(0.0f, sun.getState().getZenithAngle(), 0.0001);

        sun.setBrightness(PercentHelper.getDecimalValue(32));
        sun.setRiseAngle(0.47f);
        sun.setZenithAngle(0.78f);
        assertEquals(sunState, sun.getState());
        assertSame(sunState, sun.getState());
        assertEquals(0.32, sun.getState().getBrightness().doubleValue());
        assertEquals(0.47f, sun.getState().getRiseAngle(), 0.0001);
        assertEquals(0.78f, sun.getState().getZenithAngle(), 0.0001);

    }

    public void testGetBrightness() {
        sun.setBrightness(PercentHelper.getDecimalValue(30));
        assertEquals(PercentHelper.getDecimalValue(30), sun.getBrightness());
        try {
            sun.setBrightness(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetRiseAngle() {
        sun.setRiseAngle(0.3f);
        assertEquals(0.3f, sun.getRiseAngle(), 0.0001);
    }

    public void testGetZenithAngle() {
        sun.setZenithAngle(0.3f);
        assertEquals(0.3f, sun.getZenithAngle(), 0.0001);
    }

    public void testSubscriberBrightness() {
        publisherHelper.addSubscriberTo(sun);
        assertEquals(sunState.getBrightness(), sun.getBrightness());
        sun.setBrightness(PercentHelper.getDecimalValue(90));
        assertEquals(PercentHelper.getDecimalValue(90), sun.getBrightness());
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(SunUpdateCode.brightness, publisherHelper.getUpdateObjects().get(0));
    }

    public void testSubscriberBrightnessUnchanged() {
        publisherHelper.addSubscriberTo(sun);
        assertEquals(sunState.getBrightness(), sun.getBrightness());
        sun.setBrightness(sunState.getBrightness());
        assertEquals(sunState.getBrightness(), sun.getBrightness());
        assertEquals("The subscriber should not be notified when setting the same value as before", 0,
                publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testSubscriberRiseAngle() {
        publisherHelper.addSubscriberTo(sun);
        assertEquals(sunState.getRiseAngle(), sun.getRiseAngle());
        sun.setRiseAngle(0.5f);
        assertEquals(0.5f, sun.getRiseAngle(), 0.0001);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(SunUpdateCode.riseAngle, publisherHelper.getUpdateObjects().get(0));
    }

    public void testSubscriberZenithAngle() {
        publisherHelper.addSubscriberTo(sun);
        assertEquals(sunState.getZenithAngle(), sun.getZenithAngle());
        sun.setZenithAngle(0.75f);
        assertEquals(0.75f, sun.getZenithAngle(), 0.0001);
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(SunUpdateCode.zenithAngle, publisherHelper.getUpdateObjects().get(0));
    }

}
