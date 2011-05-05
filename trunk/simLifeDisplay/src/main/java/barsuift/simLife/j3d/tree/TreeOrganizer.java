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
package barsuift.simLife.j3d.tree;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.landscape.Landscape3D;


public class TreeOrganizer {

    public void placeNewTree(Tree3DState tree3D, Landscape3D landscape3D) {
        int size = landscape3D.getSize();
        // getting 2 meters of margin
        float x = Randomizer.randomBetween(2, size - 2);
        float z = Randomizer.randomBetween(2, size - 2);
        float height = landscape3D.getHeight(x, z);
        Tuple3fState translationVector = new Tuple3fState(x, height, z);
        tree3D.setTranslationVector(translationVector);
    }

}
