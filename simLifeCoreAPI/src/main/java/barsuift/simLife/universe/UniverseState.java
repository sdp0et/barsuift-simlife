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

import barsuift.simLife.State;
import barsuift.simLife.environment.EnvironmentState;
import barsuift.simLife.j3d.universe.Universe3DState;
import barsuift.simLife.time.TimeControllerState;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;

@XmlRootElement
public class UniverseState implements State {

    private long creationMillis;

    private boolean fpsShowing;

    private Set<TreeState> trees;

    private Set<TreeLeafState> fallenLeaves;

    private EnvironmentState environment;

    private TimeControllerState timeControllerState;

    private Universe3DState univ3DState;

    public UniverseState() {
        super();
        this.creationMillis = 0;
        this.fpsShowing = false;
        this.trees = new HashSet<TreeState>();
        this.fallenLeaves = new HashSet<TreeLeafState>();
        this.environment = new EnvironmentState();
        this.timeControllerState = new TimeControllerState();
        this.univ3DState = new Universe3DState();
    }

    public UniverseState(long creationMillis, boolean fpsShowing, Set<TreeState> trees,
            Set<TreeLeafState> fallenLeaves, EnvironmentState environment, TimeControllerState timeControllerState,
            Universe3DState univ3DState) {
        super();
        this.creationMillis = creationMillis;
        this.fpsShowing = fpsShowing;
        this.trees = trees;
        this.fallenLeaves = fallenLeaves;
        this.environment = environment;
        this.timeControllerState = timeControllerState;
        this.univ3DState = univ3DState;
    }

    public long getCreationMillis() {
        return creationMillis;
    }

    public void setCreationMillis(long creationMillis) {
        this.creationMillis = creationMillis;
    }

    public boolean isFpsShowing() {
        return fpsShowing;
    }

    public void setFpsShowing(boolean fpsShowing) {
        this.fpsShowing = fpsShowing;
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

    public TimeControllerState getTimeControllerState() {
        return timeControllerState;
    }

    public void setTimeControllerState(TimeControllerState timeControllerState) {
        this.timeControllerState = timeControllerState;
    }

    public Universe3DState getUniv3DState() {
        return univ3DState;
    }

    public void setUniv3DState(Universe3DState univ3dState) {
        univ3DState = univ3dState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (creationMillis ^ (creationMillis >>> 32));
        result = prime * result + ((environment == null) ? 0 : environment.hashCode());
        result = prime * result + ((timeControllerState == null) ? 0 : timeControllerState.hashCode());
        result = prime * result + ((fallenLeaves == null) ? 0 : fallenLeaves.hashCode());
        result = prime * result + (fpsShowing ? 1231 : 1237);
        result = prime * result + ((trees == null) ? 0 : trees.hashCode());
        result = prime * result + ((univ3DState == null) ? 0 : univ3DState.hashCode());
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
        if (creationMillis != other.creationMillis)
            return false;
        if (environment == null) {
            if (other.environment != null)
                return false;
        } else
            if (!environment.equals(other.environment))
                return false;
        if (timeControllerState == null) {
            if (other.timeControllerState != null)
                return false;
        } else
            if (!timeControllerState.equals(other.timeControllerState))
                return false;
        if (fallenLeaves == null) {
            if (other.fallenLeaves != null)
                return false;
        } else
            if (!fallenLeaves.equals(other.fallenLeaves))
                return false;
        if (fpsShowing != other.fpsShowing)
            return false;
        if (trees == null) {
            if (other.trees != null)
                return false;
        } else
            if (!trees.equals(other.trees))
                return false;
        if (univ3DState == null) {
            if (other.univ3DState != null)
                return false;
        } else
            if (!univ3DState.equals(other.univ3DState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "UniverseState [creationMillis=" + creationMillis + ", fpsShowing=" + fpsShowing + ", trees=" + trees
                + ", fallenLeaves=" + fallenLeaves + ", environment=" + environment + ", timeControllerState="
                + timeControllerState + ", univ3DState=" + univ3DState + "]";
    }

}
