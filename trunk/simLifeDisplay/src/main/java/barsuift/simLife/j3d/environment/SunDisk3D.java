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
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

import barsuift.simLife.j3d.AppearanceFactory;

// FIXME transform the disk in a sphere and the method to move the central point and it should be fine
public class SunDisk3D extends Shape3D {

    private static final int NB_TRIANGLES_IN_FAN = 24;

    private static final double ANGLE = 2 * Math.PI / NB_TRIANGLES_IN_FAN;

    private static final float Z_POSITION = 0.1f;

    public SunDisk3D() {
        Geometry disk = createDiskGeometry();
        Appearance appearance = new Appearance();
        AppearanceFactory.setColorWithColoringAttributes(appearance, new Color3f(1.0f, 1.0f, 0.5f));
        AppearanceFactory.setCullFace(appearance, PolygonAttributes.CULL_NONE);
        setGeometry(disk);
        setAppearance(appearance);
    }

    private Geometry createDiskGeometry() {
        TriangleFanArray disk = new TriangleFanArray(NB_TRIANGLES_IN_FAN + 2, GeometryArray.COORDINATES,
                new int[] { NB_TRIANGLES_IN_FAN + 2 });
        disk.setCapability(TriangleFanArray.ALLOW_COORDINATE_WRITE);
        disk.setCoordinate(0, new Point3d(0, 0, Z_POSITION));
        for (int i = 0; i <= NB_TRIANGLES_IN_FAN; i++) {
            float xPos = (float) (Math.cos(i * ANGLE) / 100);
            float yPos = (float) (Math.sin(i * ANGLE) / 100);
            Point3f coordinate = new Point3f(xPos, yPos, Z_POSITION);
            disk.setCoordinate(i + 1, coordinate);
        }

        return disk;
    }

    // TODO this is a test method
    public void moveGeom() {
        TriangleFanArray geom = (TriangleFanArray) getGeometry();
        for (int i = 0; i < NB_TRIANGLES_IN_FAN + 2; i++) {
            Point3f coordinate = new Point3f();
            geom.getCoordinate(i, coordinate);
            System.out.println("old coord=" + coordinate);
            coordinate.y -= 0.001;
            geom.setCoordinate(i, coordinate);
        }

    }

}
