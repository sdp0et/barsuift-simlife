package barsuift.simLife.j3d.util;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

import junit.framework.TestCase;


public class AreaHelperTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testComputeArea() {
        TriangleArray triangle;
        double area;

        // first test : all points are (0,0,0)
        triangle = new TriangleArray(3, GeometryArray.COORDINATES);
        triangle.setCoordinate(0, new Point3d(0, 0, 0));
        triangle.setCoordinate(1, new Point3d(0, 0, 0));
        triangle.setCoordinate(2, new Point3d(0, 0, 0));
        area = AreaHelper.computeArea(triangle);
        assertEquals(0d, area, 0.0000001);

        // second test : right triangle
        triangle = new TriangleArray(3, GeometryArray.COORDINATES);
        triangle.setCoordinate(0, new Point3d(0, 0, 0));
        triangle.setCoordinate(1, new Point3d(3, 0, 0));
        triangle.setCoordinate(2, new Point3d(3, 1, 0));
        area = AreaHelper.computeArea(triangle);
        assertEquals(1.5, area, 0.0000001);

        // second test : isosceles triangle
        triangle = new TriangleArray(3, GeometryArray.COORDINATES);
        triangle.setCoordinate(0, new Point3d(0, 1, 1));
        triangle.setCoordinate(1, new Point3d(0, 3, 1));
        triangle.setCoordinate(2, new Point3d(0, 2, 8));
        area = AreaHelper.computeArea(triangle);
        assertEquals(7, area, 0.0000001);
    }

}
