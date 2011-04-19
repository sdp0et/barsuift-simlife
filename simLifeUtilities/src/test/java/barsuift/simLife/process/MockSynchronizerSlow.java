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

import barsuift.simLife.UtilDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;


public class MockSynchronizerSlow extends BasicPublisher implements SynchronizerSlow {

    private CyclicBarrier barrier;

    private Speed speed;

    private boolean running;

    private int scheduleCalled;

    private List<ConditionalTask> tasksToSchedule;

    private int unscheduleCalled;

    private List<ConditionalTask> tasksToUnschedule;

    private int startCalled;

    private int stopCalled;

    private SynchronizerSlowState state;

    private int synchronizeCalled;

    private int updateCounter;

    private List<Publisher> publisherObjectsSubscribed;

    private List<Object> arguments;

    public MockSynchronizerSlow() {
        super(null);
        reset();
    }

    public void reset() {
        barrier = new CyclicBarrier(1);
        speed = Speed.DEFAULT_SPEED;
        running = false;
        scheduleCalled = 0;
        tasksToSchedule = new ArrayList<ConditionalTask>();
        unscheduleCalled = 0;
        tasksToUnschedule = new ArrayList<ConditionalTask>();
        startCalled = 0;
        stopCalled = 0;
        state = UtilDataCreatorForTests.createSpecificSynchronizerSlowState();
        synchronizeCalled = 0;
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
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public Speed getSpeed() {
        return speed;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void schedule(ConditionalTask task) {
        scheduleCalled++;
        tasksToSchedule.add(task);
    }

    public int getNbScheduleCalled() {
        return scheduleCalled;
    }

    public List<ConditionalTask> getScheduledTasks() {
        return tasksToSchedule;
    }

    @Override
    public void unschedule(ConditionalTask task) {
        unscheduleCalled++;
        tasksToUnschedule.add(task);
    }

    public int getNbUnscheduleCalled() {
        return unscheduleCalled;
    }

    public List<ConditionalTask> getUnscheduledTasks() {
        return tasksToUnschedule;
    }


    @Override
    public void start() throws IllegalStateException {
        startCalled++;
    }

    @Override
    public long getNbStarts() {
        return startCalled;
    }

    @Override
    public void stop() {
        stopCalled++;
    }

    @Override
    public long getNbStops() {
        return stopCalled;
    }

    @Override
    public SynchronizerSlowState getState() {
        return state;
    }

    public void setState(SynchronizerSlowState state) {
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
