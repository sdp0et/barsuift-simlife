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

import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.tree.BasicTreeBranch3D;
import barsuift.simLife.j3d.tree.TreeBranch3D;
import barsuift.simLife.universe.Universe;

public class BasicTreeBranch implements TreeBranch {

    private static final BigDecimal ENERGY_RATIO_TO_KEEP = PercentHelper.getDecimalValue(0);

    private static final BigDecimal MAX_ENERGY = new BigDecimal(200);

    private final TreeBranchState state;

    private final List<TreeBranchPart> parts;

    private final TreeBranch3D branch3D;

    private final long creationMillis;

    private BigDecimal energy;

    private BigDecimal freeEnergy;

    public BasicTreeBranch(Universe universe, TreeBranchState state) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (state == null) {
            throw new IllegalArgumentException("null branch state");
        }
        this.state = state;
        this.creationMillis = state.getCreationMillis();
        this.energy = state.getEnergy();
        this.freeEnergy = state.getFreeEnergy();
        List<TreeBranchPartState> partStates = state.getBranchPartStates();
        this.parts = new ArrayList<TreeBranchPart>(partStates.size());
        for (TreeBranchPartState treeBranchPartState : partStates) {
            BasicTreeBranchPart branchPart = new BasicTreeBranchPart(universe, treeBranchPartState);
            parts.add(branchPart);
        }
        branch3D = new BasicTreeBranch3D(universe.getUniverse3D(), state.getBranch3DState(), this);

    }

    @Override
    public long getCreationMillis() {
        return creationMillis;
    }

    /**
     * Make all parts spend time.
     */
    @Override
    public void spendTime() {
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
        BigDecimal energyCollectedForBranch = freeEnergyCollectedFromParts.multiply(ENERGY_RATIO_TO_KEEP);
        BigDecimal freeEnergyCollected = freeEnergyCollectedFromParts.subtract(energyCollectedForBranch);
        this.energy = energy.add(energyCollectedForBranch);
        // limit energy to MAX_ENERGY
        energy = energy.min(MAX_ENERGY);
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
        BigDecimal currentFreeEnergy = freeEnergy;
        freeEnergy = new BigDecimal(0);
        return currentFreeEnergy;
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
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setEnergy(energy);
        state.setFreeEnergy(freeEnergy);
        List<TreeBranchPartState> branchPartStates = new ArrayList<TreeBranchPartState>();
        for (TreeBranchPart branchPart : parts) {
            branchPartStates.add((TreeBranchPartState) branchPart.getState());
        }
        state.setBranchPartStates(branchPartStates);
        branch3D.synchronize();
    }

    @Override
    public TreeBranch3D getBranch3D() {
        return branch3D;
    }

}
