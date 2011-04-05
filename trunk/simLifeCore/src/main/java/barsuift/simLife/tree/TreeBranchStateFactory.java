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
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DStateFactory;
import barsuift.simLife.j3d.util.BarycentreHelper;
import barsuift.simLife.j3d.util.PointHelper;

public class TreeBranchStateFactory {

    public TreeBranchState createRandomBranchState(Vector3f translationVector, Point3f branchEndPoint) {
        int creationMillis = Randomizer.randomBetween(0, 100) * 1000;
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        TreeBranchPartStateFactory treeBranchPartStateFactory = new TreeBranchPartStateFactory();
        List<TreeBranchPartState> treeBranchPartStates = new ArrayList<TreeBranchPartState>();
        // TODO 050. 025. once parts are removed and branches are added to branches, branches should manage their own transforms, as done for leaves
        int nbParts = 3;
        for (int i = 0; i < nbParts; i++) {
            Point3f branchPartEndPoint = computeBranchPartEndPoint(branchEndPoint, nbParts);
            treeBranchPartStates.add(treeBranchPartStateFactory.createRandomBranchPartState(branchPartEndPoint));
        }

        TreeBranch3DStateFactory branch3DStateFactory = new TreeBranch3DStateFactory();
        TreeBranch3DState branch3DState = branch3DStateFactory.createRandomTreeBranch3DState(translationVector);

        return new TreeBranchState(creationMillis, energy, freeEnergy, treeBranchPartStates, branch3DState);
    }

    protected Point3f computeBranchPartEndPoint(Point3f branchEndPoint, int nbParts) {
        Point3f startPoint = new Point3f(0, 0, 0);
        float maxDistance = startPoint.distance(branchEndPoint);
        float averagePartLength = maxDistance / nbParts;
        Point3f partEndPoint = BarycentreHelper.getBarycentre(new Point3f(0, 0, 0), branchEndPoint,
                (Randomizer.random2() + 1) * averagePartLength);
        partEndPoint = PointHelper.shiftPoint(partEndPoint, maxDistance / 10);
        return partEndPoint;
    }

}
