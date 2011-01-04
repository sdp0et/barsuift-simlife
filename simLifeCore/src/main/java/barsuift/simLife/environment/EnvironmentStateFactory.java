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
package barsuift.simLife.environment;

import barsuift.simLife.j3d.environment.Environment3DState;
import barsuift.simLife.j3d.environment.Environment3DStateFactory;
import barsuift.simLife.j3d.terrain.LandscapeParameters;
import barsuift.simLife.terrain.LandscapeState;
import barsuift.simLife.terrain.LandscapeStateFactory;


public class EnvironmentStateFactory {

    public EnvironmentState createRandomEnvironmentState() {
        SunStateFactory sunStateFactory = new SunStateFactory();
        SunState sunState = sunStateFactory.createSunState();
        Environment3DStateFactory env3DStateFactory = new Environment3DStateFactory();
        Environment3DState env3DState = env3DStateFactory.createEnvironment3DState();
        LandscapeStateFactory landscapeFactory = new LandscapeStateFactory();
        LandscapeState landscape = landscapeFactory.createRandomLandscapeState();
        return new EnvironmentState(sunState, landscape, env3DState);
    }

    public EnvironmentState createRandomEnvironmentStateWithParameters(LandscapeParameters parameters) {
        SunStateFactory sunStateFactory = new SunStateFactory();
        SunState sunState = sunStateFactory.createSunState();
        Environment3DStateFactory env3DStateFactory = new Environment3DStateFactory();
        Environment3DState env3DState = env3DStateFactory.createEnvironment3DState();
        LandscapeStateFactory landscapeFactory = new LandscapeStateFactory();
        LandscapeState landscape = landscapeFactory.createRandomLandscapeStateWithParameters(parameters);
        return new EnvironmentState(sunState, landscape, env3DState);
    }

}
