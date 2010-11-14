package barsuift.simLife.tree;

/**
 * Mask to determine what has been updated about a leaf
 */
public class LeafUpdateMask {

    private static final int FALLING = 0;

    private static final int FALLEN = 1;

    private static final int EFFICIENCY = 2;


    public static final int FALLING_MASK = 1 << FALLING;

    public static final int FALLEN_MASK = 1 << FALLEN;

    public static final int EFFICIENCY_MASK = 1 << EFFICIENCY;

    public static boolean isFieldSet(int globalMask, int fieldMask) {
        return (globalMask & fieldMask) != 0;
    }

}
