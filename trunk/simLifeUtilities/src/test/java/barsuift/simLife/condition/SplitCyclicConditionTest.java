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


public class SplitCyclicConditionTest {

    private SplitCyclicCondition condition;

    private CyclicConditionState state;

    @BeforeMethod
    protected void setUp() {
        state = new CyclicConditionState(3, 2);
        condition = new SplitCyclicCondition(state);
    }

    @AfterMethod
    protected void tearDown() {
        state = null;
        condition = null;
    }

    @Test
    public void testEvaluate() {
        // 4/3
        assertThat(condition.evaluate(2)).isTrue();
        // 6/3
        assertThat(condition.evaluate(2)).isTrue();
        // 8/3
        AssertJUnit.assertFalse(condition.evaluate(2));
        // 10/3
        assertThat(condition.evaluate(2)).isTrue();
        // 12/3
        assertThat(condition.evaluate(2)).isTrue();
        // 14/3
        AssertJUnit.assertFalse(condition.evaluate(2));
        // 16/3
        assertThat(condition.evaluate(2)).isTrue();
    }

    @Test
    public void testGetState() {
        assertThat(condition.getState()).isEqualTo(state);
        AssertJUnit.assertSame(state, condition.getState());
        assertThat(condition.getState().getCount()).isEqualTo(2);
        assertThat(condition.getState().getCycle()).isEqualTo(3);
        condition.evaluate(2);
        assertThat(condition.getState()).isEqualTo(state);
        AssertJUnit.assertSame(state, condition.getState());
        assertThat(condition.getState().getCount()).isEqualTo(1);
        assertThat(condition.getState().getCycle()).isEqualTo(3);
    }

}
