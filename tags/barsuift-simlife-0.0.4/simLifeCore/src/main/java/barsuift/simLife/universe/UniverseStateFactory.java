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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Point3d;

import barsuift.simLife.Randomizer;
import barsuift.simLife.environment.EnvironmentState;
import barsuift.simLife.environment.EnvironmentStateFactory;
import barsuift.simLife.time.TimeCounterState;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;
import barsuift.simLife.tree.TreeStateFactory;


public class UniverseStateFactory {

    private static final Map<Long, Point3d> originPoints = new HashMap<Long, Point3d>();

    static {
        // TODO 999. improve this random placement code
        originPoints.put(new Long(0), new Point3d(0, 0, 0));
        originPoints.put(new Long(1), new Point3d(5, 0, 0));
        originPoints.put(new Long(2), new Point3d(2, 0, -4));
        originPoints.put(new Long(3), new Point3d(-4, 0, 2));
    }

    /**
     * Creates a random universe state with the following values :
     * <ul>
     * <li>nb of trees between 1 and 4</li>
     * <li>a random environment</li>
     * <li>fpsShowing = false</li>
     * </ul>
     * 
     * @return
     */
    public UniverseState createRandomUniverseState() {
        boolean fpsShowing = false;
        int nbTrees = Randomizer.randomBetween(1, 4);
        TreeStateFactory treeStateFactory = new TreeStateFactory();
        Set<TreeState> trees = new HashSet<TreeState>(nbTrees);
        for (int i = 0; i < nbTrees; i++) {
            Point3d translationVector = originPoints.get(new Long(i));
            trees.add(treeStateFactory.createRandomTreeState(translationVector));
        }
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(0);

        EnvironmentStateFactory envStateFactory = new EnvironmentStateFactory();
        EnvironmentState environment = envStateFactory.createEnvironmentState();

        return new UniverseState(0, fpsShowing, trees, fallenLeaves, environment, new TimeCounterState());
    }

    /**
     * Creates an empty universe state with the following values :
     * <ul>
     * <li>nb of trees = 0</li>
     * <li>a random environment</li>
     * <li>fpsShowing = false</li>
     * </ul>
     * 
     * @return
     */
    public UniverseState createEmptyUniverseState() {
        boolean fpsShowing = false;
        Set<TreeState> trees = new HashSet<TreeState>(0);
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(0);
        EnvironmentStateFactory envStateFactory = new EnvironmentStateFactory();
        EnvironmentState environment = envStateFactory.createEnvironmentState();
        return new UniverseState(0, fpsShowing, trees, fallenLeaves, environment, new TimeCounterState());
    }

}