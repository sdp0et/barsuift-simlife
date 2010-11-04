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

import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;


public class MockSynchronizer3D extends BasicPublisher implements Synchronizer3D {

    private CyclicBarrier barrier;

    private boolean running;

    private int scheduleCalled;

    private List<Runnable> runnablesToSchedule;

    private int unscheduleCalled;

    private List<Runnable> runnablesToUnschedule;

    private int startCalled;

    private int stopCalled;

    private Synchronizer3DState state;

    private int synchronizeCalled;

    private int stepSize;

    public MockSynchronizer3D() {
        super(null);
        reset();
    }

    public void reset() {
        barrier = new CyclicBarrier(1);
        running = false;
        scheduleCalled = 0;
        runnablesToSchedule = new ArrayList<Runnable>();
        unscheduleCalled = 0;
        runnablesToUnschedule = new ArrayList<Runnable>();
        startCalled = 0;
        stopCalled = 0;
        state = DisplayDataCreatorForTests.createSpecificSynchronizer3DState();
        synchronizeCalled = 0;
        stepSize = 1;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void schedule(SplitBoundedRunnable runnable) {
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
        startCalled++;
    }

    public int getNbStartCalled() {
        return startCalled;
    }

    @Override
    public void stop() {
        stopCalled++;
    }

    public int getNbStopCalled() {
        return stopCalled;
    }

    @Override
    public Synchronizer3DState getState() {
        return state;
    }

    public void setState(Synchronizer3DState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

    @Override
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    public int getStepSize() {
        return stepSize;
    }

}
