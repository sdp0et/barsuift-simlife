package barsuift.simLife;

import junit.framework.TestCase;


public class MathHelperTest extends TestCase {

    public void testIsPowerOfTwo() {
        assertTrue(MathHelper.isPowerOfTwo(1));
        assertTrue(MathHelper.isPowerOfTwo(2));
        assertTrue(MathHelper.isPowerOfTwo(4));
        assertTrue(MathHelper.isPowerOfTwo(4));
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

}
