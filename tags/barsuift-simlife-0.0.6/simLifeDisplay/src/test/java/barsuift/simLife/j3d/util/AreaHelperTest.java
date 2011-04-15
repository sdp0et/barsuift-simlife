/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife.j3d.util;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3f;

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
        float area;

        // first test : all points are (0,0,0)
        triangle = new TriangleArray(3, GeometryArray.COORDINATES);
        triangle.setCoordinate(0, new Point3f(0, 0, 0));
        triangle.setCoordinate(1, new Point3f(0, 0, 0));
        triangle.setCoordinate(2, new Point3f(0, 0, 0));
        area = AreaHelper.computeArea(triangle);
        assertEquals(0d, area, 0.0000001);

        // second test : right triangle
        triangle = new TriangleArray(3, GeometryArray.COORDINATES);
        triangle.setCoordinate(0, new Point3f(0, 0, 0));
        triangle.setCoordinate(1, new Point3f(3, 0, 0));
        triangle.setCoordinate(2, new Point3f(3, 1, 0));
        area = AreaHelper.computeArea(triangle);
        assertEquals(1.5, area, 0.0000001);

        // second test : isosceles triangle
        triangle = new TriangleArray(3, GeometryArray.COORDINATES);
        triangle.setCoordinate(0, new Point3f(0, 1, 1));
        triangle.setCoordinate(1, new Point3f(0, 3, 1));
        triangle.setCoordinate(2, new Point3f(0, 2, 8));
        area = AreaHelper.computeArea(triangle);
        assertEquals(7, area, 0.0000001);
    }

}
