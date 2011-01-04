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

import barsuift.simLife.landscape.LandscapeParameters;



public class BasicUniverseContextFactory {

    /**
     * Create a random populated universe context
     * 
     * @return a new universe context instance
     */
    public UniverseContext createPopulatedRandom() {
        UniverseContext universeContext = createEmptyRandom();
        BasicUniverseFactory universeFactory = new BasicUniverseFactory();
        universeFactory.populateEmptyUniverse(universeContext.getUniverse());
        return universeContext;
    }

    /**
     * Create an empty random universe context
     * 
     * @return a new universe context instance
     */
    public UniverseContext createEmptyRandom() {
        UniverseContextStateFactory universeContextStateFactory = new UniverseContextStateFactory();
        UniverseContextState universeContextState = universeContextStateFactory.createEmptyRandomUniverseContextState();
        UniverseContext universeContext = new BasicUniverseContext(universeContextState);
        return universeContext;
    }

    /**
     * Create a random populated universe context, from parameters
     * 
     * @return a new universe context instance
     */
    public UniverseContext createPopulatedRandomWithParameters(LandscapeParameters parameters) {
        UniverseContext universeContext = createEmptyRandomWithParameters(parameters);
        BasicUniverseFactory universeFactory = new BasicUniverseFactory();
        universeFactory.populateEmptyUniverse(universeContext.getUniverse());
        return universeContext;
    }

    /**
     * Create an empty random universe context, from parameters
     * 
     * @return a new universe context instance
     */
    public UniverseContext createEmptyRandomWithParameters(LandscapeParameters parameters) {
        UniverseContextStateFactory universeContextStateFactory = new UniverseContextStateFactory();
        UniverseContextState universeContextState = universeContextStateFactory
                .createEmptyRandomUniverseContextStateWithParameters(parameters);
        UniverseContext universeContext = new BasicUniverseContext(universeContextState);
        return universeContext;
    }

}
