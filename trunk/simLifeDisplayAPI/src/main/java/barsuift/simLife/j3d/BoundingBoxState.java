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

import javax.media.j3d.BoundingBox;
import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;

@XmlRootElement
public class BoundingBoxState implements State {

    private Tuple3dState lower;

    private Tuple3dState upper;

    public BoundingBoxState() {
        super();
        this.lower = new Tuple3dState();
        this.upper = new Tuple3dState();
    }

    public BoundingBoxState(Tuple3dState lower, Tuple3dState upper) {
        super();
        this.lower = lower;
        this.upper = upper;
    }

    public BoundingBox toBoundingBox() {
        return new BoundingBox(lower.toPointValue(), upper.toPointValue());
    }

    public Tuple3dState getLower() {
        return lower;
    }

    public void setLower(Tuple3dState lower) {
        this.lower = lower;
    }

    public Tuple3dState getUpper() {
        return upper;
    }

    public void setUpper(Tuple3dState upper) {
        this.upper = upper;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lower == null) ? 0 : lower.hashCode());
        result = prime * result + ((upper == null) ? 0 : upper.hashCode());
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
        BoundingBoxState other = (BoundingBoxState) obj;
        if (lower == null) {
            if (other.lower != null)
                return false;
        } else
            if (!lower.equals(other.lower))
                return false;
        if (upper == null) {
            if (other.upper != null)
                return false;
        } else
            if (!upper.equals(other.upper))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "BoundingBoxState [lower=" + lower + ", upper=" + upper + "]";
    }

}
