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

import static org.fest.assertions.Assertions.assertThat;


public class BasicUniverseFactoryTest {

    @Test
    public void testPopulateEmptyUniverse() {
        AllParameters parameters = new AllParameters();
        parameters.random();
        // for test performance, limit landscape size to minimum
        parameters.getLandscape().setSize(32);

        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createEmptyRandomUniverseState(parameters);
        BasicUniverse universe = new BasicUniverse(universeState);
        universe.init();

        assertThat(universe.getEnvironment()).isNotNull();
        assertThat(universe.getTrees()).isEmpty();
        assertThat(universe.getFallenLeaves()).isEmpty();

        BasicUniverseFactory factory = new BasicUniverseFactory();
        factory.populateEmptyUniverse(universe);

        assertThat(universe.getEnvironment()).isNotNull();
        int nbTrees = universe.getTrees().size();
        assertThat(nbTrees).isGreaterThanOrEqualTo(1);
        assertThat(nbTrees).isLessThanOrEqualTo(parameters.getLandscape().getSize() / 5);
        assertThat(universe.getFallenLeaves()).isEmpty();
    }

}
