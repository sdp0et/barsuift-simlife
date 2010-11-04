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

public class MockSingleRunSynchronizedRunnable implements SynchronizedRunnable {

    private CyclicBarrier barrier;

    private int nbExecuted;

    private boolean running;

    private int stopCalled;

    public MockSingleRunSynchronizedRunnable() {
        reset();
    }

    public void reset() {
        nbExecuted = 0;
        stopCalled = 0;
    }

    public int getNbExecuted() {
        return nbExecuted;
    }

    @Override
    public void changeBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    @Override
    public void run() {
        nbExecuted++;
        try {
            barrier.await();
        } catch (InterruptedException e) {
            // nothing to do
        } catch (BrokenBarrierException e) {
            // nothing to do
        }
    }

    @Override
    public void stop() {
        stopCalled++;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
