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
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.universe.environment.MockSun3D;


public class SunColorPanelTest extends TestCase {

    private MockSun3D mockSun;

    private SunColorPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        mockSun = new MockSun3D();
        display = new SunColorPanel(mockSun);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        mockSun = null;
        display = null;
    }

    public void testInit() {
        assertEquals(mockSun.getWhiteFactor(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setWhiteFactor(PercentHelper.getDecimalValue(90));
        display = new SunColorPanel(mockSun);
        assertEquals(mockSun.getWhiteFactor(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setWhiteFactor(PercentHelper.getDecimalValue(80));
        display = new SunColorPanel(mockSun);
        assertEquals(mockSun.getWhiteFactor(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setWhiteFactor(PercentHelper.getDecimalValue(100));
        display = new SunColorPanel(mockSun);
        assertEquals(mockSun.getWhiteFactor(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
    }

    public void testUpdate() {
        assertEquals(mockSun.getWhiteFactor(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setWhiteFactor(PercentHelper.getDecimalValue(90));
        display.update(mockSun, SunUpdateCode.color);
        assertEquals(mockSun.getWhiteFactor(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setWhiteFactor(PercentHelper.getDecimalValue(90));
        display.update(mockSun, SunUpdateCode.color);
        assertEquals(mockSun.getWhiteFactor(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
        mockSun.setWhiteFactor(PercentHelper.getDecimalValue(100));
        display.update(mockSun, SunUpdateCode.color);
        assertEquals(mockSun.getWhiteFactor(), PercentHelper.getDecimalValue(display.getSlider().getValue()));
    }

}
