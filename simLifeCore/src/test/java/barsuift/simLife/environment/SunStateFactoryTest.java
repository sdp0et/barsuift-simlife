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

import java.math.BigDecimal;

import junit.framework.TestCase;


public class SunStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateSunState() {
        SunStateFactory factory = new SunStateFactory();
        SunState sunState = factory.createSunState();
        BigDecimal luminosity = sunState.getLuminosity();
        assertEquals(1.00, luminosity.doubleValue());
        BigDecimal riseAngle = sunState.getRiseAngle();
        assertEquals(0.25, riseAngle.doubleValue());
        BigDecimal zenithAngle = sunState.getZenithAngle();
        assertEquals(0.50, zenithAngle.doubleValue());
    }

}
