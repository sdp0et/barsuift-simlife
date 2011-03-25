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
package barsuift.simLife.j2d.panel;

import junit.framework.TestCase;
import barsuift.simLife.MathHelper;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.environment.MockSun3D;


public class EarthRevolutionPanelTest extends TestCase {

    private MockSun3D sun3D;

    private EarthRevolutionPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        sun3D = new MockSun3D();
        display = new EarthRevolutionPanel(sun3D);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sun3D = null;
        display = null;
    }

    public void testInit() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
        sun3D.setEarthRevolution((float) (Math.PI * 2 * 0.9));
        display = new EarthRevolutionPanel(sun3D);
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
        sun3D.setEarthRevolution((float) (Math.PI * 2 * 0.8));
        display = new EarthRevolutionPanel(sun3D);
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
        sun3D.setEarthRevolution((float) (Math.PI * 2));
        display = new EarthRevolutionPanel(sun3D);
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
    }

    public void testUpdate() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
        sun3D.setEarthRevolution((float) (Math.PI * 2 * 0.9));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
        sun3D.setEarthRevolution((float) (Math.PI * 2 * 0.8));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
        sun3D.setEarthRevolution((float) (Math.PI * 2));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
    }


}
