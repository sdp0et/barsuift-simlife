package barsuift.simLife.j3d.util;

import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;


public final class NormalHelper {

    private NormalHelper() {
        // private constructor to enforce static access
    }

    /**
     * Computes the normal vector based on the surface defined by the 3 given points
     * 
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public static Vector3f computeNormal(Point3f p1, Point3f p2, Point3f p3) {
        Vector3f normal = new Vector3f();
        Vector3f vector1 = new Vector3f();
        Vector3f vector2 = new Vector3f();

        vector1.sub(p2, p1);
        vector2.sub(p3, p1);

        normal.cross(vector1, vector2);
        normal.normalize();
        return normal;
    }

    /**
     * Computes the normal vector based on the surface defined by the 3 given points
     * 
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public static Vector3f computeNormal(Point3d p1, Point3d p2, Point3d p3) {
        Vector3d normal = new Vector3d();
        Vector3d vector1 = new Vector3d();
        Vector3d vector2 = new Vector3d();

        vector1.sub(p2, p1);
        vector2.sub(p3, p1);

        normal.cross(vector1, vector2);
        normal.normalize();
        return new Vector3f(normal);
    }

}
