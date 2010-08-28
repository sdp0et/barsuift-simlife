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
package barsuift.simLife.j3d;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.Tree3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranchPart3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeTrunk3DState;



public final class DisplayDataCreatorForTests {

    private DisplayDataCreatorForTests() {
        // private constructor to enforce static access
    }

    public static Point3dState createRandomPointState() {
        return new Point3dState(Math.random(), Math.random(), Math.random());
    }

    public static TreeLeaf3DState createRandomTreeLeaf3DState() {
        Point3dState attachPoint = createRandomPointState();
        Point3dState initialEndPoint1 = createRandomPointState();
        Point3dState initialEndPoint2 = createRandomPointState();
        Point3dState endPoint1 = createRandomPointState();
        Point3dState endPoint2 = createRandomPointState();
        double rotation = Randomizer.randomRotation();
        return new TreeLeaf3DState(attachPoint, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2, rotation);
    }

    /**
     * Create a specific leaf 3D with
     * <ul>
     * <li>attachPoint=(0.1, 0.2, 0.0)</li>
     * <li>initialEndPoint1=(0.2, 0.2, 0.0)</li>
     * <li>initialEndPoint2=(0.2, 0.1, 0.0)</li>
     * <li>endPoint1=(0.4, 0.0, 0.0)</li>
     * <li>endPoint2=(0.2, 0.4, 0.0)</li>
     * <li>rotation=Pi/3</li>
     * </ul>
     * For information, the area is 0.08
     * 
     * @return
     */
    public static TreeLeaf3DState createSpecificTreeLeaf3DState() {
        Point3dState attachPoint = new Point3dState(0.1, 0.2, 0.0);
        Point3dState initialEndPoint1 = new Point3dState(0.2, 0.2, 0.0);
        Point3dState initialEndPoint2 = new Point3dState(0.2, 0.1, 0.0);
        Point3dState endPoint1 = new Point3dState(0.4, 0.0, 0.0);
        Point3dState endPoint2 = new Point3dState(0.2, 0.4, 0.0);
        double rotation = Math.PI / 3;
        return new TreeLeaf3DState(attachPoint, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2, rotation);
    }

    public static TreeBranchPart3DState createRandomTreeBranchPart3DState() {
        Point3dState endPoint = createRandomPointState();
        return new TreeBranchPart3DState(endPoint);
    }

    public static TreeBranch3DState createRandomTreeBranch3DState() {
        Point3dState translationVector = createRandomPointState();
        return new TreeBranch3DState(translationVector);
    }

    public static TreeTrunk3DState createRandomTreeTrunk3DState() {
        return new TreeTrunk3DState();
    }

    public static Tree3DState createRandomTree3DState() {
        Point3dState translationVector = createRandomPointState();
        return new Tree3DState(translationVector);
    }

}
