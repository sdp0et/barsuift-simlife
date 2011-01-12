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

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Long, Point3f> originPoints = new HashMap<Long, Point3f>();

    static {
        // TODO 300. randomize this placement code (with something ala MidPoint Displacement stuff)
        originPoints.put(new Long(0), new Point3f(0, 0, 0));
        originPoints.put(new Long(1), new Point3f(5, 0, 0));
        originPoints.put(new Long(2), new Point3f(2, 0, 4));
        originPoints.put(new Long(3), new Point3f(4, 0, 2));
    }

    public void populateEmptyUniverse(Universe universe) {
        Landscape3D landscape3D = universe.getEnvironment().getLandscape().getLandscape3D();

        int nbTrees = Randomizer.randomBetween(1, 4);
        TreeStateFactory treeStateFactory = new TreeStateFactory();
        for (int i = 0; i < nbTrees; i++) {
            Point3f translationVector = originPoints.get(new Long(i));
            float height = landscape3D.getHeight(translationVector.x, translationVector.z);
            translationVector.y = height;
            TreeState treeState = treeStateFactory.createRandomTreeState(translationVector);
            Tree tree = new BasicTree(universe, treeState);
            universe.addTree(tree);
        }
    }

}
