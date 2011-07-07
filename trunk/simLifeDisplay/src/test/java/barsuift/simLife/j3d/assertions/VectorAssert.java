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

import javax.vecmath.Vector3f;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;


public class VectorAssert extends GenericAssert<VectorAssert, Vector3f> {

    /**
     * Creates a new </code>{@link VectorAssert}</code> to make assertions on actual Vector3f.
     * 
     * @param actual the Vector3f we want to make assertions on.
     */
    public VectorAssert(Vector3f actual) {
        super(VectorAssert.class, actual);
    }

    /**
     * An entry vector for VectorAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(vector).isEqualTo(anotherVector);</code>
     * 
     * @param actual the Vector3f we want to make assertions on.
     * @return a new </code>{@link VectorAssert}</code>
     */
    public static VectorAssert assertThat(Vector3f actual) {
        return new VectorAssert(actual);
    }

    /**
     * Verifies that the actual vector is (epsilon) equal to the given one.
     * 
     * @param vector the given vector to compare the actual vector to.
     * @return this assertion object.
     * @throws AssertionError - if the actual vector is not equal to the given one.
     */
    public VectorAssert isEqualTo(Vector3f vector) {
        // check that actual Vector3f we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected vector to be <%s> but was <%s>", vector, actual);

        // check that actual vector is (epsilon) equal to the given vector
        Assertions.assertThat(actual.epsilonEquals(vector, 0.0001f)).overridingErrorMessage(errorMessage).isTrue();

        // return the current assertion for method chaining
        return this;
    }


}
