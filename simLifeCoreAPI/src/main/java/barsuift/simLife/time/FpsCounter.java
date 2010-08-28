/**
 * barsuift-simlife is a life simulator programm
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
package barsuift.simLife.time;

// TODO 001 unit test
public class FpsCounter {

    private static final int ITERATION_BEFORE_AVERAGE = 10;

    private static final int ITERATION_BEFORE_COMPUTING = 10;

    private static final long NANO_TO_MILLI = 1000000;

    private int computingCounter;

    private int avgCounter;

    private double previousTick;

    private double currentTick;

    private double lastExecTime;

    private double avgExecTime;

    private Double[] lastExecTimes;

    public FpsCounter() {
        computingCounter = 0;
        avgCounter = 0;
        previousTick = System.nanoTime();
        currentTick = previousTick + NANO_TO_MILLI;
        lastExecTime = 1000;
        avgExecTime = 1000;
        lastExecTimes = new Double[ITERATION_BEFORE_AVERAGE];
    }

    public void tick() {
        computingCounter++;
        if (computingCounter == ITERATION_BEFORE_COMPUTING) {
            computingCounter = 0;
            previousTick = currentTick;
            currentTick = System.nanoTime();
            lastExecTime = (currentTick - previousTick) / NANO_TO_MILLI;
            lastExecTime /= ITERATION_BEFORE_COMPUTING;
            lastExecTimes[avgCounter] = lastExecTime;
            avgCounter++;
            if (avgCounter == ITERATION_BEFORE_AVERAGE) {
                avgCounter = 0;
                computeAvgExecTime();
            }
        }
    }

    private void computeAvgExecTime() {
        double result = 0;
        for (Double historicExecTime : lastExecTimes) {
            result += historicExecTime;
        }
        avgExecTime = result / ITERATION_BEFORE_AVERAGE;
    }

    /**
     * Get the execution time in milliseconds
     * 
     * @return the execution time in milliseconds
     */
    public double getExecTime() {
        return lastExecTime;
    }

    /**
     * Get the current estimated fps, based on the current execution time.
     * 
     * @return the current FPS
     */
    public int getFps() {
        return (int) Math.round(1000. / lastExecTime);
    }

    /**
     * Get the average execution time in milliseconds
     * 
     * @return the average execution time in milliseconds
     */
    public double getAvgExecTime() {
        return avgExecTime;
    }

    /**
     * Get the average estimated fps, based on the average execution time.
     * 
     * @return the average FPS
     */
    public int getAvgFps() {
        return (int) Math.round(1000. / avgExecTime);
    }

}
