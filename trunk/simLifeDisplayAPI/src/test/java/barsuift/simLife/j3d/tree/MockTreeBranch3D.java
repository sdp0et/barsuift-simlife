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

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;


public class MockTreeBranch3D implements TreeBranch3D {

    private List<TreeLeaf3D> leaves;

    private float length;

    private float radius;

    private BranchGroup branchGroup;

    private TreeBranch3DState state;

    private int synchronizedCalled;

    private int increaseOneLeafSizeCalled;

    private Transform3D transform;

    public MockTreeBranch3D() {
        super();
        reset();
    }

    public void reset() {
        leaves = new ArrayList<TreeLeaf3D>();
        length = 0;
        radius = 0;
        branchGroup = new BranchGroup();
        state = new TreeBranch3DState();
        synchronizedCalled = 0;
        increaseOneLeafSizeCalled = 0;
        transform = new Transform3D();
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
    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    @Override
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public BranchGroup getBranchGroup() {
        return branchGroup;
    }

    public void setGroup(BranchGroup branchGroup) {
        this.branchGroup = branchGroup;
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
    public void increaseOneLeafSize() {
        this.increaseOneLeafSizeCalled++;
    }

    public int getNbIncreaseOneLeafSizeCalled() {
        return increaseOneLeafSizeCalled;
    }

    @Override
    public Transform3D getTransform() {
        return transform;
    }

    public void setTransform(Transform3D transform) {
        this.transform = transform;
    }

}
