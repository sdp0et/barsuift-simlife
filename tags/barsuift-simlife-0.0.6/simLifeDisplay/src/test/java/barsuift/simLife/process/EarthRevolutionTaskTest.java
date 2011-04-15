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

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.j3d.environment.MockSun3D;
import barsuift.simLife.time.Month;
import barsuift.simLife.time.SimLifeDate;


public class EarthRevolutionTaskTest extends TestCase {

    private EarthRevolutionTask task;

    private SimLifeDate date;

    private MockSun3D sun3D;

    private SplitConditionalTaskState state;

    private int stepSize;

    private ConditionalTaskState conditionalTaskState;

    protected void setUp() throws Exception {
        super.setUp();
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
        task = new EarthRevolutionTask(state, sun3D, date);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        sun3D = null;
        date = null;
        task = null;
    }

    public void testExecuteSplitConditionalStepAutomatic() {
        // cycle = 0/20
        // end = 0/40
        assertEquals(0f, sun3D.getEarthRevolution(), 0.0001);

        task.executeStep();
        // cycle = 10/20
        // end = 10/40
        assertEquals(0f, sun3D.getEarthRevolution(), 0.0001);

        task.executeStep();
        // cycle = 0/20
        // end = 20/40
        // secondOfYear = (((1 * 18) + 3) * 20 + 3) * 60 + 15 = 25 395
        assertEquals(EarthRevolutionTask.REVOLUTION_ANGLE_PER_SECOND * 25395, sun3D.getEarthRevolution(), 0.0001);

        task.executeStep();
        // cycle = 10/20
        // end = 30/40
        // secondOfYear = (((2 * 18) + 4) * 20 + 3) * 60 + 15 = 25 395
        assertEquals(EarthRevolutionTask.REVOLUTION_ANGLE_PER_SECOND * 25395, sun3D.getEarthRevolution(), 0.0001);
    }

    public void testExecuteSplitConditionalStepManual() {
        task.setAutomatic(false);
        // cycle = 0/20
        // end = 0/40
        assertEquals(0f, sun3D.getEarthRevolution(), 0.0001);

        task.executeStep();
        // cycle = 10/20
        // end = 10/40
        assertEquals(0f, sun3D.getEarthRevolution(), 0.0001);

        task.executeStep();
        // cycle = 0/20
        // end = 20/40
        assertEquals(0f, sun3D.getEarthRevolution(), 0.0001);

        task.executeStep();
        // cycle = 10/20
        // end = 30/40
        assertEquals(0f, sun3D.getEarthRevolution(), 0.0001);
    }

    public void testSetAutomatic() {
        task.executeStep();
        task.executeStep();
        assertEquals(EarthRevolutionTask.REVOLUTION_ANGLE_PER_SECOND * 25395, sun3D.getEarthRevolution(), 0.0001);

        sun3D.setEarthRevolution(0f);
        assertEquals(0f, sun3D.getEarthRevolution(), 0.0001);

        task.setAutomatic(true);
        // this should force the computation of the sun position
        assertEquals(EarthRevolutionTask.REVOLUTION_ANGLE_PER_SECOND * 25395, sun3D.getEarthRevolution(), 0.0001);
    }

}
