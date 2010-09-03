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
import barsuift.simLife.time.ObservableTestHelper;


public class BasicSunTest extends TestCase {

    private ObservableTestHelper observerHelper;

    private SunState sunState;

    private BasicSun sun;

    protected void setUp() throws Exception {
        super.setUp();
        sunState = new SunState();
        sun = new BasicSun(sunState);
        observerHelper = new ObservableTestHelper();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sun = null;
        observerHelper = null;
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
        sunState = new SunState(PercentHelper.getDecimalValue(32), PercentHelper.getDecimalValue(47),
                PercentHelper.getDecimalValue(78));
        sun = new BasicSun(sunState);
        assertEquals(sunState, sun.getState());
    }

    public void testGetLuminosity() {
        sun.setLuminosity(PercentHelper.getDecimalValue(30));
        assertEquals(PercentHelper.getDecimalValue(30), sun.getLuminosity());
        try {
            sun.setLuminosity(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetRiseAngle() {
        sun.setRiseAngle(PercentHelper.getDecimalValue(30));
        assertEquals(PercentHelper.getDecimalValue(30), sun.getRiseAngle());
        try {
            sun.setRiseAngle(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetZenithAngle() {
        sun.setZenithAngle(PercentHelper.getDecimalValue(30));
        assertEquals(PercentHelper.getDecimalValue(30), sun.getZenithAngle());
        try {
            sun.setZenithAngle(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testObserverLuminosity() {
        observerHelper.addObserver(sun);
        assertEquals(sunState.getLuminosity(), sun.getLuminosity());
        sun.setLuminosity(PercentHelper.getDecimalValue(90));
        assertEquals(PercentHelper.getDecimalValue(90), sun.getLuminosity());
        assertEquals(1, observerHelper.nbUpdated());
        assertEquals(SunUpdateCode.luminosity, observerHelper.getUpdateObjects().get(0));
    }

    public void testObserverLuminosityUnchanged() {
        observerHelper.addObserver(sun);
        assertEquals(sunState.getLuminosity(), sun.getLuminosity());
        sun.setLuminosity(sunState.getLuminosity());
        assertEquals(sunState.getLuminosity(), sun.getLuminosity());
        assertEquals("The observer should not be notified when setting the same value as before", 0,
                observerHelper.nbUpdated());
        assertEquals(0, observerHelper.getUpdateObjects().size());
    }

    public void testObserverRiseAngle() {
        observerHelper.addObserver(sun);
        assertEquals(sunState.getRiseAngle(), sun.getRiseAngle());
        sun.setRiseAngle(PercentHelper.getDecimalValue(50));
        assertEquals(PercentHelper.getDecimalValue(50), sun.getRiseAngle());
        assertEquals(1, observerHelper.nbUpdated());
        assertEquals(SunUpdateCode.riseAngle, observerHelper.getUpdateObjects().get(0));
    }

    public void testObserverRiseAngleUnchanged() {
        observerHelper.addObserver(sun);
        assertEquals(sunState.getRiseAngle(), sun.getRiseAngle());
        sun.setRiseAngle(sunState.getRiseAngle());
        assertEquals(sunState.getRiseAngle(), sun.getRiseAngle());
        assertEquals("The observer should not be notified when setting the same value as before", 0,
                observerHelper.nbUpdated());
        assertEquals(0, observerHelper.getUpdateObjects().size());
    }

    public void testObserverZenithAngle() {
        observerHelper.addObserver(sun);
        assertEquals(sunState.getZenithAngle(), sun.getZenithAngle());
        sun.setZenithAngle(PercentHelper.getDecimalValue(75));
        assertEquals(PercentHelper.getDecimalValue(75), sun.getZenithAngle());
        assertEquals(1, observerHelper.nbUpdated());
        assertEquals(SunUpdateCode.zenithAngle, observerHelper.getUpdateObjects().get(0));
    }

    public void testObserverZenithAngleUnchanged() {
        observerHelper.addObserver(sun);
        assertEquals(sunState.getZenithAngle(), sun.getZenithAngle());
        sun.setZenithAngle(sunState.getZenithAngle());
        assertEquals(sunState.getZenithAngle(), sun.getZenithAngle());
        assertEquals("The observer should not be notified when setting the same value as before", 0,
                observerHelper.nbUpdated());
        assertEquals(0, observerHelper.getUpdateObjects().size());
    }

}
