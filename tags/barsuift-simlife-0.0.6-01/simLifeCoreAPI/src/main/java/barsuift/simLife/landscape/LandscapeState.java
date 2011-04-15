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
package barsuift.simLife.landscape;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.landscape.Landscape3DState;

@XmlRootElement
public class LandscapeState implements State {

    private Landscape3DState landscape3D;

    public LandscapeState() {
        this.landscape3D = new Landscape3DState();
    }

    public LandscapeState(Landscape3DState landscape3D) {
        this.landscape3D = landscape3D;
    }

    public Landscape3DState getLandscape3D() {
        return landscape3D;
    }

    public void setLandscape3D(Landscape3DState landscape3D) {
        this.landscape3D = landscape3D;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((landscape3D == null) ? 0 : landscape3D.hashCode());
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
        LandscapeState other = (LandscapeState) obj;
        if (landscape3D == null) {
            if (other.landscape3D != null)
                return false;
        } else
            if (!landscape3D.equals(other.landscape3D))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "LandscapeState [landscape3D=" + landscape3D + "]";
    }


}
