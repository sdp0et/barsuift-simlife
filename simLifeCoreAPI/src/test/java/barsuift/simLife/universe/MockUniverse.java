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
package barsuift.simLife.universe;

import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.LivingPart;
import barsuift.simLife.environment.Environment;
import barsuift.simLife.environment.MockEnvironment;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.process.MockSynchronizerCore;
import barsuift.simLife.process.SynchronizerCore;
import barsuift.simLife.time.SimLifeDate;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.universe.physic.MockPhysics;
import barsuift.simLife.universe.physic.Physics;


public class MockUniverse implements Universe {

    private Set<LivingPart> livingParts;

    private Set<Tree> trees;

    private Set<TreeLeaf> fallenLeaves;

    private long creationMillis;

    private Environment environment;

    private Physics physics;

    private SynchronizerCore synchronizer;

    private SimLifeDate date;

    private Universe3D universe3D;

    private UniverseState state;

    private int nbSynchronizedCalled;

    public MockUniverse() {
        reset();
    }

    public void reset() {
        livingParts = new HashSet<LivingPart>();
        trees = new HashSet<Tree>();
        fallenLeaves = new HashSet<TreeLeaf>();
        creationMillis = 0;
        environment = new MockEnvironment();
        physics = new MockPhysics();
        synchronizer = new MockSynchronizerCore();
        date = new SimLifeDate();
        universe3D = new MockUniverse3D();
        state = new UniverseState();
        nbSynchronizedCalled = 0;
    }


    @Override
    public Set<LivingPart> getLivingParts() {
        return livingParts;
    }

    public void addLivingPart(LivingPart livingPart) {
        livingParts.add(livingPart);
    }

    public void removeLivingPart(LivingPart livingPart) {
        livingParts.remove(livingPart);
    }

    public long getCreationMillis() {
        return creationMillis;
    }

    public void setCreationMillis(long creationMillis) {
        this.creationMillis = creationMillis;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Physics getPhysics() {
        return physics;
    }

    public void setPhysics(Physics physics) {
        this.physics = physics;
    }

    @Override
    public SynchronizerCore getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(SynchronizerCore synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Override
    public SimLifeDate getDate() {
        return date;
    }

    public void setDate(SimLifeDate date) {
        this.date = date;
    }

    @Override
    public Set<Tree> getTrees() {
        return trees;
    }

    @Override
    public void addTree(Tree tree) {
        trees.add(tree);
    }

    public void removeTree(Tree tree) {
        trees.remove(tree);
    }

    @Override
    public Set<TreeLeaf> getFallenLeaves() {
        return fallenLeaves;
    }

    public void addFallenLeaf(TreeLeaf treeLeaf) {
        fallenLeaves.add(treeLeaf);
    }

    public void removeFallenLeaf(TreeLeaf treeLeaf) {
        fallenLeaves.remove(treeLeaf);
    }

    @Override
    public Universe3D getUniverse3D() {
        return universe3D;
    }

    public void setUniverse3D(Universe3D universe3D) {
        this.universe3D = universe3D;
    }

    @Override
    public UniverseState getState() {
        return state;
    }

    public void setState(UniverseState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.nbSynchronizedCalled++;
    }

    public int getNbSynchronizeCalled() {
        return nbSynchronizedCalled;
    }

}
