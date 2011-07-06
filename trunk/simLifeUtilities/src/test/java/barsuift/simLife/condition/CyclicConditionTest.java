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


public class CyclicConditionTest {

    private CyclicCondition condition;

    private CyclicConditionState state;

    @BeforeMethod
    protected void setUp() {
        state = new CyclicConditionState(5, 2);
        condition = new CyclicCondition(state);
    }

    @AfterMethod
    protected void tearDown() {
        state = null;
        condition = null;
    }

    @Test
    public void testEvaluate() {
        // 3/5
        AssertJUnit.assertFalse(condition.evaluate());
        // 4/5
        AssertJUnit.assertFalse(condition.evaluate());

        // 5/5
        assertThat(condition.evaluate()).isTrue();
        // 1/5
        AssertJUnit.assertFalse(condition.evaluate());
        // 2/5
        AssertJUnit.assertFalse(condition.evaluate());
        // 3/5
        AssertJUnit.assertFalse(condition.evaluate());
        // 4/5
        AssertJUnit.assertFalse(condition.evaluate());

        // 5/5
        assertThat(condition.evaluate()).isTrue();
    }

    @Test
    public void testGetState() {
        assertThat(condition.getState()).isEqualTo(state);
        AssertJUnit.assertSame(state, condition.getState());
        assertThat(condition.getState().getCount()).isEqualTo(2);
        assertThat(condition.getState().getCycle()).isEqualTo(5);
        condition.evaluate();
        assertThat(condition.getState()).isEqualTo(state);
        AssertJUnit.assertSame(state, condition.getState());
        assertThat(condition.getState().getCount()).isEqualTo(3);
        assertThat(condition.getState().getCycle()).isEqualTo(5);
    }

}
