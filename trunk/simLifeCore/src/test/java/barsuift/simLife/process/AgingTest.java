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

import barsuift.simLife.UtilDataCreatorForTests;
import barsuift.simLife.tree.MockTree;

import static org.fest.assertions.Assertions.assertThat;


public class AgingTest {

    @Test
    public void testExecuteCyclicStep() {
        MockTree mockTree = new MockTree();
        ConditionalTaskState conditionalTaskState = UtilDataCreatorForTests.createSpecificConditionalTaskState();
        // this is to ensure the task can be executed exactly once.
        conditionalTaskState.getEndingCondition().setBound(conditionalTaskState.getExecutionCondition().getCycle() + 1);
        Aging aging = new Aging(conditionalTaskState);
        aging.init(mockTree);
        aging.executeConditionalStep();
        assertThat(mockTree.getNbAgeCalled()).isEqualTo(1);

        // cyclic condition = 2/5
        aging.executeStep();
        // the cyclic condition does not return true
        assertThat(mockTree.getNbAgeCalled()).isEqualTo(1);

        // cyclic condition = 3/5
        aging.executeStep();
        // the cyclic condition does not return true
        assertThat(mockTree.getNbAgeCalled()).isEqualTo(1);

        // cyclic condition = 4/5
        aging.executeStep();
        // the cyclic condition return true
        assertThat(mockTree.getNbAgeCalled()).isEqualTo(2);
    }

}
