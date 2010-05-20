package barsuift.simLife.tree;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.tree.TreeTrunk3DState;
import barsuift.simLife.j3d.tree.TreeTrunk3DStateFactory;


public class TreeTrunkStateFactory {

    private static long TRUNK_COUNT = 1;

    /**
     * Creates a default tree trunk state with given radius and height and default 3D state. The id is incremented from
     * a sequence.
     */
    public TreeTrunkState createRandomTreeTrunkState(float radius, float height) {
        int age = Randomizer.randomBetween(0, 100);
        TreeTrunk3DStateFactory trunk3DStateFactory = new TreeTrunk3DStateFactory();
        TreeTrunk3DState leaf3dState = trunk3DStateFactory.createRandomTreeTrunk3DState();
        return new TreeTrunkState(TRUNK_COUNT++, age, radius, height, leaf3dState);
    }

}
