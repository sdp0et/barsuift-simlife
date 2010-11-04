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
 * This abstract class represents a bounded task that automatically stops after a number of run.
 * <p>
 * It has a {@code bound} parameter which defines the number of call before it stops. If {@code bound=n}, the task is
 * executed exactly {@code n} times, and then stops.
 * </p>
 */
public abstract class BoundedRunnable extends AbstractSynchronizedRunnable implements Persistent<BoundedRunnableState> {

    private final BoundedRunnableState state;

    private final int bound;

    private int count;

    public BoundedRunnable(BoundedRunnableState state) {
        super();
        this.state = state;
        this.bound = state.getBound();
        this.count = state.getCount();
    }

    @Override
    public final void executeStep() {
        count++;
        if (count == bound) {
            stop();
        }
        executeBoundedStep();
    }

    @Override
    public BoundedRunnableState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setCount(count);
        state.setBound(bound);
    }

    public abstract void executeBoundedStep();

}
