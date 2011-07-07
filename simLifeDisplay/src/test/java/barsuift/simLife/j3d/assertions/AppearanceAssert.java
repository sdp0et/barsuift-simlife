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

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;


public class AppearanceAssert extends GenericAssert<AppearanceAssert, Appearance> {

    /**
     * Creates a new </code>{@link AppearanceAssert}</code> to make assertions on actual Appearance.
     * 
     * @param actual the Appearance we want to make assertions on.
     */
    public AppearanceAssert(Appearance actual) {
        super(AppearanceAssert.class, actual);
    }

    /**
     * An entry point for AppearanceAssert to follow Fest standard <code>assertThat()</code> statements.<br>
     * With a static import, one's can write directly : <code>assertThat(appearance).hasAmbientColor(color);</code>
     * 
     * @param actual the Appearance we want to make assertions on.
     * @return a new </code>{@link AppearanceAssert}</code>
     */
    public static AppearanceAssert assertThat(Appearance actual) {
        return new AppearanceAssert(actual);
    }

    /**
     * Verifies that the actual appearance ambient color is equal to the given one.
     * 
     * @param color the given color to compare the actual appearance ambient color to.
     * @return this assertion object.
     * @throws AssertionError - if the actual appearance ambient color is not equal to the given one.
     */
    public AppearanceAssert hasAmbientColor(Color3f color) {
        // check that actual Appearance we want to make assertions on is not null.
        isNotNull();

        // check that it has a material property
        Material material = actual.getMaterial();
        Assertions.assertThat(material).isNotNull();

        // get the actual ambient color
        Color3f actualAmbientColor = new Color3f();
        material.getAmbientColor(actualAmbientColor);

        // check the actual ambient color is not null
        Assertions.assertThat(actualAmbientColor).isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected appearance ambient color to be <%s> but was <%s>", color,
                actualAmbientColor);

        // check that actual appearance ambient color is equal to the given color
        Assertions.assertThat(actualAmbientColor.epsilonEquals(color, 0.001f)).overridingErrorMessage(errorMessage)
                .isTrue();

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual appearance specular color is equal to the given one.
     * 
     * @param color the given color to compare the actual appearance specular color to.
     * @return this assertion object.
     * @throws AssertionError - if the actual appearance specular color is not equal to the given one.
     */
    public AppearanceAssert hasSpecularColor(Color3f color) {
        // check that actual Appearance we want to make assertions on is not null.
        isNotNull();

        // check that it has a material property
        Material material = actual.getMaterial();
        Assertions.assertThat(material).isNotNull();

        // get the actual specular color
        Color3f actualSpecularColor = new Color3f();
        material.getSpecularColor(actualSpecularColor);

        // check the actual specular color is not null
        Assertions.assertThat(actualSpecularColor).isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected appearance specular color to be <%s> but was <%s>", color,
                actualSpecularColor);

        // check that actual appearance ambient color is equal to the given color
        Assertions.assertThat(actualSpecularColor.epsilonEquals(color, 0.001f)).overridingErrorMessage(errorMessage)
                .isTrue();

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual appearance diffuse color is equal to the given one.
     * 
     * @param color the given color to compare the actual appearance diffuse color to.
     * @return this assertion object.
     * @throws AssertionError - if the actual appearance diffuse color is not equal to the given one.
     */
    public AppearanceAssert hasDiffuseColor(Color3f color) {
        // check that actual Appearance we want to make assertions on is not null.
        isNotNull();

        // check that it has a material property
        Material material = actual.getMaterial();
        Assertions.assertThat(material).isNotNull();

        // get the actual diffuse color
        Color3f actualDiffuseColor = new Color3f();
        material.getDiffuseColor(actualDiffuseColor);

        // check the actual diffuse color is not null
        Assertions.assertThat(actualDiffuseColor).isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected appearance diffuse color to be <%s> but was <%s>", color,
                actualDiffuseColor);

        // check that actual appearance diffuse color is equal to the given color
        Assertions.assertThat(actualDiffuseColor.epsilonEquals(color, 0.001f)).overridingErrorMessage(errorMessage)
                .isTrue();

        // return the current assertion for method chaining
        return this;
    }

    /**
     * Verifies that the actual appearance coloring attributes color is equal to the given one.
     * 
     * @param color the given color to compare the actual appearance coloring attributes color to.
     * @return this assertion object.
     * @throws AssertionError - if the actual appearance coloring attributes color is not equal to the given one.
     */
    public AppearanceAssert hasColoringAttributesColor(Color3f color) {
        // check that actual Appearance we want to make assertions on is not null.
        isNotNull();

        // check that it has a coloring attributes property
        ColoringAttributes coloringAttributes = actual.getColoringAttributes();
        Assertions.assertThat(coloringAttributes).isNotNull();

        // get the actual color
        Color3f actualColor = new Color3f();
        coloringAttributes.getColor(actualColor);

        // check the actual color is not null
        Assertions.assertThat(actualColor).isNotNull();

        // we overrides the default error message with a more explicit one
        String errorMessage = String.format("Expected appearance coloring attributes color to be <%s> but was <%s>",
                color, actualColor);

        // check that actual color is equal to the given color
        Assertions.assertThat(actualColor.epsilonEquals(color, 0.001f)).overridingErrorMessage(errorMessage).isTrue();

        // return the current assertion for method chaining
        return this;
    }

}
