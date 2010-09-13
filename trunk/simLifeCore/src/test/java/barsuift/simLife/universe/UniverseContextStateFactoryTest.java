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


public class UniverseContextStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomUniverseContextState() {
        UniverseContextStateFactory factory = new UniverseContextStateFactory();
        UniverseContextState universeContextState = factory.createRandomUniverseContextState();
        assertTrue(universeContextState.isAxisShowing());
        assertFalse(universeContextState.isFpsShowing());

        UniverseState universeState = universeContextState.getUniverseState();
        assertNotNull(universeState);
        int nbTrees = universeState.getTrees().size();
        assertTrue(nbTrees >= 1);
        assertTrue(nbTrees <= 4);

    }

}
