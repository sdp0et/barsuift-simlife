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


public class SplitCyclicConditionTest extends TestCase {

    private SplitCyclicCondition condition;

    private CyclicConditionState state;

    protected void setUp() throws Exception {
        super.setUp();
        state = new CyclicConditionState(3, 2);
        condition = new SplitCyclicCondition(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        condition = null;
    }

    public void testEvaluate() {
        // 4/3
        assertTrue(condition.evaluate(2));
        // 6/3
        assertTrue(condition.evaluate(2));
        // 8/3
        assertFalse(condition.evaluate(2));
        // 10/3
        assertTrue(condition.evaluate(2));
        // 12/3
        assertTrue(condition.evaluate(2));
        // 14/3
        assertFalse(condition.evaluate(2));
        // 16/3
        assertTrue(condition.evaluate(2));
    }

    public void testGetState() {
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(2, condition.getState().getCount());
        assertEquals(3, condition.getState().getCycle());
        condition.evaluate(2);
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(1, condition.getState().getCount());
        assertEquals(3, condition.getState().getCycle());
    }

}
