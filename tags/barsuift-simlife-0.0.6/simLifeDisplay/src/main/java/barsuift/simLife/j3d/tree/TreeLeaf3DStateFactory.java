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

import javax.media.j3d.Transform3D;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.Tuple3fState;


public class TreeLeaf3DStateFactory {

    /**
     * Create a random leaf3D state with :
     * <ul>
     * <li>the given transform</li>
     * <li>initial end point 1 : (-0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>initial end point 2 : (0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>end point 1 : 10 * initial end point 1</li>
     * <li>end point 2 : 10 * initial end point 2</li>
     * </ul>
     */
    public TreeLeaf3DState createRandomTreeLeaf3DState(Transform3D transform) {
        Transform3DState transformState = new Transform3DState(transform);
        Tuple3fState initialEndPoint1 = new Tuple3fState(-0.02f + Randomizer.random1() / 10, -0.04f
                + Randomizer.random1() / 10, 0);
        Tuple3fState initialEndPoint2 = new Tuple3fState(0.02f + Randomizer.random1() / 10, -0.04f
                + Randomizer.random1() / 10, 0);
        Tuple3fState endPoint1 = new Tuple3fState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                initialEndPoint1.getZ() * 10);
        Tuple3fState endPoint2 = new Tuple3fState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                initialEndPoint2.getZ() * 10);
        return new TreeLeaf3DState(transformState, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2);
    }

    /**
     * Create a new leaf3D state with :
     * <ul>
     * <li>the given transform</li>
     * <li>initial end point 1 : (-0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>initial end point 2 : (0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>end point 1 : initial end point 1</li>
     * <li>end point 2 : initial end point 2</li>
     * </ul>
     */
    public TreeLeaf3DState createNewTreeLeaf3DState(Transform3D transform) {
        Transform3DState transformState = new Transform3DState(transform);

        float x1 = -0.02f + Randomizer.random1() / 10;
        float y1 = -0.04f + Randomizer.random1() / 10;
        int z1 = 0;
        Tuple3fState initialEndPoint1 = new Tuple3fState(x1, y1, z1);

        float x2 = 0.02f + Randomizer.random1() / 10;
        float y2 = -0.04f + Randomizer.random1() / 10;
        int z2 = 0;
        Tuple3fState initialEndPoint2 = new Tuple3fState(x2, y2, z2);

        Tuple3fState endPoint1 = new Tuple3fState(x1, y1, z1);
        Tuple3fState endPoint2 = new Tuple3fState(x2, y2, z2);

        return new TreeLeaf3DState(transformState, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2);
    }
}
