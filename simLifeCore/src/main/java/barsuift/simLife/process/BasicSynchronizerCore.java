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
import java.util.concurrent.ScheduledExecutorService;

/**
 * The synchronizer allows to run the list of given {@link SynchronizedRunnable} at a given rate. A
 * {@link CyclicBarrier} is used to synchronized all the tasks, and a {@link Temporizer}, in a
 * {@link ScheduledExecutorService}, is used to ensure there is always the same delay between two runs.
 * 
 */
public class BasicSynchronizerCore extends AbstractSynchronizer<SynchronizedRunnable> implements SynchronizerCore {

    private final SynchronizerCoreState state;

    private Speed speed;

    public BasicSynchronizerCore(SynchronizerCoreState state) {
        super();
        this.state = state;
        this.speed = state.getSpeed();
    }

    @Override
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public Speed getSpeed() {
        return speed;
    }

    @Override
    protected int getTemporizerPeriod() {
        return CYCLE_LENGTH_CORE_MS / speed.getSpeed();
    }

    @Override
    public SynchronizerCoreState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setSpeed(speed);
    }

    @Override
    protected Runnable createBarrierTask() {
        return new BarrierTask();
    }

    /**
     * This class is used to stop all running process if isStopAsked is set to true.
     * 
     */
    private class BarrierTask implements Runnable {

        @Override
        public synchronized void run() {
            updateTaskList(true);
            synchronizeWithOthers();
            if (isStopAsked()) {
                internalStop();
            }
        }

    }

}
