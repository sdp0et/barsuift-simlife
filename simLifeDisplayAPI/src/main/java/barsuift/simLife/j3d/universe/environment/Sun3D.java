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
package barsuift.simLife.j3d.universe.environment;

import javax.media.j3d.DirectionalLight;

import barsuift.simLife.IObservable;
import barsuift.simLife.Percent;




public interface Sun3D extends IObservable {

    /**
     * Computes the white factor, based on the position (rise and zenith angles). The lower the sun is, the lower the
     * white factor.
     * <p>
     * <ul>
     * <li>When the white factor is 100%, the sun is white (Red = Green = Blue).</li>
     * <li>When the white factor is 0%, the sun is red (Red = 100%, Green = Blue = 0%).</li>
     * </ul>
     * </p>
     * <p>
     * Concretely, here is the computation :<br/>
     * <code>whiteFactor = sqrt(abs(sinus(riseAngle) * sinus(zenithAngle)))</code>
     * </p>
     * 
     * @return the white factor
     */
    public Percent getWhiteFactor();

    public DirectionalLight getLight();

}