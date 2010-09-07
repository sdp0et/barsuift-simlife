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


public class MockTree3D implements Tree3D {

    private BranchGroup bg = new BranchGroup();

    private List<TreeBranch3D> branches3D = new ArrayList<TreeBranch3D>();

    private Tree3DState state = new Tree3DState();

    private TreeTrunk3D trunk3D = new MockTreeTrunk3D();

    @Override
    public BranchGroup getBranchGroup() {
        return bg;
    }

    public void setBranchGroup(BranchGroup bg) {
        this.bg = bg;
    }

    @Override
    public List<TreeBranch3D> getBranches() {
        return branches3D;
    }

    public void addBranch3D(TreeBranch3D branch3D) {
        branches3D.add(branch3D);
    }

    public void removeBranch3D(TreeBranch3D branch3D) {
        branches3D.remove(branch3D);
    }

    @Override
    public Tree3DState getState() {
        return state;
    }

    public void setState(Tree3DState state) {
        this.state = state;
    }

    @Override
    public TreeTrunk3D getTrunk() {
        return trunk3D;
    }

    public void setTrunk(TreeTrunk3D trunk3D) {
        this.trunk3D = trunk3D;
    }

}
