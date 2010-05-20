/**
 * barsuift-simlife is a life simulator programm
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

import barsuift.simLife.Randomizer;

public class BasicUniverseFactory {

    /**
     * Create a random universe with between 1 and 4 trees
     * 
     * @return a new universe instance
     */
    public Universe createRandom() {
        UniverseStateFactory envStateFactory = new UniverseStateFactory();
        int nbTrees = Randomizer.randomBetween(1, 4);
        UniverseState envState = envStateFactory.createRandomUniverseState(nbTrees);
        Universe environment = new BasicUniverse(envState);
        return environment;
    }

    /**
     * Create an empty universe with no trees
     * 
     * @return a new universe instance
     */
    public Universe createEmpty() {
        UniverseStateFactory envStateFactory = new UniverseStateFactory();
        UniverseState envState = envStateFactory.createEmptyUniverseState();
        Universe environment = new BasicUniverse(envState);
        return environment;
    }

}
