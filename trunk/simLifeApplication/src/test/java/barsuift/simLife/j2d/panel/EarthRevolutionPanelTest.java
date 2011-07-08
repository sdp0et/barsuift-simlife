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

import barsuift.simLife.MathHelper;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.environment.MockSun3D;

import static org.fest.assertions.Assertions.assertThat;


public class EarthRevolutionPanelTest {

    private MockSun3D sun3D;

    private EarthRevolutionPanel display;

    @BeforeMethod
    protected void setUp() {
        sun3D = new MockSun3D();
        display = new EarthRevolutionPanel(sun3D);
    }

    @AfterMethod
    protected void tearDown() {
        sun3D = null;
        display = null;
    }

    @Test
    public void testInit() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertThat(display.getText()).isEqualTo("Earth revolution (180°)");
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo(sun3D.getEarthRevolution(),
                Delta.delta(0.0050001));

        sun3D.setEarthRevolution((float) (Math.PI * 2));
        display = new EarthRevolutionPanel(sun3D);
        assertThat(display.getText()).isEqualTo("Earth revolution (360°)");
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo(sun3D.getEarthRevolution(),
                Delta.delta(0.0050001));

        sun3D.setEarthRevolution((float) (Math.PI / 2));
        display = new EarthRevolutionPanel(sun3D);
        assertThat(display.getText()).isEqualTo("Earth revolution (90°)");
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo(sun3D.getEarthRevolution(),
                Delta.delta(0.0050001));

        sun3D.setEarthRevolution((float) (Math.PI / 4));
        display = new EarthRevolutionPanel(sun3D);
        assertThat(display.getText()).isEqualTo("Earth revolution (45°)");
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo(sun3D.getEarthRevolution(),
                Delta.delta(0.0050001));
    }

    @Test
    public void testUpdate() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertThat(display.getText()).isEqualTo("Earth revolution (180°)");
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo(sun3D.getEarthRevolution(),
                Delta.delta(0.0050001));

        sun3D.setEarthRevolution((float) (Math.PI * 2));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertThat(display.getText()).isEqualTo("Earth revolution (360°)");
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo(sun3D.getEarthRevolution(),
                Delta.delta(0.0050001));

        sun3D.setEarthRevolution((float) (Math.PI / 2));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertThat(display.getText()).isEqualTo("Earth revolution (90°)");
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo(sun3D.getEarthRevolution(),
                Delta.delta(0.0050001));

        sun3D.setEarthRevolution((float) (Math.PI / 4));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertThat(display.getText()).isEqualTo("Earth revolution (45°)");
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo(sun3D.getEarthRevolution(),
                Delta.delta(0.0050001));

        display.setAutomatic(false);

        sun3D.setEarthRevolution((float) (Math.PI / 2));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        // the values should not change this time
        assertThat(display.getText()).isEqualTo("Earth revolution (45°)");
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) (Math.PI / 2), Delta.delta(0.0050001));
        assertThat(MathHelper.toRadian(display.getSlider().getValue())).isEqualTo((float) (Math.PI / 4),
                Delta.delta(0.0050001));
    }

    @Test
    public void testStateChanged() {
        // initial value
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0050001));
        assertThat(display.getText()).isEqualTo("Earth revolution (180°)");

        display.getSlider().setValue(90);
        // stateChanged automatically called, but the mode is automatic, so the value remains unchanged
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0050001));
        assertThat(display.getText()).isEqualTo("Earth revolution (180°)");

        display.stateChanged(null);
        // force stateChanged call, but the mode is still automatic, so the value remains unchanged
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) Math.PI, Delta.delta(0.0050001));
        assertThat(display.getText()).isEqualTo("Earth revolution (180°)");

        display.setAutomatic(false);
        display.stateChanged(null);
        // stateChanged called, and the mode is manual, so the value is updated
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) (Math.PI / 2), Delta.delta(0.0050001));
        assertThat(display.getText()).isEqualTo("Earth revolution (90°)");

        display.getSlider().setValue(45);
        // stateChanged automatically called, and the mode is manual, so the value is updated
        assertThat(sun3D.getEarthRevolution()).isEqualTo((float) (Math.PI / 4), Delta.delta(0.0050001));
        assertThat(display.getText()).isEqualTo("Earth revolution (45°)");
    }

    @Test
    public void testSetAutomatic() {
        // initial state
        assertThat(display.getSlider().isEnabled()).isFalse();
        assertThat(sun3D.isEarthRevolutionTaskAutomatic()).isTrue();

        display.setAutomatic(true);
        // should not change anything
        assertThat(display.getSlider().isEnabled()).isFalse();
        assertThat(sun3D.isEarthRevolutionTaskAutomatic()).isTrue();

        display.setAutomatic(false);
        // the values are now inverted
        assertThat(display.getSlider().isEnabled()).isTrue();
        assertThat(sun3D.isEarthRevolutionTaskAutomatic()).isFalse();
    }

}
