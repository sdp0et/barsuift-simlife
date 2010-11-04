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
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import barsuift.simLife.InitException;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.time.DateHandler;
import barsuift.simLife.time.SimLifeDate;

/**
 * The synchronizer allows to run the list of given {@link SynchronizedRunnable} at a given rate. A
 * {@link CyclicBarrier} is used to synchronized all the tasks, and a {@link Temporizer}, in a
 * {@link ScheduledExecutorService}, is used to ensure there is always the same delay between two runs.
 * 
 */
public class BasicSynchronizer implements Synchronizer {

    /**
     * Length of a core cycle, used to schedule the core temporizer, and to know how much to add to the date at each
     * iteration.
     */
    public static final int CYCLE_LENGTH_CORE_MS = 500;

    /**
     * Length of a 3D cycle, used to schedule the 3D temporizer.
     */
    public static final int CYCLE_LENGTH_3D_MS = 25;

    private final SynchronizerState state;

    private final DateHandler dateHandler;

    private boolean running;

    private boolean isStopAsked;

    private Speed speed;


    private final ScheduledExecutorService scheduledThreadPool;

    private final Temporizer temporizer;

    private ScheduledFuture<?> temporizerFuture;


    private CyclicBarrier barrier;

    private final ExecutorService standardThreadPool;

    private final List<SynchronizedRunnable> runnables = new ArrayList<SynchronizedRunnable>();


    private final ConcurrentLinkedQueue<SynchronizedRunnable> newTasksToSchedule = new ConcurrentLinkedQueue<SynchronizedRunnable>();

    private final ConcurrentLinkedQueue<SynchronizedRunnable> tasksToUnschedule = new ConcurrentLinkedQueue<SynchronizedRunnable>();


    private final Publisher publisher = new BasicPublisher(this);


    public BasicSynchronizer(SynchronizerState state) throws InitException {
        this.state = state;
        this.running = false;
        this.isStopAsked = false;
        this.speed = state.getSpeed();
        this.dateHandler = new DateHandler(state.getDateHandler());
        this.barrier = new CyclicBarrier(1, new BarrierTask());
        this.temporizer = new Temporizer(barrier);
        this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
        this.standardThreadPool = Executors.newCachedThreadPool();
        DateUpdater dateUpdater = new DateUpdater(dateHandler.getDate());
        schedule(dateUpdater);
    }

    @Override
    public SimLifeDate getDate() {
        return dateHandler.getDate();
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

    @Override
    public void schedule(SynchronizedRunnable runnable) {
        newTasksToSchedule.add(runnable);
    }

    @Override
    public void unschedule(SynchronizedRunnable runnable) {
        // first try to remove it from the list of tasks to add
        if (!newTasksToSchedule.remove(runnable)) {
            if (!runnables.contains(runnable)) {
                throw new IllegalStateException("The task to unschedule is not acutally scheduled. task=" + runnable);
            }
            // if not present in the list to add, add it to the list to remove
            tasksToUnschedule.add(runnable);
        }
    }

    /**
     * Execute one step of the synchronizer.
     * 
     * @throws IllegalStateException if the synchronizer is already running
     */
    @Override
    public synchronized void oneStep() {
        isStopAsked = true;
        internalStart();
    }

    /**
     * Start the synchronizer.
     * <p>
     * All the SynchronizedRunnable objects given at initialization time are started.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is already running
     */
    @Override
    public synchronized void start() {
        isStopAsked = false;
        internalStart();
    }

    private void internalStart() {
        if (running == true) {
            throw new IllegalStateException("The synchronizer is already running");
        }
        updateTaskList(false);
        running = true;

        // wake-up period (speed = cycles / cycle length)
        long period = CYCLE_LENGTH_CORE_MS / speed.getSpeed();
        temporizerFuture = scheduledThreadPool.scheduleWithFixedDelay(temporizer, 0, period, TimeUnit.MILLISECONDS);

        for (Runnable runnable : runnables) {
            standardThreadPool.submit(runnable);
        }
        setChanged();
        notifySubscribers();
    }

    /**
     * Stop the synchronizer.
     * <p>
     * The running processes are asked to stop, once they have completed their current execution.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is not running
     */
    @Override
    public synchronized void stop() {
        if (running == false) {
            throw new IllegalStateException("The synchronizer is not running");
        }
        isStopAsked = true;
    }

    public synchronized void stopAndWait() {
        stop();
        while (isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                return;
            }
        }
    }

    private void internalStop() {
        if (running == false) {
            throw new IllegalStateException("The synchronizer is not running");
        }
        running = false;

        temporizerFuture.cancel(false);
        for (SynchronizedRunnable runnable : runnables) {
            runnable.stop();
        }
        setChanged();
        notifySubscribers();
    }

    @Override
    public SynchronizerState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setSpeed(speed);
        dateHandler.synchronize();
    }


    private void updateTaskList(boolean startNewTasks) {
        int nbNewTasksToAdd = newTasksToSchedule.size();
        int nbTasksToRemove = tasksToUnschedule.size();
        // if there are new tasks to schedule or to unschedule
        if (nbNewTasksToAdd > 0 || nbTasksToRemove > 0) {
            // 1. update the new barrier
            barrier = new CyclicBarrier(barrier.getParties() + nbNewTasksToAdd - nbTasksToRemove, new BarrierTask());
            // 2. update the list of executed tasks
            runnables.addAll(newTasksToSchedule);
            runnables.removeAll(tasksToUnschedule);
            // 3. update the barrier for everyone
            for (SynchronizedRunnable runnable : runnables) {
                runnable.changeBarrier(barrier);
            }
            // 4. also change the temporizer barrier
            temporizer.changeBarrier(barrier);
            // 5. start the new tasks if needed, or simply purge the list
            if (startNewTasks) {
                while (!newTasksToSchedule.isEmpty()) {
                    standardThreadPool.submit(newTasksToSchedule.poll());
                }
            } else {
                // simply purge the list
                while (!newTasksToSchedule.isEmpty()) {
                    newTasksToSchedule.poll();
                }
            }
            // 6. stop the old tasks
            while (!tasksToUnschedule.isEmpty()) {
                SynchronizedRunnable taskToUnschedule = tasksToUnschedule.poll();
                if (taskToUnschedule.isRunning()) {
                    taskToUnschedule.stop();
                }
            }
        }
    }

    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    public int countSubscribers() {
        return publisher.countSubscribers();
    }

    public void setChanged() {
        publisher.setChanged();
    }

    public void clearChanged() {
        publisher.clearChanged();
    }

    /**
     * This class is used to stop all running process if isStopAsked is set to true.
     * 
     */
    private class BarrierTask implements Runnable {

        @Override
        public synchronized void run() {
            updateTaskList(true);
            if (isStopAsked) {
                internalStop();
            }
        }

    }

}
