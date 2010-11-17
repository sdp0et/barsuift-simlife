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

import javax.media.j3d.Group;

import barsuift.simLife.Persistent;
import barsuift.simLife.j3d.Mobile;

public interface Gravity3D extends Persistent<Gravity3DState> {

    public Group getGroup();

    /**
     * Make the given mobile fall.
     * 
     * @param mobile the mobile to fall
     */
    public void fall(Mobile mobile);

    /**
     * The given mobile is already fallen, so there is no need for Gravity3D to deal with it anymore.
     * 
     * @param mobile the fallen mobile
     */
    public void isFallen(Mobile mobile);

}