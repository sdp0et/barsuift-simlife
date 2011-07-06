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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class SplitBoundConditionTest {

    private SplitBoundCondition condition;

    private BoundConditionState state;

    @BeforeMethod
    protected void setUp() {
        setUpFromParams(7, 2);
    }

    private void setUpFromParams(int bound, int count) {
        state = new BoundConditionState(bound, count);
        condition = new SplitBoundCondition(state);
    }

    @AfterMethod
    protected void tearDown() {
        state = null;
        condition = null;
    }

    /*
     * No bound : will always return false
     */
    @Test
    public void testEvaluate0() {
        setUpFromParams(0, 0);
        // 2/0
        assertThat(condition.evaluate(2)).isFalse();
        // 4/0
        assertThat(condition.evaluate(2)).isFalse();
        // 6/0
        assertThat(condition.evaluate(2)).isFalse();
        // 8/0
        assertThat(condition.evaluate(2)).isFalse();
    }

    /*
     * One execution : return true the first time
     */
    @Test
    public void testEvaluate1() {
        setUpFromParams(1, 0);
        // 2/1
        assertThat(condition.evaluate(2)).isTrue();
        // 4/1
        assertThat(condition.evaluate(2)).isTrue();
        // 6/1
        assertThat(condition.evaluate(2)).isTrue();
        // 8/1
        assertThat(condition.evaluate(2)).isTrue();
    }

    /*
     * One execution : return true the first time
     */
    @Test
    public void testEvaluate2() {
        setUpFromParams(2, 0);
        // 2/2
        assertThat(condition.evaluate(2)).isTrue();
        // 4/2
        assertThat(condition.evaluate(2)).isTrue();
        // 6/2
        assertThat(condition.evaluate(2)).isTrue();
        // 8/2
        assertThat(condition.evaluate(2)).isTrue();
    }

    /*
     * Two execution : will return false, and true
     */
    @Test
    public void testEvaluate3() {
        setUpFromParams(4, 0);
        // 2/4
        assertThat(condition.evaluate(2)).isFalse();
        // 4/4
        assertThat(condition.evaluate(2)).isTrue();
        // 6/4
        assertThat(condition.evaluate(2)).isTrue();
        // 8/4
        assertThat(condition.evaluate(2)).isTrue();
    }

    /*
     * 3 executions : false, false, true
     */
    @Test
    public void testEvaluate4() {
        setUpFromParams(7, 2);
        // 4/7
        assertThat(condition.evaluate(2)).isFalse();
        // 6/7
        assertThat(condition.evaluate(2)).isFalse();
        // 8/7
        assertThat(condition.evaluate(2)).isTrue();
        // 10/7
        assertThat(condition.evaluate(2)).isTrue();
    }

    @Test
    public void testGetState() {
        assertThat(condition.getState()).isEqualTo(state);
        assertThat(condition.getState()).isSameAs(state);
        assertThat(condition.getState().getCount()).isEqualTo(2);
        assertThat(condition.getState().getBound()).isEqualTo(7);
        condition.evaluate(2);
        assertThat(condition.getState()).isEqualTo(state);
        assertThat(condition.getState()).isSameAs(state);
        assertThat(condition.getState().getCount()).isEqualTo(4);
        assertThat(condition.getState().getBound()).isEqualTo(7);
    }

}
