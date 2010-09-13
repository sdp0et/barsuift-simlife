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

import barsuift.simLife.j3d.SimLifeCanvas3DState;
import barsuift.simLife.j3d.SimLifeCanvas3DStateFactory;


public class UniverseContextStateFactory {

    /**
     * Creates a random UniverseContextState with the following values :
     * <ul>
     * <li>a random universe state</li>
     * <li>a random canvas state</li>
     * <li>axisShowing = true</li>
     * </ul>
     * 
     * @return
     */
    public UniverseContextState createRandomUniverseContextState() {
        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createRandomUniverseState();
        SimLifeCanvas3DStateFactory canvasStateFactory = new SimLifeCanvas3DStateFactory();
        SimLifeCanvas3DState canvasState = canvasStateFactory.createRandomCanvasState();
        return new UniverseContextState(universeState, canvasState, true);
    }

    /**
     * Creates an empty universe context state
     * <ul>
     * <li>no trees</li>
     * <li>an empty universe state</li>
     * <li>an empty canvas state</li>
     * <li>axisShowing = true</li>
     * </ul>
     * 
     * @return
     */
    public UniverseContextState createEmptyUniverseContextState() {
        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createEmptyUniverseState();
        SimLifeCanvas3DStateFactory canvasStateFactory = new SimLifeCanvas3DStateFactory();
        SimLifeCanvas3DState canvasState = canvasStateFactory.createEmptyCanvasState();
        return new UniverseContextState(universeState, canvasState, true);
    }

}
