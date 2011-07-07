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
package barsuift.simLife.j3d.assertions;

import javax.vecmath.Point3f;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;


public class Point3fAssert extends GenericAssert<Point3fAssert, Point3f> {

    /**
     * Creates a new </code>{@link Point3fAssert}</code> to make assertions on actual Point3f.
     * 
     * @param actual the Point3f we want to make assertions on.
     */
    public Point3fAssert(Point3f actual) {
        super(Point3fAssert.class, actual);
    }

    /**
     * An entry point for PointAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(point).isEqualTo(anotherPoint);</code>
     * 
     * @param actual the Point3f we want to make assertions on.
     * @return a new </code>{@link Point3fAssert}</code>
     */
    public static Point3fAssert assertThat(Point3f actual) {
        return new Point3fAssert(actual);
    }

    /**
     * Verifies that the actual point is (epsilon) equal to the given one.
     * 
     * @param point the given point to compare the actual point to.
     * @return this assertion object.
     * @throws AssertionError - if the actual point is not equal to the given one.
     */
    public Point3fAssert isEqualTo(Point3f point) {
        // check that actual Point3f we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected point to be <%s> but was <%s>", point, actual);

        // check that actual point is (epsilon) equal to the given point
        Assertions.assertThat(actual.epsilonEquals(point, 0.0001f)).overridingErrorMessage(errorMessage).isTrue();

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual point is with a cube defined by the 2 given points.
     * 
     * @param boundsStartPoint the start point of the bound range
     * @param boundsEndPoint the end point of the bound range
     * @return this assertion object.
     * @throws AssertionError - if the actual point is not within the given bound range.
     */
    public Point3fAssert isWithin(Point3f boundsStartPoint, Point3f boundsEndPoint) {
        float minX = Math.min(boundsStartPoint.getX(), boundsEndPoint.getX());
        float maxX = Math.max(boundsStartPoint.getX(), boundsEndPoint.getX());
        float minY = Math.min(boundsStartPoint.getY(), boundsEndPoint.getY());
        float maxY = Math.max(boundsStartPoint.getY(), boundsEndPoint.getY());
        float minZ = Math.min(boundsStartPoint.getZ(), boundsEndPoint.getZ());
        float maxZ = Math.max(boundsStartPoint.getZ(), boundsEndPoint.getZ());

        Assertions.assertThat(actual.getX()).isGreaterThanOrEqualTo(minX);
        Assertions.assertThat(actual.getX()).isLessThanOrEqualTo(maxX);
        Assertions.assertThat(actual.getY()).isGreaterThanOrEqualTo(minY);
        Assertions.assertThat(actual.getY()).isLessThanOrEqualTo(maxY);
        Assertions.assertThat(actual.getZ()).isGreaterThanOrEqualTo(minZ);
        Assertions.assertThat(actual.getZ()).isLessThanOrEqualTo(maxZ);

        return this;
    }

}
