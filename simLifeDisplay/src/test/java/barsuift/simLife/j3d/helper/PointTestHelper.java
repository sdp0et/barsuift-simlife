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
package barsuift.simLife.j3d.helper;

import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import junit.framework.Assert;

// TODO convert to FEST assertion
public final class PointTestHelper extends Assert {

    private PointTestHelper() {
        // private constructor to enforce static access
    }

    public static final void assertPointEquals(Point3f p1, Point3f p2) {
        assertPointEquals(p1, p2, 0.0001f, 0.0001f, 0.0001f);
    }

    public static final void assertPointEquals(Point3f p1, Point3f p2, float xPrecision, float yPrecision,
            float zPrecision) {
        assertEquals(p1.getX(), p2.getX(), xPrecision);
        assertEquals(p1.getY(), p2.getY(), yPrecision);
        assertEquals(p1.getZ(), p2.getZ(), zPrecision);
    }

    public static final void assertPointIsWithinBounds(Point3f point, Point3f boundsStartPoint, Point3f boundsEndPoint) {
        float boundsMinX = Math.min(boundsStartPoint.getX(), boundsEndPoint.getX());
        float boundsMaxX = Math.max(boundsStartPoint.getX(), boundsEndPoint.getX());
        float boundsMinY = Math.min(boundsStartPoint.getY(), boundsEndPoint.getY());
        float boundsMaxY = Math.max(boundsStartPoint.getY(), boundsEndPoint.getY());
        float boundsMinZ = Math.min(boundsStartPoint.getZ(), boundsEndPoint.getZ());
        float boundsMaxZ = Math.max(boundsStartPoint.getZ(), boundsEndPoint.getZ());

        assertTrue("\"x >= min\" : " + point.getX() + " >= " + boundsMinX, point.getX() >= boundsMinX);
        assertTrue("\"x <= max\" : " + point.getX() + " <= " + boundsMaxX, point.getX() <= boundsMaxX);
        assertTrue("\"y >= min\" : " + point.getY() + " >= " + boundsMinY, point.getY() >= boundsMinY);
        assertTrue("\"y <= max\" : " + point.getY() + " <= " + boundsMaxY, point.getY() <= boundsMaxY);
        assertTrue("\"z >= min\" : " + point.getZ() + " >= " + boundsMinZ, point.getZ() >= boundsMinZ);
        assertTrue("\"z <= max\" : " + point.getZ() + " <= " + boundsMaxZ, point.getZ() <= boundsMaxZ);
    }

    public static final void assertPointEquals(Point3d p1, Point3d p2) {
        assertPointEquals(p1, p2, 0.0001, 0.0001, 0.0001);
    }

    public static final void assertPointEquals(Point3d p1, Point3d p2, double xPrecision, double yPrecision,
            double zPrecision) {
        assertEquals(p1.getX(), p2.getX(), xPrecision);
        assertEquals(p1.getY(), p2.getY(), yPrecision);
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
