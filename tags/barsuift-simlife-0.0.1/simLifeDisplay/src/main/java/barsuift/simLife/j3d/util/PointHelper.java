/**
 * barsuift-simlife is a life simulator programm
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

import javax.vecmath.Point3d;

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
    public static final Point3d shiftPoint(Point3d point, double distanceMax) {
        double x = point.getX() + Randomizer.random3() * distanceMax;
        double y = point.getY() + Randomizer.random3() * distanceMax;
        double z = point.getZ() + Randomizer.random3() * distanceMax;
        return new Point3d(x, y, z);
    }

    public static final boolean areAlmostEquals(Point3d p1, Point3d p2) {
        return areAlmostEquals(p1, p2, 0.0001, 0.0001, 0.0001);
    }

    public static final boolean areAlmostEquals(Point3d p1, Point3d p2, double xPrecision, double yPrecision,
            double zPrecision) {
        if (!(Math.abs(p1.getX() - p2.getX()) <= xPrecision))
            return false;
        if (!(Math.abs(p1.getY() - p2.getY()) <= yPrecision))
            return false;
        if (!(Math.abs(p1.getZ() - p2.getZ()) <= zPrecision))
            return false;
        return true;
    }

    public static final boolean isPointWithinBounds(Point3d point, Point3d boundsStartPoint, Point3d boundsEndPoint) {
        double boundsMinX = Math.min(boundsStartPoint.getX(), boundsEndPoint.getX());
        double boundsMaxX = Math.max(boundsStartPoint.getX(), boundsEndPoint.getX());
        double boundsMinY = Math.min(boundsStartPoint.getY(), boundsEndPoint.getY());
        double boundsMaxY = Math.max(boundsStartPoint.getY(), boundsEndPoint.getY());
        double boundsMinZ = Math.min(boundsStartPoint.getZ(), boundsEndPoint.getZ());
        double boundsMaxZ = Math.max(boundsStartPoint.getZ(), boundsEndPoint.getZ());

        if (point.getX() < boundsMinX) {
            return false;
        }
        if (point.getX() > boundsMaxX) {
            return false;
        }
        if (point.getY() < boundsMinY) {
            return false;
        }
        if (point.getY() > boundsMaxY) {
            return false;
        }
        if (point.getZ() < boundsMinZ) {
            return false;
        }
        if (point.getZ() > boundsMaxZ) {
            return false;
        }
        return true;
    }

}
