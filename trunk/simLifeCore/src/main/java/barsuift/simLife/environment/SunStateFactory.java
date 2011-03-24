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

import java.math.BigDecimal;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.PlanetParameters;
import barsuift.simLife.j3d.environment.Sun3DState;
import barsuift.simLife.j3d.environment.Sun3DStateFactory;
import barsuift.simLife.landscape.LandscapeParameters;


public class SunStateFactory {

    /**
     * Creates a default sun state with following values :
     * <ul>
     * <li>brightness = 0%</li>
     * <li>earth rotation = 0%</li>
     * <li>earth revolution = 0%</li>
     * </ul>
     */
    public SunState createSunState(PlanetParameters planetParameters, LandscapeParameters landscapeParameters) {
        BigDecimal brightness = PercentHelper.getDecimalValue(0);
        float earthRotation = 0f;
        float earthRevolution = 0f;
        Sun3DStateFactory sun3DStateFactory = new Sun3DStateFactory();
        Sun3DState sun3DState = sun3DStateFactory.createSun3DState(planetParameters, landscapeParameters);
        return new SunState(brightness, earthRotation, earthRevolution, sun3DState);
    }

}
