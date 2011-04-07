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
        assertEquals("Earth revolution (180°)", display.getCheckBox().getText());
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRevolution((float) (Math.PI * 2));
        display = new EarthRevolutionPanel(sun3D);
        assertEquals("Earth revolution (360°)", display.getCheckBox().getText());
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRevolution((float) (Math.PI / 2));
        display = new EarthRevolutionPanel(sun3D);
        assertEquals("Earth revolution (90°)", display.getCheckBox().getText());
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRevolution((float) (Math.PI / 4));
        display = new EarthRevolutionPanel(sun3D);
        assertEquals("Earth revolution (45°)", display.getCheckBox().getText());
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
    }

    public void testUpdate() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals("Earth revolution (180°)", display.getCheckBox().getText());
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRevolution((float) (Math.PI * 2));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertEquals("Earth revolution (360°)", display.getCheckBox().getText());
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRevolution((float) (Math.PI / 2));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertEquals("Earth revolution (90°)", display.getCheckBox().getText());
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRevolution((float) (Math.PI / 4));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        assertEquals("Earth revolution (45°)", display.getCheckBox().getText());
        assertEquals(sun3D.getEarthRevolution(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        display.setAutomatic(false);

        sun3D.setEarthRevolution((float) (Math.PI / 2));
        display.update(sun3D, SunUpdateCode.EARTH_REVOLUTION);
        // the values should not change this time
        assertEquals("Earth revolution (45°)", display.getCheckBox().getText());
        assertEquals(Math.PI / 2, sun3D.getEarthRevolution(), 0.0050001);
        assertEquals(Math.PI / 4, MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
    }

    public void testStateChanged() {
        // initial value
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0050001);
        assertEquals("Earth revolution (180°)", display.getCheckBox().getText());

        display.getSlider().setValue(90);
        // stateChanged not called yet, so the value remains unchanged
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0050001);
        assertEquals("Earth revolution (180°)", display.getCheckBox().getText());

        display.stateChanged(null);
        // stateChanged called, but the mode is automatic, so the value remains unchanged
        assertEquals((float) Math.PI, sun3D.getEarthRevolution(), 0.0050001);
        assertEquals("Earth revolution (180°)", display.getCheckBox().getText());

        display.setAutomatic(false);
        display.stateChanged(null);
        // stateChanged called, and the mode is manual, so the value is updated
        assertEquals((float) (Math.PI / 2), sun3D.getEarthRevolution(), 0.0050001);
        assertEquals("Earth revolution (90°)", display.getCheckBox().getText());
    }

    public void testSetAutomatic() {
        // initial state
        assertFalse(display.getSlider().isEnabled());
        assertTrue(sun3D.isEarthRevolutionTaskAutomatic());

        display.setAutomatic(true);
        // should not change anything
        assertFalse(display.getSlider().isEnabled());
        assertTrue(sun3D.isEarthRevolutionTaskAutomatic());

        display.setAutomatic(false);
        // the values are now inverted
        assertTrue(display.getSlider().isEnabled());
        assertFalse(sun3D.isEarthRevolutionTaskAutomatic());
    }

}
