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

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import barsuift.simLife.j3d.AppearanceFactory;

import com.sun.j3d.utils.geometry.Sphere;

// FIXME transform the disk in a sphere and the method to move the central point and it should be fine
public class SunDisk3D {

    private static final float Z_POSITION = 0.15f;

    private final Sphere sphere;

    private final GeometryArray geometry;

    public SunDisk3D() {
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
            coordinate.z += Z_POSITION;
            geometry.setCoordinate(i, coordinate);
        }
    }

    public Group getGroup() {
        return sphere;
    }

    // TODO this is a test method
    public void moveGeom(float diff) {
        float[] coords = new float[geometry.getVertexCount() * 3];
        geometry.getCoordinates(0, coords);
        for (int i = 0; i < geometry.getVertexCount(); i++) {
            // coords[i * 3 + 1] += 0.001;
            coords[i * 3 + 1] += diff / 10;

        }
        geometry.setCoordinates(0, coords);
    }

}
