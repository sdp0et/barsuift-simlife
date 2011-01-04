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
package barsuift.simLife.landscape;

import barsuift.simLife.CommonParameters;
import barsuift.simLife.j3d.landscape.Landscape3DState;
import barsuift.simLife.j3d.landscape.Landscape3DStateFactory;



public class LandscapeStateFactory {

    public LandscapeState createRandomLandscapeState() {
        Landscape3DStateFactory landscape3DStateFactory = new Landscape3DStateFactory();
        Landscape3DState landscape3D = landscape3DStateFactory.createRandomLandscape3DState();
        return new LandscapeState(landscape3D);
    }

    public LandscapeState createRandomLandscapeStateWithParameters(LandscapeParameters parameters,
            CommonParameters commonParameters) {
        Landscape3DStateFactory landscape3DStateFactory = new Landscape3DStateFactory();
        Landscape3DState landscape3D = landscape3DStateFactory.createRandomLandscape3DStateWithParameters(parameters,
                commonParameters);
        return new LandscapeState(landscape3D);
    }

}
