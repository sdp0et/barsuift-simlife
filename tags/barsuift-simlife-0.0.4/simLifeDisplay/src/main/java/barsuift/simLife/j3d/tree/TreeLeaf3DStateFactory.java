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

import javax.vecmath.Point3d;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Tuple3dState;


public class TreeLeaf3DStateFactory {

    /**
     * Create a random leaf3D state with :
     * <ul>
     * <li>the given attach point</li>
     * <li>initial end point 1 : (-0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>initial end point 2 : (0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>end point 1 : 10 * initial end point 1</li>
     * <li>end point 2 : 10 * initial end point 2</li>
     * <li>rotation : a random number between 0 and 2 PI</li>
     * </ul>
     */
    public TreeLeaf3DState createRandomTreeLeaf3DState(Point3d leafAttachPoint) {
        Tuple3dState leafAttachPointState = new Tuple3dState(leafAttachPoint);
        Tuple3dState initialEndPoint1 = new Tuple3dState(-0.02 + Randomizer.random1() / 10, -0.04
                + Randomizer.random1() / 10, 0);
        Tuple3dState initialEndPoint2 = new Tuple3dState(0.02 + Randomizer.random1() / 10, -0.04 + Randomizer.random1()
                / 10, 0);
        Tuple3dState endPoint1 = new Tuple3dState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                initialEndPoint1.getZ() * 10);
        Tuple3dState endPoint2 = new Tuple3dState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                initialEndPoint2.getZ() * 10);
        double rotation = Randomizer.randomRotation();
        return new TreeLeaf3DState(leafAttachPointState, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2,
                rotation);
    }

    /**
     * Create a new leaf3D state with :
     * <ul>
     * <li>the given attach point</li>
     * <li>initial end point 1 : (-0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>initial end point 2 : (0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>end point 1 : initial end point 1</li>
     * <li>end point 2 : initial end point 2</li>
     * <li>rotation : a random number between 0 and 2 PI</li>
     * </ul>
     */
    public TreeLeaf3DState createNewTreeLeaf3DState(Point3d leafAttachPoint) {
        Tuple3dState leafAttachPointState = new Tuple3dState(leafAttachPoint);

        double x1 = -0.02 + Randomizer.random1() / 10;
        double y1 = -0.04 + Randomizer.random1() / 10;
        int z1 = 0;
        Tuple3dState initialEndPoint1 = new Tuple3dState(x1, y1, z1);

        double x2 = 0.02 + Randomizer.random1() / 10;
        double y2 = -0.04 + Randomizer.random1() / 10;
        int z2 = 0;
        Tuple3dState initialEndPoint2 = new Tuple3dState(x2, y2, z2);

        Tuple3dState endPoint1 = new Tuple3dState(x1, y1, z1);
        Tuple3dState endPoint2 = new Tuple3dState(x2, y2, z2);

        double rotation = Randomizer.randomRotation();
        return new TreeLeaf3DState(leafAttachPointState, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2,
                rotation);
    }
}