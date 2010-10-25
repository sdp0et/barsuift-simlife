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

import barsuift.simLife.PercentHelper;
import barsuift.simLife.environment.Sun;
import barsuift.simLife.j3d.tree.BasicTreeLeaf3D;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.universe.Universe;

public class BasicTreeLeaf implements TreeLeaf {


    private static final BigDecimal ONE = new BigDecimal(1);

    /**
     * 5% decrease
     */
    private static final BigDecimal AGING_EFFICIENCY_DECREASE = PercentHelper.getDecimalValue(95);

    private static final BigDecimal LOWEST_EFFICIENCY_BEFORE_FALLING = PercentHelper.getDecimalValue(10);

    private static final BigDecimal ENERGY_RATIO_TO_KEEP = PercentHelper.getDecimalValue(66);

    private static final BigDecimal MAX_ENERGY = new BigDecimal(100);

    private final TreeLeafState state;

    private BigDecimal efficiency;

    private final long creationMillis;

    private BigDecimal energy;

    private BigDecimal freeEnergy;


    private final TreeLeaf3D leaf3D;

    private final Universe universe;

    private final Publisher publisher = new BasicPublisher(this);

    public BasicTreeLeaf(Universe universe, TreeLeafState leafState) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (leafState == null) {
            throw new IllegalArgumentException("null leaf state");
        }
        this.state = leafState;
        this.efficiency = state.getEfficiency();
        this.creationMillis = state.getCreationMillis();
        this.energy = state.getEnergy();
        this.freeEnergy = state.getFreeEnergy();

        this.universe = universe;
        this.leaf3D = new BasicTreeLeaf3D(universe.getUniverse3D(), leafState.getLeaf3DState(), this);
    }

    /**
     * Make the leaf older than it was.
     * <p>
     * Concretely, it means :
     * <ol>
     * <li>use collected energy to improve the leaf efficiency</li>
     * </ol>
     * </p>
     */
    public void spendTime() {
        useEnergy();
    }

    /**
     * Reduce the efficiency by 5 percent, and notify subscribers. If the leaf is too weak, then fall (notify
     * subscribers).
     */
    @Override
    public void age() {
        efficiency = efficiency.multiply(AGING_EFFICIENCY_DECREASE);
        setChanged();
        notifySubscribers(LeafUpdateMask.EFFICIENCY_MASK);
        if (isTooWeak()) {
            fall();
        }
    }

    @Override
    public void collectSolarEnergy() {
        BigDecimal lightRate = universe.getEnvironment().getSun().getLuminosity();
        BigDecimal solarEnergyRateCollected = efficiency.multiply(lightRate);
        BigDecimal energyCollected = solarEnergyRateCollected.multiply(Sun.ENERGY_DENSITY).multiply(
                new BigDecimal(leaf3D.getArea()));
        BigDecimal energyCollectedForLeaf = energyCollected.multiply(ENERGY_RATIO_TO_KEEP);
        BigDecimal freeEnergyCollected = energyCollected.subtract(energyCollectedForLeaf);
        energy = energy.add(energyCollectedForLeaf).setScale(10, RoundingMode.HALF_DOWN);
        // limit the energy to MAX_ENERGY
        energy = energy.min(MAX_ENERGY);
        freeEnergy = freeEnergy.add(freeEnergyCollected).setScale(5, RoundingMode.HALF_DOWN);
    }

    /**
     * Return true if the leaf efficiency is lower than the lowest acceptable value
     */
    public boolean isTooWeak() {
        return efficiency.compareTo(LOWEST_EFFICIENCY_BEFORE_FALLING) < 0;
    }

    /**
     * Send a notifying message of LeafUpdateCode.fall
     */
    private void fall() {
        universe.addFallenLeaf(this);
        setChanged();
        notifySubscribers(LeafUpdateMask.FALL_MASK);
    }

    private void useEnergy() {
        improveEfficiency();
    }

    // TODO 045. do not use all energy at one time to improve efficiency
    // energy collected is around 6 in best case, and we can use up to 90 here
    /**
     * One energy point allows to gain 1% of energy
     */
    private void improveEfficiency() {
        BigDecimal maxEfficiencyToAdd = ONE.subtract(efficiency);
        // use all the energy, up to the max efficiency that can be added to get 100
        BigDecimal efficiencyToAdd = maxEfficiencyToAdd.min(energy.movePointLeft(2));
        efficiency = efficiency.add(efficiencyToAdd).setScale(10, RoundingMode.HALF_DOWN);
        setChanged();
        energy = energy.subtract(efficiencyToAdd.movePointRight(2)).setScale(5, RoundingMode.HALF_DOWN);
        notifySubscribers(LeafUpdateMask.EFFICIENCY_MASK);
    }

    @Override
    public BigDecimal getEfficiency() {
        return efficiency;
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
    public long getCreationMillis() {
        return creationMillis;
    }

    public TreeLeaf3D getTreeLeaf3D() {
        return leaf3D;
    }

    @Override
    public TreeLeafState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setEfficiency(efficiency);
        state.setEnergy(energy);
        state.setFreeEnergy(freeEnergy);
        leaf3D.synchronize();
    }

    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    public int countSubscribers() {
        return publisher.countSubscribers();
    }

    public void setChanged() {
        publisher.setChanged();
    }

    public void clearChanged() {
        publisher.clearChanged();
    }

}
