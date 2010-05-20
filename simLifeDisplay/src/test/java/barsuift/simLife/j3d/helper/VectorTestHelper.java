package barsuift.simLife.j3d.helper;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import junit.framework.Assert;

public final class VectorTestHelper extends Assert {

    private VectorTestHelper() {
        // private constructor to enforce static access
    }

    public static final void assertVectorEquals(Vector3f v1, Vector3f v2) {
        assertVectorEquals(v1, v2, 0.0001, 0.0001, 0.0001);
    }

    public static final void assertVectorEquals(Vector3f v1, Vector3f v2, double xPrecision, double yPrecision,
            double zPrecision) {
        assertEquals("expected X = " + v1.getX() + " - actual X = " + v2.getX(), v1.getX(), v2.getX(), xPrecision);
        assertEquals("expected Y = " + v1.getY() + " - actual Y = " + v2.getY(), v1.getY(), v2.getY(), yPrecision);
        assertEquals("expected Z = " + v1.getZ() + " - actual Z = " + v2.getZ(), v1.getZ(), v2.getZ(), zPrecision);
    }

    public static final void assertVectorEquals(Vector3d v1, Vector3d v2) {
        assertVectorEquals(v1, v2, 0.0001, 0.0001, 0.0001);
    }

    public static final void assertVectorEquals(Vector3d v1, Vector3d v2, double xPrecision, double yPrecision,
            double zPrecision) {
        assertEquals("expected X = " + v1.getX() + " - actual X = " + v2.getX(), v1.getX(), v2.getX(), xPrecision);
        assertEquals("expected Y = " + v1.getY() + " - actual Y = " + v2.getY(), v1.getY(), v2.getY(), yPrecision);
        assertEquals("expected Z = " + v1.getZ() + " - actual Z = " + v2.getZ(), v1.getZ(), v2.getZ(), zPrecision);
    }

}
