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

import javax.vecmath.Point3f;

import barsuift.simLife.Randomizer;

public final class PointHelper {

    private PointHelper() {
        // private constructor to enforce static access
    }

    /**
     * Shifts the X, Y, and Z coordinates between (-maxDistance, +maxDistance)
     * 
     * @param point
     * @param distanceMax
     * @return
     */
    public static final Point3f shiftPoint(Point3f point, float distanceMax) {
        float x = point.getX() + Randomizer.random3() * distanceMax;
        float y = point.getY() + Randomizer.random3() * distanceMax;
        float z = point.getZ() + Randomizer.random3() * distanceMax;
        return new Point3f(x, y, z);
    }

    public static final boolean areAlmostEquals(Point3f p1, Point3f p2) {
        return areAlmostEquals(p1, p2, 0.0001f, 0.0001f, 0.0001f);
    }

    public static final boolean areAlmostEquals(Point3f p1, Point3f p2, float xPrecision, float yPrecision,
            float zPrecision) {
        if (!(Math.abs(p1.getX() - p2.getX()) <= xPrecision))
            return false;
        if (!(Math.abs(p1.getY() - p2.getY()) <= yPrecision))
            return false;
        if (!(Math.abs(p1.getZ() - p2.getZ()) <= zPrecision))
            return false;
        return true;
    }

    public static final boolean isPointWithinBounds(Point3f point, Point3f boundsStartPoint, Point3f boundsEndPoint) {
        if (point.getX() < Math.min(boundsStartPoint.getX(), boundsEndPoint.getX())) {
            return false;
        }
        if (point.getX() > Math.max(boundsStartPoint.getX(), boundsEndPoint.getX())) {
            return false;
        }
        if (point.getY() < Math.min(boundsStartPoint.getY(), boundsEndPoint.getY())) {
            return false;
        }
        if (point.getY() > Math.max(boundsStartPoint.getY(), boundsEndPoint.getY())) {
            return false;
        }
        if (point.getZ() < Math.min(boundsStartPoint.getZ(), boundsEndPoint.getZ())) {
            return false;
        }
        if (point.getZ() > Math.max(boundsStartPoint.getZ(), boundsEndPoint.getZ())) {
            return false;
        }
        return true;
    }

}
