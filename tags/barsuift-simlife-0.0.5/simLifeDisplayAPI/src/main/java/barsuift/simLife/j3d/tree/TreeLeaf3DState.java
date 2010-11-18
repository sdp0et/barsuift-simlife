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

import barsuift.simLife.State;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.Tuple3dState;

@XmlRootElement
public class TreeLeaf3DState implements State {

    /**
     * Leaf transform, relative to its attach branchGroup
     */
    private Transform3DState transform;

    /**
     * End point 1 at the creation of the leaf (its birth end point 1). The point is relative to the attach point.
     */
    private Tuple3dState initialEndPoint1;

    /**
     * End point 2 at the creation of the leaf (its birth end point 2). The point is relative to the attach point.
     */
    private Tuple3dState initialEndPoint2;

    /**
     * Current end point 1. The point is relative to the attach point.
     */
    private Tuple3dState endPoint1;

    /**
     * Current end point 2. The point is relative to the attach point.
     */
    private Tuple3dState endPoint2;

    public TreeLeaf3DState() {
        super();
        this.transform = new Transform3DState();
        this.initialEndPoint1 = new Tuple3dState();
        this.initialEndPoint2 = new Tuple3dState();
        this.endPoint1 = new Tuple3dState();
        this.endPoint2 = new Tuple3dState();
    }

    public TreeLeaf3DState(Transform3DState transform, Tuple3dState initialEndPoint1, Tuple3dState initialEndPoint2,
            Tuple3dState endPoint1, Tuple3dState endPoint2) {
        super();
        this.transform = transform;
        this.initialEndPoint1 = initialEndPoint1;
        this.initialEndPoint2 = initialEndPoint2;
        this.endPoint1 = endPoint1;
        this.endPoint2 = endPoint2;
    }

    public Transform3DState getTransform() {
        return transform;
    }

    public void setTransform(Transform3DState transform) {
        this.transform = transform;
    }

    public Tuple3dState getInitialEndPoint1() {
        return initialEndPoint1;
    }

    public void setInitialEndPoint1(Tuple3dState initialEndPoint1) {
        this.initialEndPoint1 = initialEndPoint1;
    }

    public Tuple3dState getInitialEndPoint2() {
        return initialEndPoint2;
    }

    public void setInitialEndPoint2(Tuple3dState initialEndPoint2) {
        this.initialEndPoint2 = initialEndPoint2;
    }

    public Tuple3dState getEndPoint1() {
        return endPoint1;
    }

    public void setEndPoint1(Tuple3dState endPoint1) {
        this.endPoint1 = endPoint1;
    }

    public Tuple3dState getEndPoint2() {
        return endPoint2;
    }

    public void setEndPoint2(Tuple3dState endPoint2) {
        this.endPoint2 = endPoint2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((initialEndPoint1 == null) ? 0 : initialEndPoint1.hashCode());
        result = prime * result + ((initialEndPoint2 == null) ? 0 : initialEndPoint2.hashCode());
        result = prime * result + ((endPoint1 == null) ? 0 : endPoint1.hashCode());
        result = prime * result + ((endPoint2 == null) ? 0 : endPoint2.hashCode());
        result = prime * result + ((transform == null) ? 0 : transform.hashCode());
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
        if (transform == null) {
            if (other.transform != null)
                return false;
        } else
            if (!transform.equals(other.transform))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeLeaf3DState [initialEndPoint1=" + initialEndPoint1 + ", initialEndPoint2=" + initialEndPoint2
                + ", endPoint1=" + endPoint1 + ", endPoint2=" + endPoint2 + ", transform=" + transform + "]";
    }

}
