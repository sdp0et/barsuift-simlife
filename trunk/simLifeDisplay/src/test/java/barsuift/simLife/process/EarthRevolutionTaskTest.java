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

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.time.Month;
import barsuift.simLife.time.SimLifeDate;

import static org.fest.assertions.Assertions.assertThat;


public class EarthRevolutionTaskTest {

    private EarthRevolutionTask task;

    private SimLifeDate date;

    private MockSun3D sun3D;

    private SplitConditionalTaskState state;

    private int stepSize;

    private ConditionalTaskState conditionalTaskState;

    @BeforeMethod
    protected void setUp() {
        stepSize = 10;
        CyclicConditionState executionCondition = new CyclicConditionState(20, 0);
        BoundConditionState endingCondition = new BoundConditionState(40, 0);
        conditionalTaskState = new ConditionalTaskState(executionCondition, endingCondition);
        state = new SplitConditionalTaskState(conditionalTaskState, stepSize);
        sun3D = new MockSun3D();
        sun3D.setEarthRevolution(0f);
        date = new SimLifeDate();
        date.setMonthOfYear(Month.SPRIM);
        date.setDayOfMonth(4);
        date.setMinuteOfDay(3);
        date.setSecondOfMinute(15);
        task = new EarthRevolutionTask(state);
        task.init(sun3D, date);
    }

    @AfterMethod
    protected void tearDown() {
        state = null;
        sun3D = null;
        date = null;
        task = null;
    }

    @Test
    public void testExecuteSplitConditionalStepAutomatic() {
        // cycle = 0/20
        // end = 0/40
        assertThat(sun3D.getEarthRevolution()).isEqualTo(0f, Delta.delta(0.0001));

        task.executeStep();
        // cycle = 10/20
        // end = 10/40
        assertThat(sun3D.getEarthRevolution()).isEqualTo(0f, Delta.delta(0.0001));

        task.executeStep();
        // cycle = 0/20
        // end = 20/40
        // secondOfYear = (((1 * 18) + 3) * 20 + 3) * 60 + 15 = 25 395
        assertThat(sun3D.getEarthRevolution()).isEqualTo(
                (float) (EarthRevolutionTask.REVOLUTION_ANGLE_PER_SECOND * 25395), Delta.delta(0.0001));

        task.executeStep();
        // cycle = 10/20
        // end = 30/40
        assertThat(sun3D.getEarthRevolution()).isEqualTo(
                (float) (EarthRevolutionTask.REVOLUTION_ANGLE_PER_SECOND * 25395), Delta.delta(0.0001));
    }

    @Test
    public void testExecuteSplitConditionalStepManual() {
        task.setAutomatic(false);
        // cycle = 0/20
        // end = 0/40
        assertThat(sun3D.getEarthRevolution()).isEqualTo(0f, Delta.delta(0.0001));

        task.executeStep();
        // cycle = 10/20
        // end = 10/40
        assertThat(sun3D.getEarthRevolution()).isEqualTo(0f, Delta.delta(0.0001));

        task.executeStep();
        // cycle = 0/20
        // end = 20/40
        assertThat(sun3D.getEarthRevolution()).isEqualTo(0f, Delta.delta(0.0001));

        task.executeStep();
        // cycle = 10/20
        // end = 30/40
        assertThat(sun3D.getEarthRevolution()).isEqualTo(0f, Delta.delta(0.0001));
    }

    @Test
    public void testSetAutomatic() {
        task.executeStep();
        task.executeStep();
        assertThat(sun3D.getEarthRevolution()).isEqualTo(
                (float) (EarthRevolutionTask.REVOLUTION_ANGLE_PER_SECOND * 25395), Delta.delta(0.0001));

        sun3D.setEarthRevolution(0f);
        assertThat(sun3D.getEarthRevolution()).isEqualTo(0f, Delta.delta(0.0001));

        task.setAutomatic(true);
        // this should force the computation of the sun position
        assertThat(sun3D.getEarthRevolution()).isEqualTo(
                (float) (EarthRevolutionTask.REVOLUTION_ANGLE_PER_SECOND * 25395), Delta.delta(0.0001));
    }

}
