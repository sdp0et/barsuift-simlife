/**
 * barsuift-simlife is a life simulator programm
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

import junit.framework.TestCase;

public class AppearanceFactoryTest extends TestCase {

    public void testSetColorWithColoringAttributes() {
        Color3f actualColor = new Color3f(0.1f, 0.2f, 0.3f);
        Appearance appearance = new Appearance();
        AppearanceFactory.setColorWithColoringAttributes(appearance, actualColor);
        Color3f expectedColor = new Color3f();
        assertFalse(expectedColor.equals(actualColor));
        appearance.getColoringAttributes().getColor(expectedColor);
        assertEquals(expectedColor, actualColor);
    }

    public void testSetColorWithMaterial() {
        Color3f actualAmbientColor = new Color3f(0.1f, 0.2f, 0.3f);
        Color3f actualDiffuseColor = new Color3f(0.2f, 0.4f, 0.6f);
        Color3f actualSpecularColor = new Color3f(0.3f, 0.6f, 0.9f);
        Appearance appearance = new Appearance();
        AppearanceFactory.setColorWithMaterial(appearance, actualAmbientColor, actualDiffuseColor, actualSpecularColor);
        Color3f expectedAmbientColor = new Color3f();
        Color3f expectedDiffuseColor = new Color3f();
        Color3f expectedSpecularColor = new Color3f();
        assertFalse(expectedAmbientColor.equals(actualAmbientColor));
        assertFalse(expectedDiffuseColor.equals(actualDiffuseColor));
        assertFalse(expectedSpecularColor.equals(actualSpecularColor));
        appearance.getMaterial().getAmbientColor(expectedAmbientColor);
        appearance.getMaterial().getDiffuseColor(expectedDiffuseColor);
        appearance.getMaterial().getSpecularColor(expectedSpecularColor);
        assertTrue(expectedAmbientColor.equals(actualAmbientColor));
        assertTrue(expectedDiffuseColor.equals(actualDiffuseColor));
        assertTrue(expectedSpecularColor.equals(actualSpecularColor));
    }

    public void testSetCullFace() {
        Appearance appearance = new Appearance();
        assertNull(appearance.getPolygonAttributes());

        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_NONE);
        assertEquals(PolygonAttributes.CULL_NONE, appearance.getPolygonAttributes().getCullFace());
        assertEquals(true, appearance.getPolygonAttributes().getBackFaceNormalFlip());

        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_BACK);
        assertEquals(PolygonAttributes.CULL_BACK, appearance.getPolygonAttributes().getCullFace());
        assertEquals(false, appearance.getPolygonAttributes().getBackFaceNormalFlip());

        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_NONE);
        assertEquals(true, appearance.getPolygonAttributes().getBackFaceNormalFlip());

        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_FRONT);
        assertEquals(PolygonAttributes.CULL_FRONT, appearance.getPolygonAttributes().getCullFace());
        assertEquals(false, appearance.getPolygonAttributes().getBackFaceNormalFlip());
    }

}
