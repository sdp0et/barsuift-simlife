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
 * The temporizer is an empty task, that only waits for the barrier. Its sole purpose is to release the barrier at
 * cyclic time, controlled by its scheduledThreadPool.
 * 
 */
public class Temporizer implements Runnable {

    private CyclicBarrier barrier;

    private CyclicBarrier nextBarrier;

    public Temporizer(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        // there is nothing to do in the temporizer.
        // its sole purpose is to release the barrier at cyclic time, controlled by its scheduledThreadPool
        try {
            switchBarrier();
            barrier.await();
        } catch (InterruptedException e) {
            return;
        } catch (BrokenBarrierException e) {
            return;
        }
    }

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

    private void switchBarrier() {
        if (nextBarrier != null) {
            barrier = nextBarrier;
            nextBarrier = null;
        }
    }

}
