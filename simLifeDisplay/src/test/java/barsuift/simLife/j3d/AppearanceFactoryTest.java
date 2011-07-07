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
package barsuift.simLife.j3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.PolygonAttributes;
import javax.vecmath.Color3f;

import org.testng.annotations.Test;

import static barsuift.simLife.j3d.assertions.AppearanceAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;

public class AppearanceFactoryTest {

    @Test
    public void testSetColorWithColoringAttributes() {
        Color3f actualColor = new Color3f(0.1f, 0.2f, 0.3f);
        Appearance appearance = new Appearance();
        AppearanceFactory.setColorWithColoringAttributes(appearance, actualColor);
        Color3f expectedColor = new Color3f();
        assertThat(actualColor).isNotEqualTo(expectedColor);
        appearance.getColoringAttributes().getColor(expectedColor);
        assertThat(actualColor).isEqualTo(expectedColor);
    }

    @Test
    public void testSetColorWithMaterial() {
        Color3f ambientColor = new Color3f(0.1f, 0.2f, 0.3f);
        Color3f diffuseColor = new Color3f(0.2f, 0.4f, 0.6f);
        Color3f specularColor = new Color3f(0.3f, 0.6f, 0.9f);
        Appearance appearance = new Appearance();
        AppearanceFactory.setColorWithMaterial(appearance, ambientColor, diffuseColor, specularColor);

        assertThat(appearance).hasAmbientColor(ambientColor);
        assertThat(appearance).hasSpecularColor(specularColor);
        assertThat(appearance).hasDiffuseColor(diffuseColor);
    }

    @Test
    public void testSetCullFace() {
        Appearance appearance = new Appearance();
        assertThat(appearance.getPolygonAttributes()).isNull();

        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_NONE);
        assertThat(appearance.getPolygonAttributes().getCullFace()).isEqualTo(PolygonAttributes.CULL_NONE);
        assertThat(appearance.getPolygonAttributes().getBackFaceNormalFlip()).isTrue();

        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_BACK);
        assertThat(appearance.getPolygonAttributes().getCullFace()).isEqualTo(PolygonAttributes.CULL_BACK);
        assertThat(appearance.getPolygonAttributes().getBackFaceNormalFlip()).isFalse();

        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_NONE);
        assertThat(appearance.getPolygonAttributes().getBackFaceNormalFlip()).isTrue();

        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_FRONT);
        assertThat(appearance.getPolygonAttributes().getCullFace()).isEqualTo(PolygonAttributes.CULL_FRONT);
        assertThat(appearance.getPolygonAttributes().getBackFaceNormalFlip()).isFalse();
    }

}
