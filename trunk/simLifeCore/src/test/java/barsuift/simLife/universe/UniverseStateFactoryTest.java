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

import java.util.Set;

import junit.framework.TestCase;
import barsuift.simLife.tree.TreeState;


public class UniverseStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomUniverseState() {
        UniverseStateFactory factory = new UniverseStateFactory();
        UniverseState universeState = factory.createRandomUniverseState();
        assertNotNull(universeState.getEnvironment());

        int nbTrees = universeState.getTrees().size();
        assertTrue(nbTrees >= 1);
        assertTrue(nbTrees <= 4);

        Set<TreeState> trees = universeState.getTrees();
        for (TreeState treeState : trees) {
            int nbBranches = treeState.getBranches().size();
            assertTrue(nbBranches >= 30);
            assertTrue(nbBranches <= 50);
            float height = treeState.getHeight();
            assertTrue(height >= 3);
            assertTrue(height <= 5);
        }
    }

}
