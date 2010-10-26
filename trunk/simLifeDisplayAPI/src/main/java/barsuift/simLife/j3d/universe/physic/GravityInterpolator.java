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
package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.BranchGroup;


// TODO 003. leaves should stop falling when time is stopped, and follow application speed
// TODO 002. Create a Synchronizer3D instead to synchronize Alphas
// TODO 001. refactor GravityInterpolator to use Gravity and Gravity3D instead
public interface GravityInterpolator {

    /**
     * Make the given group fall.
     * 
     * @param groupToFall a BG, containing a TG, containing the leaf
     */
    public void fall(BranchGroup groupToFall);

}