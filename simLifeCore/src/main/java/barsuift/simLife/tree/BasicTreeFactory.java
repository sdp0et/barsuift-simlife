package barsuift.simLife.tree;

import javax.vecmath.Point3d;

import barsuift.simLife.universe.Universe;

public class BasicTreeFactory {

    private final Universe universe;

    public BasicTreeFactory(Universe universe) {
        this.universe = universe;
    }

    public Tree createRandom(Point3d translationVector, int nbBranches, float height) {
        TreeStateFactory treeStateFactory = new TreeStateFactory();
        TreeState treeState = treeStateFactory.createRandomTreeState(translationVector, nbBranches, height);
        Tree tree = new BasicTree(universe, treeState);
        return tree;
    }

}
