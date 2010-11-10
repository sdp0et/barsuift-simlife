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
package barsuift.simLife.process;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;

// TODO delete
@XmlRootElement
public class SplitBoundedTaskState implements State {

    private int bound;

    private int count;

    private int stepSize;

    public SplitBoundedTaskState() {
        super();
        bound = 20;
        count = 0;
        stepSize = 1;
    }

    public SplitBoundedTaskState(int bound, int count, int stepSize) {
        super();
        this.bound = bound;
        this.count = count;
        this.stepSize = stepSize;
    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + count;
        result = prime * result + bound;
        result = prime * result + stepSize;
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
        SplitBoundedTaskState other = (SplitBoundedTaskState) obj;
        if (count != other.count)
            return false;
        if (bound != other.bound)
            return false;
        if (stepSize != other.stepSize)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SplitBoundedTaskState [bound=" + bound + ", count=" + count + ", stepSize=" + stepSize + "]";
    }

}
