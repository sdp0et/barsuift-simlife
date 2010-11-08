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
 * This synchronizer is able to work by steps of increments.
 * <p>
 * It manages {@link SplitBoundedTask} and remove them from the list of scheduled tasks when they have reach their
 * bound.
 * </p>
 */
public class BasicSynchronizer3D extends AbstractTaskSynchronizer<SplitBoundedTask> implements Synchronizer3D {

    private static final int RATIO_CORE_3D = Synchronizer.CYCLE_LENGTH_CORE_MS / Synchronizer.CYCLE_LENGTH_3D_MS;

    private final Synchronizer3DState state;

    private int stepSize;

    private int stepBeforeSynchro;

    private int currentStep;


    public BasicSynchronizer3D(Synchronizer3DState state) {
        super();
        this.state = state;
        this.stepSize = state.getStepSize();
        this.stepBeforeSynchro = RATIO_CORE_3D / stepSize;
        this.currentStep = 0;
    }

    @Override
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
        stepBeforeSynchro = RATIO_CORE_3D / stepSize;
        for (SplitBoundedTask task : getTasks()) {
            task.setStepSize(stepSize);
        }
    }

    @Override
    public int getStepSize() {
        return stepSize;
    }

    @Override
    protected int getTemporizerPeriod() {
        return CYCLE_LENGTH_3D_MS;
    }

    @Override
    public void schedule(SplitBoundedTask task) {
        super.schedule(task);
        task.addSubscriber(this);
    }

    @Override
    public void unschedule(SplitBoundedTask task) {
        super.unschedule(task);
        task.deleteSubscriber(this);
    }

    @Override
    public Synchronizer3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setStepSize(stepSize);
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        unschedule((SplitBoundedTask) publisher);
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
            currentStep++;
            updateTaskList(true);
            if (currentStep == stepBeforeSynchro) {
                currentStep = 0;
                synchronizeWithOthers();
                if (isStopAsked()) {
                    internalStop();
                }
            }
        }

    }

}
