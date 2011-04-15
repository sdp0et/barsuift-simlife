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

@XmlRootElement
public class SplitConditionalTaskState implements State {

    private ConditionalTaskState conditionalTask;

    private int stepSize;

    public SplitConditionalTaskState() {
        super();
        this.conditionalTask = new ConditionalTaskState();
        stepSize = Speed.DEFAULT_SPEED.getSpeed();
    }

    public SplitConditionalTaskState(ConditionalTaskState conditionalTask, int stepSize) {
        super();
        this.conditionalTask = conditionalTask;
        this.stepSize = stepSize;
    }

    public ConditionalTaskState getConditionalTask() {
        return conditionalTask;
    }

    public void setConditionalTask(ConditionalTaskState conditionalTask) {
        this.conditionalTask = conditionalTask;
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
        result = prime * result + ((conditionalTask == null) ? 0 : conditionalTask.hashCode());
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
        SplitConditionalTaskState other = (SplitConditionalTaskState) obj;
        if (conditionalTask == null) {
            if (other.conditionalTask != null)
                return false;
        } else
            if (!conditionalTask.equals(other.conditionalTask))
                return false;

        if (stepSize != other.stepSize)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SplitConditionalTaskState [conditionalTask=" + conditionalTask + ", stepSize=" + stepSize + "]";
    }

}
