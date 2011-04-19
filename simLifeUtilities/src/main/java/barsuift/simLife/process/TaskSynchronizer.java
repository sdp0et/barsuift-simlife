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

import barsuift.simLife.message.Subscriber;

/**
 * A task synchronizer is a synchronizer able to schedule or unschedule tasks.
 * 
 * @param <E> the sub-type of ConditionalTask to use
 */
public interface TaskSynchronizer<E extends ConditionalTask> extends Synchronizer, Subscriber {

    /**
     * Adds the given task to the list of synchronized tasks.
     * 
     * @param task the task to synchronize
     */
    public void schedule(E task);

    /**
     * Remove the given task from the list of synchronized tasks.
     * 
     * @param task the task to desynchronize
     */
    public void unschedule(E task);

}
