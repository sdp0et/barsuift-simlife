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

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.environment.MockSun3D;

import static org.fest.assertions.Assertions.assertThat;


public class SunColorPanelTest {

    private MockSun3D mockSun;

    private SunColorPanel display;

    @BeforeMethod
    protected void setUp() {
        mockSun = new MockSun3D();
        display = new SunColorPanel(mockSun);
    }

    @AfterMethod
    protected void tearDown() {
        mockSun = null;
        display = null;
    }

    @Test
    public void testInit() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertThat((float) display.getSlider().getValue() / 100).isEqualTo(mockSun.getWhiteFactor(),
                Delta.delta(0.0050001));
        mockSun.setWhiteFactor(0.9f);
        display = new SunColorPanel(mockSun);
        assertThat((float) display.getSlider().getValue() / 100).isEqualTo(mockSun.getWhiteFactor(),
                Delta.delta(0.0050001));
        mockSun.setWhiteFactor(0.8f);
        display = new SunColorPanel(mockSun);
        assertThat((float) display.getSlider().getValue() / 100).isEqualTo(mockSun.getWhiteFactor(),
                Delta.delta(0.0050001));
        mockSun.setWhiteFactor(1f);
        display = new SunColorPanel(mockSun);
        assertThat((float) display.getSlider().getValue() / 100).isEqualTo(mockSun.getWhiteFactor(),
                Delta.delta(0.0050001));
    }

    @Test
    public void testUpdate() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertThat((float) display.getSlider().getValue() / 100).isEqualTo(mockSun.getWhiteFactor(),
                Delta.delta(0.0050001));
        mockSun.setWhiteFactor(0.9f);
        display.update(mockSun, SunUpdateCode.COLOR);
        assertThat((float) display.getSlider().getValue() / 100).isEqualTo(mockSun.getWhiteFactor(),
                Delta.delta(0.0050001));
        mockSun.setWhiteFactor(0.8f);
        display.update(mockSun, SunUpdateCode.COLOR);
        assertThat((float) display.getSlider().getValue() / 100).isEqualTo(mockSun.getWhiteFactor(),
                Delta.delta(0.0050001));
        mockSun.setWhiteFactor(1f);
        display.update(mockSun, SunUpdateCode.COLOR);
        assertThat((float) display.getSlider().getValue() / 100).isEqualTo(mockSun.getWhiteFactor(),
                Delta.delta(0.0050001));
    }

}
