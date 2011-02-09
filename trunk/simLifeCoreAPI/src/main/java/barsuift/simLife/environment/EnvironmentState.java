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
import barsuift.simLife.landscape.LandscapeState;

@XmlRootElement
public class EnvironmentState implements State {

    private SkyState skyState;

    private LandscapeState landscape;

    private Environment3DState environment3DState;

    public EnvironmentState() {
        this.skyState = new SkyState();
        this.landscape = new LandscapeState();
        this.environment3DState = new Environment3DState();
    }

    public EnvironmentState(SkyState skyState, LandscapeState landscape, Environment3DState environment3DState) {
        this.skyState = skyState;
        this.landscape = landscape;
        this.environment3DState = environment3DState;
    }

    public void setSkyState(SkyState skyState) {
        this.skyState = skyState;
    }

    public SkyState getSkyState() {
        return skyState;
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
        result = prime * result + ((skyState == null) ? 0 : skyState.hashCode());
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
        if (skyState == null) {
            if (other.skyState != null)
                return false;
        } else
            if (!skyState.equals(other.skyState))
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
        return "EnvironmentState [skyState=" + skyState + ", landscape=" + landscape + ", environment3DState="
                + environment3DState + "]";
    }

}
