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
        AssertJUnit.assertFalse(condition.evaluate());
        // 2/0
        AssertJUnit.assertFalse(condition.evaluate());
        // 3/0
        AssertJUnit.assertFalse(condition.evaluate());
        // 4/0
        AssertJUnit.assertFalse(condition.evaluate());
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
        AssertJUnit.assertFalse(condition.evaluate());
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
        AssertJUnit.assertFalse(condition.evaluate());
        // 4/5
        AssertJUnit.assertFalse(condition.evaluate());

        // 5/5
        assertThat(condition.evaluate()).isTrue();
        // 6/5
        assertThat(condition.evaluate()).isTrue();
    }

    @Test
    public void getState() {
        AssertJUnit.assertEquals(state, condition.getState());
        AssertJUnit.assertSame(state, condition.getState());
        AssertJUnit.assertEquals(2, condition.getState().getCount());
        AssertJUnit.assertEquals(5, condition.getState().getBound());
        condition.evaluate();
        AssertJUnit.assertEquals(state, condition.getState());
        AssertJUnit.assertSame(state, condition.getState());
        AssertJUnit.assertEquals(3, condition.getState().getCount());
        AssertJUnit.assertEquals(5, condition.getState().getBound());
    }

}
