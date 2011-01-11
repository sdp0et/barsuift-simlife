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


public class SunZenithPanelTest extends TestCase {

    private MockSun mockSun;

    private SunZenithPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun();
        display = new SunZenithPanel(mockSun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        display = null;
    }

    public void testInit() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals(mockSun.getZenithAngle().floatValue(),
                PercentHelper.getDecimalValue(display.getSlider().getValue()).floatValue(), 0.50001);
        mockSun.setZenithAngle(PercentHelper.getDecimalValue(90));
        display = new SunZenithPanel(mockSun);
        assertEquals(mockSun.getZenithAngle(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setZenithAngle(PercentHelper.getDecimalValue(80));
        display = new SunZenithPanel(mockSun);
        assertEquals(mockSun.getZenithAngle(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setZenithAngle(PercentHelper.getDecimalValue(100));
        display = new SunZenithPanel(mockSun);
        assertEquals(mockSun.getZenithAngle(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
    }

    public void testUpdate() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals(mockSun.getZenithAngle().floatValue(),
                PercentHelper.getDecimalValue(display.getSlider().getValue()).floatValue(), 0.50001);
        mockSun.setZenithAngle(PercentHelper.getDecimalValue(90));
        display.update(mockSun, SunUpdateCode.zenithAngle);
        assertEquals(mockSun.getZenithAngle(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setZenithAngle(PercentHelper.getDecimalValue(90));
        display.update(mockSun, SunUpdateCode.zenithAngle);
        assertEquals(mockSun.getZenithAngle(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setZenithAngle(PercentHelper.getDecimalValue(100));
        display.update(mockSun, SunUpdateCode.zenithAngle);
        assertEquals(mockSun.getZenithAngle(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
    }

}
