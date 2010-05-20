package barsuift.simLife.j3d.tree;

import javax.vecmath.Point3d;

import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.Point3dState;


public class TreeLeaf3DStateFactory {

    /**
     * Create a random leaf3D state with :
     * <ul>
     * <li>the given attach point</li>
     * <li>initial end point 1 : (-0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>initial end point 2 : (0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>end point 1 : 10 * initial end point 1</li>
     * <li>end point 2 : 10 * initial end point 2</li>
     * <li>rotation : a random number between 0 and 2 PI</li>
     * </ul>
     */
    public TreeLeaf3DState createRandomTreeLeaf3DState(Point3d leafAttachPoint) {
        Point3dState leafAttachPointState = new Point3dState(leafAttachPoint);
        Point3dState initialEndPoint1 = new Point3dState(-0.02 + Randomizer.random1() / 10, -0.04
                + Randomizer.random1() / 10, 0);
        Point3dState initialEndPoint2 = new Point3dState(0.02 + Randomizer.random1() / 10, -0.04 + Randomizer.random1()
                / 10, 0);
        Point3dState endPoint1 = new Point3dState(initialEndPoint1.getX() * 10, initialEndPoint1.getY() * 10,
                initialEndPoint1.getZ() * 10);
        Point3dState endPoint2 = new Point3dState(initialEndPoint2.getX() * 10, initialEndPoint2.getY() * 10,
                initialEndPoint2.getZ() * 10);
        double rotation = Randomizer.randomRotation();
        return new TreeLeaf3DState(leafAttachPointState, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2,
                rotation);
    }

    /**
     * Create a new leaf3D state with :
     * <ul>
     * <li>the given attach point</li>
     * <li>initial end point 1 : (-0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>initial end point 2 : (0.02&plusmn;0.01, -0.04&plusmn;0.01, 0)</li>
     * <li>end point 1 : initial end point 1</li>
     * <li>end point 2 : initial end point 2</li>
     * <li>rotation : a random number between 0 and 2 PI</li>
     * </ul>
     */
    public TreeLeaf3DState createNewTreeLeaf3DState(Point3d leafAttachPoint) {
        Point3dState leafAttachPointState = new Point3dState(leafAttachPoint);
        Point3dState initialEndPoint1 = new Point3dState(-0.02 + Randomizer.random1() / 10, -0.04
                + Randomizer.random1() / 10, 0);
        Point3dState initialEndPoint2 = new Point3dState(0.02 + Randomizer.random1() / 10, -0.04 + Randomizer.random1()
                / 10, 0);
        Point3dState endPoint1 = new Point3dState(initialEndPoint1);
        Point3dState endPoint2 = new Point3dState(initialEndPoint2);
        double rotation = Randomizer.randomRotation();
        return new TreeLeaf3DState(leafAttachPointState, initialEndPoint1, initialEndPoint2, endPoint1, endPoint2,
                rotation);
    }

}
