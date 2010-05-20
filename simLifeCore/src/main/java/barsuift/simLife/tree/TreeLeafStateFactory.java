package barsuift.simLife.tree;

import java.math.BigDecimal;

import javax.vecmath.Point3d;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.PercentState;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DStateFactory;


public class TreeLeafStateFactory {

    private static long LEAF_COUNT = 1;

    /**
     * Create a random tree leaf state with following values :
     * <ul>
     * <li>id incremented from a sequence</li>
     * <li>efficiency between 90 and 100</li>
     * <li>energy between 0 and 100</li>
     * <li>freeEnergy between 0 and 50</li>
     * <li>age between 0 and 100</li>
     * <li>random 3D state</li>
     * </ul>
     */
    public TreeLeafState createRandomTreeLeafState(Point3d leafAttachPoint) {
        PercentState efficiency = new PercentState(PercentHelper.getDecimalValue(Randomizer.randomBetween(90, 100)));
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        TreeLeaf3DStateFactory leaf3DStateFactory = new TreeLeaf3DStateFactory();
        TreeLeaf3DState leaf3dState = leaf3DStateFactory.createRandomTreeLeaf3DState(leafAttachPoint);
        return new TreeLeafState(LEAF_COUNT++, age, energy, freeEnergy, efficiency, leaf3dState);
    }

    /**
     * Create a new tree leaf state with following values :
     * <ul>
     * <li>id incremented from a sequence</li>
     * <li>efficiency between 90 and 100</li>
     * <li>freeEnergy = 0</li>
     * <li>age = 0</li>
     * <li>new 3D state</li>
     * </ul>
     */
    public TreeLeafState createNewTreeLeafState(Point3d leafAttachPoint, BigDecimal energy) {
        PercentState efficiency = new PercentState(PercentHelper.getDecimalValue(Randomizer.randomBetween(90, 100)));
        int age = 0;
        BigDecimal freeEnergy = new BigDecimal(0);
        TreeLeaf3DStateFactory leaf3DStateFactory = new TreeLeaf3DStateFactory();
        TreeLeaf3DState leaf3dState = leaf3DStateFactory.createNewTreeLeaf3DState(leafAttachPoint);
        return new TreeLeafState(LEAF_COUNT++, age, energy, freeEnergy, efficiency, leaf3dState);
    }

}
