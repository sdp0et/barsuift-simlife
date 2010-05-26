/**
 * barsuift-simlife is a life simulator programm
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

import javax.vecmath.Point3d;

import barsuift.simLife.universe.Universe;

public class BasicTreeFactory {

    private final Universe universe;

    public BasicTreeFactory(Universe universe) {
        this.universe = universe;
    }

    public Tree createRandom(Point3d translationVector, int nbBranches, float height) {
        TreeStateFactory treeStateFactory = new TreeStateFactory();
        TreeState treeState = treeStateFactory.createRandomTreeState(translationVector, nbBranches, height);
        Tree tree = new BasicTree(universe, treeState);
        return tree;
    }

}
