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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import barsuift.simLife.LivingPart;
import barsuift.simLife.environment.BasicEnvironment;
import barsuift.simLife.environment.Environment;
import barsuift.simLife.j3d.universe.BasicUniverse3D;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.process.BasicSynchronizerCore;
import barsuift.simLife.process.ConditionalTaskState;
import barsuift.simLife.process.ConditionalTaskStateFactory;
import barsuift.simLife.process.DateUpdater;
import barsuift.simLife.process.SynchronizerCore;
import barsuift.simLife.time.DateHandler;
import barsuift.simLife.time.SimLifeDate;
import barsuift.simLife.tree.BasicTree;
import barsuift.simLife.tree.BasicTreeLeaf;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;
import barsuift.simLife.universe.physic.BasicPhysics;
import barsuift.simLife.universe.physic.Physics;

public class BasicUniverse implements Universe {

    private final UniverseState state;

    private final List<Tree> trees;

    private final List<TreeLeaf> fallenLeaves;

    private final Environment environment;

    private final Physics physics;

    private final BasicSynchronizerCore synchronizer;

    private final DateHandler dateHandler;

    private final BasicUniverse3D universe3D;


    public BasicUniverse(UniverseState state) {
        this.state = state;
        this.universe3D = new BasicUniverse3D(state.getUniv3DState(), this);
        this.environment = new BasicEnvironment(state.getEnvironment());
        this.physics = new BasicPhysics(this, state.getPhysics());
        this.synchronizer = new BasicSynchronizerCore(state.getSynchronizerState());
        this.dateHandler = new DateHandler(state.getDateHandler());
        ConditionalTaskStateFactory taskStateFactory = new ConditionalTaskStateFactory();
        ConditionalTaskState dateUpdaterState = taskStateFactory.createConditionalTaskState(DateUpdater.class);
        DateUpdater dateUpdater = new DateUpdater(dateUpdaterState, dateHandler.getDate());
        synchronizer.schedule(dateUpdater);
        this.trees = new ArrayList<Tree>();
        Set<TreeState> treeStates = state.getTrees();
        for (TreeState treeState : treeStates) {
            BasicTree newTree = new BasicTree(this, treeState);
            trees.add(newTree);
            System.out.println("nb Leaves=" + newTree.getNbLeaves());
        }
        this.fallenLeaves = new ArrayList<TreeLeaf>();
        Set<TreeLeafState> fallenLeafStates = state.getFallenLeaves();
        for (TreeLeafState fallenLeafState : fallenLeafStates) {
            fallenLeaves.add(new BasicTreeLeaf(this, fallenLeafState));
        }
        this.universe3D.initFromUniverse(this);
    }

    @Override
    public List<LivingPart> getLivingParts() {
        List<LivingPart> livingParts = new ArrayList<LivingPart>();
        livingParts.addAll(trees);
        return Collections.unmodifiableList(livingParts);
    }

    @Override
    public List<Tree> getTrees() {
        return Collections.unmodifiableList(trees);
    }

    @Override
    public void addTree(Tree tree) {
        trees.add(tree);
    }

    @Override
    public List<TreeLeaf> getFallenLeaves() {
        return Collections.unmodifiableList(fallenLeaves);
    }

    @Override
    public void addFallenLeaf(TreeLeaf treeLeaf) {
        fallenLeaves.add(treeLeaf);
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public Physics getPhysics() {
        return physics;
    }

    @Override
    public SynchronizerCore getSynchronizer() {
        return synchronizer;
    }

    @Override
    public SimLifeDate getDate() {
        return dateHandler.getDate();
    }

    @Override
    public UniverseState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        Set<TreeState> treeStates = new HashSet<TreeState>();
        for (Tree tree : trees) {
            treeStates.add((TreeState) tree.getState());
        }
        state.setTrees(treeStates);
        Set<TreeLeafState> fallenLeaveStates = new HashSet<TreeLeafState>();
        for (TreeLeaf leaf : fallenLeaves) {
            fallenLeaveStates.add((TreeLeafState) leaf.getState());
        }
        state.setFallenLeaves(fallenLeaveStates);
        environment.synchronize();
        physics.synchronize();
        synchronizer.synchronize();
        dateHandler.synchronize();
    }

    @Override
    public Universe3D getUniverse3D() {
        return universe3D;
    }

}
