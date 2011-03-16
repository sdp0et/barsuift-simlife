package barsuift.simLife;


public final class MathHelper {

    private MathHelper() {
        // private constructor to enforce static access
    }

    /**
     * Returns true if the number is a power of 2 (1, 2, 4, 8, 16, ...). It must also be positive, and greater than 0.
     * 
     * @param number the number to test
     * @return true if the number is a power of two
     */
    public static boolean isPowerOfTwo(final int number) {
        /*
         * A good implementation found on the Java boards.
         * 
         * A number is a power of two if and only if it is the smallest number with that number of significant bits.
         * Therefore, if you subtract 1, you know that the new number will have fewer bits, so ANDing the original
         * number with anything less than it will give 0.
         */
        return (number > 0) && (number & (number - 1)) == 0;
    }

    /**
     * Returns the exponent of the given number.
     * 
     * Considering this number is of the form 2<sup>n</sup>, this method returns {@code n}.
     * 
     * @param number the number
     * @return the exponent if the power of 2 in the given number
     * @throws IllegalArgumentException if the given number is not a power of 2
     */
    public static int getPowerOfTwoExponent(final int number) {
        if (!isPowerOfTwo(number)) {
            throw new IllegalArgumentException("The given number " + number + " is not a power of 2");
        }
        int mask = 0x0001;
        int result = 0;
        while ((number & mask) == 0) {
            result++;
            mask = mask << 1;
        }
        return result;
    }

    /**
     * Transforms the given angle from degree to radian
     * 
     * @param degree the angle in degree
     * @return the angle in radian
     */
    public static double toRadian(double degree) {
        return degree * 2 * Math.PI / 360;
    }

    /**
     * Transforms the given angle from radian to degree
     * 
     * @param radian the angle in radian
     * @return the angle in degree
     */
    public static double toDegree(double radian) {
        return radian * 360 / 2 / Math.PI;
    }

}
