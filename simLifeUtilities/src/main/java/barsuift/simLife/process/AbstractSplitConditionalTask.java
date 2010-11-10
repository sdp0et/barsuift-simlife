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
 * This abstract class represents a split task. It is split in increments. The {@code stepSize} parameter allow to run
 * more than one increment in a row. Note that executing more than one increment in a row does NOT mean executing them
 * one after the other, but to execute the whole increment range in one action.
 */
// TODO unit test
// FIXME there should be only one SplitTask (which extends conditionalTask !!
public abstract class AbstractSplitConditionalTask extends AbstractSynchronizedTask implements SplitConditionalTask,
        Persistent<SplitConditionalTaskState> {

    private final SplitConditionalTaskState state;

    private int stepSize;

    public AbstractSplitConditionalTask(SplitConditionalTaskState state) {
        super();
        this.state = state;
        this.stepSize = state.getStepSize();
    }

    @Override
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    @Override
    public final void executeStep() {
        executeSplitStep(stepSize);
    }

    @Override
    public abstract void executeSplitStep(int stepSize);

    @Override
    public SplitConditionalTaskState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setStepSize(stepSize);
    }

}
