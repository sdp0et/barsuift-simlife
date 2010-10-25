package barsuift.simLife.tree;

/**
 * Mask to determine what has been updated about a leaf
 */
public class LeafUpdateMask {

    private static final int FALL = 0;

    private static final int EFFICIENCY = 1;


    public static final int FALL_MASK = 1 << FALL;

    public static final int EFFICIENCY_MASK = 1 << EFFICIENCY;

    public static boolean isFieldSet(int globalMask, int fieldMask) {
        return (globalMask & fieldMask) != 0;
    }

}
