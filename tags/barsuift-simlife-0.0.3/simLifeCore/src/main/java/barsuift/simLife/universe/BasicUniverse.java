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
package barsuift.simLife.universe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.LivingPart;
import barsuift.simLife.environment.BasicEnvironment;
import barsuift.simLife.environment.Environment;
import barsuift.simLife.j3d.universe.BasicUniverse3D;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.time.TimeCounter;
import barsuift.simLife.tree.BasicTree;
import barsuift.simLife.tree.BasicTreeLeaf;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;

public class BasicUniverse implements Universe {

    private final Long id;

    private int age;

    private final Set<Tree> trees;

    private final Set<TreeLeaf> fallenLeaves;

    private Environment environment;

    private BasicUniverse3D universe3D;

    private final TimeCounter counter;


    public BasicUniverse(UniverseState state) {
        this.counter = new TimeCounter(state.getTimeCounter());
        this.id = state.getId();
        this.age = state.getAge();
        this.universe3D = new BasicUniverse3D();
        this.environment = new BasicEnvironment(state.getEnvironment());
        this.trees = new HashSet<Tree>();
        Set<TreeState> treeStates = state.getTrees();
        for (TreeState treeState : treeStates) {
            trees.add(new BasicTree(this, treeState));
        }
        this.fallenLeaves = new HashSet<TreeLeaf>();
        Set<TreeLeafState> fallenLeafStates = state.getFallenLeaves();
        for (TreeLeafState fallenLeafState : fallenLeafStates) {
            fallenLeaves.add(new BasicTreeLeaf(this, fallenLeafState));
        }
        this.universe3D.initFromUniverse(this);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void spendTime() {
        counter.increment();
        age++;
        for (LivingPart livingPart : getLivingParts()) {
            livingPart.spendTime();
        }
    }

    @Override
    public Set<LivingPart> getLivingParts() {
        Set<LivingPart> livingParts = new HashSet<LivingPart>();
        livingParts.addAll(trees);
        return Collections.unmodifiableSet(livingParts);
    }

    @Override
    public Set<Tree> getTrees() {
        return Collections.unmodifiableSet(trees);
    }

    @Override
    public void addTree(Tree tree) {
        trees.add(tree);
    }

    @Override
    public Set<TreeLeaf> getFallenLeaves() {
        return Collections.unmodifiableSet(fallenLeaves);
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
    public TimeCounter getCounter() {
        return counter;
    }

    @Override
    public UniverseState getState() {
        Set<TreeState> treeStates = new HashSet<TreeState>();
        for (Tree tree : trees) {
            treeStates.add(tree.getState());
        }
        Set<TreeLeafState> fallenLeafStates = new HashSet<TreeLeafState>();
        for (TreeLeaf fallenLeaf : fallenLeaves) {
            fallenLeafStates.add(fallenLeaf.getState());
        }
        return new UniverseState(id, age, treeStates, fallenLeafStates, environment.getState(), counter.getState());
    }

    @Override
    public Universe3D getUniverse3D() {
        return universe3D;
    }

    @Override
    public String toString() {
        return "BasicUniverse [environment=" + environment + ", id=" + id + ", age=" + age + ", trees=" + trees
                + ", fallenLeaves=" + fallenLeaves + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((environment == null) ? 0 : environment.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime + age;
        result = prime * result + ((trees == null) ? 0 : trees.hashCode());
        result = prime * result + ((fallenLeaves == null) ? 0 : fallenLeaves.hashCode());
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
        BasicUniverse other = (BasicUniverse) obj;
        if (environment == null) {
            if (other.environment != null)
                return false;
        } else
            if (!environment.equals(other.environment))
                return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else
            if (!id.equals(other.id))
                return false;
        if (age != other.age)
            return false;
        if (trees == null) {
            if (other.trees != null)
                return false;
        } else
            if (!trees.equals(other.trees))
                return false;
        if (fallenLeaves == null) {
            if (other.fallenLeaves != null)
                return false;
        } else
            if (!fallenLeaves.equals(other.fallenLeaves))
                return false;
        return true;
    }

}
