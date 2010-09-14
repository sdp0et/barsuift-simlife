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
import java.util.Observable;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.tree.BasicTreeLeaf3D;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.universe.Universe;

// TODO 010. remove everywhere the age and replace with creation time : remove age++, and remove notifyObserver on aging
public class BasicTreeLeaf extends Observable implements TreeLeaf {


    private static final BigDecimal ONE = new BigDecimal(1);

    /**
     * 5% decrease
     */
    private static final BigDecimal AGING_EFFICIENCY_DECREASE = PercentHelper.getDecimalValue(95);

    private static final BigDecimal LOWEST_EFFICIENCY_BEFORE_FALLING = PercentHelper.getDecimalValue(10);

    private static final BigDecimal MAX_ENERGY_TO_COLLECT = new BigDecimal(150);

    private static final BigDecimal ENERGY_RATIO_TO_KEEP = PercentHelper.getDecimalValue(66);

    private final TreeLeafState state;

    private BigDecimal efficiency;

    private int age;

    private BigDecimal energy;

    private BigDecimal freeEnergy;


    private TreeLeaf3D leaf3D;

    private final Universe universe;

    private int updateMask;

    public BasicTreeLeaf(Universe universe, TreeLeafState leafState) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (leafState == null) {
            throw new IllegalArgumentException("null leaf state");
        }
        this.state = leafState;
        this.efficiency = state.getEfficiency();
        this.age = state.getAge();
        this.energy = state.getEnergy();
        this.freeEnergy = state.getFreeEnergy();

        this.universe = universe;
        this.leaf3D = new BasicTreeLeaf3D(universe.getUniverse3D(), leafState.getLeaf3DState(), this);
        this.updateMask = 0;
    }

    /**
     * Make the leaf older than it was.
     * <p>
     * Concretely, it means :
     * <ol>
     * <li>age : reduce the efficiency by 5 percent and send a notifying message of LeafUpdateCode.age</li>
     * <li>collect solar energy : add energy based on sun luminosity and leaf efficiency</li>
     * <li>if the leaf is too weak :
     * <ul>
     * <li>fall : send a notifying message of LeafUpdateCode.fall</li>
     * </ul>
     * else :
     * <ul>
     * <li>use energy : improve the leaf efficiency by using the collected energy</li>
     * </ul>
     * </ol>
     * </li>
     * </p>
     */
    public void spendTime() {
        updateMask = 0;
        age();
        collectSolarEnergy();
        if (isTooWeak()) {
            fall();
        } else {
            useEnergy();
        }
        if (hasChanged()) {
            notifyObservers(updateMask);
        }
    }

    private void age() {
        age++;
        setChanged();
        updateMask |= LeafUpdateMask.AGE_MASK;
        efficiency = efficiency.multiply(AGING_EFFICIENCY_DECREASE);
        setChanged();
        updateMask |= LeafUpdateMask.EFFICIENCY_MASK;
    }

    /**
     * Compute the new leaf energy. It is the old energy + the collected energy.
     * <code>collectedEnergy= sunLuminosity * leafEfficiency * 10</code>
     * 
     * @return the collected energy
     */
    private void collectSolarEnergy() {
        BigDecimal lightRate = universe.getEnvironment().getSun().getLuminosity();
        BigDecimal solarEnergyRateCollected = efficiency.multiply(lightRate);
        BigDecimal energyCollected = solarEnergyRateCollected.multiply(MAX_ENERGY_TO_COLLECT).multiply(
                new BigDecimal(leaf3D.getArea()));
        BigDecimal energyCollectedForLeaf = energyCollected.multiply(ENERGY_RATIO_TO_KEEP);
        BigDecimal freeEnergyCollected = energyCollected.subtract(energyCollectedForLeaf);
        energy = energy.add(energyCollectedForLeaf);
        freeEnergy = freeEnergy.add(freeEnergyCollected).setScale(5, RoundingMode.HALF_DOWN);
        setChanged();
        updateMask |= LeafUpdateMask.ENERGY_MASK;
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
        updateMask |= LeafUpdateMask.FALL_MASK;
    }

    private void useEnergy() {
        improveEfficiency();
    }

    private void improveEfficiency() {
        BigDecimal maxEfficiencyToAdd = ONE.subtract(efficiency);
        // use all the energy, up to the max efficiency that can be added to get 100
        BigDecimal efficiencyToAdd = maxEfficiencyToAdd.min(energy.movePointLeft(2));
        efficiency = efficiency.add(efficiencyToAdd).setScale(10, RoundingMode.HALF_DOWN);
        setChanged();
        updateMask |= LeafUpdateMask.EFFICIENCY_MASK;
        energy = energy.subtract(efficiencyToAdd.movePointRight(2)).setScale(5, RoundingMode.HALF_DOWN);
        setChanged();
        updateMask |= LeafUpdateMask.ENERGY_MASK;
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

    public int getAge() {
        return age;
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
        state.setAge(age);
        state.setEnergy(energy);
        state.setFreeEnergy(freeEnergy);
        leaf3D.synchronize();
    }

}
