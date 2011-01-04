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

import barsuift.simLife.Persistent;
import barsuift.simLife.j3d.SimLifeCanvas3D;
import barsuift.simLife.j3d.landscape.Navigator;

public interface UniverseContext3D extends Persistent<UniverseContext3DState> {

    public SimLifeCanvas3D getCanvas3D();

    public void setFpsShowing(boolean fpsShowing);

    public boolean isFpsShowing();

    /**
     * Add or remove the X-Y-Z axis as 3 segments of 5 meters along X, Y, and Z axis
     * 
     * @param axisShowing
     */
    public void setAxisShowing(boolean axisShowing);

    public boolean isAxisShowing();

    public Navigator getNavigator();

}