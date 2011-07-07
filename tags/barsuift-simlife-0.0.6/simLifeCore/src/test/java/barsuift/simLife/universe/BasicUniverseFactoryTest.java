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

import junit.framework.TestCase;


public class BasicUniverseFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testPopulateEmptyUniverse() {
        AllParameters parameters = new AllParameters();
        parameters.random();
        // for test performance, limit landscape size to minimum
        parameters.getLandscape().setSize(32);

        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createEmptyRandomUniverseState(parameters);
        Universe universe = new BasicUniverse(universeState);

        assertNotNull(universe.getEnvironment());
        assertEquals(0, universe.getTrees().size());
        assertEquals(0, universe.getFallenLeaves().size());

        BasicUniverseFactory factory = new BasicUniverseFactory();
        factory.populateEmptyUniverse(universe);

        assertNotNull(universe.getEnvironment());
        int nbTrees = universe.getTrees().size();
        assertTrue(nbTrees >= 1);
        assertTrue(nbTrees <= parameters.getLandscape().getSize() / 5);
        assertEquals(0, universe.getFallenLeaves().size());
    }

}