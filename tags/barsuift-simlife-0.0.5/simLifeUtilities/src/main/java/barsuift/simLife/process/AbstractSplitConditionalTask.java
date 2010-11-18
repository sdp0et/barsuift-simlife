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
import barsuift.simLife.condition.SplitBoundCondition;
import barsuift.simLife.condition.SplitCyclicCondition;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;



/**
 * This abstract class represents a split task. It is split in increments. The {@code stepSize} parameter allow to run
 * more than one increment in a row. Note that executing more than one increment in a row does NOT mean executing them
 * one after the other, but to execute the whole increment range in one action.
 */
/**
 * A abstract conditional task with an execution condition and an ending condition.
 * <p>
 * The task notifies its subscribers when it reaches its bound and stops.
 * </p>
 */
public abstract class AbstractSplitConditionalTask extends AbstractSynchronizedTask implements SplitConditionalTask,
        Persistent<SplitConditionalTaskState> {

    private final SplitConditionalTaskState state;

    private int stepSize;

    private final SplitCyclicCondition executionCondition;

    private final SplitBoundCondition endingCondition;

    private final Publisher publisher = new BasicPublisher(this);

    public AbstractSplitConditionalTask(SplitConditionalTaskState state) {
        super();
        this.state = state;
        this.stepSize = state.getStepSize();
        this.executionCondition = new SplitCyclicCondition(state.getConditionalTask().getExecutionCondition());
        this.endingCondition = new SplitBoundCondition(state.getConditionalTask().getEndingCondition());
    }

    @Override
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public final void executeStep() {
        if (endingCondition.evaluate(stepSize)) {
            stop();
            setChanged();
            notifySubscribers();
        }
        if (executionCondition.evaluate(stepSize)) {
            executeConditionalStep();
        }
    }

    public final void executeConditionalStep() {
        executeSplitConditionalStep(stepSize);
    }

    @Override
    public abstract void executeSplitConditionalStep(int stepSize);

    @Override
    public SplitConditionalTaskState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setStepSize(stepSize);
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
