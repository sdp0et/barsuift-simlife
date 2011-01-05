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
package barsuift.simLife.j3d.universe;

import barsuift.simLife.CommonParameters;
import barsuift.simLife.j3d.SimLifeCanvas3DState;
import barsuift.simLife.j3d.SimLifeCanvas3DStateFactory;
import barsuift.simLife.j3d.landscape.NavigatorState;
import barsuift.simLife.j3d.landscape.NavigatorStateFactory;


public class UniverseContext3DStateFactory {

    /**
     * Creates a universe context 3D state with the following values :
     * <ul>
     * <li>a default canvas state</li>
     * <li>axisShowing = true</li>
     * <li>a default navigator</li>
     * </ul>
     */
    public UniverseContext3DState createUniverseContext3DState(CommonParameters parameters) {
        boolean axisShowing = true;
        SimLifeCanvas3DStateFactory canvasStateFactory = new SimLifeCanvas3DStateFactory();
        SimLifeCanvas3DState canvasState = canvasStateFactory.createCanvas3DState();
        NavigatorStateFactory navigatorStateFactory = new NavigatorStateFactory();
        NavigatorState navigatorState = navigatorStateFactory.createNavigatorState(parameters);
        return new UniverseContext3DState(canvasState, axisShowing, navigatorState);
    }

}
