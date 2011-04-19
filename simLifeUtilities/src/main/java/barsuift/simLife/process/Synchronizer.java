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

import barsuift.simLife.message.Publisher;

/**
 * A synchronizer is used to synchronized elements. It can basically be started, stopped and asked about its current
 * running status.
 * <p>
 * The specific synchronized mechanism depends on the implementing classes.
 * </p>
 */
public interface Synchronizer extends Publisher {

    /**
     * Tests if the synchronizer is running.
     * 
     * @return true if the synchronizer is running, false otherwise
     */
    public boolean isRunning();

    /**
     * Start the synchronizer.
     * <p>
     * The synchronized elements are started.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is already running
     */
    public void start();

    /**
     * Number of times the {@link #start()} method has been called.
     */
    public long getNbStarts();

    /**
     * Stop the synchronizer.
     * <p>
     * The synchronized elements are asked to stop, once they have completed their current execution.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is not running
     */
    public void stop();

    /**
     * Number of times the {@link #stop()} method has been called.
     */
    public long getNbStops();

}