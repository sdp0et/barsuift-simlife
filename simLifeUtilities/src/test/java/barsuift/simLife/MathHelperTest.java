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

import org.fest.assertions.Delta;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class MathHelperTest {

    @Test
    public void isPowerOfTwo() {
        assertThat(MathHelper.isPowerOfTwo(1)).isTrue();
        assertThat((MathHelper.isPowerOfTwo(2))).isTrue();
        assertThat((MathHelper.isPowerOfTwo(4))).isTrue();
        assertThat((MathHelper.isPowerOfTwo(8))).isTrue();
        assertThat((MathHelper.isPowerOfTwo(16))).isTrue();
        assertThat((MathHelper.isPowerOfTwo(32))).isTrue();

        assertThat((MathHelper.isPowerOfTwo(0))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(-1))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(-2))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(-3))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(-4))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(3))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(5))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(6))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(7))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(9))).isFalse();
        assertThat((MathHelper.isPowerOfTwo(10))).isFalse();
    }

    @Test
    public void testGetPowerOfTwoExponent() {
        assertThat( MathHelper.getPowerOfTwoExponent(1)).isEqualTo(0);
        assertThat( MathHelper.getPowerOfTwoExponent(2)).isEqualTo(1);
        assertThat( MathHelper.getPowerOfTwoExponent(4)).isEqualTo(2);
        assertThat( MathHelper.getPowerOfTwoExponent(8)).isEqualTo(3);
        assertThat( MathHelper.getPowerOfTwoExponent(16)).isEqualTo(4);
        assertThat( MathHelper.getPowerOfTwoExponent(32)).isEqualTo(5);

        internalTestGetPowerOfTwoExponentException(0);
        internalTestGetPowerOfTwoExponentException(-1);
        internalTestGetPowerOfTwoExponentException(-2);
        internalTestGetPowerOfTwoExponentException(-3);
        internalTestGetPowerOfTwoExponentException(-4);
        internalTestGetPowerOfTwoExponentException(3);
        internalTestGetPowerOfTwoExponentException(5);
        internalTestGetPowerOfTwoExponentException(6);
        internalTestGetPowerOfTwoExponentException(7);
        internalTestGetPowerOfTwoExponentException(9);
        internalTestGetPowerOfTwoExponentException(10);
    }

    private void internalTestGetPowerOfTwoExponentException(int number) {
        try {
            MathHelper.getPowerOfTwoExponent(number);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    @Test
    public void testToRadian() {
        Delta delta = Delta.delta(0.00001);
        assertThat(MathHelper.toRadian(0)).isEqualTo(0, delta);
        assertThat(MathHelper.toRadian(45)).isEqualTo((float) (Math.PI / 4), delta);
        assertThat(MathHelper.toRadian(90)).isEqualTo((float) (Math.PI / 2), delta);
        assertThat(MathHelper.toRadian(180)).isEqualTo((float) Math.PI, delta);
        assertThat(MathHelper.toRadian(360)).isEqualTo((float) (2 * Math.PI), delta);

        assertThat(MathHelper.toRadian(-45)).isEqualTo((float) (-Math.PI / 4), delta);
        assertThat(MathHelper.toRadian(-90)).isEqualTo((float) (-Math.PI / 2), delta);
        assertThat(MathHelper.toRadian(-180)).isEqualTo((float) -Math.PI, delta);
        assertThat(MathHelper.toRadian(-360)).isEqualTo((float) (-2 * Math.PI), delta);
    }

    @Test
    public void testToDegree() {
        Delta delta = Delta.delta(0.00001);
        assertThat(MathHelper.toDegree(0)).isEqualTo(0, delta);
        assertThat(MathHelper.toDegree(Math.PI / 4)).isEqualTo(45, delta);
        assertThat(MathHelper.toDegree(Math.PI / 2)).isEqualTo(90, delta);
        assertThat(MathHelper.toDegree(Math.PI)).isEqualTo(180, delta);
        assertThat(MathHelper.toDegree(2 * Math.PI)).isEqualTo(360, delta);

        assertThat(MathHelper.toDegree(-Math.PI / 4)).isEqualTo(-45, delta);
        assertThat(MathHelper.toDegree(-Math.PI / 2)).isEqualTo(-90, delta);
        assertThat(MathHelper.toDegree(-Math.PI)).isEqualTo(-180, delta);
        assertThat(MathHelper.toDegree(-2 * Math.PI)).isEqualTo(-360, delta);
    }

}
