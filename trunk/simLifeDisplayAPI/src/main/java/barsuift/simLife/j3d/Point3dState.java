package barsuift.simLife.j3d;

import javax.vecmath.Point3d;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Point3dState {

    private double x;

    private double y;

    private double z;

    public Point3dState() {
        super();
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point3dState(double x, double y, double z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3dState(Point3d point) {
        super();
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
    }

    public Point3dState(Point3dState copy) {
        super();
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
    }

    public Point3d toPointValue() {
        return new Point3d(x, y, z);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point3dState other = (Point3dState) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Point3dState [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

}
