package barsuift.simLife.j3d.tree;

import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.Point3dState;
import barsuift.simLife.j3d.helper.PointTestHelper;


public class TreeLeaf3DStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomTreeLeaf3DState() {
        TreeLeaf3DStateFactory factory = new TreeLeaf3DStateFactory();
        Point3d leafAttachPoint = new Point3d(0.32, 0.33, 0.34);
        TreeLeaf3DState leaf3DState = factory.createRandomTreeLeaf3DState(leafAttachPoint);

        Point3dState actualStartPoint = leaf3DState.getLeafAttachPoint();
        Point3dState actualInitialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Point3dState actualInitialEndPoint2 = leaf3DState.getInitialEndPoint2();
        Point3dState actualEndPoint1 = leaf3DState.getEndPoint1();
        Point3dState actualEndPoint2 = leaf3DState.getEndPoint2();

        // test point 1 position
        PointTestHelper.assertPointEquals(leafAttachPoint, actualStartPoint.toPointValue());

        // test initial point 2 position
        assertTrue("Wrong X position for end point 1.", actualInitialEndPoint1.getX() > 0 - 0.02 - 0.01);
        assertTrue("Wrong X position for end point 1.", actualInitialEndPoint1.getX() < 0 - 0.02 + 0.01);
        assertTrue("Wrong Y position for end point 1.", actualInitialEndPoint1.getY() > 0 - 0.04 - 0.01);
        assertTrue("Wrong Y position for end point 1.", actualInitialEndPoint1.getY() < 0 - 0.04 + 0.01);
        assertTrue("Wrong Z position for end point 1.", actualInitialEndPoint1.getZ() > 0 - 0.01);
        assertTrue("Wrong Z position for end point 1.", actualInitialEndPoint1.getZ() < 0 + 0.01);
        // test initial point 3 position
        assertTrue("Wrong X position for end point 2.", actualInitialEndPoint2.getX() > 0 + 0.02 - 0.01);
        assertTrue("Wrong X position for end point 2.", actualInitialEndPoint2.getX() < 0 + 0.02 + 0.01);
        assertTrue("Wrong Y position for end point 2.", actualInitialEndPoint2.getY() > 0 - 0.04 - 0.01);
        assertTrue("Wrong Y position for end point 2.", actualInitialEndPoint2.getY() < 0 - 0.04 + 0.01);
        assertTrue("Wrong Z position for end point 2.", actualInitialEndPoint2.getZ() > 0 - 0.01);
        assertTrue("Wrong Z position for end point 2.", actualInitialEndPoint2.getZ() < 0 + 0.01);

        // test point 2 position
        assertTrue("Wrong X position for end point 1.", actualEndPoint1.getX() > 0 - 0.2 - 0.1);
        assertTrue("Wrong X position for end point 1.", actualEndPoint1.getX() < 0 - 0.2 + 0.1);
        assertTrue("Wrong Y position for end point 1.", actualEndPoint1.getY() > 0 - 0.4 - 0.1);
        assertTrue("Wrong Y position for end point 1.", actualEndPoint1.getY() < 0 - 0.4 + 0.1);
        assertTrue("Wrong Z position for end point 1.", actualEndPoint1.getZ() > 0 - 0.1);
        assertTrue("Wrong Z position for end point 1.", actualEndPoint1.getZ() < 0 + 0.1);
        // test point 3 position
        assertTrue("Wrong X position for end point 2.", actualEndPoint2.getX() > 0 + 0.2 - 0.1);
        assertTrue("Wrong X position for end point 2.", actualEndPoint2.getX() < 0 + 0.2 + 0.1);
        assertTrue("Wrong Y position for end point 2.", actualEndPoint2.getY() > 0 - 0.4 - 0.1);
        assertTrue("Wrong Y position for end point 2.", actualEndPoint2.getY() < 0 - 0.4 + 0.1);
        assertTrue("Wrong Z position for end point 2.", actualEndPoint2.getZ() > 0 - 0.1);
        assertTrue("Wrong Z position for end point 2.", actualEndPoint2.getZ() < 0 + 0.1);

        assertTrue("Leaf rotation should be positive.", leaf3DState.getRotation() > 0);
        assertTrue("Leaf rotation should be less than 2 Pi.", leaf3DState.getRotation() < Math.PI * 2);
    }

    public void testCreateNewTreeLeaf3DState() {
        TreeLeaf3DStateFactory factory = new TreeLeaf3DStateFactory();
        Point3d leafAttachPoint = new Point3d(0.32, 0.33, 0.34);
        TreeLeaf3DState leaf3DState = factory.createNewTreeLeaf3DState(leafAttachPoint);

        Point3dState actualStartPoint = leaf3DState.getLeafAttachPoint();
        Point3dState actualInitialEndPoint1 = leaf3DState.getInitialEndPoint1();
        Point3dState actualInitialEndPoint2 = leaf3DState.getInitialEndPoint2();
        Point3dState actualEndPoint1 = leaf3DState.getEndPoint1();
        Point3dState actualEndPoint2 = leaf3DState.getEndPoint2();

        // test point 1 position
        PointTestHelper.assertPointEquals(leafAttachPoint, actualStartPoint.toPointValue());

        // test initial point 2 position
        assertTrue("Wrong X position for end point 1.", actualInitialEndPoint1.getX() > 0 - 0.02 - 0.01);
        assertTrue("Wrong X position for end point 1.", actualInitialEndPoint1.getX() < 0 - 0.02 + 0.01);
        assertTrue("Wrong Y position for end point 1.", actualInitialEndPoint1.getY() > 0 - 0.04 - 0.01);
        assertTrue("Wrong Y position for end point 1.", actualInitialEndPoint1.getY() < 0 - 0.04 + 0.01);
        assertTrue("Wrong Z position for end point 1.", actualInitialEndPoint1.getZ() > 0 - 0.01);
        assertTrue("Wrong Z position for end point 1.", actualInitialEndPoint1.getZ() < 0 + 0.01);
        // test initial point 3 position
        assertTrue("Wrong X position for end point 2.", actualInitialEndPoint2.getX() > 0 + 0.02 - 0.01);
        assertTrue("Wrong X position for end point 2.", actualInitialEndPoint2.getX() < 0 + 0.02 + 0.01);
        assertTrue("Wrong Y position for end point 2.", actualInitialEndPoint2.getY() > 0 - 0.04 - 0.01);
        assertTrue("Wrong Y position for end point 2.", actualInitialEndPoint2.getY() < 0 - 0.04 + 0.01);
        assertTrue("Wrong Z position for end point 2.", actualInitialEndPoint2.getZ() > 0 - 0.01);
        assertTrue("Wrong Z position for end point 2.", actualInitialEndPoint2.getZ() < 0 + 0.01);

        // test point 2 position
        PointTestHelper.assertPointEquals(actualInitialEndPoint1.toPointValue(), actualEndPoint1.toPointValue());
        // test point 3 position
        PointTestHelper.assertPointEquals(actualInitialEndPoint2.toPointValue(), actualEndPoint2.toPointValue());

        assertTrue("Leaf rotation should be positive.", leaf3DState.getRotation() > 0);
        assertTrue("Leaf rotation should be less than 2 Pi.", leaf3DState.getRotation() < Math.PI * 2);
    }

}
