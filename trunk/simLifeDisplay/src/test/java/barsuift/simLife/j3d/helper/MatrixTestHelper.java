package barsuift.simLife.j3d.helper;

import javax.vecmath.Matrix3d;

import junit.framework.Assert;

public final class MatrixTestHelper extends Assert {

    private MatrixTestHelper() {
        // private constructor to enforce static access
    }

    public static final void assertMatrixEquals(Matrix3d m1, Matrix3d m2) {
        assertMatrixEquals(m1, m2, 0.0001);
    }

    public static final void assertMatrixEquals(Matrix3d m1, Matrix3d m2, double precision) {
        assertEquals("expected m00 = " + m1.m00 + " - actual m00 = " + m2.m00, m1.m00, m2.m00, precision);
        assertEquals("expected m01 = " + m1.m01 + " - actual m01 = " + m2.m01, m1.m01, m2.m01, precision);
        assertEquals("expected m02 = " + m1.m02 + " - actual m02 = " + m2.m02, m1.m02, m2.m02, precision);

        assertEquals("expected m10 = " + m1.m10 + " - actual m10 = " + m2.m10, m1.m10, m2.m10, precision);
        assertEquals("expected m11 = " + m1.m11 + " - actual m11 = " + m2.m11, m1.m11, m2.m11, precision);
        assertEquals("expected m12 = " + m1.m12 + " - actual m12 = " + m2.m12, m1.m12, m2.m12, precision);

        assertEquals("expected m20 = " + m1.m20 + " - actual m20 = " + m2.m20, m1.m20, m2.m20, precision);
        assertEquals("expected m21 = " + m1.m21 + " - actual m21 = " + m2.m21, m1.m21, m2.m21, precision);
        assertEquals("expected m22 = " + m1.m22 + " - actual m22 = " + m2.m22, m1.m22, m2.m22, precision);
    }

}
