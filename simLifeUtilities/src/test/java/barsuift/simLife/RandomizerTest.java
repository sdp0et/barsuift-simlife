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

import junit.framework.TestCase;

public class RandomizerTest extends TestCase {

    public void testRandom1() {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.random1();
            assertTrue(result <= 0.1);
            assertTrue(result >= -0.1);
        }
    }

    public void testRandom2() {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.random2();
            assertTrue(result <= 0.5);
            assertTrue(result >= -0.5);
        }
    }

    public void testRandom3() {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.random3();
            assertTrue(result <= 1);
            assertTrue(result >= -1);
        }
    }

    public void testRandom4() {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.random4();
            assertTrue(result <= 0.1);
            assertTrue(result >= 0);
        }
    }

    public void testRandomBetweenInt() {
        internalTestRandomBetweenInt(5, 10);
        internalTestRandomBetweenInt(-10, -5);
        internalTestRandomBetweenInt(-5, 5);
        try {
            Randomizer.randomBetween(10, 5);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    private void internalTestRandomBetweenInt(int min, int max) {
        for (int index = 0; index < 1000; index++) {
            int result = Randomizer.randomBetween(min, max);
            assertTrue(result + " <= " + max, result <= max);
            assertTrue(result + " >= " + min, result >= min);
        }
    }

    public void testRandomBetweenFloat() {
        internalTestRandomBetweenFloat(0.05f, 0.10f);
        internalTestRandomBetweenFloat(-0.10f, -0.05f);
        internalTestRandomBetweenFloat(-1.5f, 1.5f);
        try {
            Randomizer.randomBetween(0.10f, 0.05f);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    private void internalTestRandomBetweenFloat(float min, float max) {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.randomBetween(min, max);
            assertTrue(result + " <= " + max, result <= max);
            assertTrue(result + " >= " + min, result >= min);
        }
    }

    public void testRandomRotation() {
        for (int index = 0; index < 1000; index++) {
            double result = Randomizer.randomRotation();
            assertTrue(result <= Math.PI * 2);
            assertTrue(result >= 0);
        }

    }

}
