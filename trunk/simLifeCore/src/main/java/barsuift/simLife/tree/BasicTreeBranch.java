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
import java.util.Collections;
import java.util.List;

import barsuift.simLife.Percent;
import barsuift.simLife.j3d.tree.BasicTreeBranch3D;
import barsuift.simLife.j3d.tree.TreeBranch3D;
import barsuift.simLife.universe.Universe;

public class BasicTreeBranch implements TreeBranch {

    private static final Percent ENERGY_RATIO_TO_KEEP = PercentHelper.getDecimalValue(0);

    private final Long id;

    private List<TreeBranchPart> parts;

    private TreeBranch3D branch3D;

    private int age;

    private BigDecimal energy;

    private BigDecimal freeEnergy;

    public BasicTreeBranch(Universe universe, TreeBranchState branchState) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (branchState == null) {
            throw new IllegalArgumentException("null branch state");
        }
        this.id = branchState.getId();
        this.age = branchState.getAge();
        this.energy = branchState.getEnergy();
        this.freeEnergy = branchState.getFreeEnergy();
        List<TreeBranchPartState> partStates = branchState.getBranchPartStates();
        this.parts = new ArrayList<TreeBranchPart>(partStates.size());
        for (TreeBranchPartState treeBranchPartState : partStates) {
            BasicTreeBranchPart branchPart = new BasicTreeBranchPart(universe, treeBranchPartState);
            parts.add(branchPart);
        }
        branch3D = new BasicTreeBranch3D(universe.getUniverse3D(), branchState.getBranch3DState(), this);

    }

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    /**
     * Make all parts spend time.
     */
    @Override
    public void spendTime() {
        age++;
        for (TreeBranchPart branchPart : parts) {
            branchPart.spendTime();
        }
        collectFreeEnergyFromParts();
    }

    private void collectFreeEnergyFromParts() {
        BigDecimal freeEnergyCollectedFromParts = new BigDecimal(0);
        for (TreeBranchPart part : parts) {
            freeEnergyCollectedFromParts = freeEnergyCollectedFromParts.add(part.collectFreeEnergy());
        }
        BigDecimal energyCollectedForBranch = freeEnergyCollectedFromParts.multiply(ENERGY_RATIO_TO_KEEP.getValue());
        BigDecimal freeEnergyCollected = freeEnergyCollectedFromParts.subtract(energyCollectedForBranch);
        this.energy = energy.add(energyCollectedForBranch);
        this.freeEnergy = freeEnergy.add(freeEnergyCollected);
    }

    /**
     * Return the sum of branch parts energies
     */
    @Override
    public BigDecimal getEnergy() {
        return energy;
    }

    @Override
    public BigDecimal collectFreeEnergy() {
        BigDecimal freeEnergy = this.freeEnergy;
        this.freeEnergy = new BigDecimal(0);
        return freeEnergy;
    }

    public int getNbLeaves() {
        int result = 0;
        for (TreeBranchPart branchPart : parts) {
            result += branchPart.getNbLeaves();
        }
        return result;
    }

    public List<TreeBranchPart> getParts() {
        return Collections.unmodifiableList(parts);
    }

    public int getNbParts() {
        return parts.size();
    }

    @Override
    public TreeBranchState getState() {
        List<TreeBranchPartState> partStates = new ArrayList<TreeBranchPartState>(parts.size());
        for (TreeBranchPart branchPart : parts) {
            partStates.add(branchPart.getState());
        }
        return new TreeBranchState(id, age, energy, freeEnergy, partStates, branch3D.getState());
    }

    @Override
    public TreeBranch3D getBranch3D() {
        return branch3D;
    }

    @Override
    public String toString() {
        return "BasicTreeBranch [age=" + age + ", energy=" + energy + ", freeEnergy=" + freeEnergy + ", id=" + id
                + ", parts=" + parts + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((energy == null) ? 0 : energy.hashCode());
        result = prime * result + ((freeEnergy == null) ? 0 : freeEnergy.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((parts == null) ? 0 : parts.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BasicTreeBranch other = (BasicTreeBranch) obj;
        if (age != other.age)
            return false;
        if (energy == null) {
            if (other.energy != null)
                return false;
        } else
            if (!energy.equals(other.energy))
                return false;
        if (freeEnergy == null) {
            if (other.freeEnergy != null)
                return false;
        } else
            if (!freeEnergy.equals(other.freeEnergy))
                return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else
            if (!id.equals(other.id))
                return false;
        if (parts == null) {
            if (other.parts != null)
                return false;
        } else
            if (!parts.equals(other.parts))
                return false;
        return true;
    }

}
