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

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DStateFactory;
import barsuift.simLife.j3d.util.BarycentreHelper;
import barsuift.simLife.j3d.util.DistanceHelper;
import barsuift.simLife.j3d.util.TransformerHelper;

public class TreeBranchStateFactory {

    public TreeBranchState createRandomBranchState(Vector3f translationVector, Point3f branchEndPoint) {
        int creationMillis = Randomizer.randomBetween(0, 100) * 1000;
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        TreeLeafStateFactory leafStateFactory = new TreeLeafStateFactory();
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>();
        int nbLeaves = Randomizer.randomBetween(6, 12);
        float maxDistance = DistanceHelper.distanceFromOrigin(branchEndPoint);
        float shift = maxDistance / nbLeaves;
        for (int index = 0; index < nbLeaves; index++) {
            Point3f leafAttachPoint = BarycentreHelper.getBarycentre(new Point3f(0, 0, 0), branchEndPoint,
                    (index + Randomizer.random2()) * shift);
            double rotation = Randomizer.randomRotation();
            Transform3D transform = TransformerHelper.getTranslationTransform3D(new Vector3f(leafAttachPoint));
            Transform3D rotationT3D = TransformerHelper.getRotationTransform3D(rotation, Axis.Y);
            transform.mul(rotationT3D);
            leavesStates.add(leafStateFactory.createRandomTreeLeafState(transform));
        }

        TreeBranch3DStateFactory branch3DStateFactory = new TreeBranch3DStateFactory();
        TreeBranch3DState branch3DState = branch3DStateFactory.createRandomTreeBranch3DState(translationVector,
                branchEndPoint);

        return new TreeBranchState(creationMillis, energy, freeEnergy, leavesStates, branch3DState);
    }

}
