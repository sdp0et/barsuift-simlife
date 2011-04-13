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
package barsuift.simLife.universe;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.universe.UniverseContext3DState;

@XmlRootElement
public class UniverseContextState implements State {

    private UniverseState universe;

    private boolean fpsShowing;

    private UniverseContext3DState universeContext3D;

    public UniverseContextState() {
        super();
        this.universe = new UniverseState();
        this.fpsShowing = false;
        this.universeContext3D = new UniverseContext3DState();
    }

    public UniverseContextState(UniverseState universe, boolean fpsShowing, UniverseContext3DState universeContext3D) {
        super();
        this.universe = universe;
        this.fpsShowing = fpsShowing;
        this.universeContext3D = universeContext3D;
    }

    public boolean isFpsShowing() {
        return fpsShowing;
    }

    public void setFpsShowing(boolean fpsShowing) {
        this.fpsShowing = fpsShowing;
    }

    public UniverseState getUniverse() {
        return universe;
    }

    public void setUniverse(UniverseState universe) {
        this.universe = universe;
    }

    public UniverseContext3DState getUniverseContext3D() {
        return universeContext3D;
    }

    public void setUniverseContext3D(UniverseContext3DState universeContext3D) {
        this.universeContext3D = universeContext3D;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (fpsShowing ? 1231 : 1237);
        result = prime * result + ((universe == null) ? 0 : universe.hashCode());
        result = prime * result + ((universeContext3D == null) ? 0 : universeContext3D.hashCode());
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
        UniverseContextState other = (UniverseContextState) obj;
        if (fpsShowing != other.fpsShowing)
            return false;
        if (universe == null) {
            if (other.universe != null)
                return false;
        } else
            if (!universe.equals(other.universe))
                return false;
        if (universeContext3D == null) {
            if (other.universeContext3D != null)
                return false;
        } else
            if (!universeContext3D.equals(other.universeContext3D))
                return false;
        return true;
    }



    @Override
    public String toString() {
        return "UniverseContextState [universe=" + universe + ", fpsShowing=" + fpsShowing + ", universeContext3D="
                + universeContext3D + "]";
    }

}
