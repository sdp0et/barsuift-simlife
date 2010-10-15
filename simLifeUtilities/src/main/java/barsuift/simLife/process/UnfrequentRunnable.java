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

/**
 * This abstract class represents a task that does no need to run each time it is called.
 * <p>
 * It has a <code>delay</code> parameter which defines the number of call required before the task is actually executed.
 * If <code>delay=n</code>, the task is executed exactly on the n<sup>th</sup> call, and n call after that, and so on.
 * </p>
 */
// FIXME we should not be able to save the application wile it is running
public abstract class UnfrequentRunnable extends AbstractSynchronizedRunnable {

    private final UnfrequentRunnableState state;

    private final int delay;

    private int count;

    public UnfrequentRunnable(CyclicBarrier barrier, UnfrequentRunnableState state) {
        super(barrier, state);
        this.state = state;
        this.delay = state.getDelay();
        this.count = state.getCount();
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
