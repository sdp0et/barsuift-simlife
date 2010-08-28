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

import barsuift.simLife.Percent;
import barsuift.simLife.PercentState;
import barsuift.simLife.j3d.tree.BasicTreeLeaf3D;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.universe.Universe;

public class BasicTreeLeaf extends Observable implements TreeLeaf {

    /**
     * 5% decrease
     */
    private static final Percent AGING_EFFICIENCY_DECREASE = new Percent(95);

    private static final Percent LOWEST_EFFICIENCY_BEFORE_FALLING = new Percent(10);

    private static final BigDecimal MAX_ENERGY_TO_COLLECT = new BigDecimal(150);

    private static final Percent ENERGY_RATIO_TO_KEEP = new Percent(66);

    private TreeLeafState state;

    private TreeLeaf3D leaf3D;

    private final Universe universe;

    public BasicTreeLeaf(Universe universe, TreeLeafState leafState) {
        if (universe == null) {
            throw new IllegalArgumentException("null universe");
        }
        if (leafState == null) {
            throw new IllegalArgumentException("null leaf state");
        }
        this.universe = universe;
        this.state = new TreeLeafState(leafState);
        this.leaf3D = new BasicTreeLeaf3D(universe.getUniverse3D(), leafState.getLeaf3DState(), this);

    }

    public Long getId() {
        return state.getId();
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
        age();
        collectSolarEnergy();
        if (isTooWeak()) {
            fall();
        } else {
            useEnergy();
        }
    }

    private void age() {
        state.setAge(state.getAge() + 1);
        setChanged();
        notifyObservers(LeafUpdateCode.age);
        BigDecimal newEfficiency = getEfficiency().getValue().multiply(AGING_EFFICIENCY_DECREASE.getValue());
        state.setEfficiency(new PercentState(newEfficiency));
        setChanged();
        notifyObservers(LeafUpdateCode.efficiency);
    }

    /**
     * Compute the new leaf energy. It is the old energy + the collected energy.
     * <code>collectedEnergy= sunLuminosity * leafEfficiency * 10</code>
     * 
     * @return the collected energy
     */
    private void collectSolarEnergy() {
        Percent lightRate = universe.getEnvironment().getSun().getLuminosity();
        BigDecimal solarEnergyRateCollected = getEfficiency().getValue().multiply(lightRate.getValue());
        BigDecimal energyCollected = solarEnergyRateCollected.multiply(MAX_ENERGY_TO_COLLECT).multiply(
                new BigDecimal(leaf3D.getArea()));
        BigDecimal energyCollectedForLeaf = energyCollected.multiply(ENERGY_RATIO_TO_KEEP.getValue());
        BigDecimal freeEnergyCollected = energyCollected.subtract(energyCollectedForLeaf);
        state.setEnergy(state.getEnergy().add(energyCollectedForLeaf));
        state.setFreeEnergy(state.getFreeEnergy().add(freeEnergyCollected));
        setChanged();
        notifyObservers(LeafUpdateCode.energy);
    }

    /**
     * Return true if the leaf efficiency is lower than the lowest acceptable value
     */
    public boolean isTooWeak() {
        return getEfficiency().compareTo(LOWEST_EFFICIENCY_BEFORE_FALLING) < 0;
    }

    /**
     * Send a notifying message of LeafUpdateCode.fall
     */
    private void fall() {
        universe.addFallenLeaf(this);
        setChanged();
        notifyObservers(LeafUpdateCode.fall);
    }

    private void useEnergy() {
        improveEfficiency();
    }

    private void improveEfficiency() {
        BigDecimal maxEfficiencyToAdd = new BigDecimal(1).subtract(getEfficiency().getValue());
        // use all the energy, up to the max efficiency that can be added to get 100
        BigDecimal efficiencyToAdd = maxEfficiencyToAdd.min(getEnergy().movePointLeft(2));
        state.setEfficiency(new PercentState(getEfficiency().getValue().add(efficiencyToAdd).setScale(10,
                RoundingMode.HALF_DOWN)));
        setChanged();
        notifyObservers(LeafUpdateCode.efficiency);
        state.setEnergy(getEnergy().subtract(efficiencyToAdd.movePointRight(2)).setScale(10, RoundingMode.HALF_DOWN));
        setChanged();
        notifyObservers(LeafUpdateCode.energy);
    }

    @Override
    public Percent getEfficiency() {
        return state.getEfficiency().toPercent();
    }

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

    public int getAge() {
        return state.getAge();
    }

    public TreeLeaf3D getTreeLeaf3D() {
        return leaf3D;
    }

    @Override
    public TreeLeafState getState() {
        state.setLeaf3DState(leaf3D.getState());
        return new TreeLeafState(state);
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
        BasicTreeLeaf other = (BasicTreeLeaf) obj;
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
        return "BasicTreeLeaf [state=" + state + "]";
    }

}
