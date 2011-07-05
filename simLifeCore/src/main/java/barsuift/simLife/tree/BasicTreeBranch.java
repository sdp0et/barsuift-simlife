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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.tree.BasicTreeBranch3D;
import barsuift.simLife.j3d.tree.TreeBranch3D;
import barsuift.simLife.j3d.tree.TreeLeavesOrganizer;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.universe.Universe;

public class BasicTreeBranch implements TreeBranch {

    private static final BigDecimal ENERGY_RATIO_TO_KEEP = PercentHelper.getDecimalValue(50);

    /**
     * The maximum energy the branch can stock
     */
    private static final BigDecimal MAX_ENERGY = new BigDecimal(200);

    /**
     * The maximum number of leaves per branch
     */
    protected static final int MAX_NB_LEAVES = 12;

    /**
     * energy consumed to create a new leaf
     */
    private static final BigDecimal NEW_LEAF_CREATION_COST = new BigDecimal(40);

    /**
     * Energy to give to the newly created leaf
     */
    private static final BigDecimal NEW_LEAF_ENERGY_PROVIDED = new BigDecimal(50);

    /**
     * Energy consumed to increase a leaf size
     */
    private static final BigDecimal INCREASE_LEAF_COST = new BigDecimal(20);



    private final TreeBranchState state;

    private final ConcurrentLinkedQueue<TreeLeaf> leaves;

    private final BasicTreeBranch3D branch3D;

    private final long creationMillis;

    private BigDecimal energy;

    private BigDecimal freeEnergy;

    private Universe universe;


    public BasicTreeBranch(TreeBranchState state) {
        if (state == null) {
            throw new IllegalArgumentException("null branch state");
        }
        this.state = state;
        this.creationMillis = state.getCreationMillis();
        this.energy = state.getEnergy();
        this.freeEnergy = state.getFreeEnergy();
        List<TreeLeafState> leavesStates = state.getLeavesStates();
        this.leaves = new ConcurrentLinkedQueue<TreeLeaf>();
        for (TreeLeafState treeLeafState : leavesStates) {
            BasicTreeLeaf leaf = new BasicTreeLeaf(treeLeafState);
            leaf.addSubscriber(this);
            leaves.add(leaf);
        }
        branch3D = new BasicTreeBranch3D(state.getBranch3DState());
    }

