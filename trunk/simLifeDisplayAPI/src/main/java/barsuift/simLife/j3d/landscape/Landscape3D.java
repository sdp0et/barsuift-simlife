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
package barsuift.simLife.j3d.landscape;

import javax.media.j3d.BranchGroup;

import barsuift.simLife.Persistent;

/**
 * This interface represents a 3D landscape.
 */
public interface Landscape3D extends Persistent<Landscape3DState> {

    /**
     * Returns the height of the landscape at a specific point. The point is specified with its x and z coordinates. the
     * height is the y coordinate at the given point.
     * 
     * @param x the x coordinate
     * @param z the z coordinate
     * @return the y coordinate (height)
     */
    public float getHeight(float x, float z);

    /**
     * Returns true if the given point is part of the landscape, false otherwise. The point is specified with its x and
     * z coordinates.
     * 
     * @param x the x coordinate
     * @param z the z coordinate
     * @return true if the point is part of the landscape, false otherwise.
     */
    public boolean inLandscape(float x, float z);

    public BranchGroup getBranchGroup();

}
