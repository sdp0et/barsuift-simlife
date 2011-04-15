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

import barsuift.simLife.j3d.tree.MockTreeLeaf3D;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.MockSubscriber;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class MockTreeLeaf extends MockSubscriber implements TreeLeaf {

    private BigDecimal efficiency;

    private long creationMillis;

    private BigDecimal energy;

    private BigDecimal freeEnergy;

    private boolean isTooWeak;

    private TreeLeaf3D treeLeaf3D;

    private TreeLeafState state;

    private int synchronizedCalled;

    private int collectSolarEnergyCalled;

    private int ageCalled;

    private int improveEfficiencyCalled;

    private Publisher publisher;

    public MockTreeLeaf() {
        reset();
    }

    public void reset() {
        efficiency = new BigDecimal(0);
        creationMillis = 0;
        energy = new BigDecimal(0);
        freeEnergy = new BigDecimal(0);
        isTooWeak = false;
        treeLeaf3D = new MockTreeLeaf3D();
        state = new TreeLeafState();
        synchronizedCalled = 0;
        collectSolarEnergyCalled = 0;
        ageCalled = 0;
        improveEfficiencyCalled = 0;
        publisher = new BasicPublisher(this);
    }

    @Override
    public BigDecimal getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(BigDecimal efficiency) {
        this.efficiency = efficiency;
    }

    public long getCreationMillis() {
        return creationMillis;
    }

    public void setCreationMillis(long creationMillis) {
        this.creationMillis = creationMillis;
    }

    @Override
    public BigDecimal getEnergy() {
        return energy;
    }

    public void setEnergy(BigDecimal energy) {
        this.energy = energy;
    }

    @Override
    public BigDecimal collectFreeEnergy() {
        return freeEnergy;
    }

    public void setFreeEnergy(BigDecimal freeEnergy) {
        this.freeEnergy = freeEnergy;
    }

    @Override
    public void collectSolarEnergy() {
        collectSolarEnergyCalled++;
    }

    public int getNbCollectSolarEnergyCalled() {
        return collectSolarEnergyCalled;
    }

    @Override
    public void age() {
        ageCalled++;
    }

    public int getNbAgeCalled() {
        return ageCalled;
    }

    @Override
    public void improveEfficiency() {
        improveEfficiencyCalled++;
    }

    public int getNbImproveEfficiencyCalled() {
        return improveEfficiencyCalled;
    }

    public boolean isTooWeak() {
        return isTooWeak;
    }

    public void setTooWeak(boolean isTooWeak) {
        this.isTooWeak = isTooWeak;
    }

    @Override
    public TreeLeaf3D getTreeLeaf3D() {
        return treeLeaf3D;
    }

    @Override
    public TreeLeafState getState() {
        return state;
    }

    public void setState(TreeLeafState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
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
