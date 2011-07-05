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
package barsuift.simLife.j3d.environment;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.process.SplitConditionalTaskState;

@XmlRootElement
public class Wind3DState implements State {

    private SplitConditionalTaskState windTask;

    public Wind3DState() {
        this.windTask = new SplitConditionalTaskState();
    }

    public Wind3DState(SplitConditionalTaskState windTask) {
        this.windTask = windTask;
    }

    public SplitConditionalTaskState getWindTask() {
        return windTask;
    }

    public void setWindTask(SplitConditionalTaskState windTask) {
        this.windTask = windTask;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((windTask == null) ? 0 : windTask.hashCode());
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
        Wind3DState other = (Wind3DState) obj;
        if (windTask == null) {
            if (other.windTask != null)
                return false;
        } else
            if (!windTask.equals(other.windTask))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "Wind3DState [windTask=" + windTask + "]";
    }

}
