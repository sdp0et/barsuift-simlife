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
package barsuift.simLife.universe;

import javax.vecmath.Point3f;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.landscape.Landscape3D;
import barsuift.simLife.tree.BasicTree;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeState;
import barsuift.simLife.tree.TreeStateFactory;

/**
 * This factory class is used to populate the Universe with living elements.
 * 
 */
public class BasicUniverseFactory {

    public void populateEmptyUniverse(Universe universe) {
        Landscape3D landscape3D = universe.getEnvironment().getLandscape().getLandscape3D();
        int size = landscape3D.getSize();
        int maxTrees = size / 5;
        System.out.println(maxTrees);

        int nbTrees = Randomizer.randomBetween(1, maxTrees);
        TreeStateFactory treeStateFactory = new TreeStateFactory();
        for (int i = 0; i < nbTrees; i++) {
            // TODO 300. improve this placement code (with something in the MidPoint Displacement style)
            // getting 2 meters of margin
            float x = Randomizer.randomBetween(2, size - 2);
            float z = Randomizer.randomBetween(2, size - 2);
            float height = landscape3D.getHeight(x, z);
            Point3f translationVector = new Point3f(x, height, z);
            TreeState treeState = treeStateFactory.createRandomTreeState(translationVector);
            Tree tree = new BasicTree(universe, treeState);
            universe.addTree(tree);
        }
    }

}
