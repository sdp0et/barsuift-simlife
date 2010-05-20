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
