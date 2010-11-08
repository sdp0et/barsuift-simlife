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

import barsuift.simLife.Persistent;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;


/**
 * This abstract class represents a split bounded task. As a bounded task, it automatically stops after a number of run.
 * Additionally, the task is split in increments. The {@code stepSize} parameter allow to run more than one increment in
 * a row. Note that executing more than one increment in a row does NOT mean executing them one after the other, but to
 * execute the whole increment range in one action.
 * <p>
 * The bounded task notifies its subscribers when it reaches its bound and stops.
 * </p>
 */
// TODO 002. use condition
public abstract class SplitBoundedTask extends AbstractSynchronizedTask implements Publisher,
        Persistent<SplitBoundedTaskState> {

    private final SplitBoundedTaskState state;

    private final int bound;

    private int count;

    private int stepSize;

    private final Publisher publisher = new BasicPublisher(this);

    public SplitBoundedTask(SplitBoundedTaskState state) {
        super();
        this.state = state;
        this.bound = state.getBound();
        this.count = state.getCount();
        this.stepSize = state.getStepSize();
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public final void executeStep() {
        count += stepSize;
        if (count >= bound) {
            stop();
            setChanged();
            notifySubscribers();
        }
        executeSplitBoundedStep(stepSize);
    }

    @Override
    public SplitBoundedTaskState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setCount(count);
        state.setBound(bound);
        state.setStepSize(stepSize);
    }

    public abstract void executeSplitBoundedStep(int stepSize);

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
