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
import javax.vecmath.Point3f;

/**
 * Helper class to "project" a point on the "ground".
 * <p>
 * Projecting a point means setting its Y value to 0. Thus a (1,2,3) point is projected to (1,0,3).
 * </p>
 * <p>
 * The new point is the same one but on the X-Z plan.
 * </p>
 * 
 */
public final class ProjectionHelper {

    private ProjectionHelper() {
        // private constructor to enforce static access
    }

    /**
     * Project the given point to the X-Z plan, by setting the Y value to 0.
     * 
     * @param originPoint the point to project
     * @return a new point, with the same X and Z values, but Y set to 0.
     */
    public static Point3d getProjectionPoint(Point3d originPoint) {
        Point3d result = new Point3d(originPoint);
        result.setY(0);
        return result;
    }

    /**
     * Project the given point to the X-Z plan, by setting the Y value to 0.
     * 
     * @param originPoint the point to project
     * @return a new point, with the same X and Z values, but Y set to 0.
     */
    public static Point3f getProjectionPoint(Point3f originPoint) {
        Point3f result = new Point3f(originPoint);
        result.setY(0);
        return result;
    }

}
