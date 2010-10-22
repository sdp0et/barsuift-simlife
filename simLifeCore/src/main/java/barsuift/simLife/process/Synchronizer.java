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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import barsuift.simLife.Persistent;
import barsuift.simLife.time.TimeController;

/**
 * The synchronizer allows to run the list of given {@link SynchronizedRunnable} at a given rate. A
 * {@link CyclicBarrier} is used to synchronized all the tasks, and a {@link Temporizer}, in a
 * {@link ScheduledExecutorService}, is used to ensure there is always the same delay between two runs.
 * 
 */
public class Synchronizer implements Persistent<SynchronizerState> {

    /**
     * Length of a cycle, used to schedule the temporizer, and to know how much to add to the date at each iteration.
     */
    public static final int CYCLE_LENGTH_MS = 100;

    private final SynchronizerState state;

    private boolean running;

    private boolean isStopAsked;

    private int speed;


    private final ScheduledExecutorService scheduledThreadPool;

    private final Temporizer temporizer;

    private ScheduledFuture<?> temporizerFuture;


    private CyclicBarrier barrier;

    private final ExecutorService standardThreadPool;

    private final List<SynchronizedRunnable> runnables = new ArrayList<SynchronizedRunnable>();


    private final ConcurrentLinkedQueue<SynchronizedRunnable> newTasksToSchedule = new ConcurrentLinkedQueue<SynchronizedRunnable>();


    public Synchronizer(SynchronizerState state, TimeController timeController) throws InitException {
        this.state = state;
        this.running = false;
        this.isStopAsked = false;
        this.speed = state.getSpeed();

        List<SynchronizedRunnableState> stateRunnables = new ArrayList<SynchronizedRunnableState>();
        stateRunnables.addAll(state.getSynchronizedRunnables());
        stateRunnables.addAll(state.getUnfrequentRunnables());

        int nbScheduledThread = 1;
        int nbStandardThread = stateRunnables.size();
        int totalNbThread = nbScheduledThread + nbStandardThread;

        barrier = new CyclicBarrier(totalNbThread, new BarrierTask());

        temporizer = new Temporizer(barrier);
        scheduledThreadPool = Executors.newScheduledThreadPool(nbScheduledThread);

        for (SynchronizedRunnableState runState : stateRunnables) {
            Class<? extends SynchronizedRunnable> clazz = runState.getClazz();
            runnables.add(instantiateClass(clazz, runState, barrier, timeController));
        }

        standardThreadPool = Executors.newFixedThreadPool(64);
    }


    private SynchronizedRunnable instantiateClass(Class<? extends SynchronizedRunnable> clazz,
            SynchronizedRunnableState runState, CyclicBarrier barrier, TimeController timeController)
            throws InitException {
        Constructor<? extends SynchronizedRunnable> runnableConstructor;
        try {
            runnableConstructor = clazz.getConstructor();
        } catch (SecurityException e) {
            throw new InitException("Unable to get the appropriate constructor for " + clazz, e);
        } catch (NoSuchMethodException e) {
            throw new InitException("Unable to get the appropriate constructor for " + clazz, e);
        }
        SynchronizedRunnable result;
        try {
            result = runnableConstructor.newInstance();
        } catch (IllegalArgumentException e) {
            throw new InitException("Unable to instantiate the constructor for " + clazz, e);
        } catch (InstantiationException e) {
            throw new InitException("Unable to instantiate the constructor for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new InitException("Unable to instantiate the constructor for " + clazz, e);
        } catch (InvocationTargetException e) {
            throw new InitException("Unable to instantiate the constructor for " + clazz, e);
        }
        result.init(runState, timeController);
        result.changeBarrier(barrier);
        return result;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isRunning() {
        return running;
    }

    public void schedule(SynchronizedRunnable runnable) {
        newTasksToSchedule.add(runnable);
    }

    /**
     * Execute one step of the synchronizer.
     * 
     * @throws IllegalStateException if the synchronizer is already running
     */
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
    public synchronized void start() {
        isStopAsked = false;
        internalStart();
    }

    private void internalStart() {
        if (running == true) {
            throw new IllegalStateException("The synchronizer is already running");
        }
        running = true;

        // wakeup period (speed = cycles / second)
        long period = CYCLE_LENGTH_MS / speed;
        temporizerFuture = scheduledThreadPool.scheduleWithFixedDelay(temporizer, 0, period, TimeUnit.MILLISECONDS);

        for (Runnable runnable : runnables) {
            standardThreadPool.submit(runnable);
        }
    }

    /**
     * Stop the synchronizer.
     * <p>
     * The running processes are asked to stop, once they have completed their current execution.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is not running
     */
    public synchronized void stop() {
        isStopAsked = true;
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
    }

    @Override
    public SynchronizerState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setSpeed(speed);
        List<SynchronizedRunnableState> synchroRunnableStates = new ArrayList<SynchronizedRunnableState>();
        List<UnfrequentRunnableState> unfrequentRunnableStates = new ArrayList<UnfrequentRunnableState>();
        for (SynchronizedRunnable runnable : runnables) {
            if (runnable instanceof UnfrequentRunnable) {
                unfrequentRunnableStates.add((UnfrequentRunnableState) runnable.getState());
            } else {
                synchroRunnableStates.add((SynchronizedRunnableState) runnable.getState());
            }
        }
        state.setSynchronizedRunnables(synchroRunnableStates);
        state.setUnfrequentRunnables(unfrequentRunnableStates);
    }


    /**
     * This class is used to stop all running process if isStopAsked is set to true.
     * 
     */
    private class BarrierTask implements Runnable {

        @Override
        public synchronized void run() {
            int nbNewTasks = newTasksToSchedule.size();
            // if there are new tasks to schedule
            if (nbNewTasks > 0) {
                // 1. create the new barrier
                barrier = new CyclicBarrier(barrier.getParties() + nbNewTasks, new BarrierTask());
                // 2. add the new tasks to the list of executed tasks
                runnables.addAll(newTasksToSchedule);
                // 3. update the barrier for everyone
                for (SynchronizedRunnable runnable : runnables) {
                    runnable.changeBarrier(barrier);
                }
                // 4. also change the temporizer barrier
                temporizer.changeBarrier(barrier);
                // if not asked to stop, then start the new tasks. Else, no need to start the new tasks.
                if (!isStopAsked) {
                    while (!newTasksToSchedule.isEmpty()) {
                        standardThreadPool.submit(newTasksToSchedule.poll());
                    }
                }
            }
            if (isStopAsked) {
                internalStop();
            }
        }

    }

}
