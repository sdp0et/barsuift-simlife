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
// FIXME the count should be given by state, not set to 0 (or some process will never execute)
public abstract class UnfrequentRunnable extends SynchronizedRunnable {

    private final int delay;

    private int count;

    public UnfrequentRunnable(CyclicBarrier barrier, int delay) {
        super(barrier);
        this.delay = delay;
        this.count = 0;
    }

    @Override
    public final void executeStep() {
        count++;
        if (count % delay == 0) {
            executeUnfrequentStep();
            count = 0;
        }
    }

    public abstract void executeUnfrequentStep();

}
