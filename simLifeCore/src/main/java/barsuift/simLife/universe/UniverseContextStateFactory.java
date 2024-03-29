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

import barsuift.simLife.j3d.universe.UniverseContext3DState;
import barsuift.simLife.j3d.universe.UniverseContext3DStateFactory;


public class UniverseContextStateFactory {

    /**
     * Creates an empty universe context state with the following values :
     * <ul>
     * <li>an empty random universe state, from parameters</li>
     * <li>fpsShowing = false</li>
     * <li>a default universe context 3D state</li>
     * </ul>
     * 
     * @return
     */
    public UniverseContextState createEmptyRandomUniverseContextState(AllParameters parameters) {
        boolean fpsShowing = false;
        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createEmptyRandomUniverseState(parameters);
        UniverseContext3DStateFactory universeContext3DStateFactory = new UniverseContext3DStateFactory();
        UniverseContext3DState universeContext3DState = universeContext3DStateFactory
                .createUniverseContext3DState(parameters.getLandscape());
        return new UniverseContextState(universeState, fpsShowing, universeContext3DState);
    }

}
