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

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.universe.MockUniverse;

import static org.fest.assertions.Assertions.assertThat;


public class BasicSunTest {

    private SunState sunState;

    private BasicSun sun;

    @BeforeMethod
    protected void setUp() {
        sunState = new SunState();
        sun = new BasicSun(sunState);
        sun.init(new MockUniverse());
    }

    @AfterMethod
    protected void tearDown() {
        sun = null;
    }

    @Test
    public void testConstructor() {
        try {
            new BasicSun(null);
            Assert.fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    @Test
    public void testGetState() {
        assertThat(sun.getState()).isEqualTo(sunState);
        assertThat(sun.getState()).isSameAs(sunState);
        assertThat(sun.getState().getSun3DState().getEarthRevolution()).isEqualTo(0.0f);

        sun.getSun3D().setEarthRevolution(0.123f);
        assertThat(sun.getState()).isEqualTo(sunState);
        assertThat(sun.getState()).isSameAs(sunState);
        assertThat(sun.getState().getSun3DState().getEarthRevolution()).isEqualTo(0.123f);
    }

}
