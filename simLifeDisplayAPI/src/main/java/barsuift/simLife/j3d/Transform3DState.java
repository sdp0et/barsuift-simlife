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

import java.util.Arrays;

import javax.media.j3d.Transform3D;
import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;

@XmlRootElement
public class Transform3DState implements State {

    private double[] matrix;

    public Transform3DState() {
        super();
        this.matrix = new double[16];
    }

    public Transform3DState(double[] matrix) {
        super();
        this.matrix = matrix;
    }

    public Transform3DState(Transform3D transform) {
        super();
        this.matrix = new double[16];
        transform.get(matrix);
    }

    public Transform3D toTransform3D() {
        return new Transform3D(matrix);
    }

    public double[] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[] matrix) {
        this.matrix = matrix;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(matrix);
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
        Transform3DState other = (Transform3DState) obj;
        if (!Arrays.equals(matrix, other.matrix))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transform3DState [matrix=" + Arrays.toString(matrix) + "]";
    }

}
