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
package barsuift.simLife.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.tree.Tree3DState;
import barsuift.simLife.j3d.tree.Tree3DStateFactory;
import barsuift.simLife.process.Aging;
import barsuift.simLife.process.ConditionalTaskState;
import barsuift.simLife.process.ConditionalTaskStateFactory;
import barsuift.simLife.process.Photosynthesis;
import barsuift.simLife.process.TreeGrowth;

public class TreeStateFactory {

    public static final int HEIGHT_RADIUS_RATIO = 8;

    /**
     * Ratio between the length of branches compared to the tree height
     */
    public static final float HEIGHT_BRANCH_RADIAL_LENGTH_RATIO = 0.5f;

    public TreeState createRandomTreeState(Point3f translationVector) {
        int creationMillis = Randomizer.randomBetween(0, 100) * 1000;
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        int nbBranches = Randomizer.randomBetween(30, 50);
        float height = Randomizer.randomBetween(3, 5);
        float radius = height / HEIGHT_RADIUS_RATIO;
        List<TreeBranchState> branches = new ArrayList<TreeBranchState>(nbBranches);
        for (int i = 0; i < nbBranches; i++) {
            branches.add(computeRandomBranchState(radius, height));
        }
        ConditionalTaskStateFactory taskStateFactory = new ConditionalTaskStateFactory();
        ConditionalTaskState photosynthesis = taskStateFactory.createConditionalTaskState(Photosynthesis.class);
        ConditionalTaskState aging = taskStateFactory.createConditionalTaskState(Aging.class);
        ConditionalTaskState growth = taskStateFactory.createConditionalTaskState(TreeGrowth.class);
        TreeTrunkStateFactory trunkStateFactory = new TreeTrunkStateFactory();
        TreeTrunkState trunkState = trunkStateFactory.createRandomTreeTrunkState(radius, height);

        Tree3DStateFactory tree3DStateFactory = new Tree3DStateFactory();
        Tree3DState tree3dState = tree3DStateFactory.createRandomTree3DState(new Tuple3fState(translationVector));


        return new TreeState(creationMillis, energy, branches, photosynthesis, aging, growth, trunkState, height,
                tree3dState);
    }

    protected TreeBranchState computeRandomBranchState(float treeRadius, float treeHeight) {
        Vector3f branchTranslationVector = computeBranchTranslationVector(treeRadius, treeHeight);
        Point3f branchEndPoint = computeBranchEndPoint(treeHeight, branchTranslationVector.getX() > 0,
                branchTranslationVector.getZ() > 0);
        TreeBranchStateFactory branchStateFactory = new TreeBranchStateFactory();
        return branchStateFactory.createRandomBranchState(branchTranslationVector, branchEndPoint);
    }

    /**
     * Compute the branch translation vector.
     * <p>
     * <ul>
     * <li>x=[-1;1] * treeRadius</li>
     * <li>y=treeHeight</li>
     * <li>z=[-1;1] * treeRadius * sin(acos(x))</li>
     * </ul>
     * </p>
     * 
     * @param treeRadius the tree radius
     * @param treeHeight the tree height
     * @return the computed translation vector
     */
    protected Vector3f computeBranchTranslationVector(float treeRadius, float treeHeight) {
        float translationXShift = Randomizer.random3();
        float translationZShift = Randomizer.random3() * (float) Math.sin(Math.acos(translationXShift));
        float translationX = translationXShift * treeRadius;
        float translationZ = translationZShift * treeRadius;
        Vector3f translationVector = new Vector3f(translationX, treeHeight, translationZ);
        return translationVector;
    }

    /**
     * Compute the branch end point.
     * <p>
     * The end point is computed as follows :
     * <ul>
     * <li>x=[0-1] * HEIGHT_BRANCH_RADIAL_LENGTH_RATIO * treeHeight</li>
     * <li>y=[0-1] * treeHeight</li>
     * <li>z=[0-1] * HEIGHT_BRANCH_RADIAL_LENGTH_RATIO * treeHeight</li>
     * </ul>
     * </p>
     * 
     * @param treeHeight the tree height
     * @param goingToPositiveX true of the x should be positive, false otherwise
     * @param goingToPositiveZ true of the z should be positive, false otherwise
     * @return the computed branch end point
     */
    protected Point3f computeBranchEndPoint(float treeHeight, boolean goingToPositiveX, boolean goingToPositiveZ) {
        float xMove = (float) Math.random() * HEIGHT_BRANCH_RADIAL_LENGTH_RATIO * treeHeight;
        if (!goingToPositiveX) {
            xMove = -xMove;
        }
        float yMove = (float) Math.random() * treeHeight;
        float zMove = (float) Math.random() * HEIGHT_BRANCH_RADIAL_LENGTH_RATIO * treeHeight;
        if (!goingToPositiveZ) {
            zMove = -zMove;
        }
        Point3f endPoint = new Point3f(xMove, yMove, zMove);
        return endPoint;
    }

}
