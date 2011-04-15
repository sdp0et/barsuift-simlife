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

import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

public class BasicMainSynchronizer implements MainSynchronizer, Subscriber {

    private final MainSynchronizerState state;

    private boolean isStopAsked;

    private long nbStarts;

    private long nbStops;


    private CyclicBarrier barrier;

    private final SynchronizerSlow synchroSlow;

    private final SynchronizerFast synchroFast;

    private final Publisher publisher = new BasicPublisher(this);

    private boolean notifiedFromSlow;

    private boolean notifiedFromFast;


    public BasicMainSynchronizer(MainSynchronizerState state) {
        this.state = state;
        this.isStopAsked = false;
        this.barrier = new CyclicBarrier(2, new BarrierTask());

        this.synchroSlow = new BasicSynchronizerSlow(state.getSynchronizerSlowState());
        this.synchroSlow.addSubscriber(this);
        this.synchroSlow.setBarrier(barrier);

        this.synchroFast = new BasicSynchronizerFast(state.getSynchronizerFastState());
        this.synchroFast.addSubscriber(this);
        this.synchroFast.setBarrier(barrier);
    }

    @Override
    public void scheduleFast(SplitConditionalTask task) {
        synchroFast.schedule(task);
    }

    @Override
    public void unscheduleFast(SplitConditionalTask task) {
        synchroFast.unschedule(task);
    }

    @Override
    public void scheduleSlow(ConditionalTask task) {
        synchroSlow.schedule(task);
    }

    @Override
    public void unscheduleSlow(ConditionalTask task) {
        synchroSlow.unschedule(task);
    }

    @Override
    public void setSpeed(Speed speed) {
        synchroSlow.setSpeed(speed);
        synchroFast.setStepSize(speed.getSpeed());
    }

    @Override
    public Speed getSpeed() {
        return synchroSlow.getSpeed();
    }

    /**
     * Returns true if the slow synchronizer or fast synchronizer is running.
     */
    @Override
    public boolean isRunning() {
        return synchroSlow.isRunning() || synchroFast.isRunning();
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

    @Override
    public final long getNbStarts() {
        return nbStarts;
    }

    private void internalStart() {
        nbStarts++;
        if (isRunning() == true) {
            throw new IllegalStateException("The synchronizer is already running");
        }
        synchroSlow.start();
        synchroFast.start();
    }

    @Override
    public synchronized void stop() {
        if (isRunning() == false) {
            throw new IllegalStateException("The synchronizer is not running");
        }
        isStopAsked = true;
    }

    @Override
    public final long getNbStops() {
        return nbStops;
    }

    private void internalStop() {
        nbStops++;
        if (isRunning() == false) {
            throw new IllegalStateException("The synchronizer is not running");
        }
        synchroSlow.stop();
        synchroFast.stop();
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

    public SynchronizerSlow getSynchronizerSlow() {
        return synchroSlow;
    }

    public SynchronizerFast getSynchronizerFast() {
        return synchroFast;
    }

    @Override
    public MainSynchronizerState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        synchroFast.synchronize();
        synchroSlow.synchronize();
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (publisher instanceof BasicSynchronizerFast) {
            notifiedFromFast = true;
        }
        if (publisher instanceof BasicSynchronizerSlow) {
            notifiedFromSlow = true;
        }
        if (notifiedFromFast && notifiedFromSlow) {
            notifiedFromFast = false;
            notifiedFromSlow = false;
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
