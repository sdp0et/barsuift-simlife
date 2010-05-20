package barsuift.simLife.j3d.tree;

import barsuift.simLife.j3d.Point3dState;


public class TreeBranchPart3DStateFactory {

    public TreeBranchPart3DState createRandomTreeBranchPart3DState(Point3dState branchPartEndPoint) {
        return new TreeBranchPart3DState(branchPartEndPoint);
    }

}
