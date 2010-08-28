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

import barsuift.simLife.MockObserver;
import barsuift.simLife.j3d.tree.MockTreeBranchPart3D;
import barsuift.simLife.j3d.tree.TreeBranchPart3D;


public class MockTreeBranchPart extends MockObserver implements TreeBranchPart {

    private int spendTimeCalled = 0;

    private TreeBranchPart3D branchPart3D = new MockTreeBranchPart3D();

    private List<TreeLeaf> leaves = new ArrayList<TreeLeaf>();

    private int nbLeaves = 0;

    private TreeBranchPartState partState = new TreeBranchPartState();

    private int age = 0;

    private BigDecimal energy = new BigDecimal(0);

    private BigDecimal freeEnergy = new BigDecimal(0);

    private Long id = new Long(1);

    @Override
    public TreeBranchPart3D getBranchPart3D() {
        return branchPart3D;
    }

    public void setBranchPart3D(TreeBranchPart3D branchPart3D) {
        this.branchPart3D = branchPart3D;
    }

    @Override
    public List<TreeLeaf> getLeaves() {
        return leaves;
    }

    public void addLeaf(TreeLeaf part) {
        leaves.add(part);
    }

    public void removeLeaf(TreeLeaf part) {
        leaves.remove(part);
    }

    @Override
    public int getNbLeaves() {
        return nbLeaves;
    }

    public void setNbLeaves(int nbLeaves) {
        this.nbLeaves = nbLeaves;
    }

    @Override
    public TreeBranchPartState getState() {
        return partState;
    }

    public void setState(TreeBranchPartState partState) {
        this.partState = partState;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void spendTime() {
        spendTimeCalled++;
    }

    public int howManyTimesSpendTimeCalled() {
        return spendTimeCalled;
    }

    public void resetSpendTimeCalled() {
        this.spendTimeCalled = 0;
    }

}
