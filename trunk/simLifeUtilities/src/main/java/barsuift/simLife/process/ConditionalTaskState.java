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
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;

@XmlRootElement
public class ConditionalTaskState implements State {

    private CyclicConditionState executionCondition;

    private BoundConditionState endingCondition;

    public ConditionalTaskState() {
        super();
        this.executionCondition = new CyclicConditionState();
        this.endingCondition = new BoundConditionState();
    }

    public ConditionalTaskState(CyclicConditionState executionCondition, BoundConditionState endingCondition) {
        super();
        this.executionCondition = executionCondition;
        this.endingCondition = endingCondition;
    }

    public CyclicConditionState getExecutionCondition() {
        return executionCondition;
    }

    public void setExecutionCondition(CyclicConditionState executionCondition) {
        this.executionCondition = executionCondition;
    }

    public BoundConditionState getEndingCondition() {
        return endingCondition;
    }

    public void setEndingCondition(BoundConditionState endingCondition) {
        this.endingCondition = endingCondition;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endingCondition == null) ? 0 : endingCondition.hashCode());
        result = prime * result + ((executionCondition == null) ? 0 : executionCondition.hashCode());
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
        ConditionalTaskState other = (ConditionalTaskState) obj;
        if (endingCondition == null) {
            if (other.endingCondition != null)
                return false;
        } else
            if (!endingCondition.equals(other.endingCondition))
                return false;
        if (executionCondition == null) {
            if (other.executionCondition != null)
                return false;
        } else
            if (!executionCondition.equals(other.executionCondition))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "ConditionalTaskState [executionCondition=" + executionCondition + ", endingCondition="
                + endingCondition + "]";
    }

}
