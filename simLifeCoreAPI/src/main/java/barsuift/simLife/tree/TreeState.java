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
import barsuift.simLife.j3d.tree.Tree3DState;
import barsuift.simLife.process.UnfrequentRunnableState;

@XmlRootElement
public class TreeState implements State {

    private long creationMillis;

    private BigDecimal energy;

    private List<TreeBranchState> branches;

    private UnfrequentRunnableState photosynthesis;

    private UnfrequentRunnableState aging;

    private UnfrequentRunnableState growth;

    private TreeTrunkState trunkState;

    private float height;

    private Tree3DState tree3DState;

    public TreeState() {
        super();
        this.creationMillis = 0;
        this.energy = new BigDecimal(0);
        this.branches = new ArrayList<TreeBranchState>();
        this.height = 0;
        this.trunkState = new TreeTrunkState();
        this.tree3DState = new Tree3DState();
        this.photosynthesis = new UnfrequentRunnableState();
        this.aging = new UnfrequentRunnableState();
        this.growth = new UnfrequentRunnableState();
    }

    public TreeState(long creationMillis, BigDecimal energy, List<TreeBranchState> branches,
            UnfrequentRunnableState photosynthesis, UnfrequentRunnableState aging, UnfrequentRunnableState growth,
            TreeTrunkState trunkState, float height, Tree3DState tree3dState) {
        super();
        this.creationMillis = creationMillis;
        this.energy = energy;
        this.branches = branches;
        this.photosynthesis = photosynthesis;
        this.aging = aging;
        this.growth = growth;
        this.trunkState = trunkState;
        this.height = height;
        this.tree3DState = tree3dState;
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

    public List<TreeBranchState> getBranches() {
        return branches;
    }

    public void setBranches(List<TreeBranchState> branches) {
        this.branches = branches;
    }

    public UnfrequentRunnableState getPhotosynthesis() {
        return photosynthesis;
    }

    public void setPhotosynthesis(UnfrequentRunnableState photosynthesis) {
        this.photosynthesis = photosynthesis;
    }

    public UnfrequentRunnableState getAging() {
        return aging;
    }

    public void setAging(UnfrequentRunnableState aging) {
        this.aging = aging;
    }

    public UnfrequentRunnableState getGrowth() {
        return growth;
    }

    public void setGrowth(UnfrequentRunnableState growth) {
        this.growth = growth;
    }

    public TreeTrunkState getTrunkState() {
        return trunkState;
    }

    public void setTrunkState(TreeTrunkState trunkState) {
        this.trunkState = trunkState;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Tree3DState getTree3DState() {
        return tree3DState;
    }

    public void setTree3DState(Tree3DState tree3dState) {
        tree3DState = tree3dState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((branches == null) ? 0 : branches.hashCode());
        result = prime * result + Float.floatToIntBits(height);
        result = prime * result + (int) (creationMillis ^ (creationMillis >>> 32));
        result = prime * result + ((energy == null) ? 0 : energy.hashCode());
        result = prime * result + ((photosynthesis == null) ? 0 : photosynthesis.hashCode());
        result = prime * result + ((aging == null) ? 0 : aging.hashCode());
        result = prime * result + ((growth == null) ? 0 : growth.hashCode());
        result = prime * result + ((tree3DState == null) ? 0 : tree3DState.hashCode());
        result = prime * result + ((trunkState == null) ? 0 : trunkState.hashCode());
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
        TreeState other = (TreeState) obj;
        if (branches == null) {
            if (other.branches != null)
                return false;
        } else
            if (!branches.equals(other.branches))
                return false;
        if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
            return false;
        if (creationMillis != other.creationMillis)
            return false;
        if (energy == null) {
            if (other.energy != null)
                return false;
        } else
            if (!energy.equals(other.energy))
                return false;
        if (photosynthesis == null) {
            if (other.photosynthesis != null)
                return false;
        } else
            if (!photosynthesis.equals(other.photosynthesis))
                return false;
        if (aging == null) {
            if (other.aging != null)
                return false;
        } else
            if (!aging.equals(other.aging))
                return false;
        if (growth == null) {
            if (other.growth != null)
                return false;
        } else
            if (!growth.equals(other.growth))
                return false;
        if (tree3DState == null) {
            if (other.tree3DState != null)
                return false;
        } else
            if (!tree3DState.equals(other.tree3DState))
                return false;
        if (trunkState == null) {
            if (other.trunkState != null)
                return false;
        } else
            if (!trunkState.equals(other.trunkState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeState [branches=" + branches + ", height=" + height + ", creationMillis=" + creationMillis
                + ", energy=" + energy + ", photosynthesis=" + photosynthesis + ", aging=" + aging + ", growth="
                + growth + ", tree3DState=" + tree3DState + ", trunkState=" + trunkState + "]";
    }

}
