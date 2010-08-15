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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;

import javax.vecmath.Point3d;

import barsuift.simLife.Percent;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.BasicTreeBranchPart3D;
import barsuift.simLife.j3d.tree.TreeBranchPart3D;
import barsuift.simLife.universe.Universe;

// TODO 010. implement a MAX_ENERGY limit for leaf, branchPart, branch and tree
public class BasicTreeBranchPart implements TreeBranchPart {

    /**
     * The maximum number of leaves per branch part
     */
    private static final int MAX_NB_LEAVES = 4;

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

    private static final Percent ENERGY_RATIO_TO_KEEP = new Percent(50);

    private List<TreeLeaf> leaves;

    private Collection<TreeLeaf> oldLeavesToRemove = new HashSet<TreeLeaf>();

    private TreeBranchPart3D branchPart3D;

    private TreeBranchPartState state;

    private final Universe universe;

    public BasicTreeBranchPart(Universe universe, TreeBranchPartState state) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (state == null) {
            throw new IllegalArgumentException("null branch part state");
        }
        this.universe = universe;
        this.state = new TreeBranchPartState(state);
        List<TreeLeafState> leaveStates = state.getLeaveStates();
        this.leaves = new ArrayList<TreeLeaf>(leaveStates.size());
        for (TreeLeafState treeLeafState : leaveStates) {
            BasicTreeLeaf leaf = new BasicTreeLeaf(universe, treeLeafState);
            leaf.addObserver(this);
            leaves.add(leaf);
        }
        branchPart3D = new BasicTreeBranchPart3D(universe.getUniverse3D(), state.getBranchPart3DState(), this);
    }

    public Long getId() {
        return state.getId();
    }

    public int getAge() {
        return state.getAge();
    }

    /**
     * Make all leaves spend time.
     */
    @Override
    public void spendTime() {
        state.setAge(state.getAge() + 1);
        for (TreeLeaf leaf : leaves) {
            leaf.spendTime();
        }
        if (oldLeavesToRemove.size() != 0) {
            leaves.removeAll(oldLeavesToRemove);
            oldLeavesToRemove.clear();
        }
        collectFreeEnergyFromLeaves();
        if (shouldCreateOneNewLeaf() && canCreateOneNewLeaf()) {
            createOneNewLeaf();
        }
        if (shouldIncreaseOneLeafSize() && canIncreaseOneLeafSize()) {
            increaseOneLeafSize();
        }
    }

    protected void increaseOneLeafSize() {
        TreeLeaf leaf = getRandomLeafToIncrease();
        leaf.getTreeLeaf3D().increaseSize();
        state.setEnergy(getEnergy().subtract(INCREASE_LEAF_COST));
    }

    /**
     * Returns one random leaf, with max odd to smallest leaf
     */
    protected TreeLeaf getRandomLeafToIncrease() {
        // get applicable leaves
        List<TreeLeaf> leavesToIncrease = new ArrayList<TreeLeaf>(leaves.size());
        for (TreeLeaf leaf : leaves) {
            if (!leaf.getTreeLeaf3D().isMaxSizeReached()) {
                leavesToIncrease.add(leaf);
            }
        }
        if (leavesToIncrease.size() == 0) {
            return null;
        }
        if (leavesToIncrease.size() == 1) {
            return leavesToIncrease.get(0);
        }

        // compute areas
        Map<TreeLeaf, Double> areas = new HashMap<TreeLeaf, Double>(leavesToIncrease.size());
        for (TreeLeaf leaf : leavesToIncrease) {
            areas.put(leaf, leaf.getTreeLeaf3D().getArea());
        }

        // compute area sum
        double sumArea = 0;
        for (Double area : areas.values()) {
            sumArea += area;
        }

        // compute diffArea sum
        double sumDiffArea = (leavesToIncrease.size() - 1) * sumArea;

        // compute ratios
        Map<TreeLeaf, Double> ratios = new HashMap<TreeLeaf, Double>(areas.size());
        for (Entry<TreeLeaf, Double> entry : areas.entrySet()) {
            ratios.put(entry.getKey(), (sumArea - entry.getValue()) / sumDiffArea);
        }

        // ???
        double random = Math.random();
        double previousMinBound = 0;
        for (Entry<TreeLeaf, Double> entry : ratios.entrySet()) {
            TreeLeaf leaf = entry.getKey();
            Double ratio = entry.getValue();
            if (random < (previousMinBound + ratio)) {
                return leaf;
            }
            previousMinBound += ratio;
        }

        // return last leaf
        return null;
    }

    protected boolean canIncreaseOneLeafSize() {
        boolean atLeastOneLeafToIncrease = false;
        for (TreeLeaf leaf : leaves) {
            atLeastOneLeafToIncrease |= !leaf.getTreeLeaf3D().isMaxSizeReached();
        }
        if (!atLeastOneLeafToIncrease) {
            // all leaves have reached their maximum size
            return false;
        }
        if (getEnergy().compareTo(INCREASE_LEAF_COST) < 0) {
            // there is not enough energy to increase a leaf size
            return false;
        }
        return true;
    }

    /**
     * Test if we should increase a leaf size or not. The result is a random function with :
     * <ol>
     * <li>0% odd to be true if energy <= 20</li>
     * <li>100% odd to be true if energy >= 5 * 20</li>
     * <li>a linear progression between the two values</li>
     * </ol>
     */
    protected boolean shouldIncreaseOneLeafSize() {
        if (getEnergy().compareTo(INCREASE_LEAF_COST) <= 0) {
            // there is not enough energy to increase a leaf size
            return false;
        }
        BigDecimal maxBound = INCREASE_LEAF_COST.multiply(new BigDecimal(5));
        if (getEnergy().compareTo(maxBound) >= 0) {
            // there is enough energy to increase 5 leaves size, so we should at least increase one
            return true;
        }
        BigDecimal remainingEnergy = getEnergy().subtract(INCREASE_LEAF_COST);
        BigDecimal increaseLength = maxBound.subtract(INCREASE_LEAF_COST);
        BigDecimal ratio = remainingEnergy.divide(increaseLength, 4, RoundingMode.HALF_UP);
        return ratio.compareTo(new BigDecimal(Math.random())) >= 0;
    }

    protected boolean canCreateOneNewLeaf() {
        if (getNbLeaves() >= MAX_NB_LEAVES) {
            // there is no more leaves to create
            return false;
        }
        if (getEnergy().compareTo(NEW_LEAF_CREATION_COST.add(NEW_LEAF_ENERGY_PROVIDED)) < 0) {
            // there is not enough energy to create a new leaf
            return false;
        }
        return true;
    }

    /**
     * Test if we should create a new leaf or not. The result is a random function with :
     * <ol>
     * <li>0% odd to be true if energy < 90</li>
     * <li>100% odd to be true if energy >= 2 * 90</li>
     * <li>a linear progression between the two values</li>
     * </ol>
     */
    protected boolean shouldCreateOneNewLeaf() {
        BigDecimal totalCostOfLeafCreation = NEW_LEAF_CREATION_COST.add(NEW_LEAF_ENERGY_PROVIDED);
        if (getEnergy().compareTo(totalCostOfLeafCreation) <= 0) {
            // there is not enough energy to create a new leaf
            return false;
        }
        if (getEnergy().compareTo(totalCostOfLeafCreation.multiply(new BigDecimal(2))) >= 0) {
            // there is enough energy to create 2 leaves, so we should at least create one
            return true;
        }
        BigDecimal remainingEnergy = getEnergy().subtract(totalCostOfLeafCreation);
        BigDecimal ratio = remainingEnergy.divide(totalCostOfLeafCreation, 4, RoundingMode.HALF_UP);
        return ratio.compareTo(new BigDecimal(Math.random())) >= 0;
    }

    protected void createOneNewLeaf() {
        Point3d leafAttachPoint = computeAttachPointForNewLeaf();
        BasicTreeLeafFactory factory = new BasicTreeLeafFactory(universe);
        TreeLeaf leaf = factory.createNew(leafAttachPoint, NEW_LEAF_ENERGY_PROVIDED);
        leaf.addObserver(this);
        leaves.add(leaf);
        branchPart3D.addLeaf(leaf.getTreeLeaf3D());
        BigDecimal totalLeafCreationCost = NEW_LEAF_CREATION_COST.add(NEW_LEAF_ENERGY_PROVIDED);
        state.setEnergy(getEnergy().subtract(totalLeafCreationCost));
    }

    protected Point3d computeAttachPointForNewLeaf() {
        Point3d previousAttachPoint = new Point3d(0, 0, 0);
        Point3d saveAttachPoint1 = null;
        Point3d saveAttachPoint2 = null;
        double distance;
        double maxDistance = -1;
        List<TreeLeaf> sortedLeaves = new ArrayList<TreeLeaf>(leaves);
        Collections.sort(sortedLeaves, new TreeLeafComparator());

        for (TreeLeaf leaf : sortedLeaves) {
            Point3d attachPoint = leaf.getTreeLeaf3D().getAttachPoint();
            distance = previousAttachPoint.distance(attachPoint);
            if (distance > maxDistance) {
                maxDistance = distance;
                saveAttachPoint1 = previousAttachPoint;
                saveAttachPoint2 = attachPoint;
            }
            previousAttachPoint = attachPoint;
        }
        Point3d attachPoint = branchPart3D.getEndPoint();
        distance = previousAttachPoint.distance(attachPoint);
        if (distance > maxDistance) {
            maxDistance = distance;
            saveAttachPoint1 = previousAttachPoint;
            saveAttachPoint2 = attachPoint;
        }
        previousAttachPoint = attachPoint;
        Point3d newLeafAttachPoint = new Point3d();
        newLeafAttachPoint.interpolate(saveAttachPoint1, saveAttachPoint2, 0.5 + Randomizer.random1());
        return newLeafAttachPoint;
    }

    private void collectFreeEnergyFromLeaves() {
        BigDecimal freeEnergyCollectedFromLeaf = new BigDecimal(0);
        for (TreeLeaf leaf : leaves) {
            freeEnergyCollectedFromLeaf = freeEnergyCollectedFromLeaf.add(leaf.collectFreeEnergy());
        }
        BigDecimal energyCollectedForBranchPart = freeEnergyCollectedFromLeaf.multiply(ENERGY_RATIO_TO_KEEP.getValue());
        BigDecimal freeEnergyCollected = freeEnergyCollectedFromLeaf.subtract(energyCollectedForBranchPart);
        state.setEnergy(getEnergy().add(energyCollectedForBranchPart));
        state.setFreeEnergy(state.getFreeEnergy().add(freeEnergyCollected));
    }

    /**
     * Return the sum of leaves energies
     */
    @Override
    public BigDecimal getEnergy() {
        return state.getEnergy();
    }

    @Override
    public BigDecimal collectFreeEnergy() {
        BigDecimal freeEnergy = state.getFreeEnergy();
        state.setFreeEnergy(new BigDecimal(0));
        return freeEnergy;
    }

    public int getNbLeaves() {
        return leaves.size();
    }

    @Override
    public List<TreeLeaf> getLeaves() {
        return Collections.unmodifiableList(leaves);
    }

    public void update(Observable o, Object arg) {
        if (LeafUpdateCode.fall == arg) {
            TreeLeaf leaf = (TreeLeaf) o;
            oldLeavesToRemove.add(leaf);
        }
    }

    @Override
    public TreeBranchPartState getState() {
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(leaves.size());
        for (TreeLeaf leaf : leaves) {
            leaveStates.add(leaf.getState());
        }
        state.setLeaveStates(leaveStates);
        state.setBranchPart3DState(branchPart3D.getState());
        return new TreeBranchPartState(state);
    }

    @Override
    public TreeBranchPart3D getBranchPart3D() {
        return branchPart3D;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
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
        BasicTreeBranchPart other = (BasicTreeBranchPart) obj;
        if (state == null) {
            if (other.state != null)
                return false;
        } else
            if (!state.equals(other.state))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "BasicTreeBranchPart [state=" + state + "]";
    }

}
