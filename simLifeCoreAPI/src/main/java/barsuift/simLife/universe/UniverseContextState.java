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
import barsuift.simLife.j3d.SimLifeCanvas3DState;
import barsuift.simLife.j3d.terrain.NavigatorState;
import barsuift.simLife.process.MainSynchronizerState;

@XmlRootElement
public class UniverseContextState implements State {

    private UniverseState universeState;

    private MainSynchronizerState synchronizer;

    private SimLifeCanvas3DState canvasState;

    private boolean axisShowing;

    private boolean fpsShowing;

    private NavigatorState navigator;

    public UniverseContextState() {
        super();
        this.universeState = new UniverseState();
        this.synchronizer = new MainSynchronizerState();
        this.canvasState = new SimLifeCanvas3DState();
        this.axisShowing = true;
        this.fpsShowing = false;
        this.navigator = new NavigatorState();
    }

    public UniverseContextState(UniverseState universeState, MainSynchronizerState synchronizer,
            SimLifeCanvas3DState canvasState, boolean axisShowing, boolean fpsShowing, NavigatorState navigator) {
        super();
        this.universeState = universeState;
        this.synchronizer = synchronizer;
        this.canvasState = canvasState;
        this.axisShowing = axisShowing;
        this.fpsShowing = fpsShowing;
        this.navigator = navigator;
    }

    public boolean isAxisShowing() {
        return axisShowing;
    }

    public void setAxisShowing(boolean axisShowing) {
        this.axisShowing = axisShowing;
    }

    public boolean isFpsShowing() {
        return fpsShowing;
    }

    public void setFpsShowing(boolean fpsShowing) {
        this.fpsShowing = fpsShowing;
    }

    public UniverseState getUniverseState() {
        return universeState;
    }

    public void setUniverseState(UniverseState universeState) {
        this.universeState = universeState;
    }

    public MainSynchronizerState getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(MainSynchronizerState synchronizer) {
        this.synchronizer = synchronizer;
    }

    public SimLifeCanvas3DState getCanvasState() {
        return canvasState;
    }

    public void setCanvasState(SimLifeCanvas3DState canvasState) {
        this.canvasState = canvasState;
    }

    public NavigatorState getNavigator() {
        return navigator;
    }

    public void setNavigator(NavigatorState navigator) {
        this.navigator = navigator;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (axisShowing ? 1231 : 1237);
        result = prime * result + (fpsShowing ? 1231 : 1237);
        result = prime * result + ((canvasState == null) ? 0 : canvasState.hashCode());
        result = prime * result + ((universeState == null) ? 0 : universeState.hashCode());
        result = prime * result + ((synchronizer == null) ? 0 : synchronizer.hashCode());
        result = prime * result + ((navigator == null) ? 0 : navigator.hashCode());
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
        if (axisShowing != other.axisShowing)
            return false;
        if (fpsShowing != other.fpsShowing)
            return false;
        if (canvasState == null) {
            if (other.canvasState != null)
                return false;
        } else
            if (!canvasState.equals(other.canvasState))
                return false;
        if (universeState == null) {
            if (other.universeState != null)
                return false;
        } else
            if (!universeState.equals(other.universeState))
                return false;
        if (synchronizer == null) {
            if (other.synchronizer != null)
                return false;
        } else
            if (!synchronizer.equals(other.synchronizer))
                return false;
        if (navigator == null) {
            if (other.navigator != null)
                return false;
        } else
            if (!navigator.equals(other.navigator))
                return false;
        return true;
    }



    @Override
    public String toString() {
        return "UniverseContextState [universeState=" + universeState + ", synchronizer=" + synchronizer
                + ", canvasState=" + canvasState + ", axisShowing=" + axisShowing + ", fpsShowing=" + fpsShowing
                + ", navigator=" + navigator + "]";
    }

}
