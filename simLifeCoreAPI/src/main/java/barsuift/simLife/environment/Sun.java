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

import barsuift.simLife.IObservable;
import barsuift.simLife.j3d.universe.environment.Sun3D;

/**
 * Class representing the sun.
 */
public interface Sun extends IObservable {

    public Sun3D getSun3D();

    public BigDecimal getLuminosity();

    public void setLuminosity(BigDecimal luminosity) throws IllegalArgumentException;

    /**
     * Rise angle, in percent.
     * <p>
     * <ul>
     * <li>0 means sun is full east, enlighting only along the X axis</li>
     * <li>50 means sun is at its zenith position (neither east nor west). no X direction</li>
     * <li>100 means sun is full west, enlightin only along the reverted X axis</li>
     * </ul>
     * </p>
     */
    public BigDecimal getRiseAngle();

    public void setRiseAngle(BigDecimal riseAngle);

    /**
     * Zenith angle, in percent.
     * <p>
     * <ul>
     * <li>0 means sun is always at the horizon</li>
     * <li>50 means sun is at 45° (Pi/4)</li>
     * <li>100 means sun iis at its zenith position. no Z direction</li>
     * </ul>
     * </p>
     */
    public BigDecimal getZenithAngle();

    public void setZenithAngle(BigDecimal zenithAngle);

    public SunState getState();

}
