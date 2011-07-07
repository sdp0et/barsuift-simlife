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
package barsuift.simLife.j3d.universe;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.landscape.LandscapeParameters;
import barsuift.simLife.universe.MockUniverseContext;

import static org.fest.assertions.Assertions.assertThat;


public class BasicUniverseContext3DTest {

    private UniverseContext3DState state;

    private BasicUniverseContext3D universeContext3D;

    @BeforeMethod
    protected void setUp() {
        LandscapeParameters parameters = new LandscapeParameters();
        parameters.random();
        UniverseContext3DStateFactory factory = new UniverseContext3DStateFactory();
        state = factory.createUniverseContext3DState(parameters);
        universeContext3D = new BasicUniverseContext3D(state);
        universeContext3D.init(new MockUniverseContext());
    }

    @AfterMethod
    protected void tearDown() {
        state = null;
        universeContext3D = null;
    }

    @Test
    public void testUnsetAxis() {
        assertThat(universeContext3D.isAxisShowing()).isTrue();
        universeContext3D.setAxisShowing(false);
        assertThat(universeContext3D.isAxisShowing()).isFalse();
        universeContext3D.setAxisShowing(true);
        assertThat(universeContext3D.isAxisShowing()).isTrue();
        universeContext3D.setAxisShowing(false);
        assertThat(universeContext3D.isAxisShowing()).isFalse();
    }

    @Test
    public void testGetState() {
        assertThat(universeContext3D.getState()).isEqualTo(state);
        assertThat(universeContext3D.getState()).isSameAs(state);

        assertThat(universeContext3D.getState().isAxisShowing()).isTrue();
        assertThat(universeContext3D.getState().getCanvas().isFpsShowing()).isFalse();
        universeContext3D.setAxisShowing(false);
        universeContext3D.setFpsShowing(true);

        assertThat(universeContext3D.getState()).isEqualTo(state);
        assertThat(universeContext3D.getState()).isSameAs(state);
        assertThat(universeContext3D.getState().isAxisShowing()).isFalse();
        assertThat(universeContext3D.getState().getCanvas().isFpsShowing()).isTrue();
    }


    @Test
    public void testSetFpsShowing() {
        assertThat(universeContext3D.isFpsShowing()).isFalse();
        assertThat(universeContext3D.getCanvas3D().isFpsShowing()).isFalse();

        universeContext3D.setFpsShowing(true);
        assertThat(universeContext3D.isFpsShowing()).isTrue();
        assertThat(universeContext3D.getCanvas3D().isFpsShowing()).isTrue();
    }

}
