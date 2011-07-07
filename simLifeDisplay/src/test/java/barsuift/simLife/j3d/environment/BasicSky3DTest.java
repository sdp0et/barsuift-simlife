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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.environment.MockSky;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.environment.SunUpdateCode;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import static barsuift.simLife.j3d.assertions.ColorAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;


public class BasicSky3DTest {

    private BasicSky3D sky3D;

    private Sky3DState state;

    private MockSun3D mockSun3D;

    @BeforeMethod
    protected void setUp() {
        state = DisplayDataCreatorForTests.createRandomSky3DState();
        MockSky mockSky = new MockSky();
        mockSun3D = (MockSun3D) ((MockSun) mockSky.getSun()).getSun3D();
        mockSun3D.setBrightness(new BigDecimal("0.5"));
        sky3D = new BasicSky3D(state);
        sky3D.init(mockSky);
    }

    @AfterMethod
    protected void tearDown() {
        sky3D = null;
        state = null;
        mockSun3D = null;
    }

    @Test
    public void testGetGroup() {
        Group group = sky3D.getGroup();
        assertThat(group.numChildren()).isEqualTo(3);
        assertThat(group.getChild(0)).isInstanceOf(AmbientLight.class);
        assertThat(group.getChild(1)).isInstanceOf(DirectionalLight.class);
        Background background = (Background) group.getChild(2);
        assertThat(background.getCapability(Background.ALLOW_COLOR_WRITE)).isTrue();
        Color3f color = new Color3f();
        background.getColor(color);
        // brightness = 0.5 so color should be "half" sky blue, half black
        assertThat(color).isEqualTo(new Color3f(0.125f, 0.25f, 0.5f));
    }

    @Test
    public void testUpdate() {
        Group group = sky3D.getGroup();
        Background background = (Background) group.getChild(2);
        Color3f color = new Color3f();
        background.getColor(color);
        // brightness = 0.5 so color should be "half" sky blue, half black
        assertThat(color).isEqualTo(new Color3f(0.125f, 0.25f, 0.5f));

        mockSun3D.setBrightness(new BigDecimal("0.75"));
        background.getColor(color);
        // update method not called, so the color should not have changed yet
        assertThat(color).isEqualTo(new Color3f(0.125f, 0.25f, 0.5f));

        sky3D.update(mockSun3D, SunUpdateCode.EARTH_REVOLUTION);
        background.getColor(color);
        // update method called with another update code, so the color should not change
        assertThat(color).isEqualTo(new Color3f(0.125f, 0.25f, 0.5f));

        sky3D.update(mockSun3D, SunUpdateCode.BRIGHTNESS);
        background.getColor(color);
        // brightness = 0.75 so color should be 75% sky blue, 25% black
        assertThat(color).isEqualTo(new Color3f(0.1875f, 0.375f, 0.75f));
    }

    @Test
    public void testSubscribers() {
        assertThat(mockSun3D.countSubscribers()).isEqualTo(1);
        // check the subscriber is the sky3D
        mockSun3D.deleteSubscriber(sky3D);
        assertThat(mockSun3D.countSubscribers()).isEqualTo(0);
    }

    @Test
    public void testGetState() {
        assertThat(sky3D.getState()).isEqualTo(state);
        assertThat(sky3D.getState()).isSameAs(state);
    }

}
