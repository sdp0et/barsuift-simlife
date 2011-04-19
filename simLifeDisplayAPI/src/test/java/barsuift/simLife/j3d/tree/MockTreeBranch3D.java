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
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


public class MockTreeBranch3D implements TreeBranch3D {

    private List<TreeLeaf3D> leaves = new ArrayList<TreeLeaf3D>();

    private Point3f endPoint = new Point3f();

    private Group group = new Group();

    private TreeBranch3DState state = new TreeBranch3DState();

    private int synchronizedCalled;

    private Vector3f translationVector = new Vector3f();

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
    public Point3f getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point3f endPoint) {
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

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

    @Override
    public Vector3f getTranslationVector() {
        return translationVector;
    }

    public void setTranslationVector(Vector3f translationVector) {
        this.translationVector = translationVector;
    }

}
