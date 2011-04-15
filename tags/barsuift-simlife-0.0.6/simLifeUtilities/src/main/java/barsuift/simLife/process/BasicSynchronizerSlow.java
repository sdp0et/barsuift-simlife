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


/**
 * This synchronizer allows to change the speed of the synchronizer.
 * 
 */
public class BasicSynchronizerSlow extends AbstractTaskSynchronizer<ConditionalTask> implements SynchronizerSlow {

    /**
     * Length of a slow cycle, used to schedule the temporizerSlow.
     */
    public static final int CYCLE_LENGTH_SLOW_MS = 500;

    private final SynchronizerSlowState state;

    private Speed speed;

    public BasicSynchronizerSlow(SynchronizerSlowState state) {
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
        return CYCLE_LENGTH_SLOW_MS / speed.getSpeed();
    }

    @Override
    public SynchronizerSlowState getState() {
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
