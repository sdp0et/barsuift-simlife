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
import javax.vecmath.Vector3f;


public final class NormalHelper {

    private NormalHelper() {
        // private constructor to enforce static access
    }

    /**
     * Computes the normal vector based on the surface defined by the 3 given points
     * 
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public static Vector3f computeNormal(Point3f p1, Point3f p2, Point3f p3) {
        Vector3f normal = new Vector3f();
        Vector3f vector1 = new Vector3f();
        Vector3f vector2 = new Vector3f();

        vector1.sub(p2, p1);
        vector2.sub(p3, p1);

        normal.cross(vector1, vector2);
        normal.normalize();
        return normal;
    }

}
