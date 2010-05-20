package barsuift.simLife.tree;

import barsuift.simLife.universe.Universe;

public class BasicTreeTrunkFactory {

    private final Universe universe;

    public BasicTreeTrunkFactory(Universe universe) {
        this.universe = universe;
    }

    public TreeTrunk createRandom(float radius, float height) {
        TreeTrunkStateFactory treeTrunkStateFactory = new TreeTrunkStateFactory();
        TreeTrunkState treeTrunkState = treeTrunkStateFactory.createRandomTreeTrunkState(radius, height);
        TreeTrunk treeTrunk = new BasicTreeTrunk(universe, treeTrunkState);
        return treeTrunk;
    }

}
