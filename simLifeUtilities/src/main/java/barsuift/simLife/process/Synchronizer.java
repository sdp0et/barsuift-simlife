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
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The synchronizer allows to run the list of given {@link SynchronizedRunnable} at a given rate. A
 * {@link CyclicBarrier} is used to synchronized all the tasks, and a {@link Temporizer}, in a
 * {@link ScheduledExecutorService}, is used to ensure there is always the same delay between two runs.
 * 
 */
public class Synchronizer {

    private boolean running;

    private boolean isStopAsked;

    private int speed;


    private final ScheduledExecutorService scheduledThreadPool;

    private final Runnable temporizer;

    private ScheduledFuture<?> temporizerFuture;


    private final ExecutorService standardThreadPool;

    private final List<SynchronizedRunnable> runnables = new ArrayList<SynchronizedRunnable>();


    public Synchronizer(List<Class<? extends SynchronizedRunnable>> classes) throws SynchronizerException {
        this.running = false;
        this.isStopAsked = false;
        this.speed = 1;

        int nbScheduledThread = 1;
        int nbStandardThread = classes.size();
        int totalNbThread = nbScheduledThread + nbStandardThread;

        BarrierTask barrierTask = new BarrierTask();
        CyclicBarrier barrier = new CyclicBarrier(totalNbThread, barrierTask);

        temporizer = new Temporizer(barrier);
        scheduledThreadPool = Executors.newScheduledThreadPool(nbScheduledThread);

        for (Class<? extends SynchronizedRunnable> clazz : classes) {
            runnables.add(instantiateClass(clazz, barrier));
        }

        standardThreadPool = Executors.newFixedThreadPool(nbStandardThread);
    }

    private SynchronizedRunnable instantiateClass(Class<? extends SynchronizedRunnable> clazz, CyclicBarrier barrier)
            throws SynchronizerException {
        Constructor<? extends SynchronizedRunnable> runnableConstructor;
        try {
            runnableConstructor = clazz.getConstructor(CyclicBarrier.class);
        } catch (SecurityException e) {
            throw new SynchronizerException("Unable to get the appropriate constructor for " + clazz, e);
        } catch (NoSuchMethodException e) {
            throw new SynchronizerException("Unable to get the appropriate constructor for " + clazz, e);
        }
        try {
            return runnableConstructor.newInstance(barrier);
        } catch (IllegalArgumentException e) {
            throw new SynchronizerException("Unable to instantiate the constructor for " + clazz, e);
        } catch (InstantiationException e) {
            throw new SynchronizerException("Unable to instantiate the constructor for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new SynchronizerException("Unable to instantiate the constructor for " + clazz, e);
        } catch (InvocationTargetException e) {
            throw new SynchronizerException("Unable to instantiate the constructor for " + clazz, e);
        }
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
        long period = 100 / speed;
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

    /**
     * This class is used to stop all running process if isStopAsked is set to true.
     * 
     */
    private class BarrierTask implements Runnable {

        @Override
        public synchronized void run() {
            if (isStopAsked) {
                internalStop();
            }
        }

    }

}
