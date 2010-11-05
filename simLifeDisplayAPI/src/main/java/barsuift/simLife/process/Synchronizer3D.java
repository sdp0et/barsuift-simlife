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

import barsuift.simLife.Persistent;
import barsuift.simLife.message.Subscriber;

/**
 * The synchronizer3D allows to run a list of given {@link SplitBoundedRunnable} at a constant rate.
 * <p>
 * The synchronizer is able to manage a {@code stepSize} parameter, which is reflected to its synchronized
 * {@code SplitBoundedRunnable}.
 * </p>
 */
public interface Synchronizer3D extends TaskSynchronizer<SplitBoundedRunnable>, Subscriber,
        Persistent<Synchronizer3DState> {

    /**
     * Sets the step size of the current 3D synchronizer.
     * 
     * @param stepSize the step size
     */
    public void setStepSize(int stepSize);

    /**
     * Returns the step size of the 3D synchronizer.
     * 
     * @return the step size of the synchronizer
     */
    public int getStepSize();

    /**
     * Adds the given runnable to the list of synchronized tasks.
     * 
     * @param runnable the runnable to synchronize
     */
    public void schedule(SplitBoundedRunnable runnable);

    /**
     * Remove the given runnable from the list of synchronized tasks.
     * 
     * @param runnable the runnable to desynchronize
     */
    public void unschedule(SplitBoundedRunnable runnable);

    /**
     * Set the barrier to use for synchronization purpose.
     * 
     * @param barrier the cyclic barrier used to synchronize the synchronizer with other synchronizers
     * @throws IllegalArgumentException if the given barrier is null
     * @throws IllegalStateException if a barrier has already been set
     */
    public void setBarrier(CyclicBarrier barrier);

}