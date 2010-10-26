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

import barsuift.simLife.j3d.tree.MockTree3D;
import barsuift.simLife.j3d.tree.Tree3D;

public class MockTree implements Tree {

    private long creationMillis = 0;

    private int ageCalled = 0;

    private BigDecimal energy = new BigDecimal(0);

    private BigDecimal freeEnergy = new BigDecimal(0);

    private int collectSolarEnergyCalled = 0;

    private List<TreeBranch> branches = new ArrayList<TreeBranch>();

    private float height = 0;

    private int nbBranches = 0;

    private int nbLeaves = 0;

    private TreeState state = new TreeState();

    private Tree3D tree3D = new MockTree3D();

    private TreeTrunk trunk = new MockTreeTrunk();

    private int synchronizedCalled;

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

    public void removeBranch(TreeBranch branch) {
        this.branches.remove(branch);
    }

    public void addBranch(TreeBranch branch) {
        this.branches.add(branch);
    }

    @Override
    public List<TreeBranch> getBranches() {
        return branches;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setNbBranches(int nbBranches) {
        this.nbBranches = nbBranches;
    }

    @Override
    public int getNbBranches() {
        return nbBranches;
    }

    public void setNbLeaves(int nbLeaves) {
        this.nbLeaves = nbLeaves;
    }

    @Override
    public int getNbLeaves() {
        return nbLeaves;
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
    public TreeState getState() {
        return state;
    }

    public void setState(TreeState state) {
        this.state = state;
    }

    @Override
    public Tree3D getTree3D() {
        return tree3D;
    }

    public void setTree3D(Tree3D tree3D) {
        this.tree3D = tree3D;
    }

    @Override
    public TreeTrunk getTrunk() {
        return trunk;
    }

    public void setTreeTrunk(TreeTrunk trunk) {
        this.trunk = trunk;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

}
