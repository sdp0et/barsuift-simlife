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
package barsuift.simLife.j3d.environment;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.BoundingBoxState;

@XmlRootElement
public class Sky3DState implements State {

    private BoundingBoxState skyBounds;

    private BoundingBoxState ambientLightBounds;

    public Sky3DState() {
        this.skyBounds = new BoundingBoxState();
        this.ambientLightBounds = new BoundingBoxState();
    }

    public Sky3DState(BoundingBoxState skyBounds, BoundingBoxState ambientLightBounds) {
        this.skyBounds = skyBounds;
        this.ambientLightBounds = ambientLightBounds;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((skyBounds == null) ? 0 : skyBounds.hashCode());
        result = prime * result + ((ambientLightBounds == null) ? 0 : ambientLightBounds.hashCode());
        return result;
    }

    public BoundingBoxState getSkyBounds() {
        return skyBounds;
    }

    public void setSkyBounds(BoundingBoxState skyBounds) {
        this.skyBounds = skyBounds;
    }

    public BoundingBoxState getAmbientLightBounds() {
        return ambientLightBounds;
    }

    public void setAmbientLightBounds(BoundingBoxState ambientLightBounds) {
        this.ambientLightBounds = ambientLightBounds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sky3DState other = (Sky3DState) obj;
        if (ambientLightBounds == null) {
            if (other.ambientLightBounds != null)
                return false;
        } else
            if (!ambientLightBounds.equals(other.ambientLightBounds))
                return false;
        if (ambientLightBounds == null) {
            if (other.ambientLightBounds != null)
                return false;
        } else
            if (!ambientLightBounds.equals(other.ambientLightBounds))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "Sky3DState [skyBounds=" + skyBounds + ", ambientLightBounds=" + ambientLightBounds + "]";
    }
}
