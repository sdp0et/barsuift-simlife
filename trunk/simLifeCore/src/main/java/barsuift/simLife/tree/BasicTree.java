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

import barsuift.simLife.j3d.tree.BasicTree3D;
import barsuift.simLife.j3d.tree.Tree3D;
import barsuift.simLife.universe.Universe;

public class BasicTree implements Tree {

    private final Long id;

    private int age;

    private BigDecimal energy;

    private List<TreeBranch> branches;

    private TreeTrunk trunk;

    private float height;

    private Tree3D tree3D;

    public BasicTree(Universe universe, TreeState state) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (state == null) {
            throw new IllegalArgumentException("null tree state");
        }
        this.id = state.getId();
        this.age = state.getAge();
        this.energy = state.getEnergy();
        this.height = state.getHeight();
        List<TreeBranchState> branchStates = state.getBranches();
        this.branches = new ArrayList<TreeBranch>(branchStates.size());
        for (TreeBranchState treeBranchState : branchStates) {
            branches.add(new BasicTreeBranch(universe, treeBranchState));
        }
        this.trunk = new BasicTreeTrunk(universe, state.getTrunkState());
        this.tree3D = new BasicTree3D(universe.getUniverse3D(), state.getTree3DState(), this);
    }

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    /**
     * Make all branches spend time
     */
    @Override
    public void spendTime() {
        age++;
        trunk.spendTime();
        for (TreeBranch branch : branches) {
            branch.spendTime();
        }
        collectFreeEnergyFromBranches();
    }

    private void collectFreeEnergyFromBranches() {
        BigDecimal freeEnergyCollectedFromBranches = new BigDecimal(0);
        for (TreeBranch branch : branches) {
            freeEnergyCollectedFromBranches = freeEnergyCollectedFromBranches.add(branch.collectFreeEnergy());
        }
        this.energy = energy.add(freeEnergyCollectedFromBranches);
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public int getNbBranches() {
        return branches.size();
    }

    /**
     * Return the sum of branch energies
     */
    @Override
    public BigDecimal getEnergy() {
        return energy;
    }

    @Override
    public int getNbLeaves() {
        int result = 0;
        for (TreeBranch branch : branches) {
            result += branch.getNbLeaves();
        }
        return result;
    }

    @Override
    public List<TreeBranch> getBranches() {
        return branches;
    }

    @Override
    public TreeState getState() {
        List<TreeBranchState> branchStates = new ArrayList<TreeBranchState>();
        for (TreeBranch treeBranch : branches) {
            branchStates.add(treeBranch.getState());
        }
        return new TreeState(id, age, energy, branchStates, trunk.getState(), height, tree3D.getState());
    }

    @Override
    public Tree3D getTree3D() {
        return tree3D;
    }

    @Override
    public TreeTrunk getTrunk() {
        return trunk;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((branches == null) ? 0 : branches.hashCode());
        result = prime * result + ((energy == null) ? 0 : energy.hashCode());
        result = prime * result + Float.floatToIntBits(height);
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((trunk == null) ? 0 : trunk.hashCode());
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
        BasicTree other = (BasicTree) obj;
        if (age != other.age)
            return false;
        if (branches == null) {
            if (other.branches != null)
                return false;
        } else
            if (!branches.equals(other.branches))
                return false;
        if (energy == null) {
            if (other.energy != null)
                return false;
        } else
            if (!energy.equals(other.energy))
                return false;
        if (Float.floatToIntBits(height) != Float.floatToIntBits(other.height))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else
            if (!id.equals(other.id))
                return false;
        if (trunk == null) {
            if (other.trunk != null)
                return false;
        } else
            if (!trunk.equals(other.trunk))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "BasicTree [age=" + age + ", branches=" + branches + ", energy=" + energy + ", height=" + height
                + ", id=" + id + ", trunk=" + trunk + "]";
    }

}
