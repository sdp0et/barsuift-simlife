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

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.landscape.Landscape3D;
import barsuift.simLife.j3d.tree.TreeOrganizer;
import barsuift.simLife.tree.BasicTree;
import barsuift.simLife.tree.TreeState;
import barsuift.simLife.tree.TreeStateFactory;

/**
 * This factory class is used to populate the Universe with living elements.
 * 
 */
public class BasicUniverseFactory {

    public void populateEmptyUniverse(Universe universe) {
        TreeOrganizer treeOrganizer = new TreeOrganizer();
        Landscape3D landscape3D = universe.getEnvironment().getLandscape().getLandscape3D();
        int size = landscape3D.getSize();
        int maxTrees = size / 5;

        int nbTrees = Randomizer.randomBetween(1, maxTrees);
        TreeStateFactory treeStateFactory = new TreeStateFactory();
        for (int i = 0; i < nbTrees; i++) {
            TreeState treeState = treeStateFactory.createRandomTreeState();
            treeOrganizer.placeNewTree(treeState.getTree3DState(), landscape3D);
            BasicTree tree = new BasicTree(treeState);
            tree.init(universe);
            universe.addTree(tree);
        }
    }

}
