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

@XmlRootElement
public class EnvironmentState {

    private SunState sunState;

    public EnvironmentState() {
        this.sunState = new SunState();
    }

    public EnvironmentState(SunState sunState) {
        this.sunState = sunState;
    }

    public EnvironmentState(EnvironmentState copy) {
        this.sunState = new SunState(copy.sunState);
    }

    public void setSunState(SunState sunState) {
        this.sunState = sunState;
    }

    public SunState getSunState() {
        return sunState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        EnvironmentState other = (EnvironmentState) obj;
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
        return "BasicEnvironmentState [sunState=" + sunState + "]";
    }

}
