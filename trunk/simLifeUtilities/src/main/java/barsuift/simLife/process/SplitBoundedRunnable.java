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


/**
 * This abstract class represents a split bounded task. As a bounded task, it automatically stops after a number of run.
 * Additionally, the task is split in increments. The {@code stepSize} parameter allow to run more than one increment in
 * a row. Note that executing more than one increment in a row does NOT mean exectuing them one after the other, but to
 * execute the whole increment range in one action.
 */
public abstract class SplitBoundedRunnable extends AbstractSynchronizedRunnable implements
        Persistent<SplitBoundedRunnableState> {

    private final SplitBoundedRunnableState state;

    private final int bound;

    private int count;

    private int stepSize;

    public SplitBoundedRunnable(SplitBoundedRunnableState state) {
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
        }
        executeSplitBoundedStep(stepSize);
    }

    @Override
    public SplitBoundedRunnableState getState() {
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

}
