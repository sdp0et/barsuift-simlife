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
    public static double random1() {
        double result = Math.random();
        result -= 0.5;
        result /= 5;
        return result;
    }

    /**
     * 
     * @return between -0.5 and 0.5
     */
    public static double random2() {
        double result = Math.random();
        result -= 0.5;
        return result;
    }

    /**
     * 
     * @return between -1 and 1
     */
    public static double random3() {
        double result = Math.random();
        result -= 0.5;
        result *= 2;
        return result;
    }

    /**
     * 
     * @return between 0 and 0.1
     */
    public static double random4() {
        double result = Math.random();
        result /= 10;
        return result;
    }

    /**
     * Returns a random number comprised between min and max (both inclusive)
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
     * Random angle for rotation
     * 
     * @return a random number between 0 and 2 PI
     */
    public static double randomRotation() {
        return Math.random() * Math.PI * 2;
    }

}
