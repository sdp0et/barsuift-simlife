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
import barsuift.simLife.process.Aging;
import barsuift.simLife.process.Photosynthesis;
import barsuift.simLife.process.TreeGrowth;
import barsuift.simLife.universe.Universe;

public class BasicTree implements Tree {

    private static final BigDecimal MAX_ENERGY = new BigDecimal(4000);

    private final TreeState state;

    private final Photosynthesis photosynthesis;

    private final Aging aging;

    private final TreeGrowth growth;

    private final long creationMillis;

    private BigDecimal energy;

    private float height;

    private final List<TreeBranch> branches;

    private final BasicTreeTrunk trunk;

    private final BasicTree3D tree3D;

    public BasicTree(TreeState state) {
        if (state == null) {
            throw new IllegalArgumentException("null tree state");
        }
        this.state = state;
        this.creationMillis = state.getCreationMillis();
        this.energy = state.getEnergy();
        this.height = state.getHeight();
        List<TreeBranchState> branchStates = state.getBranches();
        this.branches = new ArrayList<TreeBranch>(branchStates.size());
        for (TreeBranchState treeBranchState : branchStates) {
            branches.add(new BasicTreeBranch(treeBranchState));
        }
        this.trunk = new BasicTreeTrunk(state.getTrunkState());
        this.tree3D = new BasicTree3D(state.getTree3DState());
        this.photosynthesis = new Photosynthesis(state.getPhotosynthesis());
        this.aging = new Aging(state.getAging());
        this.growth = new TreeGrowth(state.getGrowth());
    }

    public void init(Universe universe) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        for (TreeBranch treeBranch : branches) {
            ((BasicTreeBranch) treeBranch).init(universe);
        }
        this.trunk.init(universe);
        this.tree3D.init(universe.getUniverse3D(), this);
        this.photosynthesis.init(this);
        universe.getSynchronizer().scheduleSlow(photosynthesis);
        this.aging.init(this);
        universe.getSynchronizer().scheduleSlow(aging);
        this.growth.init(this);
        universe.getSynchronizer().scheduleSlow(growth);
    }

    @Override
    public long getCreationMillis() {
        return creationMillis;
    }

    @Override
    public void age() {
        for (TreeBranch branch : branches) {
            branch.age();
        }
    }

    @Override
    public BigDecimal collectFreeEnergy() {
        return BigDecimal.ZERO;
    }

    @Override
    public void collectSolarEnergy() {
        BigDecimal freeEnergyCollectedFromBranches = new BigDecimal(0);
        for (TreeBranch branch : branches) {
            branch.collectSolarEnergy();
            freeEnergyCollectedFromBranches = freeEnergyCollectedFromBranches.add(branch.collectFreeEnergy());
        }
        this.energy = energy.add(freeEnergyCollectedFromBranches);
        // limit energy to MAX_ENERGY
        energy = energy.min(MAX_ENERGY);
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
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setEnergy(energy);
        state.setHeight(height);
        List<TreeBranchState> treeBranchStates = new ArrayList<TreeBranchState>();
        for (TreeBranch treeBranch : branches) {
            treeBranchStates.add((TreeBranchState) treeBranch.getState());
        }
        state.setBranches(treeBranchStates);
        trunk.synchronize();
        tree3D.synchronize();
        photosynthesis.synchronize();
        aging.synchronize();
        growth.synchronize();
    }

    @Override
    public Tree3D getTree3D() {
        return tree3D;
    }

    @Override
    public TreeTrunk getTrunk() {
        return trunk;
    }

}
