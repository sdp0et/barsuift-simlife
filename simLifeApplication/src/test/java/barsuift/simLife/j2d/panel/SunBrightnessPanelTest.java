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
import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.environment.SunUpdateCode;


public class SunBrightnessPanelTest extends TestCase {

    private MockSun mockSun;

    private SunBrightnessPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun();
        display = new SunBrightnessPanel(mockSun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        display = null;
    }

    public void testInit() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals(mockSun.getBrightness().floatValue(), PercentHelper
                .getDecimalValue(display.getSlider().getValue()).floatValue(), 0.0050001);
        mockSun.setBrightness(PercentHelper.getDecimalValue(90));
        display = new SunBrightnessPanel(mockSun);
        assertEquals(mockSun.getBrightness(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setBrightness(PercentHelper.getDecimalValue(80));
        display = new SunBrightnessPanel(mockSun);
        assertEquals(mockSun.getBrightness(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setBrightness(PercentHelper.getDecimalValue(100));
        display = new SunBrightnessPanel(mockSun);
        assertEquals(mockSun.getBrightness(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
    }

    public void testUpdate() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals("Sun brightness (100.00%)", display.getLabel().getText());
        assertEquals(mockSun.getBrightness().floatValue(), PercentHelper
                .getDecimalValue(display.getSlider().getValue()).floatValue(), 0.0050001);
        mockSun.setBrightness(PercentHelper.getDecimalValue(90));
        display.update(mockSun, SunUpdateCode.BRIGHTNESS);
        assertEquals("Sun brightness (90.00%)", display.getLabel().getText());
        assertEquals(mockSun.getBrightness(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setBrightness(PercentHelper.getDecimalValue(90));
        display.update(mockSun, SunUpdateCode.BRIGHTNESS);
        assertEquals("Sun brightness (90.00%)", display.getLabel().getText());
        assertEquals(mockSun.getBrightness(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setBrightness(PercentHelper.getDecimalValue(100));
        display.update(mockSun, SunUpdateCode.BRIGHTNESS);
        assertEquals("Sun brightness (100.00%)", display.getLabel().getText());
        assertEquals(mockSun.getBrightness(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
    }

}
