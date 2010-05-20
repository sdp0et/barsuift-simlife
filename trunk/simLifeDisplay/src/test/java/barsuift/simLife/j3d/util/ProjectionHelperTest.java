package barsuift.simLife.j3d.util;

import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import junit.framework.TestCase;

public class ProjectionHelperTest extends TestCase {

    public void testGetProjectionPoint3d() {
        Point3d resultPoint1 = ProjectionHelper.getProjectionPoint(new Point3d(0, 0, 0));
        assertEquals(new Point3d(0, 0, 0), resultPoint1);
        Point3d resultPoint2 = ProjectionHelper.getProjectionPoint(new Point3d(1, 2, 3));
        assertEquals(new Point3d(1, 0, 3), resultPoint2);
    }

    public void testGetProjectionPoint3f() {
        Point3f resultPoint1 = ProjectionHelper.getProjectionPoint(new Point3f(0, 0, 0));
        assertEquals(new Point3f(0, 0, 0), resultPoint1);
        Point3f resultPoint2 = ProjectionHelper.getProjectionPoint(new Point3f(1, 2, 3));
        assertEquals(new Point3f(1, 0, 3), resultPoint2);
    }

}
