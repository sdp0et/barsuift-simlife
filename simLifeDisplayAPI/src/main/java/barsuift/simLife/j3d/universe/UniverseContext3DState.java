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
package barsuift.simLife.j3d.universe;

import javax.xml.bind.annotation.XmlRootElement;

import barsuift.simLife.State;
import barsuift.simLife.j3d.SimLifeCanvas3DState;
import barsuift.simLife.j3d.terrain.NavigatorState;

@XmlRootElement
public class UniverseContext3DState implements State {

    private SimLifeCanvas3DState canvas;

    private boolean axisShowing;

    private NavigatorState navigator;

    public UniverseContext3DState() {
        super();
        this.canvas = new SimLifeCanvas3DState();
        this.axisShowing = true;
        this.navigator = new NavigatorState();
    }

    public UniverseContext3DState(SimLifeCanvas3DState canvas, boolean axisShowing, NavigatorState navigator) {
        super();
        this.canvas = canvas;
        this.axisShowing = axisShowing;
        this.navigator = navigator;
    }

    public boolean isAxisShowing() {
        return axisShowing;
    }

    public void setAxisShowing(boolean axisShowing) {
        this.axisShowing = axisShowing;
    }

    public SimLifeCanvas3DState getCanvas() {
        return canvas;
    }

    public void setCanvas(SimLifeCanvas3DState canvas) {
        this.canvas = canvas;
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
        result = prime * result + ((canvas == null) ? 0 : canvas.hashCode());
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
        UniverseContext3DState other = (UniverseContext3DState) obj;
        if (axisShowing != other.axisShowing)
            return false;
        if (canvas == null) {
            if (other.canvas != null)
                return false;
        } else
            if (!canvas.equals(other.canvas))
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
        return "UniverseContext3DState [canvasState=" + canvas + ", axisShowing=" + axisShowing + ", navigator="
                + navigator + "]";
    }

}
