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

/**
 * The core synchronizer allows to run a list of given {@link SynchronizedTask} at a given rate.
 * 
 */
public interface SynchronizerCore extends TaskSynchronizer<SynchronizedTask>, Persistent<SynchronizerCoreState> {

    /**
     * Sets the speed of the current core synchronizer.
     * 
     * @param speed the speed
     */
    public void setSpeed(Speed speed);

    /**
     * Returns the speed of the core synchronizer.
     * 
     * @return the speed of the synchronizer
     */
    public Speed getSpeed();

    /**
     * Adds the given task to the list of synchronized tasks.
     * 
     * @param task the task to synchronize
     */
    public void schedule(SynchronizedTask task);

    /**
     * Remove the given task from the list of synchronized tasks.
     * 
     * @param task the task to desynchronize
     */
    public void unschedule(SynchronizedTask task);

    /**
     * Set the barrier to use for synchronization purpose.
     * 
     * @param barrier the cyclic barrier used to synchronize the synchronizer with other synchronizers
     * @throws IllegalArgumentException if the given barrier is null
     * @throws IllegalStateException if a barrier has already been set
     */
    public void setBarrier(CyclicBarrier barrier);

}