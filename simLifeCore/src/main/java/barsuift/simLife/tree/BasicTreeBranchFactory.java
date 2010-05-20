package barsuift.simLife.tree;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.universe.Universe;

public class BasicTreeBranchFactory {

    private final Universe universe;

    public BasicTreeBranchFactory(Universe universe) {
        this.universe = universe;
    }

    public TreeBranch createRandom(Vector3d translationVector, Point3d branchEndPoint) {
        TreeBranchStateFactory treeBranchStateFactory = new TreeBranchStateFactory();
        TreeBranchState treeBranchState = treeBranchStateFactory.createRandomBranchState(translationVector, branchEndPoint);
        TreeBranch treeBranch = new BasicTreeBranch(universe, treeBranchState);
        return treeBranch;
    }


}
