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

    public static double[] NOMINAL_VIEWER_TRANSFORM = new double[] { 1, 0, 0, 4, 0, 1, 0, 2, 0, 0, 1, 20, 0, 0, 0, 1 };

    /**
     * Creates a random UniverseContextState with the following values :
     * <ul>
     * <li>a random universe state</li>
     * <li>a random canvas state</li>
     * <li>axisShowing = true</li>
     * <li>fpsShowing = false</li>
     * <li>viewerTransform3D = identity matrix, 4 meters right, 2 meters high, 20 meters back</li>
     * </ul>
     * 
     * @return
     */
    public UniverseContextState createRandomUniverseContextState() {
        boolean axisShowing = true;
        boolean fpsShowing = false;
        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createRandomUniverseState();
        SimLifeCanvas3DStateFactory canvasStateFactory = new SimLifeCanvas3DStateFactory();
        SimLifeCanvas3DState canvasState = canvasStateFactory.createRandomCanvasState();
        return new UniverseContextState(universeState, canvasState, axisShowing, fpsShowing, NOMINAL_VIEWER_TRANSFORM);
    }

    /**
     * Creates an empty universe context state
     * <ul>
     * <li>no trees</li>
     * <li>an empty universe state</li>
     * <li>an empty canvas state</li>
     * <li>axisShowing = true</li>
     * <li>fpsShowing = false</li>
     * <li>viewerTransform3D = identity matrix, 4 meters right, 2 meters high, 20 meters back</li>
     * </ul>
     * 
     * @return
     */
    public UniverseContextState createEmptyUniverseContextState() {
        boolean axisShowing = true;
        boolean fpsShowing = false;
        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createEmptyUniverseState();
        SimLifeCanvas3DStateFactory canvasStateFactory = new SimLifeCanvas3DStateFactory();
        SimLifeCanvas3DState canvasState = canvasStateFactory.createEmptyCanvasState();
        return new UniverseContextState(universeState, canvasState, axisShowing, fpsShowing, NOMINAL_VIEWER_TRANSFORM);
    }

}
