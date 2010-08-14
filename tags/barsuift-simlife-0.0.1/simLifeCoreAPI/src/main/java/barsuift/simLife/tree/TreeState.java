/**
 * barsuift-simlife is a life simulator programm
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

import barsuift.simLife.j3d.tree.Tree3DState;

@XmlRootElement
public class TreeState {

    private Long id;

    private int age;

    private BigDecimal energy;

    private List<TreeBranchState> branches;

    private TreeTrunkState trunkState;

    private float height;

    private Tree3DState tree3DState;

    public TreeState() {
        super();
        this.id = new Long(0);
        this.age = 0;
        this.energy = new BigDecimal(0);
        this.branches = new ArrayList<TreeBranchState>();
        this.height = 0;
        this.trunkState = new TreeTrunkState();
        this.tree3DState = new Tree3DState();
    }

    public TreeState(Long id, int age, BigDecimal energy, List<TreeBranchState> branches, TreeTrunkState trunkState,
            float height, Tree3DState tree3dState) {
        super();
        this.id = id;
        this.age = age;
        this.energy = energy;
        this.branches = branches;
        this.trunkState = trunkState;
        this.height = height;
        this.tree3DState = tree3dState;
    }

    public TreeState(TreeState copy) {
        super();
        this.id = copy.id;
        this.age = copy.age;
        this.energy = copy.energy;
        this.branches = new ArrayList<TreeBranchState>(copy.branches.size());
        for (TreeBranchState branchState : copy.branches) {
            branches.add(new TreeBranchState(branchState));
        }
        this.trunkState = new TreeTrunkState(copy.trunkState);
        this.height = copy.height;
        this.tree3DState = new Tree3DState(copy.tree3DState);
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

    public List<TreeBranchState> getBranches() {
        return branches;
    }

    public void setBranches(List<TreeBranchState> branches) {
        this.branches = branches;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + age;
        result = prime * result + ((energy == null) ? 0 : energy.hashCode());
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
        return "TreeState [branches=" + branches + ", height=" + height + ", id=" + id + ", age=" + age + ", energy="
                + energy + ", tree3DState=" + tree3DState + ", trunkState=" + trunkState + "]";
    }

}
