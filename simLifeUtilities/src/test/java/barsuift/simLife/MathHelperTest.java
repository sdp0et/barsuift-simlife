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

}
