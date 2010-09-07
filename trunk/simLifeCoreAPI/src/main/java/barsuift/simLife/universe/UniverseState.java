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

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.environment.EnvironmentState;
import barsuift.simLife.time.TimeCounterState;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;

@XmlRootElement
public class UniverseState {

    private Long id;

    private int age;

    private Set<TreeState> trees;

    private Set<TreeLeafState> fallenLeaves;

    private EnvironmentState environment;

    private TimeCounterState timeCounter;

    public UniverseState() {
        super();
        this.id = new Long(0);
        this.age = 0;
        this.trees = new HashSet<TreeState>();
        this.fallenLeaves = new HashSet<TreeLeafState>();
        this.environment = new EnvironmentState();
        this.timeCounter = new TimeCounterState();
    }

    public UniverseState(Long id, int age, Set<TreeState> trees, Set<TreeLeafState> fallenLeaves,
            EnvironmentState environment, TimeCounterState timeCounter) {
        super();
        this.id = id;
        this.age = age;
        this.trees = trees;
        this.fallenLeaves = fallenLeaves;
        this.environment = environment;
        this.timeCounter = timeCounter;
    }

    public UniverseState(UniverseState copy) {
        super();
        this.id = copy.id;
        this.age = copy.age;
        this.trees = new HashSet<TreeState>();
        for (TreeState treeState : copy.trees) {
            trees.add(new TreeState(treeState));
        }
        this.fallenLeaves = new HashSet<TreeLeafState>();
        for (TreeLeafState treeLeafState : copy.fallenLeaves) {
            fallenLeaves.add(new TreeLeafState(treeLeafState));
        }
        this.environment = new EnvironmentState(copy.environment);
        this.timeCounter = copy.timeCounter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<TreeState> getTrees() {
        return trees;
    }

    public void setTrees(Set<TreeState> trees) {
        this.trees = trees;
    }

    public Set<TreeLeafState> getFallenLeaves() {
        return fallenLeaves;
    }

    public void setFallenLeaves(Set<TreeLeafState> fallenLeaves) {
        this.fallenLeaves = fallenLeaves;
    }

    public EnvironmentState getEnvironment() {
        return environment;
    }

    public void setEnvironment(EnvironmentState environment) {
        this.environment = environment;
    }

    public TimeCounterState getTimeCounter() {
        return timeCounter;
    }

    public void setTimeCounter(TimeCounterState timeCounter) {
        this.timeCounter = timeCounter;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((environment == null) ? 0 : environment.hashCode());
        result = prime * result + ((timeCounter == null) ? 0 : timeCounter.hashCode());
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
        UniverseState other = (UniverseState) obj;
        if (environment == null) {
            if (other.environment != null)
                return false;
        } else
            if (!environment.equals(other.environment))
                return false;
        if (timeCounter == null) {
            if (other.timeCounter != null)
                return false;
        } else
            if (!timeCounter.equals(other.timeCounter))
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

    @Override
    public String toString() {
        return "UniverseState [environment=" + environment + ", timeCounter=" + timeCounter + ", id=" + id + ", age="
                + age + ", trees=" + trees + ", fallenLeaves=" + fallenLeaves + "]";
    }

}
