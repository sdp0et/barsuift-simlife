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

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.time.TimeController;

/**
 * The given <code>barrier</code> parameter allows this task to wait for others and then execute again, indefinitely. It
 * can be stopped with the <code>stop</code> method.
 * 
 */
public abstract class AbstractSynchronizedRunnable implements SynchronizedRunnable {

    private SynchronizedRunnableState state;

    private CyclicBarrier barrier;

    private TimeController timeController;

    private boolean running;

    private boolean isStopAsked;

    @Override
    public void setBarrier(CyclicBarrier barrier) {
        if (barrier == null) {
            throw new IllegalArgumentException("barrier is null");
        }
        if (running) {
            throw new IllegalStateException("Unable to change the barrier of a running process");
        }
        this.barrier = barrier;
    }

    @Override
    public void init(SynchronizedRunnableState state, TimeController timeController) {
        if (this.state != null) {
            throw new IllegalStateException("The process has already been initialized");
        }
        if (state == null) {
            throw new IllegalArgumentException("no state given to initialize the process");
        }
        if (timeController == null) {
            throw new IllegalArgumentException("no time controller given to initialize the process");
        }
        this.state = state;
        this.timeController = timeController;
        this.running = false;
    }

    @Override
    public final void run() {
        if (barrier == null) {
            throw new IllegalStateException("The barrier is not set");
        }
        if (running == true) {
            throw new IllegalStateException("The process is already running");
        }
        running = true;
        while (running) {
            executeStep();
            try {
                barrier.await();
            } catch (InterruptedException e) {
                return;
            } catch (BrokenBarrierException e) {
                return;
            }
        }
    }

    @Override
    public void stop() {
        if (running == false) {
            throw new IllegalStateException("The process is not running");
        }
        running = false;
    }

    /**
     * This method is called in the run loop. It contains the code to execute at each iteration. The run loop is in
     * charge of waiting other processes to ensure the synchronization.
     */
    public abstract void executeStep();

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public TimeController getTimeController() {
        return timeController;
    }

    @Override
    public SynchronizedRunnableState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do as the class won't change
    }

}
