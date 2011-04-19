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

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;


public class ConditionalTaskStateFactoryTest extends TestCase {

    public void testCreateConditionalTaskState() {
        ConditionalTaskState taskState;
        CyclicConditionState executionCondition;
        BoundConditionState endingCondition;
        ConditionalTaskStateFactory factory = new ConditionalTaskStateFactory();

        // existing class in properties file
        taskState = factory.createConditionalTaskState(AbstractSplitConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(5, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(10, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        // existing class in properties file
        taskState = factory.createConditionalTaskState(AbstractConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(2, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(0, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        // non existing class in properties file
        taskState = factory.createConditionalTaskState(SplitConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(1, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(0, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());
    }

    public void testCreateSplitConditionalTaskState() {
        SplitConditionalTaskState splitTaskState;
        ConditionalTaskState taskState;
        CyclicConditionState executionCondition;
        BoundConditionState endingCondition;
        ConditionalTaskStateFactory factory = new ConditionalTaskStateFactory();

        // existing class in properties file
        splitTaskState = factory.createSplitConditionalTaskState(AbstractSplitConditionalTask.class);
        assertEquals(1, splitTaskState.getStepSize());
        taskState = splitTaskState.getConditionalTask();
        executionCondition = taskState.getExecutionCondition();
        assertEquals(5, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(10, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        // existing class in properties file
        splitTaskState = factory.createSplitConditionalTaskState(AbstractConditionalTask.class);
        assertEquals(1, splitTaskState.getStepSize());
        taskState = splitTaskState.getConditionalTask();
        executionCondition = taskState.getExecutionCondition();
        assertEquals(2, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(0, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        // non existing class in properties file
        splitTaskState = factory.createSplitConditionalTaskState(SplitConditionalTask.class);
        assertEquals(1, splitTaskState.getStepSize());
        taskState = splitTaskState.getConditionalTask();
        executionCondition = taskState.getExecutionCondition();
        assertEquals(1, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(0, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());
    }

}
