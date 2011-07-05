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
import barsuift.simLife.universe.MockUniverse;


public class BasicSunTest extends TestCase {

    private SunState sunState;

    private BasicSun sun;

    protected void setUp() throws Exception {
        super.setUp();
        sunState = new SunState();
        sun = new BasicSun(sunState, new MockUniverse());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sun = null;
    }

    public void testConstructor() {
        try {
            new BasicSun(null, new MockUniverse());
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        assertEquals(sunState, sun.getState());
        assertSame(sunState, sun.getState());
        assertEquals(0.0f, sun.getState().getSun3DState().getEarthRevolution());

        sun.getSun3D().setEarthRevolution(0.123f);
        assertEquals(sunState, sun.getState());
        assertSame(sunState, sun.getState());
        assertEquals(0.123f, sun.getState().getSun3DState().getEarthRevolution());
    }

}