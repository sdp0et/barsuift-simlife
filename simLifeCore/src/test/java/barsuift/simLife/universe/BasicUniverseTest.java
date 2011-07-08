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

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.tree.MockTree;

import static org.fest.assertions.Assertions.assertThat;


public class BasicUniverseTest {

    @Test
    public void testGetState() {
        UniverseState state = CoreDataCreatorForTests.createRandomUniverseState();
        BasicUniverse universe = new BasicUniverse(state);
        universe.init();
        assertThat(universe.getState()).isEqualTo(state);
        assertThat(universe.getState()).isSameAs(state);
        int nbTrees = universe.getState().getTrees().size();
        universe.addTree(new MockTree());
        assertThat(universe.getState()).isEqualTo(state);
        assertThat(universe.getState()).isSameAs(state);
        assertThat(universe.getState().getTrees()).hasSize(nbTrees + 1);
    }

}
