package barsuift.simLife.j3d.util;

import javax.vecmath.Point3d;

import junit.framework.TestCase;

public class PointHelperTest extends TestCase {

    public void testShiftPoint() {
        Point3d p1 = new Point3d(0, 0, 0);
        Point3d actualPoint = PointHelper.shiftPoint(p1, 10);
        assertTrue(actualPoint.getX() <= 10);
        assertTrue(actualPoint.getX() >= -10);
        assertTrue(actualPoint.getY() <= 10);
        assertTrue(actualPoint.getY() >= -10);
        assertTrue(actualPoint.getZ() <= 10);
        assertTrue(actualPoint.getZ() >= -10);

        Point3d p2 = new Point3d(1, -1, 2);
        Point3d actualPoint2 = PointHelper.shiftPoint(p2, 5);
        assertTrue(actualPoint2.getX() <= 6);
        assertTrue(actualPoint2.getX() >= -4);
        assertTrue(actualPoint2.getY() <= 4);
        assertTrue(actualPoint2.getY() >= -6);
        assertTrue(actualPoint2.getZ() <= 7);
        assertTrue(actualPoint2.getZ() >= -3);

        Point3d p3 = new Point3d(0, 0, 0);
        Point3d actualPoint3 = PointHelper.shiftPoint(p3, 0.6);
        assertTrue(actualPoint3.getX() <= 0.6);
        assertTrue(actualPoint3.getX() >= -0.6);
        assertTrue(actualPoint3.getY() <= 0.6);
        assertTrue(actualPoint3.getY() >= -0.6);
        assertTrue(actualPoint3.getZ() <= 0.6);
        assertTrue(actualPoint3.getZ() >= -0.6);

        Point3d p4 = new Point3d(1, -1, 2);
        Point3d actualPoint4 = PointHelper.shiftPoint(p4, 0.6);
        assertTrue(actualPoint4.getX() <= 1.6);
        assertTrue(actualPoint4.getX() >= 0.4);
        assertTrue(actualPoint4.getY() <= -0.4);
        assertTrue(actualPoint4.getY() >= -1.6);
        assertTrue(actualPoint4.getZ() <= 2.6);
        assertTrue(actualPoint4.getZ() >= 1.4);
    }

}
