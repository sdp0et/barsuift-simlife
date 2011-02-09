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

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.environment.Sky3DState;

// TODO unit test
@XmlRootElement
public class SkyState implements State {

    private SunState sunState;

    private Sky3DState sky3DState;

    public SkyState() {
        this.sunState = new SunState();
        this.sky3DState = new Sky3DState();
    }

    public SkyState(SunState sunState, Sky3DState sky3DState) {
        this.sunState = sunState;
        this.sky3DState = sky3DState;
    }

    public void setSunState(SunState sunState) {
        this.sunState = sunState;
    }

    public SunState getSunState() {
        return sunState;
    }

    public Sky3DState getSky3DState() {
        return sky3DState;
    }

    public void setSky3DState(Sky3DState sky3dState) {
        sky3DState = sky3dState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sky3DState == null) ? 0 : sky3DState.hashCode());
        result = prime * result + ((sunState == null) ? 0 : sunState.hashCode());
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
        SkyState other = (SkyState) obj;
        if (sky3DState == null) {
            if (other.sky3DState != null)
                return false;
        } else
            if (!sky3DState.equals(other.sky3DState))
                return false;
        if (sunState == null) {
            if (other.sunState != null)
                return false;
        } else
            if (!sunState.equals(other.sunState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "SkyState [sunState=" + sunState + ", sky3DState=" + sky3DState + "]";
    }

}
