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

import java.util.List;

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.util.TransformerHelper;


public class TreeBranchesOrganizer {

    public void organizeBranches(List<TreeBranch3DState> branchesStates, float treeHeight) {
        for (TreeBranch3DState treeBranch3DState : branchesStates) {
            double yRotation = Randomizer.randomRotation();
            Transform3D transform = TransformerHelper.getRotationTransform3D(yRotation, Axis.Y);
            double zRotation = Randomizer.randomBetween(-(float) Math.PI / 2, 0);
            Transform3D rotationZ = TransformerHelper.getRotationTransform3D(zRotation, Axis.Z);
            transform.mul(rotationZ);
            transform.setTranslation(new Vector3f(0, treeHeight, 0));
            treeBranch3DState.setTransform(new Transform3DState(transform));
        }
    }

}
