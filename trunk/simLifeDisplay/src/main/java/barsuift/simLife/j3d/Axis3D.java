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
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.vecmath.Point3f;

public class Axis3D extends Shape3D {

    private final Point3f startPoint;

    private final Point3f endPoint;

    public Axis3D(Axis axisType) {
        this.startPoint = new Point3f(0, 0, 0);
        this.endPoint = new Point3f(axisType == Axis.X ? 5 : 0, axisType == Axis.Y ? 5 : 0, axisType == Axis.Z ? 5 : 0);
        createAxis();
    }

    private LineArray createAxisGeometry() {
        LineArray axisLine = new LineArray(2, GeometryArray.COORDINATES);
        axisLine.setCoordinate(0, startPoint);
        axisLine.setCoordinate(1, endPoint);
        return axisLine;
    }

    private void createAxis() {
        LineArray axisLine = createAxisGeometry();
        Appearance axisAppearance = new Appearance();
        setGeometry(axisLine);
        setAppearance(axisAppearance);
    }

}
