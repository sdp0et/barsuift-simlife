package barsuift.simLife.j3d.helper;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

import junit.framework.Assert;

public final class ColorTestHelper extends Assert {

    private ColorTestHelper() {
        // private constructor to enforce static access
    }

    /**
     * Test the given appearance is not null, and that the associated color is equals to the given expected one
     * 
     * @param appearance the appearance to test
     * @param expectedColor the expected color
     */
    public static void testColorFromColoringAttributes(Appearance appearance, Color3f expectedColor) {
        assertNotNull(appearance);
        Color3f actualColor = new Color3f();
        assertFalse(expectedColor.equals(actualColor));
        appearance.getColoringAttributes().getColor(actualColor);
        assertEquals(expectedColor, actualColor);
    }

    /**
     * Test the given appearance is not null, and has a not null mateterial, and that the associated colors (ambient,
     * specular, diffuse) are equals to the given expected one
     * 
     * @param appearance the appearance to test
     * @param expectedAmbientColor the expected ambient color
     * @param expectedSpecularColor the expected specular color
     * @param expectedDiffuseColor the expected diffuse color
     */
    public static void testColorFromMaterial(Appearance appearance, Color3f expectedAmbientColor,
            Color3f expectedSpecularColor, Color3f expectedDiffuseColor) {
        assertNotNull(appearance);
        Material material = appearance.getMaterial();
        assertNotNull(material);

        Color3f actualAmbientColor = new Color3f();
        material.getAmbientColor(actualAmbientColor);
        assertEquals(expectedAmbientColor, actualAmbientColor);

        Color3f actualSpecularColor = new Color3f();
        material.getSpecularColor(actualSpecularColor);
        assertEquals(expectedSpecularColor, actualSpecularColor);

        Color3f actualDiffuseColor = new Color3f();
        material.getDiffuseColor(actualDiffuseColor);
        assertEquals(expectedDiffuseColor, actualDiffuseColor);
    }

}
