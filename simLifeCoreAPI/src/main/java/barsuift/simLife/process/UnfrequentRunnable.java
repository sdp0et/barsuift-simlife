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

import barsuift.simLife.time.TimeController;


/**
 * This abstract class represents a task that does no need to run each time it is called.
 * <p>
 * It has a <code>delay</code> parameter which defines the number of call required before the task is actually executed.
 * If <code>delay=n</code>, the task is executed exactly on the n<sup>th</sup> call, and n call after that, and so on.
 * </p>
 */
public abstract class UnfrequentRunnable extends AbstractSynchronizedRunnable {

    private UnfrequentRunnableState state;

    private int delay;

    private int count;

    @Override
    public void init(SynchronizedRunnableState state, CyclicBarrier barrier, TimeController timeController) {
        super.init(state, barrier, timeController);
        UnfrequentRunnableState unfrequentState = (UnfrequentRunnableState) state;
        this.state = unfrequentState;
        this.delay = unfrequentState.getDelay();
        this.count = unfrequentState.getCount();
    }

    @Override
    public final void executeStep() {
        count++;
        if (count % delay == 0) {
            executeUnfrequentStep();
            count = 0;
        }
    }

    @Override
    public UnfrequentRunnableState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setCount(count);
        state.setDelay(delay);
    }

    public abstract void executeUnfrequentStep();

}
