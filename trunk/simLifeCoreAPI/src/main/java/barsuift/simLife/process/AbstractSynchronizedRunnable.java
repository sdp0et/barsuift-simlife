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

    private CyclicBarrier nextBarrier;

    private TimeController timeController;

    private boolean running;

    private boolean isStopAsked;

    @Override
    public void changeBarrier(CyclicBarrier barrier) {
        if (barrier == null) {
            throw new IllegalArgumentException("barrier is null");
        }
        if (this.barrier == null) {
            // it is the first time the barrier is set
            this.barrier = barrier;
        } else {
            this.nextBarrier = barrier;
        }
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

    /**
     * This method runs as long as the {@link #stop()} method is not called.
     * <p>
     * It calls the {@link #executeStep()} method and then waits for the other synchronized processes. If the
     * {@code stop} method has not been called in the meantime, then the process continues its loop.
     * </p>
     * <p>
     * After executing its step, it checks if it has to switch its current barrier (see
     * {@link #changeBarrier(CyclicBarrier) changeBarrier}). Its it does (the method has been called at least once
     * during the execution of this task), then it switch its barrier before waiting on it for other synchronized tasks.
     * </p>
     */
    @Override
    public final void run() {
        if (barrier == null) {
            throw new IllegalStateException("The barrier is not set");
        }
        if (running == true) {
            throw new IllegalStateException("The process is already running");
        }
        isStopAsked = false;
        running = true;
        while (!isStopAsked) {
            executeStep();
            switchBarrier();
            try {
                barrier.await();
            } catch (InterruptedException e) {
                return;
            } catch (BrokenBarrierException e) {
                return;
            }
        }
        running = false;
    }

    private void switchBarrier() {
        if (nextBarrier != null) {
            barrier = nextBarrier;
            nextBarrier = null;
        }
    }

    /**
     * Ask the current process to stop.
     * <p>
     * When the {@link #executeStep()} completes, the barrier is passed through and the process actually stops. The
     * method {@link #isRunning()} will return false only when all these steps are completed, and not as soon as this
     * method is executed. In particular, the process is still running as long as it has not passed through the barrier.
     * It stays in its running mode as long as all other synchronized process have not completed their own
     * {@code executeStep} method.
     * </p>
     */
    @Override
    public void stop() {
        if (running == false) {
            throw new IllegalStateException("The process is not running");
        }
        isStopAsked = true;
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
