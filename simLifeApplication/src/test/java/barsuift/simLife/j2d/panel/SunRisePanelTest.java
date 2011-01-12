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
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.environment.SunUpdateCode;


public class SunRisePanelTest extends TestCase {

    private MockSun sun;

    private SunRisePanel display;

    protected void setUp() throws Exception {
        super.setUp();
        sun = new MockSun();
        display = new SunRisePanel(sun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sun = null;
        display = null;
    }

    public void testInit() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals(sun.getRiseAngle(), (float) display.getSlider().getValue() / 100, 0.0050001);
        sun.setRiseAngle(0.9f);
        display = new SunRisePanel(sun);
        assertEquals(sun.getRiseAngle(), (float) display.getSlider().getValue() / 100, 0.0050001);
        sun.setRiseAngle(0.8f);
        display = new SunRisePanel(sun);
        assertEquals(sun.getRiseAngle(), (float) display.getSlider().getValue() / 100, 0.0050001);
        sun.setRiseAngle(1f);
        display = new SunRisePanel(sun);
        assertEquals(sun.getRiseAngle(), (float) display.getSlider().getValue() / 100, 0.0050001);
    }

    public void testUpdate() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals(sun.getRiseAngle(), (float) display.getSlider().getValue() / 100, 0.0050001);
        sun.setRiseAngle(0.9f);
        display.update(sun, SunUpdateCode.riseAngle);
        assertEquals(sun.getRiseAngle(), (float) display.getSlider().getValue() / 100, 0.0050001);
        sun.setRiseAngle(0.8f);
        display.update(sun, SunUpdateCode.riseAngle);
        assertEquals(sun.getRiseAngle(), (float) display.getSlider().getValue() / 100, 0.0050001);
        sun.setRiseAngle(1f);
        display.update(sun, SunUpdateCode.riseAngle);
        assertEquals(sun.getRiseAngle(), (float) display.getSlider().getValue() / 100, 0.0050001);
    }


}
