package barsuift.simLife.j3d.tree;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.Point3dState;

public class TreeBranch3DStateFactory {

    public TreeBranch3DState createRandomTreeBranch3DState(Vector3d translationVector) {
        Point3dState translationVectorState = new Point3dState(new Point3d(translationVector));
        return new TreeBranch3DState(translationVectorState);
    }

}
