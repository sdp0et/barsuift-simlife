package barsuift.simLife.tree;

import javax.vecmath.Point3d;

import barsuift.simLife.universe.Universe;

public class BasicTreeBranchPartFactory {

    private final Universe universe;

    public BasicTreeBranchPartFactory(Universe universe) {
        this.universe = universe;
    }

    public TreeBranchPart createRandom(Point3d branchPartEndPoint) {
        TreeBranchPartStateFactory treeBranchPartStateFactory = new TreeBranchPartStateFactory();
        TreeBranchPartState treeBranchPartState = treeBranchPartStateFactory.createRandomBranchPartState(branchPartEndPoint);
        TreeBranchPart treeTreeBranchPart = new BasicTreeBranchPart(universe, treeBranchPartState);
        return treeTreeBranchPart;
    }

}
