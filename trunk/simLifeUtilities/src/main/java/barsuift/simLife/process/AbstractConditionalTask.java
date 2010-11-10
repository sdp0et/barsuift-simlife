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

import barsuift.simLife.condition.BoundCondition;
import barsuift.simLife.condition.CyclicCondition;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;


/**
 * A conditional task is a task with an execution condition and an ending condition.
 */
public abstract class AbstractConditionalTask extends AbstractSynchronizedTask implements ConditionalTask {

    private final ConditionalTaskState state;

    private final CyclicCondition executionCondition;

    private final BoundCondition endingCondition;

    private final Publisher publisher = new BasicPublisher(this);

    public AbstractConditionalTask(ConditionalTaskState state) {
        super();
        this.state = state;
        this.executionCondition = new CyclicCondition(state.getExecutionCondition());
        this.endingCondition = new BoundCondition(state.getEndingCondition());
    }

    @Override
    public final void executeStep() {
        if (endingCondition.evaluate()) {
            stop();
            setChanged();
            notifySubscribers();
        }
        if (executionCondition.evaluate()) {
            executeConditionalStep();
        }
    }

    @Override
    public abstract void executeConditionalStep();

    @Override
    public ConditionalTaskState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        executionCondition.synchronize();
        endingCondition.synchronize();
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
