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
package barsuift.simLife.universe.physic;

import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.universe.physic.Gravity3DState;
import barsuift.simLife.j3d.universe.physic.Gravity3DStateFactory;
import barsuift.simLife.tree.TreeLeafState;


public class GravityStateFactory {

    public GravityState createGravityState() {
        Gravity3DStateFactory gravity3DFactory = new Gravity3DStateFactory();
        Gravity3DState gravity3D = gravity3DFactory.createGravity3DState();
        Set<TreeLeafState> fallingLeaves = new HashSet<TreeLeafState>();
        return new GravityState(gravity3D, fallingLeaves);
    }

}
