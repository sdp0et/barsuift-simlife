/**
 * barsuift-simlife is a life simulator programm
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
public class TreeBranch3DState {

    private Point3dState translationVector;

    public TreeBranch3DState() {
        super();
        this.translationVector = new Point3dState();
    }

    public TreeBranch3DState(Point3dState translationVector) {
        super();
        this.translationVector = translationVector;
    }

    public TreeBranch3DState(TreeBranch3DState copy) {
        super();
        this.translationVector = new Point3dState(copy.translationVector);
    }

    public Point3dState getTranslationVector() {
        return translationVector;
    }

    public void setTranslationVector(Point3dState translationVector) {
        this.translationVector = translationVector;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((translationVector == null) ? 0 : translationVector.hashCode());
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
        return true;
    }

    @Override
    public String toString() {
        return "TreeBranch3DState [translationVector=" + translationVector + "]";
    }

}
