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
package barsuift.simLife.j3d.environment;

import java.util.Arrays;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import barsuift.simLife.j3d.AppearanceFactory;

import com.sun.j3d.utils.geometry.Sphere;

public class SunSphere3D {

    private static final float SHIFT = 0.15f;

    private final Sphere sphere;

    private final GeometryArray geometry;

    private final float[] initialCoords;

    private final float latitude;

    private final float solsticeMaxShift;

    public SunSphere3D(float latitude, float eclipticObliquity) {
        Appearance sunSphereAppearance = new Appearance();
        AppearanceFactory.setColorWithColoringAttributes(sunSphereAppearance, new Color3f(1.0f, 1.0f, 0.5f));
        this.sphere = new Sphere(0.01f, sunSphereAppearance);

        this.solsticeMaxShift = SHIFT * (float) Math.tan(eclipticObliquity);
        this.latitude = latitude;

        Shape3D shape = sphere.getShape();
        this.geometry = (GeometryArray) shape.getGeometry();
        // this is to allow coordinate writing
        geometry.setCapability(TriangleFanArray.ALLOW_COORDINATE_WRITE);
        // initial positioning of the sphere geometry
        for (int i = 0; i < geometry.getVertexCount(); i++) {
            Point3f coordinate = new Point3f();
            geometry.getCoordinate(i, coordinate);
            coordinate.y -= SHIFT * Math.cos(latitude);
            coordinate.z += SHIFT * Math.sin(latitude);
            geometry.setCoordinate(i, coordinate);
        }

        initialCoords = new float[geometry.getVertexCount() * 3];
        geometry.getCoordinates(0, initialCoords);
    }

    public Group getGroup() {
        return sphere;
    }

    // TODO unit test
    public void updateForEclipticShift(float earthRevolution) {
        float solsticeShift = -solsticeMaxShift * (float) Math.cos(earthRevolution);
        float[] coords = Arrays.copyOf(initialCoords, initialCoords.length);
        for (int i = 0; i < geometry.getVertexCount(); i++) {
            // shift Y position
            coords[i * 3 + 1] += solsticeShift * (float) Math.sin(latitude) * Math.cos(solsticeShift);
            // shift Z position
            coords[i * 3 + 2] += solsticeShift * (float) Math.cos(latitude) * Math.cos(solsticeShift);
        }
        geometry.setCoordinates(0, coords);
    }

}
