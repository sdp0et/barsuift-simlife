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

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;

import barsuift.simLife.Persistent;

public interface TreeBranch3D extends Persistent<TreeBranch3DState> {

    public float getLength();

    public float getRadius();

    public void addLeaf(TreeLeaf3D leaf);

    public List<TreeLeaf3D> getLeaves();

    public BranchGroup getBranchGroup();

    public Transform3D getTransform();

    public void increaseOneLeafSize();

}
