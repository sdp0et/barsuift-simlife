/**
 * barsuift-simlife is a life simulator programm
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

import barsuift.simLife.PercentState;


public class SunStateFactory {

    /**
     * Creates a default sun state with following values :
     * <ul>
     * <li>luminosity = 100%</li>
     * <li>riseAngle = 25%</li>
     * <li>zenithAngle = 50%</li>
     * </ul>
     */
    public SunState createSunState() {
        PercentState luminosity = new PercentState(new BigDecimal("1.00"));
        PercentState riseAngle = new PercentState(new BigDecimal("0.25"));
        PercentState zenithAngle = new PercentState(new BigDecimal("0.50"));
        return new SunState(luminosity, riseAngle, zenithAngle);
    }
}
