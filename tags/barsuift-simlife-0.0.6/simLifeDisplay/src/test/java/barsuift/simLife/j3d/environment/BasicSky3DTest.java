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
package barsuift.simLife.j3d.environment;

import java.math.BigDecimal;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;
import javax.vecmath.Color3f;

import junit.framework.TestCase;
import barsuift.simLife.environment.MockSky;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.helper.ColorTestHelper;


public class BasicSky3DTest extends TestCase {

    private BasicSky3D sky3D;

    private Sky3DState state;

    private MockSun3D mockSun3D;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        state = DisplayDataCreatorForTests.createRandomSky3DState();
        MockSky mockSky = new MockSky();
        mockSun3D = (MockSun3D) ((MockSun) mockSky.getSun()).getSun3D();
        mockSun3D.setBrightness(new BigDecimal("0.5"));
        sky3D = new BasicSky3D(state, mockSky);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        sky3D = null;
        state = null;
        mockSun3D = null;
    }

    public void testGetGroup() {
        Group group = sky3D.getGroup();
        assertEquals(3, group.numChildren());
        assertTrue(group.getChild(0) instanceof AmbientLight);
        assertTrue(group.getChild(1) instanceof DirectionalLight);
        Background background = (Background) group.getChild(2);
        assertTrue(background.getCapability(Background.ALLOW_COLOR_WRITE));
        Color3f color = new Color3f();
        background.getColor(color);
        // brightness = 0.5 so color should be "half" sky blue, half black
        ColorTestHelper.assertEquals(new Color3f(0.125f, 0.25f, 0.5f), color);
    }

    public void testUpdate() {
        Group group = sky3D.getGroup();
        Background background = (Background) group.getChild(2);
        Color3f color = new Color3f();
        background.getColor(color);
        // brightness = 0.5 so color should be "half" sky blue, half black
        ColorTestHelper.assertEquals(new Color3f(0.125f, 0.25f, 0.5f), color);

        mockSun3D.setBrightness(new BigDecimal("0.75"));
        background.getColor(color);
        // update method not called, so the color should not have changed yet
        ColorTestHelper.assertEquals(new Color3f(0.125f, 0.25f, 0.5f), color);

        sky3D.update(mockSun3D, SunUpdateCode.EARTH_REVOLUTION);
        background.getColor(color);
        // update method called with another update code, so the color should not change
        ColorTestHelper.assertEquals(new Color3f(0.125f, 0.25f, 0.5f), color);

        sky3D.update(mockSun3D, SunUpdateCode.BRIGHTNESS);
        background.getColor(color);
        // brightness = 0.75 so color should be 75% sky blue, 25% black
        ColorTestHelper.assertEquals(new Color3f(0.1875f, 0.375f, 0.75f), color);
    }

    public void testSubscribers() {
        assertEquals(1, mockSun3D.countSubscribers());
        // check the subscriber is the sky3D
        mockSun3D.deleteSubscriber(sky3D);
        assertEquals(0, mockSun3D.countSubscribers());
    }

    public void testGetState() {
        assertEquals(state, sky3D.getState());
        assertSame(state, sky3D.getState());
    }

}
