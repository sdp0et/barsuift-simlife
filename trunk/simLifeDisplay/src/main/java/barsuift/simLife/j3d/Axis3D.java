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
import javax.vecmath.Point3d;

public class Axis3D extends Shape3D {

    private final Point3d startPoint;

    private final Point3d endPoint;

    public Axis3D(Axis axisType) {
        this.startPoint = new Point3d(0, 0, 0);
        this.endPoint = new Point3d(axisType == Axis.X ? 5 : 0, axisType == Axis.Y ? 5 : 0, axisType == Axis.Z ? 5 : 0);
        createBranchShape();
    }

    private LineArray createBranchLine() {
        LineArray branchLine = new LineArray(2, GeometryArray.COORDINATES);
        branchLine.setCoordinate(0, startPoint);
        branchLine.setCoordinate(1, endPoint);
        return branchLine;
    }

    private void createBranchShape() {
        LineArray branchLine = createBranchLine();
        Appearance branchAppearance = new Appearance();
        setGeometry(branchLine);
        setAppearance(branchAppearance);
    }

}
