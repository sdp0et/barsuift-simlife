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
