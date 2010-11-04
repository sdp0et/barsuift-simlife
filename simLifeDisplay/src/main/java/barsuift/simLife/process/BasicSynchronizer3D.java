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
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

// TODO 004. unit test
// TODO 004. javadoc
public class BasicSynchronizer3D implements Synchronizer3D {

    private final Synchronizer3DState state;

    private boolean running;

    private boolean isStopAsked;


    private final ScheduledExecutorService scheduledThreadPool;

    private final Temporizer temporizer;

    private ScheduledFuture<?> temporizerFuture;


    private CyclicBarrier innerBarrier;

    private CyclicBarrier barrierForRunnables;

    private final ExecutorService standardThreadPool;

    private final List<SplitBoundedRunnable> runnables = new ArrayList<SplitBoundedRunnable>();


    private final ConcurrentLinkedQueue<SplitBoundedRunnable> newTasksToSchedule = new ConcurrentLinkedQueue<SplitBoundedRunnable>();

    private final ConcurrentLinkedQueue<SplitBoundedRunnable> tasksToUnschedule = new ConcurrentLinkedQueue<SplitBoundedRunnable>();


    private final Publisher publisher = new BasicPublisher(this);


    public BasicSynchronizer3D(Synchronizer3DState state) {
        this.state = state;
        this.running = false;
        this.isStopAsked = false;
        this.barrierForRunnables = new CyclicBarrier(1, new BarrierTask());
        this.temporizer = new Temporizer(barrierForRunnables);
        this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
        this.standardThreadPool = Executors.newCachedThreadPool();
    }

    public void setBarrier(CyclicBarrier barrier) {
        if (this.innerBarrier != null) {
            throw new IllegalStateException("The synchronizer already has a barrier to synchronize on");
        }
        if (barrier == null) {
            throw new IllegalArgumentException("The given barrier is null");
        }
        this.innerBarrier = barrier;
    }

    @Override
    public void setStepSize(int stepSize) {
        for (SplitBoundedRunnable runnable : runnables) {
            runnable.setStepSize(stepSize);
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void schedule(SplitBoundedRunnable runnable) {
        newTasksToSchedule.add(runnable);
    }

    @Override
    public void unschedule(SplitBoundedRunnable runnable) {
        // first try to remove it from the list of tasks to add
        if (!newTasksToSchedule.remove(runnable)) {
            if (!runnables.contains(runnable)) {
                throw new IllegalStateException("The task to unschedule is not acutally scheduled. task=" + runnable);
            }
            // if not present in the list to add, add it to the list to remove
            tasksToUnschedule.add(runnable);
        }
    }

    @Override
    public synchronized void start() {
        isStopAsked = false;
        if (running == true) {
            throw new IllegalStateException("The synchronizer is already running");
        }
        updateTaskList(false);
        running = true;

        temporizerFuture = scheduledThreadPool.scheduleWithFixedDelay(temporizer, 0, CYCLE_LENGTH_3D_MS,
                TimeUnit.MILLISECONDS);

        for (SplitBoundedRunnable runnable : runnables) {
            standardThreadPool.submit(runnable);
        }
        setChanged();
        notifySubscribers();
    }

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
        for (SplitBoundedRunnable runnable : runnables) {
            runnable.stop();
        }
        setChanged();
        notifySubscribers();
    }

    @Override
    public Synchronizer3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }


    private void updateTaskList(boolean startNewTasks) {
        int nbNewTasksToAdd = newTasksToSchedule.size();
        int nbTasksToRemove = tasksToUnschedule.size();
        // if there are new tasks to schedule or to unschedule
        if (nbNewTasksToAdd > 0 || nbTasksToRemove > 0) {
            // 1. update the new barrier
            barrierForRunnables = new CyclicBarrier(barrierForRunnables.getParties() + nbNewTasksToAdd
                    - nbTasksToRemove, new BarrierTask());
            // 2. update the list of executed tasks
            runnables.addAll(newTasksToSchedule);
            runnables.removeAll(tasksToUnschedule);
            // 3. update the barrier for everyone
            for (SplitBoundedRunnable runnable : runnables) {
                runnable.changeBarrier(barrierForRunnables);
            }
            // 4. also change the temporizer barrier
            temporizer.changeBarrier(barrierForRunnables);
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
                SplitBoundedRunnable taskToUnschedule = tasksToUnschedule.poll();
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
            try {
                innerBarrier.await();
            } catch (InterruptedException e) {
                internalStop();
            } catch (BrokenBarrierException e) {
                internalStop();
            }
            updateTaskList(true);
            if (isStopAsked) {
                internalStop();
            }
        }

    }

}
