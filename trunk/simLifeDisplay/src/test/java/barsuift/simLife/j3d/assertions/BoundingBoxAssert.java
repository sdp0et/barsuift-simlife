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

import javax.media.j3d.BoundingBox;
import javax.vecmath.Point3d;

import org.fest.assertions.Delta;
import org.fest.assertions.GenericAssert;


public class BoundingBoxAssert extends GenericAssert<BoundingBoxAssert, BoundingBox> {

    /**
     * Creates a new </code>{@link BoundingBoxAssert}</code> to make assertions on actual BoundingBox.
     * 
     * @param actual the BoundingBox we want to make assertions on.
     */
    public BoundingBoxAssert(BoundingBox actual) {
        super(BoundingBoxAssert.class, actual);
    }

    /**
     * An entry bound for BoundAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(bound).hasLowerBound(lowerPoint);</code>
     * 
     * @param actual the BoundingBox we want to make assertions on.
     * @return a new </code>{@link BoundingBoxAssert}</code>
     */
    public static BoundingBoxAssert assertThat(BoundingBox actual) {
        return new BoundingBoxAssert(actual);
    }

    /**
     * Verifies that the actual lower bound is (epsilon) equal to the given one.
     * 
     * @param lower the given lower bound to compare the actual lower bound to.
     * @return this assertion object.
     * @throws AssertionError - if the actual lower bound is not equal to the given one.
     */
    public BoundingBoxAssert hasLowerBound(Point3d lower) {
        // check that actual BoundingBox we want to make assertions on is not null.
        isNotNull();

        Point3d actualLower = new Point3d();
        actual.getLower(actualLower);

        // check that actual bound is (epsilon) equal to the given bound
        Point3dAssert.assertThat(actualLower).isEqualTo(lower);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual lower bound is (epsilon) equal to the given one.
     * 
     * @param lower the given lower bound to compare the actual lower bound to.
     * @return this assertion object.
     * @throws AssertionError - if the actual lower bound is not equal to the given one.
     */
    public BoundingBoxAssert hasLowerBound(Point3d lower, Delta deltaX, Delta deltaY, Delta deltaZ) {
        // check that actual BoundingBox we want to make assertions on is not null.
        isNotNull();

        Point3d actualLower = new Point3d();
        actual.getLower(actualLower);

        // check that actual bound is (epsilon) equal to the given bound
        Point3dAssert.assertThat(actualLower).isEqualTo(lower, deltaX, deltaY, deltaZ);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual upper bound is (epsilon) equal to the given one.
     * 
     * @param upper the given upper bound to compare the actual upper bound to.
     * @return this assertion object.
     * @throws AssertionError - if the actual upper bound is not equal to the given one.
     */
    public BoundingBoxAssert hasUpperBound(Point3d upper) {
        // check that actual BoundingBox we want to make assertions on is not null.
        isNotNull();

        Point3d actualUpper = new Point3d();
        actual.getUpper(actualUpper);

        // check that actual bound is (epsilon) equal to the given bound
        Point3dAssert.assertThat(actualUpper).isEqualTo(upper);

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual upper bound is (epsilon) equal to the given one.
     * 
     * @param upper the given upper bound to compare the actual upper bound to.
     * @return this assertion object.
     * @throws AssertionError - if the actual upper bound is not equal to the given one.
     */
    public BoundingBoxAssert hasUpperBound(Point3d upper, Delta deltaX, Delta deltaY, Delta deltaZ) {
        // check that actual BoundingBox we want to make assertions on is not null.
        isNotNull();

        Point3d actualUpper = new Point3d();
        actual.getUpper(actualUpper);

        // check that actual bound is (epsilon) equal to the given bound
        Point3dAssert.assertThat(actualUpper).isEqualTo(upper, deltaX, deltaY, deltaZ);

        // return the current assertion for method chaining
        return this;
    }

}
