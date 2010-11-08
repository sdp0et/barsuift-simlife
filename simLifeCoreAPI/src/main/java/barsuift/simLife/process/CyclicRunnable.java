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
import barsuift.simLife.condition.CyclicCondition;


/**
 * This abstract class represents a task that does no need to run each time it is called.
 * <p>
 * It has a <code>delay</code> parameter which defines the number of call required before the task is actually executed.
 * If <code>delay=n</code>, the task is executed exactly on the n<sup>th</sup> call, and n call after that, and so on.
 * </p>
 */
// TODO 002. create split unbounded condition
// TODO 002. create split cyclic condition
// TODO 001. refactor runnables to make them common
// TODO 000. merge with BoundedRunnable
// TODO 004. add a startCondition to SynchronizedRunnable : a task can wait a notification before it starts
// TODO 004. add a pauseCondition to SynchronizedRunnable : a task can pause and wait to restart later (with a timeout)
public abstract class CyclicRunnable extends AbstractSynchronizedRunnable implements
        Persistent<CyclicRunnableState> {

    private final CyclicRunnableState state;

    private final CyclicCondition executionCondition;

    public CyclicRunnable(CyclicRunnableState state) {
        super();
        this.state = state;
        this.executionCondition = new CyclicCondition(state.getExecutionCondition());
    }

    @Override
    public final void executeStep() {
        if (executionCondition.evaluate()) {
            executeCyclicStep();
        }
    }

    @Override
    public CyclicRunnableState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        executionCondition.synchronize();
    }

    public abstract void executeCyclicStep();

}
