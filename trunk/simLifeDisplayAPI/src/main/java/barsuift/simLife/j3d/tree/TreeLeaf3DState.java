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
package barsuift.simLife.j3d.tree;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.j3d.Point3dState;

@XmlRootElement
public class TreeLeaf3DState {

    /**
     * Leaf attach point, relative to the branch part
     */
    private Point3dState leafAttachPoint;

    /**
     * End point 1 at the creation of the leaf (its birth end point 1). The point is relative to the attach point.
     */
    private Point3dState initialEndPoint1;

    /**
     * End point 2 at the creation of the leaf (its birth end point 2). The point is relative to the attach point.
     */
    private Point3dState initialEndPoint2;

    /**
     * Current end point 1. The point is relative to the attach point.
     */
    private Point3dState endPoint1;

    /**
     * Current end point 2. The point is relative to the attach point.
     */
    private Point3dState endPoint2;

    /**
     * Leaf rotation (in radian)
     */
    private double rotation;

    public TreeLeaf3DState() {
        super();
        this.leafAttachPoint = new Point3dState();
        this.initialEndPoint1 = new Point3dState();
        this.initialEndPoint2 = new Point3dState();
        this.endPoint1 = new Point3dState();
        this.endPoint2 = new Point3dState();
        this.rotation = 0;
    }

    public TreeLeaf3DState(Point3dState leafAttachPoint, Point3dState initialEndPoint1, Point3dState initialEndPoint2,
            Point3dState endPoint1, Point3dState endPoint2, double rotation) {
        super();
        this.leafAttachPoint = leafAttachPoint;
        this.initialEndPoint1 = initialEndPoint1;
        this.initialEndPoint2 = initialEndPoint2;
        this.endPoint1 = endPoint1;
        this.endPoint2 = endPoint2;
        this.rotation = rotation;
    }

    public TreeLeaf3DState(TreeLeaf3DState copy) {
        super();
        this.leafAttachPoint = new Point3dState(copy.leafAttachPoint);
        this.initialEndPoint1 = new Point3dState(copy.initialEndPoint1);
        this.initialEndPoint2 = new Point3dState(copy.initialEndPoint2);
        this.endPoint1 = new Point3dState(copy.endPoint1);
        this.endPoint2 = new Point3dState(copy.endPoint2);
        this.rotation = copy.rotation;
    }

    public Point3dState getLeafAttachPoint() {
        return leafAttachPoint;
    }

    public void setLeafAttachPoint(Point3dState leafAttachPoint) {
        this.leafAttachPoint = leafAttachPoint;
    }

    public Point3dState getInitialEndPoint1() {
        return initialEndPoint1;
    }

    public void setInitialEndPoint1(Point3dState initialEndPoint1) {
        this.initialEndPoint1 = initialEndPoint1;
    }

    public Point3dState getInitialEndPoint2() {
        return initialEndPoint2;
    }

    public void setInitialEndPoint2(Point3dState initialEndPoint2) {
        this.initialEndPoint2 = initialEndPoint2;
    }

    public Point3dState getEndPoint1() {
        return endPoint1;
    }

    public void setEndPoint1(Point3dState endPoint1) {
        this.endPoint1 = endPoint1;
    }

    public Point3dState getEndPoint2() {
        return endPoint2;
    }

    public void setEndPoint2(Point3dState endPoint2) {
        this.endPoint2 = endPoint2;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((initialEndPoint1 == null) ? 0 : initialEndPoint1.hashCode());
        result = prime * result + ((initialEndPoint2 == null) ? 0 : initialEndPoint2.hashCode());
        result = prime * result + ((endPoint1 == null) ? 0 : endPoint1.hashCode());
        result = prime * result + ((endPoint2 == null) ? 0 : endPoint2.hashCode());
        result = prime * result + ((leafAttachPoint == null) ? 0 : leafAttachPoint.hashCode());
        long temp;
        temp = Double.doubleToLongBits(rotation);
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
        TreeLeaf3DState other = (TreeLeaf3DState) obj;
        if (initialEndPoint1 == null) {
            if (other.initialEndPoint1 != null)
                return false;
        } else
            if (!initialEndPoint1.equals(other.initialEndPoint1))
                return false;
        if (initialEndPoint2 == null) {
            if (other.initialEndPoint2 != null)
                return false;
        } else
            if (!initialEndPoint2.equals(other.initialEndPoint2))
                return false;
        if (endPoint1 == null) {
            if (other.endPoint1 != null)
                return false;
        } else
            if (!endPoint1.equals(other.endPoint1))
                return false;
        if (endPoint2 == null) {
            if (other.endPoint2 != null)
                return false;
        } else
            if (!endPoint2.equals(other.endPoint2))
                return false;
        if (leafAttachPoint == null) {
            if (other.leafAttachPoint != null)
                return false;
        } else
            if (!leafAttachPoint.equals(other.leafAttachPoint))
                return false;
        if (Double.doubleToLongBits(rotation) != Double.doubleToLongBits(other.rotation))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeLeaf3DState [initialEndPoint1=" + initialEndPoint1 + ", initialEndPoint2=" + initialEndPoint2
                + ", endPoint1=" + endPoint1 + ", endPoint2=" + endPoint2 + ", leafAttachPoint=" + leafAttachPoint
                + ", rotation=" + rotation + "]";
    }

}
