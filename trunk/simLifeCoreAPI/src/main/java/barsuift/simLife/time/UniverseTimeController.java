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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import barsuift.simLife.universe.Universe;

/**
 * Controller for time control on the universe.
 * 
 */
public class UniverseTimeController {

    private final ScheduledExecutorService scheduledThreadPool;

    private ScheduledFuture<?> runningProcess;

    private final TimeMessenger timeMessenger;

    private boolean running;

    public UniverseTimeController(Universe universe) {
        super();
        int poolSize = 1;
        this.scheduledThreadPool = Executors.newScheduledThreadPool(poolSize);
        this.timeMessenger = new TimeMessenger(universe);
        this.running = false;
    }

    /**
     * Start the controller.
     * <p>
     * A {@link TimeMessenger} instance is scheduled to be run every seconds.
     * </p>
     * 
     * @throws IllegalStateException if the controller is already running
     */
    public void start() throws IllegalStateException {
        if (running == true) {
            throw new IllegalStateException("The controller is already running");
        }
        running = true;
        // start immediately
        int initialDelay = 0;
        // wakeup every seconds
        // TODO 005. implement a proper speed switch button (be careful to tests as it assume a speed of 1)
        // TODO 006. store the actual speed somewhere
        int speed = 100;
        long period = 1000;
        long actualPeriod = period / speed;
        runningProcess = scheduledThreadPool.scheduleAtFixedRate(timeMessenger, initialDelay, actualPeriod,
                TimeUnit.MILLISECONDS);
    }

    /**
     * Execute one step of the controller.
     * <p>
     * A {@link TimeMessenger} instance is run once.
     * </p>
     * 
     * @throws IllegalStateException if the controller is already running
     */
    public void oneStep() {
        if (running == true) {
            throw new IllegalStateException("The controller is already running");
        }
        timeMessenger.run();
    }

    /**
     * Pause the controller.
     * <p>
     * The running process is aksed to stop, once it has completede its current execution.
     * </p>
     * 
     * @throws IllegalStateException if the controller is not running
     */
    public void pause() {
        if (running == false) {
            throw new IllegalStateException("The controller is not running");
        }
        running = false;
        runningProcess.cancel(false);
    }

    public boolean isRunning() {
        return running;
    }

    public TimeCounter getTimeCounter() {
        return timeMessenger.getTimeCounter();
    }

}
