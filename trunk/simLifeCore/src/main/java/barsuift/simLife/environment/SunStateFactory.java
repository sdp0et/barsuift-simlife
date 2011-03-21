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
     * <li>brightness = 100%</li>
     * <li>earth rotation = 37.5%</li>
     * <li>zenithAngle = 50%</li>
     * </ul>
     */
    // FIXME earthRotation and earthRevolution should be computed from the current date (how to do that ???)
    public SunState createSunState(PlanetParameters planetParameters, LandscapeParameters landscapeParameters) {
        BigDecimal brightness = PercentHelper.getDecimalValue(100);
        float earthRotation = 0.375f;
        float earthRevolution = 0f;
        float zenithAngle = 0.5f;
        Sun3DStateFactory sun3DStateFactory = new Sun3DStateFactory();
        Sun3DState sun3DState = sun3DStateFactory.createSun3DState(planetParameters, landscapeParameters);
        return new SunState(brightness, earthRotation, earthRevolution, zenithAngle, sun3DState);
    }

}
