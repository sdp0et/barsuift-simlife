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
package barsuift.simLife.time;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class FpsCounterTest {

    private FpsCounter counter;

    @BeforeMethod
    protected void setUp() {
        counter = new FpsCounter();
    }

    @AfterMethod
    protected void tearDown() {
        counter = null;
    }

    @Test
    public void tick1() {
        // test default values
        checkInitialValues();
        // one single tick
        counter.tick();
        checkInitialValues();
    }

    private void checkInitialValues() {
        assertThat(counter.getExecTime()).isEqualTo(1f);
        assertThat(counter.getAvgExecTime()).isEqualTo(1f);
        assertThat(counter.getFps()).isEqualTo(1);
        assertThat(counter.getAvgFps()).isEqualTo(1);
    }

    @Test
    public void tick10() {
        checkInitialValues();
        // 10 ticks
        tenTicks();
        assertThat(counter.getExecTime()).as("10 ticks should not take more than 10 seconds").isLessThan(10000);
        assertThat(counter.getExecTime()).as("The exec time must always be positive").isGreaterThan(0);
        assertThat(counter.getAvgExecTime()).as("The average time should not have been recomputed").isEqualTo(1f);
        assertThat(counter.getFps()).isGreaterThan(0);// we can not assume anything else
        assertThat(counter.getAvgFps()).as("The average fsp should not have been recomputed").isEqualTo(1);
    }

    @Test
    public void tick100() {
        checkInitialValues();
        // 100 ticks
        for (int i = 0; i < 10; i++) {
            tenTicks();
        }
        assertThat(counter.getExecTime()).as("100 ticks should not take more than 10 seconds").isLessThan(10000);
        assertThat(counter.getExecTime()).as("The exec time must always be positive").isGreaterThan(0);
        assertThat(counter.getAvgExecTime()).as("100 ticks should not take more than 10 seconds").isLessThan(10000);
        assertThat(counter.getAvgExecTime()).as("The exec time must always be positive").isGreaterThan(0);
        // we can not assume anything else
        assertThat(counter.getFps()).isGreaterThan(0);
        // we can not assume anything else
        assertThat(counter.getAvgFps()).isGreaterThan(0);
    }

    private void tenTicks() {
        for (int i = 0; i < 10; i++) {
            counter.tick();
        }
    }

}