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
package barsuift.simLife.universe;

import org.testng.annotations.Test;

import barsuift.simLife.j3d.universe.UniverseContext3DState;

import static org.fest.assertions.Assertions.assertThat;


public class UniverseContextStateFactoryTest {

    @Test
    public void testCreateEmptyUniverseContextState() {
        AllParameters parameters = new AllParameters();
        parameters.random();
        // for tests performance
        parameters.getLandscape().setSize(32);

        UniverseContextStateFactory factory = new UniverseContextStateFactory();
        UniverseContextState universeContextState = factory.createEmptyRandomUniverseContextState(parameters);
        assertThat(universeContextState.isFpsShowing()).isFalse();

        UniverseState universeState = universeContextState.getUniverse();
        assertThat(universeState).isNotNull();

        assertThat(universeState.getTrees()).isEmpty();
        assertThat(universeState.getFallenLeaves()).isEmpty();

        UniverseContext3DState universeContext3D = universeContextState.getUniverseContext3D();
        assertThat(universeContext3D).isNotNull();

    }
}
