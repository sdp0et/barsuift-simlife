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
package barsuift.simLife.condition;

import junit.framework.TestCase;
import barsuift.simLife.process.MockSingleSynchronizedTask;
import barsuift.simLife.process.MockSynchronizedTask;

public class CyclicConditionStateFactoryTest extends TestCase {

    public void testCreateCyclicTaskState() {
        CyclicConditionStateFactory factory = new CyclicConditionStateFactory();

        CyclicConditionState mockSynchronizedState = factory.createCyclicConditionState(MockSynchronizedTask.class);
        assertEquals(5, mockSynchronizedState.getCycle());
        assertEquals(0, mockSynchronizedState.getCount());

        CyclicConditionState unknownState = factory.createCyclicConditionState(MockSingleSynchronizedTask.class);
        assertEquals(1, unknownState.getCycle());
        assertEquals(0, unknownState.getCount());
    }

}
