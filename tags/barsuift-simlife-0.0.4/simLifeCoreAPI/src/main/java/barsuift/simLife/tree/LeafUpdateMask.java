package barsuift.simLife.tree;

/**
 * Mask to determine what has been updated about a leaf
 */
public class LeafUpdateMask {

    private static final int FALL = 1;

    private static final int AGE = 2;

    private static final int ENERGY = 3;

    private static final int EFFICIENCY = 4;


    public static final int FALL_MASK = 1 << FALL;

    public static final int AGE_MASK = 1 << AGE;

    public static final int ENERGY_MASK = 1 << ENERGY;

    public static final int EFFICIENCY_MASK = 1 << EFFICIENCY;

    public static boolean isFieldSet(int globalMask, int fieldMask) {
        return (globalMask & fieldMask) != 0;
    }

}