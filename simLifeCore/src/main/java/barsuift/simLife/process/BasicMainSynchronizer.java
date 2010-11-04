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

    private final SynchronizerCore synchroCore;

    private final Synchronizer3D synchro3D;

    private final Publisher publisher = new BasicPublisher(this);

    private boolean notifiedFromCore;

    private boolean notifiedFrom3D;


    public BasicMainSynchronizer(MainSynchronizerState state, Universe universe) throws InitException {
        this.state = state;
        this.isStopAsked = false;
        this.barrier = new CyclicBarrier(2, new BarrierTask());

        this.synchroCore = universe.getSynchronizer();
        this.synchroCore.addSubscriber(this);
        this.synchroCore.setBarrier(barrier);

        this.synchro3D = universe.getUniverse3D().getSynchronizer();
        this.synchro3D.addSubscriber(this);
        this.synchro3D.setBarrier(barrier);
    }

    @Override
    public void setSpeed(Speed speed) {
        synchroCore.setSpeed(speed);
        synchro3D.setStepSize(speed.getSpeed());
    }

    @Override
    public Speed getSpeed() {
        return synchroCore.getSpeed();
    }

    /**
     * Returns true if the core synchronizer or the 3D synchronizer is running.
     */
    @Override
    public boolean isRunning() {
        return synchroCore.isRunning() || synchro3D.isRunning();
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
        synchroCore.start();
        synchro3D.start();
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
        synchroCore.stop();
        synchro3D.stop();
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
        // nothing to do
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (publisher instanceof BasicSynchronizer3D) {
            notifiedFrom3D = true;
        }
        if (publisher instanceof BasicSynchronizerCore) {
            notifiedFromCore = true;
        }
        if (notifiedFrom3D && notifiedFromCore) {
            notifiedFrom3D = false;
            notifiedFromCore = false;
            setChanged();
            notifySubscribers();
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
