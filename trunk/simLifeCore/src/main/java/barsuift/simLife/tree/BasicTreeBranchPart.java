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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.vecmath.Point3d;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.BasicTreeBranchPart3D;
import barsuift.simLife.j3d.tree.TreeBranchPart3D;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.universe.Universe;

public class BasicTreeBranchPart implements TreeBranchPart {

    /**
     * The maximum number of leaves per branch part
     */
    private static final int MAX_NB_LEAVES = 4;

    /**
     * The maximum energy the branch part can stock
     */
    private static final BigDecimal MAX_ENERGY = new BigDecimal(200);

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

    private static final BigDecimal ENERGY_RATIO_TO_KEEP = PercentHelper.getDecimalValue(50);



    private final TreeBranchPartState state;

    private final long creationMillis;

    private BigDecimal energy;

    private BigDecimal freeEnergy;

    private final ConcurrentLinkedQueue<TreeLeaf> leaves;

    private final TreeBranchPart3D branchPart3D;

    private final Universe universe;

    public BasicTreeBranchPart(Universe universe, TreeBranchPartState state) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (state == null) {
            throw new IllegalArgumentException("null branch part state");
        }
        this.universe = universe;
        this.state = state;
        this.creationMillis = state.getCreationMillis();
        this.energy = state.getEnergy();
        this.freeEnergy = state.getFreeEnergy();
        List<TreeLeafState> leaveStates = state.getLeaveStates();
        this.leaves = new ConcurrentLinkedQueue<TreeLeaf>();
        for (TreeLeafState treeLeafState : leaveStates) {
            BasicTreeLeaf leaf = new BasicTreeLeaf(universe, treeLeafState);
            leaf.addSubscriber(this);
            leaves.add(leaf);
        }
        branchPart3D = new BasicTreeBranchPart3D(universe.getUniverse3D(), state.getBranchPart3DState(), this);
    }

    @Override
    public long getCreationMillis() {
        return creationMillis;
    }

    /**
     * Make all leaves spend time.
     */
    @Override
    public void spendTime() {
        for (TreeLeaf leaf : leaves) {
            leaf.spendTime();
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
        energy = energy.subtract(INCREASE_LEAF_COST);
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
        // thanks to the use of sumDiffArea, the sum of ratios is equals to 1 (100%)
        Map<TreeLeaf, Double> ratios = new HashMap<TreeLeaf, Double>(areas.size());
        for (Entry<TreeLeaf, Double> entry : areas.entrySet()) {
            ratios.put(entry.getKey(), (sumArea - entry.getValue()) / sumDiffArea);
        }

        // select one leaf
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
     * <li>a linear progression between the two values</li>
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
        Point3d leafAttachPoint = computeAttachPointForNewLeaf();
        BasicTreeLeafFactory factory = new BasicTreeLeafFactory(universe);
        TreeLeaf leaf = factory.createNew(leafAttachPoint, NEW_LEAF_ENERGY_PROVIDED, universe.getTimeController()
                .getDate().getTimeInMillis());
        leaf.addSubscriber(this);
        leaves.add(leaf);
        branchPart3D.addLeaf(leaf.getTreeLeaf3D());
        BigDecimal totalLeafCreationCost = NEW_LEAF_CREATION_COST.add(NEW_LEAF_ENERGY_PROVIDED);
        energy = energy.subtract(totalLeafCreationCost);
    }

    protected Point3d computeAttachPointForNewLeaf() {
        Point3d previousAttachPoint = new Point3d(0, 0, 0);
        Point3d saveAttachPoint1 = null;
        Point3d saveAttachPoint2 = null;
        double distance;
        double maxDistance = -1;
        List<TreeLeaf> sortedLeaves = new ArrayList<TreeLeaf>(leaves);
        Collections.sort(sortedLeaves, new TreeLeafComparator());

        // compute which couple of leaves are the most distant
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

        // once this couple is found, place the new leaf approximately in the middle +/-20%
        Point3d newLeafAttachPoint = new Point3d();
        newLeafAttachPoint.interpolate(saveAttachPoint1, saveAttachPoint2, 0.5 + Randomizer.random1());
        return newLeafAttachPoint;
    }

    private void collectFreeEnergyFromLeaves() {
        BigDecimal freeEnergyCollectedFromLeaf = new BigDecimal(0);
        for (TreeLeaf leaf : leaves) {
            freeEnergyCollectedFromLeaf = freeEnergyCollectedFromLeaf.add(leaf.collectFreeEnergy());
        }
        BigDecimal energyCollectedForBranchPart = freeEnergyCollectedFromLeaf.multiply(ENERGY_RATIO_TO_KEEP);
        BigDecimal freeEnergyCollected = freeEnergyCollectedFromLeaf.subtract(energyCollectedForBranchPart);
        energy = energy.add(energyCollectedForBranchPart);
        // limit the branch part energy to MAX_ENERGY
        energy = energy.min(MAX_ENERGY);
        freeEnergy = freeEnergy.add(freeEnergyCollected);
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

    public int getNbLeaves() {
        return leaves.size();
    }

    @Override
    public Collection<TreeLeaf> getLeaves() {
        return Collections.unmodifiableCollection(leaves);
    }

    public void update(Publisher publisher, Object arg) {
        if (LeafUpdateMask.isFieldSet((Integer) arg, LeafUpdateMask.FALL_MASK)) {
            TreeLeaf leaf = (TreeLeaf) publisher;
            leaves.remove(leaf);
        }
    }

    @Override
    public TreeBranchPartState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setEnergy(energy);
        state.setFreeEnergy(freeEnergy);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>();
        for (TreeLeaf leaf : leaves) {
            leaveStates.add((TreeLeafState) leaf.getState());
        }
        state.setLeaveStates(leaveStates);
        branchPart3D.synchronize();
    }

    @Override
    public TreeBranchPart3D getBranchPart3D() {
        return branchPart3D;
    }

}
