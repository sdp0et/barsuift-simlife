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

import javax.vecmath.Color3f;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;


public class ColorAssert extends GenericAssert<ColorAssert, Color3f> {

    /**
     * Creates a new </code>{@link ColorAssert}</code> to make assertions on actual Color3f.
     * 
     * @param actual the Color3f we want to make assertions on.
     */
    public ColorAssert(Color3f actual) {
        super(ColorAssert.class, actual);
    }

    /**
     * An entry point for ColorAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(color).isEqualTo(anotherColor);</code>
     * 
     * @param actual the Color3f we want to make assertions on.
     * @return a new </code>{@link ColorAssert}</code>
     */
    public static ColorAssert assertThat(Color3f actual) {
        return new ColorAssert(actual);
    }

    /**
     * Verifies that the actual color is (epsilon) equal to the given one.
     * 
     * @param color the given color to compare the actual color to.
     * @return this assertion object.
     * @throws AssertionError - if the actual color is not equal to the given one.
     */
    public ColorAssert isEqualTo(Color3f color) {
        // check that actual Color3f we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected color to be <%s> but was <%s>", color, actual);

        // check that actual color is (epsilon) equal to the given color
        Assertions.assertThat(actual.epsilonEquals(color, 0.001f)).overridingErrorMessage(errorMessage).isTrue();

        // return the current assertion for method chaining
        return this;
    }


}
