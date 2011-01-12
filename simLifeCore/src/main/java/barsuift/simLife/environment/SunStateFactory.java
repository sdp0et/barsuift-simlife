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
import barsuift.simLife.j3d.environment.Sun3DState;
import barsuift.simLife.j3d.environment.Sun3DStateFactory;


public class SunStateFactory {

    /**
     * Creates a default sun state with following values :
     * <ul>
     * <li>brightness = 100%</li>
     * <li>riseAngle = 37.5%</li>
     * <li>zenithAngle = 50%</li>
     * </ul>
     */
    // TODO 010. the sun position should depend on the current date, at init time
    // TODO 010. 001. zenith=(1-cosinus(dayOfYear*2*Pi/72)/2
    // TODO 010. 002. dayLight = same function = % of day where sun is "visible"
    // TODO 010. 003. rise = to define later (bounds will depend on dayLight)
    // TODO 007. the sun brightness should depend on the sun position (same function as white factor ?? should be 0 at
    // night at least)
    // TODO 020. the sun position should evolve with the time of the day
    // TODO 025. the sun sliders should be able to be decorrelated from auto move
    public SunState createSunState() {
        BigDecimal brightness = PercentHelper.getDecimalValue(100);
        float riseAngle = 0.375f;
        float zenithAngle = 0.5f;
        Sun3DStateFactory sun3DStateFactory = new Sun3DStateFactory();
        Sun3DState sun3DState = sun3DStateFactory.createSun3DState();
        return new SunState(brightness, riseAngle, zenithAngle, sun3DState);
    }

}
