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

import barsuift.simLife.j3d.tree.MockTreeBranch3D;
import barsuift.simLife.j3d.tree.TreeBranch3D;
import barsuift.simLife.message.MockSubscriber;

public class MockTreeBranch extends MockSubscriber implements TreeBranch {

    private long creationMillis = 0;

    private int ageCalled = 0;

    private BigDecimal energy = new BigDecimal(0);

    private BigDecimal freeEnergy = new BigDecimal(0);

    private int collectSolarEnergyCalled = 0;

    private int nbLeaves = 0;

    private TreeBranch3D branch3D = new MockTreeBranch3D();

    private List<TreeLeaf> leaves = new ArrayList<TreeLeaf>();

    private TreeBranchState state = new TreeBranchState();

    private int synchronizedCalled;

    private int growCalled = 0;

    @Override
    public long getCreationMillis() {
        return creationMillis;
    }

    public void setCreationMillis(long creationMillis) {
        this.creationMillis = creationMillis;
    }

    @Override
    public void age() {
        ageCalled++;
    }

    public int getNbAgeCalled() {
        return ageCalled;
    }

    @Override
    public int getNbLeaves() {
        return nbLeaves;
    }

    public void setNbLeaves(int nbLeaves) {
        this.nbLeaves = nbLeaves;
    }

    @Override
    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }

    @Override
    public BigDecimal collectFreeEnergy() {
        return freeEnergy;
    }

    public void setFreeEnergy(BigDecimal freeEnergy) {
        this.freeEnergy = freeEnergy;
    }

    @Override
    public void collectSolarEnergy() {
        collectSolarEnergyCalled++;
    }

    public int getNbCollectSolarEnergyCalled() {
        return collectSolarEnergyCalled;
    }

    @Override
    public TreeBranch3D getBranch3D() {
        return branch3D;
    }

    public void set(TreeBranch3D branch3D) {
        this.branch3D = branch3D;
    }

    @Override
    public List<TreeLeaf> getLeaves() {
        return leaves;
    }

    public void addLeaf(TreeLeaf leaf) {
        leaves.add(leaf);
    }

    public void removeLeaf(TreeLeaf leaf) {
        leaves.remove(leaf);
    }

    @Override
    public TreeBranchState getState() {
        return state;
    }

    public void setState(TreeBranchState state) {
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
    public void grow() {
        growCalled++;
    }

    public int getNbGrowCalled() {
        return growCalled;
    }

}
