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
 * This class represents a task that can be synchronized with others.
 */
public interface SynchronizedTask extends Runnable {

    /**
     * Change the barrier to use for synchronization purpose.
     * <p>
     * This method can be used even for a running task.
     * </p>
     * 
     * @param barrier the new cyclic barrier used to synchronize the task
     * @throws IllegalArgumentException if the given barrier is null
     */
    public void changeBarrier(CyclicBarrier barrier);

    /**
     * Start the process.
     * 
     * @throws IllegalStateException if the process is already running, or if the barrier is not set
     */
    public void run();

    /**
     * Stop the process.
     * 
     * @throws IllegalStateException if the process is not running.
     */
    public void stop();

    /**
     * Returns true if the process is running.
     * 
     * @return true if the process is running, false otherwise.
     */
    public boolean isRunning();

}