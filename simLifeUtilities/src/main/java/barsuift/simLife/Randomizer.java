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
package barsuift.simLife;

public final class Randomizer {

    private Randomizer() {
        // private constructor to enforce static access
    }

    /**
     * 
     * @return between -0.1 and 0.1
     */
    public static float random1() {
        float result = (float) Math.random();
        result -= 0.5;
        result /= 5;
        return result;
    }

    /**
     * 
     * @return between -0.5 and 0.5
     */
    public static float random2() {
        float result = (float) Math.random();
        result -= 0.5;
        return result;
    }

    /**
     * 
     * @return between -1 and 1
     */
    public static float random3() {
        float result = (float) Math.random();
        result -= 0.5;
        result *= 2;
        return result;
    }

    /**
     * 
     * @return between 0 and 0.1
     */
    public static float random4() {
        float result = (float) Math.random();
        result /= 10;
        return result;
    }

    /**
     * Returns a random number in the range [min; max[
     * 
     * @return random int between min and max
     * @throws IllegalArgumentException if min > max
     */
    public static int randomBetween(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min (" + min + ") > max (" + max + ")");
        }
        return (int) Math.round(Math.random() * (max - min)) + min;
    }

    /**
     * Returns a random number in the range [min; max[
     * 
     * @return random float between min and max
     * @throws IllegalArgumentException if min > max
     */
    public static float randomBetween(float min, float max) {
        if (min > max) {
            throw new IllegalArgumentException("min (" + min + ") > max (" + max + ")");
        }
        return (float) (Math.random() * (max - min)) + min;
    }

    /**
     * Random angle for rotation
     * 
     * @return a random number in the range [0; 2 PI[
     */
    public static double randomRotation() {
        return (Math.random() * Math.PI * 2);
    }

}
