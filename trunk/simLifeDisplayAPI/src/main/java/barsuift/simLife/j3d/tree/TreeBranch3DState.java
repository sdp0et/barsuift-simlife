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
import barsuift.simLife.j3d.Tuple3fState;

@XmlRootElement
public class TreeBranch3DState implements State {

    private Tuple3fState translationVector;

    private Tuple3fState endPoint;

    public TreeBranch3DState() {
        super();
        this.translationVector = new Tuple3fState();
        this.endPoint = new Tuple3fState();
    }

    public TreeBranch3DState(Tuple3fState translationVector, Tuple3fState endPoint) {
        super();
        this.translationVector = translationVector;
        this.endPoint = endPoint;
    }

    public Tuple3fState getTranslationVector() {
        return translationVector;
    }

    public void setTranslationVector(Tuple3fState translationVector) {
        this.translationVector = translationVector;
    }

    public Tuple3fState getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Tuple3fState endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((translationVector == null) ? 0 : translationVector.hashCode());
        result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
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
        if (translationVector == null) {
            if (other.translationVector != null)
                return false;
        } else
            if (!translationVector.equals(other.translationVector))
                return false;
        if (endPoint == null) {
            if (other.endPoint != null)
                return false;
        } else
            if (!endPoint.equals(other.endPoint))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "TreeBranch3DState [translationVector=" + translationVector + ", endPoint=" + endPoint + "]";
    }

}
