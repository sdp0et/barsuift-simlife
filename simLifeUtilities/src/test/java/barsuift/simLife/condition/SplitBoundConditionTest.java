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


public class SplitBoundConditionTest extends TestCase {

    private SplitBoundCondition condition;

    private BoundConditionState state;

    protected void setUp() throws Exception {
        super.setUp();
        setUpFromParams(7, 2);
    }

    private void setUpFromParams(int bound, int count) {
        state = new BoundConditionState(bound, count);
        condition = new SplitBoundCondition(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        condition = null;
    }

    /*
     * No bound : will always return false
     */
    public void testEvaluate0() {
        setUpFromParams(0, 0);
        // 2/0
        assertFalse(condition.evaluate(2));
        // 4/0
        assertFalse(condition.evaluate(2));
        // 6/0
        assertFalse(condition.evaluate(2));
        // 8/0
        assertFalse(condition.evaluate(2));
    }

    /*
     * One execution : return true the first time
     */
    public void testEvaluate1() {
        setUpFromParams(1, 0);
        // 2/1
        assertTrue(condition.evaluate(2));
        // 4/1
        assertTrue(condition.evaluate(2));
        // 6/1
        assertTrue(condition.evaluate(2));
        // 8/1
        assertTrue(condition.evaluate(2));
    }

    /*
     * One execution : return true the first time
     */
    public void testEvaluate2() {
        setUpFromParams(2, 0);
        // 2/2
        assertTrue(condition.evaluate(2));
        // 4/2
        assertTrue(condition.evaluate(2));
        // 6/2
        assertTrue(condition.evaluate(2));
        // 8/2
        assertTrue(condition.evaluate(2));
    }

    /*
     * Two execution : will return false, and true
     */
    public void testEvaluate3() {
        setUpFromParams(4, 0);
        // 2/4
        assertFalse(condition.evaluate(2));
        // 4/4
        assertTrue(condition.evaluate(2));
        // 6/4
        assertTrue(condition.evaluate(2));
        // 8/4
        assertTrue(condition.evaluate(2));
    }

    /*
     * 3 executions : false, false, true
     */
    public void testEvaluate4() {
        setUpFromParams(7, 2);
        // 4/7
        assertFalse(condition.evaluate(2));
        // 6/7
        assertFalse(condition.evaluate(2));
        // 8/7
        assertTrue(condition.evaluate(2));
        // 10/7
        assertTrue(condition.evaluate(2));
    }

    public void testGetState() {
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(2, condition.getState().getCount());
        assertEquals(7, condition.getState().getBound());
        condition.evaluate(2);
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(4, condition.getState().getCount());
        assertEquals(7, condition.getState().getBound());
    }

}
