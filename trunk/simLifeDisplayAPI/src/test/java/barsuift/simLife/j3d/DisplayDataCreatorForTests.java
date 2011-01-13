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

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import barsuift.simLife.Randomizer;
import barsuift.simLife.UtilDataCreatorForTests;
import barsuift.simLife.j3d.environment.Environment3DState;
import barsuift.simLife.j3d.environment.Sun3DState;
import barsuift.simLife.j3d.landscape.Landscape3DState;
import barsuift.simLife.j3d.landscape.NavigationMode;
import barsuift.simLife.j3d.landscape.NavigatorState;
import barsuift.simLife.j3d.tree.Tree3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranchPart3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeTrunk3DState;
import barsuift.simLife.j3d.universe.Universe3DState;
import barsuift.simLife.j3d.universe.UniverseContext3DState;
import barsuift.simLife.j3d.universe.physic.Gravity3DState;
import barsuift.simLife.j3d.universe.physic.Physics3DState;
import barsuift.simLife.process.SplitConditionalTaskState;
import barsuift.simLife.process.Synchronizer3DState;



public final class DisplayDataCreatorForTests {

    private DisplayDataCreatorForTests() {
        // private constructor to enforce static access
    }

    public static Synchronizer3DState createRandomSynchronizer3DState() {
        return new Synchronizer3DState(Randomizer.randomBetween(1, 20));
    }

    public static Synchronizer3DState createSpecificSynchronizer3DState() {
        return new Synchronizer3DState(1);
    }

    public static Physics3DState createRandomPhysics3DState() {
        return new Physics3DState();
    }

    public static Physics3DState createSpecificPhysics3DState() {
        return new Physics3DState();
    }

    public static Gravity3DState createRandomGravity3DState() {
        SplitConditionalTaskState gravityTask = UtilDataCreatorForTests.createRandomSplitConditionalTaskState();
        return new Gravity3DState(gravityTask);
    }

    public static Gravity3DState createSpecificGravity3DState() {
        SplitConditionalTaskState gravityTask = UtilDataCreatorForTests.createSpecificSplitConditionalTaskState();
        return new Gravity3DState(gravityTask);
    }

    public static Tuple3fState createRandomTuple3fState() {
        return new Tuple3fState((float) Math.random(), (float) Math.random(), (float) Math.random());
    }

    public static Tuple3dState createRandomTuple3dState() {
        return new Tuple3dState(Math.random(), Math.random(), Math.random());
    }

    public static Transform3DState createSpecificTransform3DState() {
        return new Transform3DState(new Transform3D());
    }

