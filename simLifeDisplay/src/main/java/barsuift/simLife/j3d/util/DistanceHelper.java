package barsuift.simLife.j3d.util;

import javax.vecmath.Point3d;

public final class DistanceHelper {

    private DistanceHelper() {
        // private constructor to enforce static access
    }

    /**
     * a Point at (0,0,0)
     */
    private static final Point3d zeroPoint = new Point3d(0, 0, 0);

    /**
     * Computes the distance between the given point and the origin point (0,0,0).
     * 
     * @param point the point to compute distance for
     * @return the distance between point and origin
     */
    public static double distanceFromOrigin(Point3d point) {
        return zeroPoint.distance(point);
    }

}
