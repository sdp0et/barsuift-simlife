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

@XmlRootElement
public class TreeBranch3DState implements State {

    /**
     * Branch transform, relative to its attach branchGroup
     */
    private Transform3DState transform;

    private float length;

    private float radius;

    public TreeBranch3DState() {
        super();
        this.transform = new Transform3DState();
        this.length = 0;
        this.radius = 0;
    }

    public TreeBranch3DState(Transform3DState transform, float length, float radius) {
        super();
        this.transform = transform;
        this.length = length;
        this.radius = radius;
    }

    public Transform3DState getTransform() {
        return transform;
    }

    public void setTransform(Transform3DState transform) {
        this.transform = transform;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(length);
        result = prime * result + Float.floatToIntBits(radius);
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
        TreeBranch3DState other = (TreeBranch3DState) obj;
        if (Float.floatToIntBits(length) != Float.floatToIntBits(other.length))
            return false;
        if (Float.floatToIntBits(radius) != Float.floatToIntBits(other.radius))
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
        return "TreeBranch3DState [transform=" + transform + ", length=" + length + ", radius=" + radius + "]";
    }

}
