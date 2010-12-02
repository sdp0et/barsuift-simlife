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
import barsuift.simLife.j3d.environment.Environment3DState;
import barsuift.simLife.terrain.LandscapeState;

@XmlRootElement
public class EnvironmentState implements State {

    private SunState sunState;

    private LandscapeState landscape;

    private Environment3DState environment3DState;

    public EnvironmentState() {
        this.sunState = new SunState();
        this.landscape = new LandscapeState();
        this.environment3DState = new Environment3DState();
    }

    public EnvironmentState(SunState sunState, LandscapeState landscape, Environment3DState environment3DState) {
        this.sunState = sunState;
        this.landscape = landscape;
        this.environment3DState = environment3DState;
    }

    public void setSunState(SunState sunState) {
        this.sunState = sunState;
    }

    public SunState getSunState() {
        return sunState;
    }

    public LandscapeState getLandscape() {
        return landscape;
    }

    public void setLandscape(LandscapeState landscape) {
        this.landscape = landscape;
    }

    public Environment3DState getEnvironment3DState() {
        return environment3DState;
    }

    public void setEnvironment3DState(Environment3DState environment3dState) {
        environment3DState = environment3dState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((environment3DState == null) ? 0 : environment3DState.hashCode());
        result = prime * result + ((sunState == null) ? 0 : sunState.hashCode());
        result = prime * result + ((landscape == null) ? 0 : landscape.hashCode());
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
        EnvironmentState other = (EnvironmentState) obj;
        if (environment3DState == null) {
            if (other.environment3DState != null)
                return false;
        } else
            if (!environment3DState.equals(other.environment3DState))
                return false;
        if (sunState == null) {
            if (other.sunState != null)
                return false;
        } else
            if (!sunState.equals(other.sunState))
                return false;
        if (landscape == null) {
            if (other.landscape != null)
                return false;
        } else
            if (!landscape.equals(other.landscape))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "EnvironmentState [sunState=" + sunState + ", landscape=" + landscape + ", environment3DState="
                + environment3DState + "]";
    }

}
