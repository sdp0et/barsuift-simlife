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
package barsuift.simLife.time;

public class FpsCounter {

    private static final int COMPUTINGS_BEFORE_AVERAGE = 10;

    private static final int TICKS_BEFORE_COMPUTING = 10;

    private static final long NANO_TO_MILLI = 1000000;

    private short ticksCounter;

    private short computingsCounter;

    private long previousTick;

    private long currentTick;

    private float execTime;

    private int fps;

    private float avgExecTime;

    private int avgFps;

    private float[] lastExecTimes;

    public FpsCounter() {
        reset();
    }

    public void reset() {
        ticksCounter = 0;
        computingsCounter = 0;
        previousTick = System.nanoTime();
        currentTick = previousTick;
        execTime = 1;
        fps = 1;
        avgExecTime = 1;
        avgFps = 1;
        lastExecTimes = new float[COMPUTINGS_BEFORE_AVERAGE];
    }

    public void tick() {
        ticksCounter++;
        if (ticksCounter == TICKS_BEFORE_COMPUTING) {
            ticksCounter = 0;
            previousTick = currentTick;
            currentTick = System.nanoTime();
            execTime = (float) (currentTick - previousTick) / NANO_TO_MILLI;
            execTime /= TICKS_BEFORE_COMPUTING;
            lastExecTimes[computingsCounter] = execTime;
            fps = Math.round(1000f / execTime);
            computingsCounter++;
            if (computingsCounter == COMPUTINGS_BEFORE_AVERAGE) {
                computingsCounter = 0;
                computeAvgExecTime();
            }
        }
    }

    private void computeAvgExecTime() {
        float result = 0;
        for (float historicExecTime : lastExecTimes) {
            result += historicExecTime;
        }
        avgExecTime = result / COMPUTINGS_BEFORE_AVERAGE;
        avgFps = Math.round(1000 / avgExecTime);
    }

    /**
     * Get the execution time in milliseconds
     * 
     * @return the execution time in milliseconds
     */
    public float getExecTime() {
        return execTime;
    }

    /**
     * Get the current estimated fps, based on the current execution time.
     * 
     * @return the current FPS
     */
    public int getFps() {
        return fps;
    }

    /**
     * Get the average execution time in milliseconds
     * 
     * @return the average execution time in milliseconds
     */
    public float getAvgExecTime() {
        return avgExecTime;
    }

    /**
     * Get the average estimated fps, based on the average execution time.
     * 
     * @return the average FPS
     */
    public int getAvgFps() {
        return avgFps;
    }

}