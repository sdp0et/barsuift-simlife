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

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.tree.TreeBranchPart3DState;

@XmlRootElement
public class TreeBranchPartState implements State {

    private Long id;

    private int age;

    private BigDecimal energy;

    private BigDecimal freeEnergy;

    private List<TreeLeafState> leaveStates;

    private TreeBranchPart3DState branchPart3DState;

    public TreeBranchPartState() {
        super();
        this.leaveStates = new ArrayList<TreeLeafState>();
        this.id = new Long(0);
        this.age = 0;
        this.energy = new BigDecimal(0);
        this.freeEnergy = new BigDecimal(0);
        this.branchPart3DState = new TreeBranchPart3DState();
    }

    public TreeBranchPartState(Long id, int age, BigDecimal energy, BigDecimal freeEnergy,
            List<TreeLeafState> leaveStates, TreeBranchPart3DState branchPart3DState) {
        super();
        this.id = id;
        this.age = age;
        this.energy = energy;
        this.freeEnergy = freeEnergy;
        this.leaveStates = leaveStates;
        this.branchPart3DState = branchPart3DState;
    }

    public TreeBranchPartState(TreeBranchPartState copy) {
        super();
        this.id = copy.id;
        this.age = copy.age;
        this.energy = copy.energy;
        this.freeEnergy = copy.freeEnergy;
        this.leaveStates = new ArrayList<TreeLeafState>();
        for (TreeLeafState leafState : copy.leaveStates) {
            leaveStates.add(new TreeLeafState(leafState));
        }
        this.branchPart3DState = new TreeBranchPart3DState(copy.branchPart3DState);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }

    public BigDecimal getFreeEnergy() {
        return freeEnergy;
    }

    public void setFreeEnergy(BigDecimal freeEnergy) {
        this.freeEnergy = freeEnergy;
    }

    public List<TreeLeafState> getLeaveStates() {
        return leaveStates;
    }

    public void setLeaveStates(List<TreeLeafState> leaveStates) {
        this.leaveStates = leaveStates;
    }

    public TreeBranchPart3DState getBranchPart3DState() {
        return branchPart3DState;
    }

    public void setBranchPart3DState(TreeBranchPart3DState branchPart3DState) {
        this.branchPart3DState = branchPart3DState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((branchPart3DState == null) ? 0 : branchPart3DState.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + age;
        result = prime * result + ((energy == null) ? 0 : energy.hashCode());
        result = prime * result + ((freeEnergy == null) ? 0 : freeEnergy.hashCode());
        result = prime * result + ((leaveStates == null) ? 0 : leaveStates.hashCode());
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
        TreeBranchPartState other = (TreeBranchPartState) obj;
        if (branchPart3DState == null) {
            if (other.branchPart3DState != null)
                return false;
        } else
            if (!branchPart3DState.equals(other.branchPart3DState))
                return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else
            if (!id.equals(other.id))
                return false;
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
        if (leaveStates == null) {
            if (other.leaveStates != null)
                return false;
        } else
            if (!leaveStates.equals(other.leaveStates))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeBranchState [branchPart3DState=" + branchPart3DState + ", id=" + id + ", age=" + age + ", energy="
                + energy + ", freeEnergy=" + freeEnergy + ", leaveStates=" + leaveStates + "]";
    }

}
