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

import javax.vecmath.Matrix3d;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;


public class Matrix3dAssert extends GenericAssert<Matrix3dAssert, Matrix3d> {

    /**
     * Creates a new </code>{@link Matrix3dAssert}</code> to make assertions on actual Matrix3d.
     * 
     * @param actual the Matrix3d we want to make assertions on.
     */
    public Matrix3dAssert(Matrix3d actual) {
        super(Matrix3dAssert.class, actual);
    }

    /**
     * An entry matrix for Matrix3dAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(matrix).isEqualTo(anotherMatrix);</code>
     * 
     * @param actual the Matrix3d we want to make assertions on.
     * @return a new </code>{@link Matrix3dAssert}</code>
     */
    public static Matrix3dAssert assertThat(Matrix3d actual) {
        return new Matrix3dAssert(actual);
    }

    /**
     * Verifies that the actual matrix is (epsilon) equal to the given one.
     * 
     * @param matrix the given matrix to compare the actual matrix to.
     * @return this assertion object.
     * @throws AssertionError - if the actual matrix is not equal to the given one.
     */
    public Matrix3dAssert isEqualTo(Matrix3d matrix) {
        // check that actual Matrix3d we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected matrix to be <%s> but was <%s>", matrix, actual);

        // check that actual matrix is (epsilon) equal to the given matrix
        Assertions.assertThat(actual.epsilonEquals(matrix, 0.0001f)).overridingErrorMessage(errorMessage).isTrue();

        // return the current assertion for method chaining
        return this;
    }


}
