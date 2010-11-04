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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;


public class MockInstrumentedSynchronizer3D extends BasicSynchronizer3D {

    private CyclicBarrier barrier;

    private int scheduleCalled;

    private List<Runnable> runnablesToSchedule;

    private int unscheduleCalled;

    private List<Runnable> runnablesToUnschedule;

    private int startCalled;

    private int stopCalled;

    private int synchronizeCalled;

    private int stepSize;

    public MockInstrumentedSynchronizer3D(Synchronizer3DState state) {
        super(state);
        reset();
    }

    public void reset() {
        barrier = new CyclicBarrier(1);
        scheduleCalled = 0;
        runnablesToSchedule = new ArrayList<Runnable>();
        unscheduleCalled = 0;
        runnablesToUnschedule = new ArrayList<Runnable>();
        startCalled = 0;
        stopCalled = 0;
        synchronizeCalled = 0;
        stepSize = 1;
    }

    public void setBarrier(CyclicBarrier barrier) {
        super.setBarrier(barrier);
        this.barrier = barrier;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    @Override
    public boolean isRunning() {
        return super.isRunning();
    }

    @Override
    public void schedule(SplitBoundedRunnable runnable) {
        super.schedule(runnable);
        scheduleCalled++;
        runnablesToSchedule.add(runnable);
    }

    public int getNbScheduleCalled() {
        return scheduleCalled;
    }

    public List<Runnable> getScheduledRunnables() {
        return runnablesToSchedule;
    }

    @Override
    public void unschedule(SplitBoundedRunnable runnable) {
        super.unschedule(runnable);
        unscheduleCalled++;
        runnablesToUnschedule.add(runnable);
    }

    public int getNbUnscheduleCalled() {
        return unscheduleCalled;
    }

    public List<Runnable> getUnscheduledRunnables() {
        return runnablesToUnschedule;
    }


    @Override
    public void start() throws IllegalStateException {
        super.start();
        startCalled++;
    }

    public int getNbStartCalled() {
        return startCalled;
    }

    @Override
    public void stop() {
        super.stop();
        stopCalled++;
    }

    public int getNbStopCalled() {
        return stopCalled;
    }

    @Override
    public Synchronizer3DState getState() {
        return super.getState();
    }

    @Override
    public void synchronize() {
        super.synchronize();
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

    @Override
    public void setStepSize(int stepSize) {
        super.setStepSize(stepSize);
        this.stepSize = stepSize;
    }

    public int getStepSize() {
        return stepSize;
    }

}
