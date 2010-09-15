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

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.SimLifeCanvas3DState;

@XmlRootElement
public class UniverseContextState implements State {

    private UniverseState universeState;

    private SimLifeCanvas3DState canvasState;

    private boolean axisShowing;

    private double[] viewerTransform3D;

    public UniverseContextState() {
        super();
        this.universeState = new UniverseState();
        this.canvasState = new SimLifeCanvas3DState();
        this.axisShowing = true;
        // identity matrix
        this.viewerTransform3D = new double[] { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };
    }

    public UniverseContextState(UniverseState universeState, SimLifeCanvas3DState canvasState, boolean axisShowing,
            double[] viewerTransform3D) {
        super();
        this.universeState = universeState;
        this.canvasState = canvasState;
        this.axisShowing = axisShowing;
        this.viewerTransform3D = viewerTransform3D;
    }

    public boolean isAxisShowing() {
        return axisShowing;
    }

    public void setAxisShowing(boolean axisShowing) {
        this.axisShowing = axisShowing;
    }

    public UniverseState getUniverseState() {
        return universeState;
    }

    public void setUniverseState(UniverseState universeState) {
        this.universeState = universeState;
    }

    public SimLifeCanvas3DState getCanvasState() {
        return canvasState;
    }

    public void setCanvasState(SimLifeCanvas3DState canvasState) {
        this.canvasState = canvasState;
    }

    public double[] getViewerTransform3D() {
        return viewerTransform3D;
    }

    public void setViewerTransform3D(double[] viewerTransform3D) {
        this.viewerTransform3D = viewerTransform3D;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (axisShowing ? 1231 : 1237);
        result = prime * result + ((canvasState == null) ? 0 : canvasState.hashCode());
        result = prime * result + ((universeState == null) ? 0 : universeState.hashCode());
        result = prime * result + Arrays.hashCode(viewerTransform3D);
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
        if (!Arrays.equals(viewerTransform3D, other.viewerTransform3D))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UniverseContextState [universeState=" + universeState + ", canvasState=" + canvasState
                + ", axisShowing=" + axisShowing + ", viewerTransform3D=" + Arrays.toString(viewerTransform3D) + "]";
    }


}
