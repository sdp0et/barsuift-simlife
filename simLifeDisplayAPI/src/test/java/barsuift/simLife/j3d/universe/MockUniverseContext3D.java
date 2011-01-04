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

import barsuift.simLife.j3d.MockSimLifeCanvas3D;
import barsuift.simLife.j3d.SimLifeCanvas3D;
import barsuift.simLife.j3d.landscape.MockNavigator;
import barsuift.simLife.j3d.landscape.Navigator;

public class MockUniverseContext3D implements UniverseContext3D {

    private UniverseContext3DState state;

    private int nbSynchronizedCalled;

    private SimLifeCanvas3D canvas3D;

    private boolean fpsShowing;

    private boolean axisShowing;

    private Navigator navigator;

    public MockUniverseContext3D() {
        reset();
    }

    public void reset() {
        this.state = new UniverseContext3DState();
        this.nbSynchronizedCalled = 0;
        this.canvas3D = new MockSimLifeCanvas3D();
        this.fpsShowing = false;
        this.axisShowing = false;
        this.navigator = new MockNavigator();
    }

    @Override
    public UniverseContext3DState getState() {
        return state;
    }

    public void setState(UniverseContext3DState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.nbSynchronizedCalled++;
    }

    public int getNbSynchronizeCalled() {
        return nbSynchronizedCalled;
    }

    @Override
    public SimLifeCanvas3D getCanvas3D() {
        return canvas3D;
    }

    public void setCanvas3D(SimLifeCanvas3D canvas3D) {
        this.canvas3D = canvas3D;
    }

    @Override
    public void setFpsShowing(boolean fpsShowing) {
        this.fpsShowing = fpsShowing;
    }

    @Override
    public boolean isFpsShowing() {
        return fpsShowing;
    }

    @Override
    public void setAxisShowing(boolean axisShowing) {
        this.axisShowing = axisShowing;
    }

    @Override
    public boolean isAxisShowing() {
        return axisShowing;
    }

    @Override
    public Navigator getNavigator() {
        return navigator;
    }

    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

}
