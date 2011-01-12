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

public final class BarycentreHelper {

    private BarycentreHelper() {
        // private constructor to enforce static access
    }

    /**
     * Return a point on the line between startPoint and endPoint, located at the given distance from the startPoint.
     * <p>
     * The returned point is always on the line, between the 2 given points. If distance is too low (negative), then the
     * start point is returned. If distance is too high, then the end point is returned.
     * </p>
     * 
     * @param startPoint
     * @param endPoint
     * @param distance
     * @return
     */
    public static Point3f getBarycentre(Point3f startPoint, Point3f endPoint, float distance) {
        float maxDistance = startPoint.distance(endPoint);
        float distanceToUse = computeDistanceToUse(maxDistance, distance);
        float ratio = distanceToUse / maxDistance;
        Point3f result = new Point3f();
        result.interpolate(startPoint, endPoint, ratio);
        return result;
    }

    /**
     * Bounds the given distance between 0 and maxDistance
     * 
     * @param maxDistance
     * @param distance
     * @return
     */
    static float computeDistanceToUse(float maxDistance, float distance) {
        if (distance < 0) {
            return 0;
        }
        if (distance > maxDistance) {
            return maxDistance;
        }
        return distance;
    }

}
