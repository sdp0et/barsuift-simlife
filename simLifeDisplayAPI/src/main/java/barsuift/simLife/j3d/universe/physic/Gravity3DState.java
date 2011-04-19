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
package barsuift.simLife.j3d.universe.physic;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.process.SplitConditionalTaskState;

@XmlRootElement
public class Gravity3DState implements State {

    private SplitConditionalTaskState gravityTask;

    public Gravity3DState() {
        this.gravityTask = new SplitConditionalTaskState();
    }

    public Gravity3DState(SplitConditionalTaskState gravityTask) {
        this.gravityTask = gravityTask;
    }

    public SplitConditionalTaskState getGravityTask() {
        return gravityTask;
    }

    public void setGravityTask(SplitConditionalTaskState gravityTask) {
        this.gravityTask = gravityTask;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gravityTask == null) ? 0 : gravityTask.hashCode());
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
        Gravity3DState other = (Gravity3DState) obj;
        if (gravityTask == null) {
            if (other.gravityTask != null)
                return false;
        } else
            if (!gravityTask.equals(other.gravityTask))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "Gravity3DState [gravityTask=" + gravityTask + "]";
    }

}
