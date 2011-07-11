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

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;


public class Transform3DAssert extends GenericAssert<Transform3DAssert, Transform3D> {

    /**
     * Creates a new </code>{@link Transform3DAssert}</code> to make assertions on actual Transform3D.
     * 
     * @param actual the Transform3D we want to make assertions on.
     */
    public Transform3DAssert(Transform3D actual) {
        super(Transform3DAssert.class, actual);
    }

    /**
     * An entry transform for Transform3DAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(transform).isEqualTo(anotherTransform);</code>
     * 
     * @param actual the Transform3D we want to make assertions on.
     * @return a new </code>{@link Transform3DAssert}</code>
     */
    public static Transform3DAssert assertThat(Transform3D actual) {
        return new Transform3DAssert(actual);
    }

    /**
     * Verifies that the actual transform is (epsilon) equal to the given one.
     * 
     * @param transform the given transform to compare the actual transform to.
     * @return this assertion object.
     * @throws AssertionError - if the actual transform is not equal to the given one.
     */
    public Transform3DAssert isEqualTo(Transform3D transform) {
        // check that actual Transform3D we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected transform to be <%s> but was <%s>", transform, actual);

        // check that actual transform is (epsilon) equal to the given transform
        Assertions.assertThat(actual.epsilonEquals(transform, 0.0001f)).overridingErrorMessage(errorMessage).isTrue();

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual transform translation is (epsilon) equal to the given one.
     * 
     * @param transform the given translation to compare the actual transform translation to.
     * @return this assertion object.
     * @throws AssertionError - if the actual transform translation is not equal to the given one.
     */
    public Transform3DAssert hasTranslation(Vector3f translation) {
        // check that actual Transform3D we want to make assertions on is not null.
        isNotNull();

        Vector3f actualTranslation = new Vector3f();
        actual.get(actualTranslation);

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected translation to be <%s> but was <%s>", translation,
                actualTranslation);

        // check that actual transform is (epsilon) equal to the given transform
        VectorAssert.assertThat(actualTranslation).overridingErrorMessage(errorMessage).isEqualTo(translation);

        // return the current assertion for method chaining
        return this;
    }

}
