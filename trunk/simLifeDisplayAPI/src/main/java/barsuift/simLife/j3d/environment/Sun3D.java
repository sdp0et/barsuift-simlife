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
package barsuift.simLife.j3d.environment;


import java.math.BigDecimal;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;

import barsuift.simLife.Persistent;
import barsuift.simLife.message.Publisher;


public interface Sun3D extends Persistent<Sun3DState>, Publisher {

    /**
     * Earth rotation, in radian, in the range [0; 2Pi[.
     * <p>
     * <ul>
     * <li>0 means midnight. At the equator during the equinox, the sun is at its nadir, lighting only along the Y axis.
     * </li>
     * <li>Pi/2 means middle of the morning. At the equator during the equinox, the sun is full east, lighting only
     * along the X axis (sunrise).</li>
     * <li>Pi means middle of the day (noon). At the equator during the equinox, the sun is at its zenith position
     * (neither east nor west). no X direction.</li>
     * <li>3*Pi/2 means middle of the afternoon. At the equator during the equinox, the sun is full west, lighting only
     * along the reverted X axis (sunset).</li>
     * <li>2*Pi also means midnight. At the equator during the equinox, the sun is at its nadir. This value is actually
     * equivalent to 0.</li>
     * </ul>
     * </p>
     */
    public float getEarthRotation();

    /**
     * 
     * @param earthRotation the earth rotation
     * @see #getEarthRotation()
     */
    public void setEarthRotation(float earthRotation);

    /**
     * Earth revolution, in radian, in the range [0; 2Pi[.
     * <p>
     * <ul>
     * <li>0 means start of wim season (first day of year).</li>
     * <li>Pi/2 means start of sprim season.</li>
     * <li>Pi means start of sum season.</li>
     * <li>3*Pi/2 means start of tom season.</li>
     * <li>2*Pi also means start of wim season (and thus end of tom season). This value is actually equivalent to 0.</li>
     * </ul>
     * </p>
     */
    public float getEarthRevolution();

    /**
     * 
     * @param earthRevolution the earth revolution
     * @see #getEarthRevolution()
     */
    public void setEarthRevolution(float earthRevolution);

    /**
     * Get the sun height, ranged from -1 (nadir) to +1 (zenith)
     * 
     * @return the scaled sun height (-1;1]
     */
    public float getHeight();

    /**
     * Gets the sun brightness, ranged from 0 (night) to 1 (day)
     * 
     * @return the scaled sun brightness (0;1]
     */
    public BigDecimal getBrightness();

    /**
     * Computes the white factor, based on the sun position. The lower the sun is, the lower the white factor.
     * <p>
     * <ul>
     * <li>When the white factor is 1 (100%), the sun is white (Red = Green = Blue).</li>
     * <li>When the white factor is 0%, the sun is red (Red = 100%, Green = Blue = 0%).</li>
     * </ul>
     * </p>
     * 
     * @return the white factor
     */
    public float getWhiteFactor();

    public DirectionalLight getLight();

    /**
     * Returns the group which contains the graphic representation of the sun. This group needs to be added to the
     * Background instance of the scene graph.
     * 
     * @return the sun group
     */
    public BranchGroup getGroup();

}