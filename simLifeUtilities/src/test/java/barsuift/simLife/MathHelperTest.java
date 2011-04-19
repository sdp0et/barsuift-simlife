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


public class MathHelperTest extends TestCase {

    public void testIsPowerOfTwo() {
        assertTrue(MathHelper.isPowerOfTwo(1));
        assertTrue(MathHelper.isPowerOfTwo(2));
        assertTrue(MathHelper.isPowerOfTwo(4));
        assertTrue(MathHelper.isPowerOfTwo(8));
        assertTrue(MathHelper.isPowerOfTwo(16));
        assertTrue(MathHelper.isPowerOfTwo(32));

        assertFalse(MathHelper.isPowerOfTwo(0));
        assertFalse(MathHelper.isPowerOfTwo(-1));
        assertFalse(MathHelper.isPowerOfTwo(-2));
        assertFalse(MathHelper.isPowerOfTwo(-3));
        assertFalse(MathHelper.isPowerOfTwo(-4));
        assertFalse(MathHelper.isPowerOfTwo(3));
        assertFalse(MathHelper.isPowerOfTwo(5));
        assertFalse(MathHelper.isPowerOfTwo(6));
        assertFalse(MathHelper.isPowerOfTwo(7));
        assertFalse(MathHelper.isPowerOfTwo(9));
        assertFalse(MathHelper.isPowerOfTwo(10));
    }

    public void testGetPowerOfTwoExponent() {
        assertEquals(0, MathHelper.getPowerOfTwoExponent(1));
        assertEquals(1, MathHelper.getPowerOfTwoExponent(2));
        assertEquals(2, MathHelper.getPowerOfTwoExponent(4));
        assertEquals(3, MathHelper.getPowerOfTwoExponent(8));
        assertEquals(4, MathHelper.getPowerOfTwoExponent(16));
        assertEquals(5, MathHelper.getPowerOfTwoExponent(32));

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
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testToRadian() {
        double epsilon = 0.00001;
        assertEquals(0, MathHelper.toRadian(0), epsilon);
        assertEquals(Math.PI / 4, MathHelper.toRadian(45), epsilon);
        assertEquals(Math.PI / 2, MathHelper.toRadian(90), epsilon);
        assertEquals(Math.PI, MathHelper.toRadian(180), epsilon);
        assertEquals(2 * Math.PI, MathHelper.toRadian(360), epsilon);

        assertEquals(-Math.PI / 4, MathHelper.toRadian(-45), epsilon);
        assertEquals(-Math.PI / 2, MathHelper.toRadian(-90), epsilon);
        assertEquals(-Math.PI, MathHelper.toRadian(-180), epsilon);
        assertEquals(-2 * Math.PI, MathHelper.toRadian(-360), epsilon);
    }

    public void testToDegree() {
        double epsilon = 0.00001;
        assertEquals(0, MathHelper.toDegree(0), epsilon);
        assertEquals(45, MathHelper.toDegree(Math.PI / 4), epsilon);
        assertEquals(90, MathHelper.toDegree(Math.PI / 2), epsilon);
        assertEquals(180, MathHelper.toDegree(Math.PI), epsilon);
        assertEquals(360, MathHelper.toDegree(2 * Math.PI), epsilon);

        assertEquals(-45, MathHelper.toDegree(-Math.PI / 4), epsilon);
        assertEquals(-90, MathHelper.toDegree(-Math.PI / 2), epsilon);
        assertEquals(-180, MathHelper.toDegree(-Math.PI), epsilon);
        assertEquals(-360, MathHelper.toDegree(-2 * Math.PI), epsilon);
    }

}
