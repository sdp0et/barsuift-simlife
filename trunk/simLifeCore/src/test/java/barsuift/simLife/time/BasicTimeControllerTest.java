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

import junit.framework.TestCase;
import barsuift.simLife.universe.MockUniverse;

public class BasicTimeControllerTest extends TestCase {

    private TimeController controller;

    private MockUniverse mockUniverse;

    private TimeControllerState state;

    protected void setUp() throws Exception {
        super.setUp();
        mockUniverse = new MockUniverse();
        TimeControllerStateFactory stateFactory = new TimeControllerStateFactory();
        state = stateFactory.createTimeControllerState();
        // state = CoreDataCreatorForTests.createSpecificTimeControllerState();
        controller = new BasicTimeController(mockUniverse, state);
        controller.setSpeed(10);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        controller = null;
        mockUniverse = null;
    }

    public void testStart() {
        assertFalse(controller.isRunning());
        assertEquals(new SimLifeCalendar(), controller.getCalendar());
        int speed = controller.getSpeed();
        controller.start();
        // waiting 2 cycles
        try {
            synchronized (this) {
                this.wait(2000 / speed + 10);
            }
        } catch (InterruptedException e) {
        }
        assertTrue(mockUniverse.getNbTimeSpent() >= 2);
        assertTrue(controller.isRunning());
    }

    public void testPause() {
        int speed = controller.getSpeed();
        controller.start();
        assertTrue(controller.isRunning());

        // waiting 2 cycles
        try {
            synchronized (this) {
                this.wait(2000 / speed + 10);
            }
        } catch (InterruptedException e) {
        }
        int nbTimeSpent1 = mockUniverse.getNbTimeSpent();
        controller.stop();

        // waiting 2 cycles
        try {
            synchronized (this) {
                this.wait(2000 / speed + 10);
            }
        } catch (InterruptedException e) {
        }
        int nbTimeSpent2 = mockUniverse.getNbTimeSpent();
        // the controller should be stopped within a cycle (end of one single run)
        assertTrue(nbTimeSpent2 <= nbTimeSpent1 + 1);
        assertTrue(nbTimeSpent2 >= nbTimeSpent1);
        assertFalse(controller.isRunning());

        // waiting 2 cycles
        try {
            synchronized (this) {
                this.wait(2000 / speed + 10);
            }
        } catch (InterruptedException e) {
        }
        int nbTimeSpent3 = mockUniverse.getNbTimeSpent();
        assertEquals("the counter should not increment anymore", nbTimeSpent2, nbTimeSpent3);
        controller.start();
        // waiting 2 cycles
        try {
            synchronized (this) {
                this.wait(2000 / speed + 10);
            }
        } catch (InterruptedException e) {
        }
        int nbTimeSpent4 = mockUniverse.getNbTimeSpent();
        assertTrue(nbTimeSpent4 >= nbTimeSpent3 + 2);
        assertTrue(controller.isRunning());
    }

    public void testIllegalStateException() {
        int speed = controller.getSpeed();
        try {
            controller.stop();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        controller.start();
        // waiting 2 cycles
        try {
            synchronized (this) {
                this.wait(2000 / speed + 10);
            }
        } catch (InterruptedException e) {
        }
        try {
            controller.start();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        try {
            controller.oneStep();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

    }

    public void testOneStep() {
        int speed = controller.getSpeed();
        controller.oneStep();
        assertEquals(1, mockUniverse.getNbTimeSpent());
        // waiting 2 cycles
        try {
            synchronized (this) {
                this.wait(2000 / speed + 10);
            }
        } catch (InterruptedException e) {
        }
        // the time counter should not have changed
        assertEquals(1, mockUniverse.getNbTimeSpent());
    }

    public void testGetState() {
        assertEquals(state, controller.getState());
        assertSame(state, controller.getState());
        assertEquals(0, controller.getState().getCalendar().getValue());
        assertEquals(10, controller.getState().getSynchronizer().getSpeed());
        int newSpeed = 1;
        controller.setSpeed(newSpeed);
        controller.oneStep();
        // waiting 2 cycles
        try {
            synchronized (this) {
                this.wait(2000 / newSpeed + 10);
            }
        } catch (InterruptedException e) {
        }
        assertEquals(state, controller.getState());
        assertSame(state, controller.getState());
        assertEquals(100, controller.getState().getCalendar().getValue());
        assertEquals(newSpeed, controller.getState().getSynchronizer().getSpeed());
    }

}
