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

import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;

public final class AreaHelper {

    private static final Point3d p1 = new Point3d();

    private static final Point3d p2 = new Point3d();

    private static final Point3d p3 = new Point3d();

    private AreaHelper() {
        // private constructor to enforce static access
    }

    /**
     * Computes the area of the given triangle.
     * 
     * @param triangle the triangle to compute area for
     * @return the area of the triangle
     */
    public static double computeArea(TriangleArray triangle) {
        // retrieve triangle coordinates
        triangle.getCoordinate(0, p1);
        triangle.getCoordinate(1, p2);
        triangle.getCoordinate(2, p3);

        return computeArea(p1, p2, p3);
    }

    /**
     * Computes the area of the given triangle, formed by the given 3 points
     * 
     * @param p1 first point of the triangle
     * @param p2 second point of the triangle
     * @param p3 third point of the triangle
     * @return the area of the triangle
     */
    public static double computeArea(Point3d p1, Point3d p2, Point3d p3) {
        // compute the triangle sides length
        double a = p1.distance(p2);
        double b = p2.distance(p3);
        double c = p3.distance(p1);

        // compute the semi perimeter
        double s = (a + b + c) / 2;

        // compute the area
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));

        return area;
    }

}
