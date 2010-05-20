package barsuift.simLife.j3d.tree;

import barsuift.simLife.j3d.Point3dState;



public class Tree3DStateFactory {

    public Tree3DState createRandomTree3DState(Point3dState translationVector) {
        return new Tree3DState(translationVector);
    }
}
