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

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class BoundConditionTest {

    private BoundCondition condition;

    private BoundConditionState state;

    @BeforeMethod
    protected void setUp() {
        setUpFromParams(5, 2);
    }

    private void setUpFromParams(int bound, int count) {
        state = new BoundConditionState(bound, count);
        condition = new BoundCondition(state);
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
    public void evaluate0() {
        setUpFromParams(0, 0);
        // 1/0
        assertThat(condition.evaluate()).isFalse();
        // 2/0
        assertThat(condition.evaluate()).isFalse();
        // 3/0
        assertThat(condition.evaluate()).isFalse();
        // 4/0
        assertThat(condition.evaluate()).isFalse();
    }

    /*
     * One execution : return true the first time
     */
    @Test
    public void evaluate1() {
        setUpFromParams(1, 0);
        // 1/1
        assertThat(condition.evaluate()).isTrue();
        // 2/1
        assertThat(condition.evaluate()).isTrue();
        // 3/1
        assertThat(condition.evaluate()).isTrue();
        // 4/1
        assertThat(condition.evaluate()).isTrue();
    }

    /*
     * Two executions : will return false, and true
     */
    @Test
    public void evaluate2() {
        setUpFromParams(2, 0);
        // 1/2
        assertThat(condition.evaluate()).isFalse();
        // 2/2
        assertThat(condition.evaluate()).isTrue();
        // 3/2
        assertThat(condition.evaluate()).isTrue();
        // 4/2
        assertThat(condition.evaluate()).isTrue();
    }

    /*
     * 3 executions : false, false, true
     */
    @Test
    public void evaluate3() {
        setUpFromParams(5, 2);
        // 3/5
        assertThat(condition.evaluate()).isFalse();
        // 4/5
        assertThat(condition.evaluate()).isFalse();

        // 5/5
        assertThat(condition.evaluate()).isTrue();
        // 6/5
        assertThat(condition.evaluate()).isTrue();
    }

    @Test
    public void getState() {
        assertThat( condition.getState()).isEqualTo(state);
        assertThat(condition.getState()).isSameAs(state);
        assertThat( condition.getState().getCount()).isEqualTo(2);
        assertThat( condition.getState().getBound()).isEqualTo(5);
        condition.evaluate();
        assertThat( condition.getState()).isEqualTo(state);
        assertThat(condition.getState()).isSameAs(state);
        assertThat( condition.getState().getCount()).isEqualTo(3);
        assertThat( condition.getState().getBound()).isEqualTo(5);
    }

}
