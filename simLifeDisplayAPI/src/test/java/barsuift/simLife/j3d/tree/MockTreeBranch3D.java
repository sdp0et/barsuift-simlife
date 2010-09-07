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

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Group;
import javax.vecmath.Point3d;


public class MockTreeBranch3D implements TreeBranch3D {

    private List<TreeBranchPart3D> branchParts3D = new ArrayList<TreeBranchPart3D>();

    private Point3d endPoint = new Point3d();

    private Group group = new Group();

    private TreeBranch3DState state = new TreeBranch3DState();

    @Override
    public List<TreeBranchPart3D> getBranchParts() {
        return branchParts3D;
    }

    public void addTreeBranchPart3D(TreeBranchPart3D branchPart3D) {
        branchParts3D.add(branchPart3D);
    }

    public void removeTreeBranchPart3D(TreeBranchPart3D branchPart3D) {
        branchParts3D.remove(branchPart3D);
    }

    @Override
    public Point3d getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point3d endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public TreeBranch3DState getState() {
        return state;
    }

    public void setState(TreeBranch3DState state) {
        this.state = state;
    }

}
