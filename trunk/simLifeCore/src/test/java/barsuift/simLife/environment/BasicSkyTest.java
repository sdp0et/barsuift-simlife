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
package barsuift.simLife.environment;

import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.j3d.environment.Sun3D;
import barsuift.simLife.universe.MockUniverse;

import static org.fest.assertions.Assertions.assertThat;


public class BasicSkyTest {

    @Test
    public void testGetState() {
        SkyState state = CoreDataCreatorForTests.createRandomSkyState();
        BasicSky sky = new BasicSky(state);
        sky.init(new MockUniverse());
        assertThat(sky.getState()).isEqualTo(state);
        assertThat(sky.getState()).isSameAs(state);
        Sun3D sun3D = sky.getSun().getSun3D();
        assertThat(sun3D.getEarthRevolution()).isEqualTo(state.getSunState().getSun3DState().getEarthRevolution());

        sun3D.setEarthRevolution(sun3D.getEarthRevolution() / 2);

        assertThat(sky.getState()).isEqualTo(state);
        assertThat(sky.getState()).isSameAs(state);
        assertThat(sun3D.getEarthRevolution()).isEqualTo(state.getSunState().getSun3DState().getEarthRevolution());
    }

}
