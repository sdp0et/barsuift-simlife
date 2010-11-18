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


public class MockTreeBranchPart3D implements TreeBranchPart3D {

    private Point3d endPoint = new Point3d();

    private Group group = new Group();

    private List<TreeLeaf3D> leaves = new ArrayList<TreeLeaf3D>();

    private TreeBranchPart3DState part3DState = new TreeBranchPart3DState();

    private int synchronizedCalled;

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
    public List<TreeLeaf3D> getLeaves() {
        return leaves;
    }

    @Override
    public void addLeaf(TreeLeaf3D leaf3D) {
        leaves.add(leaf3D);
    }

    public void removeLeaf(TreeLeaf3D leaf3D) {
        leaves.remove(leaf3D);
    }

    @Override
    public TreeBranchPart3DState getState() {
        return part3DState;
    }

    public void setState(TreeBranchPart3DState part3DState) {
        this.part3DState = part3DState;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

}
