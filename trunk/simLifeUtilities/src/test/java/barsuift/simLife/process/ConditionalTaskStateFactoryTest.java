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

import org.testng.annotations.Test;

import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;

import static org.fest.assertions.Assertions.assertThat;


public class ConditionalTaskStateFactoryTest {

    @Test
    public void createConditionalTaskState() {
        ConditionalTaskState taskState;
        CyclicConditionState executionCondition;
        BoundConditionState endingCondition;
        ConditionalTaskStateFactory factory = new ConditionalTaskStateFactory();

        // existing class in properties file
        taskState = factory.createConditionalTaskState(AbstractSplitConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertThat(executionCondition.getCycle()).isEqualTo(5);
        assertThat(executionCondition.getCount()).isEqualTo(0);
        endingCondition = taskState.getEndingCondition();
        assertThat(endingCondition.getBound()).isEqualTo(10);
        assertThat(endingCondition.getCount()).isEqualTo(0);

        // existing class in properties file
        taskState = factory.createConditionalTaskState(AbstractConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertThat(executionCondition.getCycle()).isEqualTo(2);
        assertThat(executionCondition.getCount()).isEqualTo(0);
        endingCondition = taskState.getEndingCondition();
        assertThat(endingCondition.getBound()).isEqualTo(0);
        assertThat(endingCondition.getCount()).isEqualTo(0);

        // non existing class in properties file
        taskState = factory.createConditionalTaskState(SplitConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertThat(executionCondition.getCycle()).isEqualTo(1);
        assertThat(executionCondition.getCount()).isEqualTo(0);
        endingCondition = taskState.getEndingCondition();
        assertThat(endingCondition.getBound()).isEqualTo(0);
        assertThat(endingCondition.getCount()).isEqualTo(0);
    }

    @Test
    public void createSplitConditionalTaskState() {
        SplitConditionalTaskState splitTaskState;
        ConditionalTaskState taskState;
        CyclicConditionState executionCondition;
        BoundConditionState endingCondition;
        ConditionalTaskStateFactory factory = new ConditionalTaskStateFactory();

        // existing class in properties file
        splitTaskState = factory.createSplitConditionalTaskState(AbstractSplitConditionalTask.class);
        assertThat(splitTaskState.getStepSize()).isEqualTo(1);
        taskState = splitTaskState.getConditionalTask();
        executionCondition = taskState.getExecutionCondition();
        assertThat(executionCondition.getCycle()).isEqualTo(5);
        assertThat(executionCondition.getCount()).isEqualTo(0);
        endingCondition = taskState.getEndingCondition();
        assertThat(endingCondition.getBound()).isEqualTo(10);
        assertThat(endingCondition.getCount()).isEqualTo(0);

        // existing class in properties file
        splitTaskState = factory.createSplitConditionalTaskState(AbstractConditionalTask.class);
        assertThat(splitTaskState.getStepSize()).isEqualTo(1);
        taskState = splitTaskState.getConditionalTask();
        executionCondition = taskState.getExecutionCondition();
        assertThat(executionCondition.getCycle()).isEqualTo(2);
        assertThat(executionCondition.getCount()).isEqualTo(0);
        endingCondition = taskState.getEndingCondition();
        assertThat(endingCondition.getBound()).isEqualTo(0);
        assertThat(endingCondition.getCount()).isEqualTo(0);

        // non existing class in properties file
        splitTaskState = factory.createSplitConditionalTaskState(SplitConditionalTask.class);
        assertThat(splitTaskState.getStepSize()).isEqualTo(1);
        taskState = splitTaskState.getConditionalTask();
        executionCondition = taskState.getExecutionCondition();
        assertThat(executionCondition.getCycle()).isEqualTo(1);
        assertThat(executionCondition.getCount()).isEqualTo(0);
        endingCondition = taskState.getEndingCondition();
        assertThat(endingCondition.getBound()).isEqualTo(0);
        assertThat(endingCondition.getCount()).isEqualTo(0);
    }

}
