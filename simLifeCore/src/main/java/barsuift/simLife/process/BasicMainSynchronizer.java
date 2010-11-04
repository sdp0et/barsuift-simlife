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

import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.InitException;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;
import barsuift.simLife.universe.Universe;

public class BasicMainSynchronizer implements MainSynchronizer, Subscriber {

    private final MainSynchronizerState state;

    private boolean isStopAsked;

    private CyclicBarrier barrier;

    private final SynchronizerCore coreSynchro;

    private final Publisher publisher = new BasicPublisher(this);


    public BasicMainSynchronizer(MainSynchronizerState state, Universe universe) throws InitException {
        this.state = state;
        this.isStopAsked = false;
        this.barrier = new CyclicBarrier(1, new BarrierTask());
        this.coreSynchro = universe.getSynchronizer();
        this.coreSynchro.addSubscriber(this);
        this.coreSynchro.setBarrier(barrier);
    }

    @Override
    public void setSpeed(Speed speed) {
        coreSynchro.setSpeed(speed);
    }

    @Override
    public Speed getSpeed() {
        return coreSynchro.getSpeed();
    }

    @Override
    public boolean isRunning() {
        return coreSynchro.isRunning();
    }

    @Override
    public synchronized void oneStep() {
        isStopAsked = true;
        internalStart();
    }

    @Override
    public synchronized void start() {
        isStopAsked = false;
        internalStart();
    }

    private void internalStart() {
        if (isRunning() == true) {
            throw new IllegalStateException("The synchronizer is already running");
        }
        coreSynchro.start();
    }

    @Override
    public synchronized void stop() {
        if (isRunning() == false) {
            throw new IllegalStateException("The synchronizer is not running");
        }
        isStopAsked = true;
    }

    private void internalStop() {
        if (isRunning() == false) {
            throw new IllegalStateException("The synchronizer is not running");
        }
        coreSynchro.stop();
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

    @Override
    public MainSynchronizerState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        coreSynchro.synchronize();
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        setChanged();
        notifySubscribers();
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
     * This class is used to stop the synchronizers if isStopAsked is set to true.
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
