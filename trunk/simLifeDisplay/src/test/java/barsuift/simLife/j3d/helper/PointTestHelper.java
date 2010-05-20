package barsuift.simLife.j3d.helper;

import javax.vecmath.Point3d;

import junit.framework.Assert;

public final class PointTestHelper extends Assert {

    private PointTestHelper() {
        // private constructor to enforce static access
    }

    public static final void assertPointEquals(Point3d p1, Point3d p2) {
        assertPointEquals(p1, p2, 0.0001, 0.0001, 0.0001);
    }

    public static final void assertPointEquals(Point3d p1, Point3d p2, double xPrecision, double yPrecision,
            double zPrecision) {
        // assertEquals("expected X = " + p1.getX() + " - actual X = " + p2.getX(), p1.getX(), p2.getX(), xPrecision);
        assertEquals(p1.getX(), p2.getX(), xPrecision);
        // assertEquals("expected Y = " + p1.getY() + " - actual Y = " + p2.getY(), p1.getY(), p2.getY(), yPrecision);
        assertEquals(p1.getY(), p2.getY(), yPrecision);
        // assertEquals("expected Z = " + p1.getZ() + " - actual Z = " + p2.getZ(), p1.getZ(), p2.getZ(), zPrecision);
        assertEquals(p1.getZ(), p2.getZ(), zPrecision);
    }

    public static final void assertPointIsWithinBounds(Point3d point, Point3d boundsStartPoint, Point3d boundsEndPoint) {
        double boundsMinX = Math.min(boundsStartPoint.getX(), boundsEndPoint.getX());
        double boundsMaxX = Math.max(boundsStartPoint.getX(), boundsEndPoint.getX());
        double boundsMinY = Math.min(boundsStartPoint.getY(), boundsEndPoint.getY());
        double boundsMaxY = Math.max(boundsStartPoint.getY(), boundsEndPoint.getY());
        double boundsMinZ = Math.min(boundsStartPoint.getZ(), boundsEndPoint.getZ());
        double boundsMaxZ = Math.max(boundsStartPoint.getZ(), boundsEndPoint.getZ());

        assertTrue("\"x >= min\" : " + point.getX() + " >= " + boundsMinX, point.getX() >= boundsMinX);
        assertTrue("\"x <= max\" : " + point.getX() + " <= " + boundsMaxX, point.getX() <= boundsMaxX);
        assertTrue("\"y >= min\" : " + point.getY() + " >= " + boundsMinY, point.getY() >= boundsMinY);
        assertTrue("\"y <= max\" : " + point.getY() + " <= " + boundsMaxY, point.getY() <= boundsMaxY);
        assertTrue("\"z >= min\" : " + point.getZ() + " >= " + boundsMinZ, point.getZ() >= boundsMinZ);
        assertTrue("\"z <= max\" : " + point.getZ() + " <= " + boundsMaxZ, point.getZ() <= boundsMaxZ);
    }

}
