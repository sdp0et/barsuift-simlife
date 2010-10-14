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

    private int age = 0;

    private BigDecimal energy = new BigDecimal(0);

    private BigDecimal freeEnergy = new BigDecimal(0);

    private int spendTimeCalled = 0;

    private int nbLeaves = 0;

    private TreeBranch3D branch3D = new MockTreeBranch3D();

    private int nbParts = 0;

    private List<TreeBranchPart> parts = new ArrayList<TreeBranchPart>();

    private TreeBranchState state = new TreeBranchState();

    private int synchronizedCalled;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
    public void spendTime() {
        spendTimeCalled++;
    }

    public int howManyTimesSpendTimeCalled() {
        return spendTimeCalled;
    }

    public void resetSpendTimeCalled() {
        this.spendTimeCalled = 0;
    }

    @Override
    public TreeBranch3D getBranch3D() {
        return branch3D;
    }

    public void set(TreeBranch3D branch3D) {
        this.branch3D = branch3D;
    }

    @Override
    public int getNbParts() {
        return nbParts;
    }

    public void setNbParts(int nbParts) {
        this.nbParts = nbParts;
    }

    @Override
    public List<TreeBranchPart> getParts() {
        return parts;
    }

    public void addPart(TreeBranchPart part) {
        parts.add(part);
    }

    public void removePart(TreeBranchPart part) {
        parts.remove(part);
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

}