    public void init(Universe universe) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        this.universe = universe;
        for (TreeLeaf treeLeaf : leaves) {
            ((BasicTreeLeaf) treeLeaf).init(universe);
        }
        branch3D.init(universe.getUniverse3D(), this);
    }

    @Override
    public TreeBranch3D getBranch3D() {
        return branch3D;
    }

    @Override
    public long getCreationMillis() {
        return creationMillis;
    }

    public void age() {
        for (TreeLeaf leaf : leaves) {
            leaf.age();
        }
    }

    @Override
    public void grow() {
        if (shouldCreateOneNewLeaf() && canCreateOneNewLeaf()) {
            createOneNewLeaf();
        }
        if (shouldIncreaseOneLeafSize() && canIncreaseOneLeafSize()) {
            increaseOneLeafSize();
        }
    }

    protected void increaseOneLeafSize() {
        branch3D.increaseOneLeafSize();
        energy = energy.subtract(INCREASE_LEAF_COST);
    }

    protected boolean canIncreaseOneLeafSize() {
        if (energy.compareTo(INCREASE_LEAF_COST) < 0) {
            // there is not enough energy to increase a leaf size
            return false;
        }
        if (!hasAtLeastOneLeafToIncrease()) {
            // all leaves have reached their maximum size
            return false;
        }
        return true;
    }

    private boolean hasAtLeastOneLeafToIncrease() {
        for (TreeLeaf leaf : leaves) {
            if (!leaf.getTreeLeaf3D().isMaxSizeReached()) {
                return true;
            }
        }
        // all leaves have reached their maximum size
        return false;
    }

    /**
     * Test if we should increase a leaf size or not. The result is a random function with :
     * <ol>
     * <li>0% odd to be true if energy <= increaseLeafCost</li>
     * <li>100% odd to be true if energy >= 5 * increaseLeafCost</li>
     * <li>a linear progression between these two values</li>
     * </ol>
     */
    protected boolean shouldIncreaseOneLeafSize() {
        if (energy.compareTo(INCREASE_LEAF_COST) <= 0) {
            // there is not enough energy to increase a leaf size
            return false;
        }
        BigDecimal maxBound = INCREASE_LEAF_COST.multiply(new BigDecimal(5));
        if (energy.compareTo(maxBound) >= 0) {
            // there is enough energy to increase 5 leaves size, so we should at least increase one
            return true;
        }
        BigDecimal remainingEnergy = energy.subtract(INCREASE_LEAF_COST);
        BigDecimal increaseLength = maxBound.subtract(INCREASE_LEAF_COST);
        BigDecimal ratio = remainingEnergy.divide(increaseLength, 4, RoundingMode.HALF_UP);
        return ratio.compareTo(new BigDecimal(Math.random())) >= 0;
    }

    protected boolean canCreateOneNewLeaf() {
        if (getNbLeaves() >= MAX_NB_LEAVES) {
            // there is no more leaves to create
            return false;
        }
        if (energy.compareTo(NEW_LEAF_CREATION_COST.add(NEW_LEAF_ENERGY_PROVIDED)) < 0) {
            // there is not enough energy to create a new leaf
            return false;
        }
        return true;
    }

    /**
     * Test if we should create a new leaf or not. The result is a random function with :
     * <ol>
     * <li>0% odd to be true if energy < totalLeafCreationCost</li>
     * <li>100% odd to be true if energy >= 2 * totalLeafCreationCost</li>
     * <li>a linear progression between the two values</li>
     * </ol>
     */
    protected boolean shouldCreateOneNewLeaf() {
        BigDecimal totalCostOfLeafCreation = NEW_LEAF_CREATION_COST.add(NEW_LEAF_ENERGY_PROVIDED);
        if (energy.compareTo(totalCostOfLeafCreation) <= 0) {
            // there is not enough energy to create a new leaf
            return false;
        }
        if (energy.compareTo(totalCostOfLeafCreation.multiply(new BigDecimal(2))) >= 0) {
            // there is enough energy to create 2 leaves, so we should at least create one
            return true;
        }
        BigDecimal remainingEnergy = energy.subtract(totalCostOfLeafCreation);
        BigDecimal ratio = remainingEnergy.divide(totalCostOfLeafCreation, 4, RoundingMode.HALF_UP);
        return ratio.compareTo(new BigDecimal(Math.random())) >= 0;
    }

    protected void createOneNewLeaf() {
        TreeLeafStateFactory treeLeafStateFactory = new TreeLeafStateFactory();
        TreeLeafState treeLeafState = treeLeafStateFactory.createNewTreeLeafState(NEW_LEAF_ENERGY_PROVIDED, universe
                .getDate().getTimeInMillis());
        TreeLeavesOrganizer leavesOrganizer = new TreeLeavesOrganizer();
        leavesOrganizer.placeNewLeaf(treeLeafState.getLeaf3DState(), branch3D);
        BasicTreeLeaf leaf = new BasicTreeLeaf(treeLeafState);
        leaf.init(universe);
        leaf.addSubscriber(this);
        leaves.add(leaf);
        branch3D.addLeaf(leaf.getTreeLeaf3D());
        BigDecimal totalLeafCreationCost = NEW_LEAF_CREATION_COST.add(NEW_LEAF_ENERGY_PROVIDED);
        energy = energy.subtract(totalLeafCreationCost);
    }

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

    @Override
    public void collectSolarEnergy() {
        BigDecimal freeEnergyCollectedFromLeaf = new BigDecimal(0);
        for (TreeLeaf leaf : leaves) {
            leaf.collectSolarEnergy();
            freeEnergyCollectedFromLeaf = freeEnergyCollectedFromLeaf.add(leaf.collectFreeEnergy());
        }
        BigDecimal energyCollectedForBranch = freeEnergyCollectedFromLeaf.multiply(ENERGY_RATIO_TO_KEEP);
        BigDecimal freeEnergyCollected = freeEnergyCollectedFromLeaf.subtract(energyCollectedForBranch);
        energy = energy.add(energyCollectedForBranch);
        // limit the branch part energy to MAX_ENERGY
        energy = energy.min(MAX_ENERGY);
        freeEnergy = freeEnergy.add(freeEnergyCollected);
    }

    public int getNbLeaves() {
        return leaves.size();
    }

    public Collection<TreeLeaf> getLeaves() {
        return Collections.unmodifiableCollection(leaves);
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == MobileEvent.FALLING) {
            TreeLeaf leaf = (TreeLeaf) publisher;
            leaves.remove(leaf);
        }
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
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>();
        for (TreeLeaf leaf : leaves) {
            leavesStates.add((TreeLeafState) leaf.getState());
        }
        state.setLeavesStates(leavesStates);
        branch3D.synchronize();
    }

}
