package barsuift.simLife.j3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.vecmath.Color3f;

public final class AppearanceFactory {

    private AppearanceFactory() {
        // private constructor to enforce static access
    }

    /**
     * Set the given color to the given appearance with coloring attributes
     * 
     * @param appearance the appearance
     * @param color the color
     */
    public static void setColorWithColoringAttributes(Appearance appearance, Color3f color) {
        ColoringAttributes coloringAttributes = new ColoringAttributes();
        coloringAttributes.setColor(color);
        appearance.setColoringAttributes(coloringAttributes);
    }

    /**
     * Set the given colors to the given appearance using a material instance
     * 
     * @param appearance the appearance
     * @param ambientColor the expected ambient color
     * @param diffuseColor the expected diffuse color
     * @param specularColor the expected specular color
     */
    public static void setColorWithMaterial(Appearance appearance, Color3f ambientColor,
            Color3f diffuseColor, Color3f specularColor) {
        Material material = new Material();
        material.setAmbientColor(ambientColor);
        material.setDiffuseColor(diffuseColor);
        material.setSpecularColor(specularColor);
        appearance.setMaterial(material);
    }

    public static void setCullFace(Appearance appearance, int cullFace) {
        PolygonAttributes polygonAttributes = new PolygonAttributes();
        polygonAttributes.setCullFace(cullFace);
        if (cullFace == PolygonAttributes.CULL_NONE) {
            polygonAttributes.setBackFaceNormalFlip(true);
        }
        appearance.setPolygonAttributes(polygonAttributes);
    }

}
