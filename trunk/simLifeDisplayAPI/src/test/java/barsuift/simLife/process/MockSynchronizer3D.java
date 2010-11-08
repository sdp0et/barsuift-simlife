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
import barsuift.simLife.message.Publisher;


public class MockSynchronizer3D extends BasicPublisher implements Synchronizer3D {

    private CyclicBarrier barrier;

    private boolean running;

    private int scheduleCalled;

    private List<SynchronizedTask> tasksToSchedule;

    private int unscheduleCalled;

    private List<SynchronizedTask> tasksToUnschedule;

    private int startCalled;

    private int stopCalled;

    private Synchronizer3DState state;

    private int synchronizeCalled;

    private int stepSize;

    private int updateCounter;

    private List<Publisher> publisherObjectsSubscribed;

    private List<Object> arguments;

    public MockSynchronizer3D() {
        super(null);
        reset();
    }

    public void reset() {
        barrier = new CyclicBarrier(1);
        running = false;
        scheduleCalled = 0;
        tasksToSchedule = new ArrayList<SynchronizedTask>();
        unscheduleCalled = 0;
        tasksToUnschedule = new ArrayList<SynchronizedTask>();
        startCalled = 0;
        stopCalled = 0;
        state = DisplayDataCreatorForTests.createSpecificSynchronizer3DState();
        synchronizeCalled = 0;
        stepSize = 1;
        updateCounter = 0;
        publisherObjectsSubscribed = new ArrayList<Publisher>();
        arguments = new ArrayList<Object>();
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
    public void schedule(SplitBoundedTask task) {
        scheduleCalled++;
        tasksToSchedule.add(task);
    }

    public int getNbScheduleCalled() {
        return scheduleCalled;
    }

    public List<SynchronizedTask> getScheduledTasks() {
        return tasksToSchedule;
    }

    @Override
    public void unschedule(SplitBoundedTask task) {
        unscheduleCalled++;
        tasksToUnschedule.add(task);
    }

    public int getNbUnscheduleCalled() {
        return unscheduleCalled;
    }

    public List<SynchronizedTask> getUnscheduledTasks() {
        return tasksToUnschedule;
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

    @Override
    public void update(Publisher publisher, Object arg) {
        updateCounter++;
        publisherObjectsSubscribed.add(publisher);
        arguments.add(arg);
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public List<Publisher> getPublisherObjectsSubscribed() {
        return publisherObjectsSubscribed;
    }

    public int getUpdateCounter() {
        return updateCounter;
    }

}
