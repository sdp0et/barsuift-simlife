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
import javax.media.j3d.Shape3D;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import barsuift.simLife.j3d.AppearanceFactory;

public class SunDisk3D extends Shape3D {

    private static final int NB_TRIANGLES_IN_FAN = 24;

    private static final double ANGLE = 2 * Math.PI / NB_TRIANGLES_IN_FAN;

    public SunDisk3D() {
        Geometry disk = createDiskGeometry();
        Appearance appearance = new Appearance();
        AppearanceFactory.setColorWithColoringAttributes(appearance, new Color3f(1.0f, 1.0f, 0.5f));
        setGeometry(disk);
        setAppearance(appearance);
    }

    private Geometry createDiskGeometry() {
        TriangleFanArray disk = new TriangleFanArray(NB_TRIANGLES_IN_FAN + 2, GeometryArray.COORDINATES,
                new int[] { NB_TRIANGLES_IN_FAN + 2 });
        disk.setCoordinate(0, new Point3d(0, 0, -0.1));
        for (int i = 0; i <= NB_TRIANGLES_IN_FAN; i++) {
            Point3d coordinate = new Point3d(Math.cos(i * ANGLE) / 100, Math.sin(i * ANGLE) / 100, -0.1);
            disk.setCoordinate(i + 1, coordinate);
        }

        return disk;
    }

}
