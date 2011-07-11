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

import org.fest.assertions.Assertions;
import org.fest.assertions.Delta;
import org.fest.assertions.GenericAssert;

import com.sun.j3d.utils.geometry.Cylinder;


public class CylinderAssert extends GenericAssert<CylinderAssert, Cylinder> {

    /**
     * Creates a new </code>{@link CylinderAssert}</code> to make assertions on actual Cylinder.
     * 
     * @param actual the Cylinder we want to make assertions on.
     */
    public CylinderAssert(Cylinder actual) {
        super(CylinderAssert.class, actual);
    }

    /**
     * An entry cylinder for CylinderAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(cylinder).hasRadius(radius);</code>
     * 
     * @param actual the Cylinder we want to make assertions on.
     * @return a new </code>{@link CylinderAssert}</code>
     */
    public static CylinderAssert assertThat(Cylinder actual) {
        return new CylinderAssert(actual);
    }

    /**
     * Verifies that the actual cylinder radius is equal to the given one.
     * 
     * @param radius the given cylinder radius to compare the actual cylinder radius to.
     * @return this assertion object.
     * @throws AssertionError - if the actual cylinder radius is not equal to the given one.
     */
    public CylinderAssert hasRadius(float radius) {
        // check that actual Cylinder we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected cylinder radius to be <%s> but was <%s>", radius,
                actual.getRadius());

        // check that actual cylinder radius is equal to the given radius
        Assertions.assertThat(actual.getRadius()).overridingErrorMessage(errorMessage)
                .isEqualTo(radius, Delta.delta(0.0001));

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual cylinder height is equal to the given one.
     * 
     * @param height the given cylinder height to compare the actual cylinder height to.
     * @return this assertion object.
     * @throws AssertionError - if the actual cylinder height is not equal to the given one.
     */
    public CylinderAssert hasHeight(float height) {
        // check that actual Cylinder we want to make assertions on is not null.
        isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected cylinder height to be <%s> but was <%s>", height,
                actual.getHeight());

        // check that actual cylinder height is equal to the given height
        Assertions.assertThat(actual.getHeight()).overridingErrorMessage(errorMessage)
                .isEqualTo(height, Delta.delta(0.0001));

        // return the current assertion for method chaining
        return this;
    }

}
