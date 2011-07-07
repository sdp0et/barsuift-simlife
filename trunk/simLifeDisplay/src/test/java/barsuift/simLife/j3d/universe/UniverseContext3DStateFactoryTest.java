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

import org.testng.annotations.Test;

import barsuift.simLife.j3d.SimLifeCanvas3DState;
import barsuift.simLife.j3d.landscape.NavigatorState;
import barsuift.simLife.landscape.LandscapeParameters;

import static org.fest.assertions.Assertions.assertThat;


public class UniverseContext3DStateFactoryTest {

    @Test
    public void testCreateRandomUniverseContext3DState() {
        LandscapeParameters parameters = new LandscapeParameters();
        parameters.random();

        UniverseContext3DStateFactory factory = new UniverseContext3DStateFactory();
        UniverseContext3DState universeContext3DState = factory.createUniverseContext3DState(parameters);

        assertThat(universeContext3DState.isAxisShowing()).isTrue();

        SimLifeCanvas3DState canvasState = universeContext3DState.getCanvas();
        assertThat(canvasState).isNotNull();
        assertThat(canvasState.isFpsShowing()).isFalse();

        NavigatorState navigatorState = universeContext3DState.getNavigator();
        assertThat(navigatorState).isNotNull();
        assertThat(navigatorState.getRotationX()).isEqualTo(0.0);
        assertThat(navigatorState.getRotationY()).isEqualTo(0.0);
        assertThat(navigatorState.getTranslation().getX()).isEqualTo((float) parameters.getSize() / 2);
        assertThat(navigatorState.getTranslation().getY()).isEqualTo(2.0f);
        assertThat(navigatorState.getTranslation().getZ()).isEqualTo((float) parameters.getSize() / 2);

    }

}