    public static Transform3DState createRandomTransform3DState() {
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()));
        Transform3D rotation = new Transform3D();
        rotation.rotY(Randomizer.randomRotation());
        transform.mul(rotation);
        return new Transform3DState(transform);
    }

    public static BoundingBoxState createSpecificBoundingBoxState() {
        Tuple3dState lower = new Tuple3dState(0, 0, 0);
        Tuple3dState upper = new Tuple3dState(10, 10, 10);
        return new BoundingBoxState(lower, upper);
    }

    public static BoundingBoxState createRandomBoundingBoxState() {
        Tuple3dState lower = createRandomTuple3dState();
        Tuple3dState upper = new Tuple3dState(lower.getX() + 1, lower.getY() + 1, lower.getZ() + 1);
        return new BoundingBoxState(lower, upper);
    }

    public static Sun3DState createRandomSun3DState() {
        return new Sun3DState();
    }

    public static Sun3DState createSpecificSun3DState() {
        return new Sun3DState();
    }

    public static Environment3DState createRandomEnvironment3DState() {
        return new Environment3DState();
    }

    public static Environment3DState createSpecificEnvironment3DState() {
        return new Environment3DState();
    }


    /**
     * Create a square landscape (size=2) of 4 points from (0,0) to (1,1), with random heights between 0 and 1.
     */
    public static Landscape3DState createRandomLandscape3DState() {
        int size = 2;
        float[] coordinates = { 0, (float) Math.random(), 0, 1, (float) Math.random(), 0, 0, (float) Math.random(), 1,
                1, (float) Math.random(), 1 };
        int[] coordinatesIndices = { 0, 2, 1, 1, 2, 3 };
        int[] stripCounts = { 3, 3 };
        return new Landscape3DState(size, coordinates, coordinatesIndices, stripCounts);
    }

    /**
     * Create a square landscape, with size=2 and the 4 following points :
     * <ol>
     * <li>(0.0, 0.0, 0.0)</li>
     * <li>(1.0, 0.4, 0.0)</li>
     * <li>(0.0, 0.6, 1.0)</li>
     * <li>(1.0, 1.2, 1.0)</li>
     * </ol>
     */
    public static Landscape3DState createSpecificLandscape3DState() {
        int size = 2;
        float[] coordinates = { 0, 0, 0, 1, 0.4f, 0, 0, 0.6f, 1, 1, 1.2f, 1 };
        int[] coordinatesIndices = { 0, 2, 1, 1, 2, 3 };
        int[] stripCounts = { 3, 3 };
        return new Landscape3DState(size, coordinates, coordinatesIndices, stripCounts);
    }

    public static NavigatorState createRandomNavigatorState() {
        Tuple3fState translation = createRandomTuple3fState();
        double rotationX = UtilDataCreatorForTests.createRandomRotation();
        double rotationY = UtilDataCreatorForTests.createRandomRotation();
        NavigationMode navigationMode;
        if (UtilDataCreatorForTests.createRandomBoolean()) {
            navigationMode = NavigationMode.FLY;
        } else {
            navigationMode = NavigationMode.WALK;
        }
        return new NavigatorState(translation, rotationX, rotationY, navigationMode);
    }

    public static NavigatorState createSpecificNavigatorState() {
        Tuple3fState translation = new Tuple3fState(0.5f, 0f, 0.6f);
        double rotationX = Math.PI / 2;
        double rotationY = Math.PI;
        return new NavigatorState(translation, rotationX, rotationY, NavigationMode.DEFAULT);
    }

    public static UniverseContext3DState createRandomUniverseContext3DState() {
        SimLifeCanvas3DState canvas = createRandomCanvasState();
        boolean axisShowing = UtilDataCreatorForTests.createRandomBoolean();
        NavigatorState navigator = createRandomNavigatorState();
        return new UniverseContext3DState(canvas, axisShowing, navigator);
    }

    /**
     * Create a specific universe context state with
     * <ul>
     * <li>canvasState made through {@link #createSpecificCanvasState()}</li>
     * <li>axisShowing = true</li>
     * <li>navigator made through {@link #createSpecificNavigatorState()}</li>
     * </ul>
     */
    public static UniverseContext3DState createSpecificUniverseContext3DState() {
        SimLifeCanvas3DState canvas = createSpecificCanvasState();
        boolean axisShowing = true;
        NavigatorState navigator = createSpecificNavigatorState();
        return new UniverseContext3DState(canvas, axisShowing, navigator);
    }

    public static Universe3DState createRandomUniverse3DState() {
        Synchronizer3DState synchronizerState = createRandomSynchronizer3DState();
        return new Universe3DState(synchronizerState);
    }

    public static Universe3DState createSpecificUniverse3DState() {
        Synchronizer3DState synchronizerState = createSpecificSynchronizer3DState();
        return new Universe3DState(synchronizerState);
    }

    public static TreeLeaf3DState createRandomTreeLeaf3DState() {
        Transform3DState transform = createRandomTransform3DState();
        Tuple3fState initialEndPoint1 = createRandomTuple3fState();
        Tuple3fState initialEndPoint2 = createRandomTuple3fState();
        Tuple3fState endPoint1 = createRandomTuple3fState();
        Tuple3fState endPoint2 = createRandomTuple3fState();
        return new TreeLeaf3DState(transform, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2);
    }

    /**
     * Create a specific leaf 3D with
     * <ul>
     * <li>transform=identity</li>
     * <li>initialEndPoint1=(0.2, 0.2, 0.0)</li>
     * <li>initialEndPoint2=(0.2, 0.1, 0.0)</li>
     * <li>endPoint1=(0.4, 0.0, 0.0)</li>
     * <li>endPoint2=(0.2, 0.4, 0.0)</li>
     * </ul>
     * For information, the area is 0.08
     * 
     * @return
     */
    public static TreeLeaf3DState createSpecificTreeLeaf3DState() {
        Transform3DState transform = createSpecificTransform3DState();
        Tuple3fState initialEndPoint1 = new Tuple3fState(0.2f, 0.2f, 0.0f);
        Tuple3fState initialEndPoint2 = new Tuple3fState(0.2f, 0.1f, 0.0f);
        Tuple3fState endPoint1 = new Tuple3fState(0.4f, 0.0f, 0.0f);
        Tuple3fState endPoint2 = new Tuple3fState(0.2f, 0.4f, 0.0f);
        return new TreeLeaf3DState(transform, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2);
    }

    public static TreeBranchPart3DState createRandomTreeBranchPart3DState() {
        Tuple3fState endPoint = createRandomTuple3fState();
        return new TreeBranchPart3DState(endPoint);
    }

    public static TreeBranch3DState createRandomTreeBranch3DState() {
        Tuple3fState translationVector = createRandomTuple3fState();
        return new TreeBranch3DState(translationVector);
    }

    public static TreeTrunk3DState createRandomTreeTrunk3DState() {
        return new TreeTrunk3DState();
    }

    public static Tree3DState createRandomTree3DState() {
        Tuple3fState translationVector = createRandomTuple3fState();
        return new Tree3DState(translationVector);
    }

    public static SimLifeCanvas3DState createRandomCanvasState() {
        return new SimLifeCanvas3DState(UtilDataCreatorForTests.createRandomBoolean());
    }

    public static SimLifeCanvas3DState createSpecificCanvasState() {
        return new SimLifeCanvas3DState(false);
    }

    /**
     * Return an array of float representing an identity matrix of 16 elements
     */
    public static float[] createSpecificTransform3D() {
        return new float[] { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };
    }

}
