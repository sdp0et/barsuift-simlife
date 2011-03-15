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

    private static final float Y_POSITION = -0.15f;

    private final Sphere sphere;

    private final GeometryArray geometry;

    private final float[] initialCoords;

    public SunSphere3D() {
        Appearance sunSphereAppearance = new Appearance();
        AppearanceFactory.setColorWithColoringAttributes(sunSphereAppearance, new Color3f(1.0f, 1.0f, 0.5f));
        this.sphere = new Sphere(0.01f, sunSphereAppearance);

        Shape3D shape = sphere.getShape();
        this.geometry = (GeometryArray) shape.getGeometry();
        // this is to allow coordinate writing
        geometry.setCapability(TriangleFanArray.ALLOW_COORDINATE_WRITE);
        // initial positioning of the sphere geometry
        for (int i = 0; i < geometry.getVertexCount(); i++) {
            Point3f coordinate = new Point3f();
            geometry.getCoordinate(i, coordinate);
            coordinate.y += Y_POSITION;
            geometry.setCoordinate(i, coordinate);
        }

        initialCoords = new float[geometry.getVertexCount() * 3];
        geometry.getCoordinates(0, initialCoords);
    }

    public Group getGroup() {
        return sphere;
    }

    // TODO this is a test method
    // FIXME 000. fist thing is to put a earth ecliptic angle parameter
    public void moveGeom(float yPosition) {
        float[] coords = Arrays.copyOf(initialCoords, initialCoords.length);
        // float[] coords = new float[geometry.getVertexCount() * 3];
        // geometry.getCoordinates(0, coords);
        for (int i = 0; i < geometry.getVertexCount(); i++) {
            // move the Z coordinates
            // coords[i * 3 + 2] += yPosition / 10;
            // 0.065
            coords[i * 3 + 2] += yPosition * 0.065f;

        }
        geometry.setCoordinates(0, coords);
    }

}
