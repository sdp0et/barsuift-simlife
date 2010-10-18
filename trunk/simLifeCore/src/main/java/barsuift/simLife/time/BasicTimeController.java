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

import barsuift.simLife.InitException;
import barsuift.simLife.Persistent;
import barsuift.simLife.process.Synchronizer;
import barsuift.simLife.universe.Universe;

/**
 * Controller for time control on the universe.
 * 
 */
public class BasicTimeController implements Persistent<TimeControllerState>, TimeController {

    private final TimeControllerState state;

    private final Synchronizer synchronizer;

    private final SimLifeCalendar calendar;

    private final Universe universe;

    private final ScheduledExecutorService scheduledThreadPool;

    private ScheduledFuture<?> runningProcess;

    private final TimeMessenger timeMessenger;

    private boolean running;

    // FIXME choose whether speed is in TimeController or Synchronizer
    private int speed;

    public BasicTimeController(Universe universe, TimeControllerState state) throws InitException {
        super();
        this.state = state;
        this.universe = universe;
        int poolSize = 1;
        this.scheduledThreadPool = Executors.newScheduledThreadPool(poolSize);
        this.timeMessenger = new TimeMessenger(universe);
        this.running = false;
        this.speed = 1;
        this.calendar = new SimLifeCalendar(state.getCalendar());
        this.synchronizer = new Synchronizer(state.getSynchronizer(), this);
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void start() throws IllegalStateException {
        if (running == true) {
            throw new IllegalStateException("The controller is already running");
        }
        running = true;
        // start immediately
        int initialDelay = 0;
        // wakeup period (speed = cycles / second)
        long period = 1000 / speed;
        runningProcess = scheduledThreadPool.scheduleAtFixedRate(timeMessenger, initialDelay, period,
                TimeUnit.MILLISECONDS);
        synchronizer.start();
    }

    @Override
    public void oneStep() {
        if (running == true) {
            throw new IllegalStateException("The controller is already running");
        }
        timeMessenger.run();
        synchronizer.oneStep();
    }

    @Override
    public void pause() {
        if (running == false) {
            throw new IllegalStateException("The controller is not running");
        }
        running = false;
        runningProcess.cancel(false);
        synchronizer.stop();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public SimLifeCalendar getCalendar() {
        return calendar;
    }

    @Override
    public Universe getUniverse() {
        return universe;
    }

    @Override
    public TimeControllerState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        calendar.synchronize();
        synchronizer.synchronize();
    }

}