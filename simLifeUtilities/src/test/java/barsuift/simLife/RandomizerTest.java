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

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class RandomizerTest {

    @Test
    public void random1() {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.random1();
            assertThat(result).isLessThanOrEqualTo(0.1f);
            assertThat(result).isGreaterThanOrEqualTo(-0.1f);
        }
    }

    @Test
    public void random2() {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.random2();
            assertThat(result).isLessThanOrEqualTo(0.5f);
            assertThat(result).isGreaterThanOrEqualTo(-0.5f);
        }
    }

    @Test
    public void random3() {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.random3();
            assertThat(result).isLessThanOrEqualTo(1f);
            assertThat(result).isGreaterThanOrEqualTo(-1f);
        }
    }

    @Test
    public void random4() {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.random4();
            assertThat(result).isLessThanOrEqualTo(0.1f);
            assertThat(result).isGreaterThanOrEqualTo(0f);
        }
    }

    @Test
    public void randomBetweenInt() {
        internalTest_randomBetweenInt(5, 10);
        internalTest_randomBetweenInt(-10, -5);
        internalTest_randomBetweenInt(-5, 5);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void randomBetweenInt_exception() {
        Randomizer.randomBetween(10, 5);
    }

    private void internalTest_randomBetweenInt(int min, int max) {
        for (int index = 0; index < 1000; index++) {
            int result = Randomizer.randomBetween(min, max);
            assertThat(result).isLessThanOrEqualTo(max);
            assertThat(result).isGreaterThanOrEqualTo(min);
        }
    }

    @Test
    public void randomBetweenFloat() {
        internalTest_randomBetweenFloat(0.05f, 0.10f);
        internalTest_randomBetweenFloat(-0.10f, -0.05f);
        internalTest_randomBetweenFloat(-1.5f, 1.5f);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void randomBetweenFloat_exception() {
        Randomizer.randomBetween(0.10f, 0.05f);
    }

    private void internalTest_randomBetweenFloat(float min, float max) {
        for (int index = 0; index < 1000; index++) {
            float result = Randomizer.randomBetween(min, max);
            assertThat(result).isLessThanOrEqualTo(max);
            assertThat(result).isGreaterThanOrEqualTo(min);
        }
    }

    @Test
    public void randomRotation() {
        for (int index = 0; index < 1000; index++) {
            double result = Randomizer.randomRotation();
            assertThat(result).isLessThanOrEqualTo(Math.PI * 2);
            assertThat(result).isGreaterThanOrEqualTo(0);
        }

    }

}
