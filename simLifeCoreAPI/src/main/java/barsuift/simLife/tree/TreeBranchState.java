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
import barsuift.simLife.j3d.tree.TreeBranch3DState;

@XmlRootElement
public class TreeBranchState implements State {

    private long creationMillis;

    private BigDecimal energy;

    private BigDecimal freeEnergy;

    private List<TreeLeafState> leavesStates;

    private TreeBranch3DState branch3DState;

    public TreeBranchState() {
        super();
        this.leavesStates = new ArrayList<TreeLeafState>();
        this.creationMillis = 0;
        this.energy = new BigDecimal(0);
        this.freeEnergy = new BigDecimal(0);
        this.branch3DState = new TreeBranch3DState();
    }

    public TreeBranchState(long creationMillis, BigDecimal energy, BigDecimal freeEnergy,
            List<TreeLeafState> leavesStates, TreeBranch3DState branch3DState) {
        super();
        this.creationMillis = creationMillis;
        this.energy = energy;
        this.freeEnergy = freeEnergy;
        this.leavesStates = leavesStates;
        this.branch3DState = branch3DState;
    }

    public long getCreationMillis() {
        return creationMillis;
    }

    public void setCreationMillis(long creationMillis) {
        this.creationMillis = creationMillis;
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

    public List<TreeLeafState> getLeavesStates() {
        return leavesStates;
    }

    public void setLeavesStates(List<TreeLeafState> leavesStates) {
        this.leavesStates = leavesStates;
    }

    public TreeBranch3DState getBranch3DState() {
        return branch3DState;
    }

    public void setBranch3DState(TreeBranch3DState branch3dState) {
        branch3DState = branch3dState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((branch3DState == null) ? 0 : branch3DState.hashCode());
        result = prime * result + (int) (creationMillis ^ (creationMillis >>> 32));
        result = prime * result + ((energy == null) ? 0 : energy.hashCode());
        result = prime * result + ((freeEnergy == null) ? 0 : freeEnergy.hashCode());
        result = prime * result + ((leavesStates == null) ? 0 : leavesStates.hashCode());
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
        TreeBranchState other = (TreeBranchState) obj;
        if (branch3DState == null) {
            if (other.branch3DState != null)
                return false;
        } else
            if (!branch3DState.equals(other.branch3DState))
                return false;
        if (creationMillis != other.creationMillis)
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
        if (leavesStates == null) {
            if (other.leavesStates != null)
                return false;
        } else
            if (!leavesStates.equals(other.leavesStates))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeBranchState [branch3DState=" + branch3DState + ", creationMillis=" + creationMillis + ", energy="
                + energy + ", freeEnergy=" + freeEnergy + ", leavesStates=" + leavesStates + "]";
    }

}
