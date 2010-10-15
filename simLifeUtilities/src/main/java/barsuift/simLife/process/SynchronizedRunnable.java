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

/**
 * This class represents a task that can be synchronized with others.
 * <p>
 * The given <code>barrier</code> parameter allows this task to wait for others and then execute again, indefinitely. It
 * can be stopped with the <code>stop</code> method.
 * </p>
 * 
 */
public abstract class SynchronizedRunnable implements Runnable {

    private final CyclicBarrier barrier;

    private boolean running;

    public SynchronizedRunnable(CyclicBarrier barrier) {
        this.barrier = barrier;
        this.running = false;
    }

    /**
     * Start the process.
     * 
     * @throws IllegalStateException if the process is already running
     */
    @Override
    public final void run() {
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

    /**
     * Stop the process
     * 
     * @throws IllegalStateException if the process is not running.
     */
    public void stop() {
        if (running == false) {
            throw new IllegalStateException("The process is not running");
        }
        running = false;
    }

    public abstract void executeStep();

    public boolean isRunning() {
        return running;
    }

}
