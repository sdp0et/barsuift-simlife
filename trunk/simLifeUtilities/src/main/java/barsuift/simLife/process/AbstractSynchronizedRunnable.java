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
 * The given <code>barrier</code> parameter allows this task to wait for others and then execute again, indefinitely. It
 * can be stopped with the <code>stop</code> method.
 * 
 */
public abstract class AbstractSynchronizedRunnable implements SynchronizedRunnable {

    private final CyclicBarrier barrier;

    private boolean running;

    public AbstractSynchronizedRunnable(CyclicBarrier barrier) {
        this.barrier = barrier;
        this.running = false;
    }

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

}
