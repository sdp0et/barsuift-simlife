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


public class EarthRotationPanelTest extends TestCase {

    private MockSun3D sun3D;

    private EarthRotationPanel display;

    protected void setUp() throws Exception {
        super.setUp();
        sun3D = new MockSun3D();
        display = new EarthRotationPanel(sun3D);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        sun3D = null;
        display = null;
    }

    public void testInit() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals("Earth rotation (135°)", display.getText());
        assertEquals(sun3D.getEarthRotation(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRotation((float) (Math.PI * 2));
        display = new EarthRotationPanel(sun3D);
        assertEquals("Earth rotation (360°)", display.getText());
        assertEquals(sun3D.getEarthRotation(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRotation((float) (Math.PI / 2));
        display = new EarthRotationPanel(sun3D);
        assertEquals("Earth rotation (90°)", display.getText());
        assertEquals(sun3D.getEarthRotation(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRotation((float) (Math.PI / 4));
        display = new EarthRotationPanel(sun3D);
        assertEquals("Earth rotation (45°)", display.getText());
        assertEquals(sun3D.getEarthRotation(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
    }

    public void testUpdate() {
        // allow +/- 0.5 difference, as the slider rounds the value to an integer
        assertEquals("Earth rotation (135°)", display.getText());
        assertEquals(sun3D.getEarthRotation(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRotation((float) (Math.PI * 2));
        display.update(sun3D, SunUpdateCode.EARTH_ROTATION);
        assertEquals("Earth rotation (360°)", display.getText());
        assertEquals(sun3D.getEarthRotation(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRotation((float) (Math.PI / 2));
        display.update(sun3D, SunUpdateCode.EARTH_ROTATION);
        assertEquals("Earth rotation (90°)", display.getText());
        assertEquals(sun3D.getEarthRotation(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        sun3D.setEarthRotation((float) (Math.PI / 4));
        display.update(sun3D, SunUpdateCode.EARTH_ROTATION);
        assertEquals("Earth rotation (45°)", display.getText());
        assertEquals(sun3D.getEarthRotation(), MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);

        display.setAutomatic(false);

        sun3D.setEarthRotation((float) (Math.PI / 2));
        display.update(sun3D, SunUpdateCode.EARTH_ROTATION);
        // the values should not change this time
        assertEquals("Earth rotation (45°)", display.getText());
        assertEquals(Math.PI / 2, sun3D.getEarthRotation(), 0.0050001);
        assertEquals(Math.PI / 4, MathHelper.toRadian(display.getSlider().getValue()), 0.0050001);
    }

    public void testStateChanged() {
        // initial value
        assertEquals((float) (3 * Math.PI / 4), sun3D.getEarthRotation(), 0.0050001);
        assertEquals("Earth rotation (135°)", display.getText());

        display.getSlider().setValue(90);
        // stateChanged automatically called, but the mode is automatic, so the value remains unchanged
        assertEquals((float) (3 * Math.PI / 4), sun3D.getEarthRotation(), 0.0050001);
        assertEquals("Earth rotation (135°)", display.getText());

        display.stateChanged(null);
        // force stateChanged call, but the mode is still automatic, so the value remains unchanged
        assertEquals((float) (3 * Math.PI / 4), sun3D.getEarthRotation(), 0.0050001);
        assertEquals("Earth rotation (135°)", display.getText());

        display.setAutomatic(false);
        display.stateChanged(null);
        // stateChanged called, and the mode is manual, so the value is updated
        assertEquals((float) (Math.PI / 2), sun3D.getEarthRotation(), 0.0050001);
        assertEquals("Earth rotation (90°)", display.getText());

        display.getSlider().setValue(45);
        // stateChanged automatically called, and the mode is manual, so the value is updated
        assertEquals((float) (Math.PI / 4), sun3D.getEarthRotation(), 0.0050001);
        assertEquals("Earth rotation (45°)", display.getText());
    }

    public void testSetAutomatic() {
        // initial state
        assertFalse(display.getSlider().isEnabled());
        assertTrue(sun3D.isEarthRotationTaskAutomatic());

        display.setAutomatic(true);
        // should not change anything
        assertFalse(display.getSlider().isEnabled());
        assertTrue(sun3D.isEarthRotationTaskAutomatic());

        display.setAutomatic(false);
        // the values are now inverted
        assertTrue(display.getSlider().isEnabled());
        assertFalse(sun3D.isEarthRotationTaskAutomatic());
    }

}
