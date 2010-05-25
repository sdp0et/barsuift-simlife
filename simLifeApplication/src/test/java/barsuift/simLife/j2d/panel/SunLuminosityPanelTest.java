/**
 * barsuift-simlife is a life simulator programm
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
import barsuift.simLife.Percent;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.environment.SunUpdateCode;


public class SunLuminosityPanelTest extends TestCase {

    private MockSun mockSun;

    private SunLuminosityPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun();
        display = new SunLuminosityPanel(mockSun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        display = null;
    }

    public void testInit() {
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(90));
        display = new SunLuminosityPanel(mockSun);
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(80));
        display = new SunLuminosityPanel(mockSun);
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(100));
        display = new SunLuminosityPanel(mockSun);
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
    }

    public void testUpdate() {
        assertEquals("Sun luminosity (100.00%)", display.getLabel().getText());
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(90));
        display.update(mockSun, SunUpdateCode.luminosity);
        assertEquals("Sun luminosity (90.00%)", display.getLabel().getText());
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(90));
        display.update(mockSun, SunUpdateCode.luminosity);
        assertEquals("Sun luminosity (90.00%)", display.getLabel().getText());
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
        mockSun.setLuminosity(new Percent(100));
        display.update(mockSun, SunUpdateCode.luminosity);
        assertEquals("Sun luminosity (100.00%)", display.getLabel().getText());
        assertEquals(mockSun.getLuminosity(), new Percent(display.getSlider().getValue()));
    }

}
