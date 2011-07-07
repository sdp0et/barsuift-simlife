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

import org.fest.assertions.Delta;
import org.testng.annotations.Test;

import barsuift.simLife.PlanetParameters;
import barsuift.simLife.landscape.LandscapeParameters;

import static org.fest.assertions.Assertions.assertThat;


public class Sun3DStateFactoryTest {

    @Test
    public void testCreateSun3DState() {
        LandscapeParameters landscapeParameters = new LandscapeParameters();
        PlanetParameters planetParameters = new PlanetParameters();
        Sun3DStateFactory factory = new Sun3DStateFactory();
        Sun3DState sunState = factory.createSun3DState(planetParameters, landscapeParameters);
        assertThat(sunState.getEarthRotation()).isEqualTo(0.0f, Delta.delta(0.0001));
        assertThat(sunState.getEarthRevolution()).isEqualTo(0.0f, Delta.delta(0.0001));
        assertThat(sunState.getEarthRotationTask()).isNotNull();
        assertThat(sunState.isEarthRotationTaskAutomatic()).isTrue();
        assertThat(sunState.isEarthRevolutionTaskAutomatic()).isTrue();
    }

}
