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
package barsuift.simLife.time;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import barsuift.simLife.InitException;
import barsuift.simLife.Persistent;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.process.Synchronizer;
import barsuift.simLife.universe.Universe;

/**
 * Controller for time control on the universe.
 * 
 */
public class BasicTimeController implements Persistent<TimeControllerState>, TimeController {

    private final TimeControllerState state;

    private final SimLifeDate date;

    private final Synchronizer synchronizer;

    private final ScheduledExecutorService scheduledThreadPool;

    private ScheduledFuture<?> runningProcess;

    private final TimeMessenger timeMessenger;

    private boolean running;

    private final Publisher publisher = new BasicPublisher(this);

    public BasicTimeController(Universe universe, TimeControllerState state) throws InitException {
        super();
        this.state = state;
        this.date = new SimLifeDate(state.getDate());
        int poolSize = 1;
        this.scheduledThreadPool = Executors.newScheduledThreadPool(poolSize);
        this.timeMessenger = new TimeMessenger(universe);
        this.running = false;
        this.synchronizer = new Synchronizer(state.getSynchronizer(), this);
    }

    @Override
    public SimLifeDate getDate() {
        return date;
    }

    @Override
    public synchronized void setSpeed(int speed) {
        synchronizer.setSpeed(speed);
    }

    @Override
    public int getSpeed() {
        return synchronizer.getSpeed();
    }

    @Override
    public synchronized void start() throws IllegalStateException {
        if (running == true) {
            throw new IllegalStateException("The controller is already running");
        }
        running = true;
        // start immediately
        int initialDelay = 0;
        // wakeup period (speed = cycles / second)
        long period = 1000 / getSpeed();
        runningProcess = scheduledThreadPool.scheduleAtFixedRate(timeMessenger, initialDelay, period,
                TimeUnit.MILLISECONDS);
        synchronizer.start();
        while (!synchronizer.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                return;
            }
        }
        setChanged();
        notifySubscribers();
    }

    @Override
    public synchronized void oneStep() {
        if (running == true) {
            throw new IllegalStateException("The controller is already running");
        }
        timeMessenger.run();
        synchronizer.oneStep();
        setChanged();
        notifySubscribers();
    }

    @Override
    public synchronized void stop() {
        if (running == false) {
            throw new IllegalStateException("The controller is not running");
        }
        running = false;
        runningProcess.cancel(false);
        synchronizer.stop();
        while (synchronizer.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                return;
            }
        }
        setChanged();
        notifySubscribers();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public TimeControllerState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        date.synchronize();
        synchronizer.synchronize();
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

}
