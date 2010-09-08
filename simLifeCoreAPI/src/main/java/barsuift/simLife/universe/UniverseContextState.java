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

@XmlRootElement
public class UniverseContextState implements State {

    private UniverseState universeState;

    private boolean showFps;

    private boolean isAxisShown;

    public UniverseContextState() {
        super();
        this.universeState = new UniverseState();
        this.showFps = false;
        this.isAxisShown = false;
    }

    public UniverseContextState(UniverseState universeState, boolean showFps, boolean isAxisShown) {
        super();
        this.universeState = universeState;
        this.showFps = showFps;
        this.isAxisShown = isAxisShown;
    }

    public boolean isShowFps() {
        return showFps;
    }

    public void setShowFps(boolean showFps) {
        this.showFps = showFps;
    }

    public boolean isAxisShown() {
        return isAxisShown;
    }

    public void setAxisShown(boolean isAxisShown) {
        this.isAxisShown = isAxisShown;
    }

    public UniverseState getUniverseState() {
        return universeState;
    }

    public void setUniverseState(UniverseState universeState) {
        this.universeState = universeState;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (isAxisShown ? 1231 : 1237);
        result = prime * result + (showFps ? 1231 : 1237);
        result = prime * result + ((universeState == null) ? 0 : universeState.hashCode());
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
        if (isAxisShown != other.isAxisShown)
            return false;
        if (showFps != other.showFps)
            return false;
        if (universeState == null) {
            if (other.universeState != null)
                return false;
        } else
            if (!universeState.equals(other.universeState))
                return false;
        return true;
    }

    @Override
    public String toString() {
        return "UniverseContextState [universeState=" + universeState + ", showFps=" + showFps + ", isAxisShown="
                + isAxisShown + "]";
    }


}
