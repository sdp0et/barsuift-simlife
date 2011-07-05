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
package barsuift.simLife.environment;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.environment.Wind3DState;
import barsuift.simLife.tree.TreeLeafState;

@XmlRootElement
public class WindState implements State {

    private Wind3DState wind3D;

    private Set<TreeLeafState> movingLeaves;

    public WindState() {
        super();
        wind3D = new Wind3DState();
        this.movingLeaves = new HashSet<TreeLeafState>();
    }

    public WindState(Wind3DState wind3D, Set<TreeLeafState> movingLeaves) {
        super();
        this.wind3D = wind3D;
        this.movingLeaves = movingLeaves;
    }

    public Wind3DState getWind3D() {
        return wind3D;
    }

    public void setWind3D(Wind3DState wind3d) {
        wind3D = wind3d;
    }

    public Set<TreeLeafState> getMovingLeaves() {
        return movingLeaves;
    }

    public void setMovingLeaves(Set<TreeLeafState> movingLeaves) {
        this.movingLeaves = movingLeaves;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((wind3D == null) ? 0 : wind3D.hashCode());
        result = prime * result + ((movingLeaves == null) ? 0 : movingLeaves.hashCode());
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
        WindState other = (WindState) obj;
        if (wind3D == null) {
            if (other.wind3D != null)
                return false;
        } else
            if (!wind3D.equals(other.wind3D))
                return false;
        if (movingLeaves == null) {
            if (other.movingLeaves != null)
                return false;
        } else
            if (!movingLeaves.equals(other.movingLeaves))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "WindState [wind3D=" + wind3D + ", movingLeaves=" + movingLeaves + "]";
    }

}
