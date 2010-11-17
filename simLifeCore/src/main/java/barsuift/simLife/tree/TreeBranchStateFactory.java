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

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DStateFactory;
import barsuift.simLife.j3d.util.BarycentreHelper;
import barsuift.simLife.j3d.util.PointHelper;

public class TreeBranchStateFactory {

    public TreeBranchState createRandomBranchState(Vector3d translationVector, Point3d branchEndPoint) {
        int creationMillis = Randomizer.randomBetween(0, 100) * 1000;
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        TreeBranchPartStateFactory treeBranchPartStateFactory = new TreeBranchPartStateFactory();
        List<TreeBranchPartState> treeBranchPartStates = new ArrayList<TreeBranchPartState>();
        // TODO 080. the number of parts should be related to the length of the branch
        // TODO 050. 001. the parts should be removed entirely and branches should be added to branches
        // TODO 050. 025. only at this time, the branch should manage their own transforms, as done for leaves
        int nbParts = 3;
        for (int i = 0; i < nbParts; i++) {
            Point3d branchPartEndPoint = computeBranchPartEndPoint(branchEndPoint, nbParts);
            treeBranchPartStates.add(treeBranchPartStateFactory.createRandomBranchPartState(branchPartEndPoint));
        }

        TreeBranch3DStateFactory branch3DStateFactory = new TreeBranch3DStateFactory();
        TreeBranch3DState branch3DState = branch3DStateFactory.createRandomTreeBranch3DState(translationVector);

        return new TreeBranchState(creationMillis, energy, freeEnergy, treeBranchPartStates, branch3DState);
    }

    protected Point3d computeBranchPartEndPoint(Point3d branchEndPoint, int nbParts) {
        Point3d startPoint = new Point3d(0, 0, 0);
        double maxDistance = startPoint.distance(branchEndPoint);
        double averagePartLength = maxDistance / nbParts;
        Point3d partEndPoint = BarycentreHelper.getBarycentre(new Point3d(0, 0, 0), branchEndPoint,
                (Randomizer.random2() + 1) * averagePartLength);
        partEndPoint = PointHelper.shiftPoint(partEndPoint, maxDistance / 10);
        return partEndPoint;
    }

}
