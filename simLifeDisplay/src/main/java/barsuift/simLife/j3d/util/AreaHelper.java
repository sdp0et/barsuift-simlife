package barsuift.simLife.j3d.util;

import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

public final class AreaHelper {

    private AreaHelper() {
        // private constructor to enforce static access
    }

    /**
     * Computes the area of the given triangle.
     * 
     * @param triangle the triangle to compute area for
     * @return the area of the triangle
     */
    public static double computeArea(TriangleArray triangle) {
        // retrieve triangle coordinates
        Point3d p1 = new Point3d();
        triangle.getCoordinate(0, p1);
        Point3d p2 = new Point3d();
        triangle.getCoordinate(1, p2);
        Point3d p3 = new Point3d();
        triangle.getCoordinate(2, p3);

        // compute the triangle sides length
        double a = p1.distance(p2);
        double b = p2.distance(p3);
        double c = p3.distance(p1);

        // compute the semi perimeter
        double s = (a + b + c) / 2;

        // compute the area
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));

        return area;
    }

}
