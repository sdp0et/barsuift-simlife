package barsuift.simLife;


public final class MathHelper {

    private MathHelper() {
        // private constructor to enforce static access
    }

    /**
     * Returns true if the number is a power of 2 (1, 2, 4, 8, 16, ...)
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

}
