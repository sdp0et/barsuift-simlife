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
package barsuift.simLife.tree;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.TreeTrunk3DState;
import barsuift.simLife.j3d.tree.TreeTrunk3DStateFactory;


public class TreeTrunkStateFactory {

    /**
     * Creates a default tree trunk state with given radius and height and default 3D state.
     */
    public TreeTrunkState createRandomTreeTrunkState(float radius, float height) {
        int creationMillis = Randomizer.randomBetween(0, 100) * 1000;
        TreeTrunk3DStateFactory trunk3DStateFactory = new TreeTrunk3DStateFactory();
        TreeTrunk3DState leaf3dState = trunk3DStateFactory.createRandomTreeTrunk3DState();
        return new TreeTrunkState(creationMillis, radius, height, leaf3dState);
    }

}
