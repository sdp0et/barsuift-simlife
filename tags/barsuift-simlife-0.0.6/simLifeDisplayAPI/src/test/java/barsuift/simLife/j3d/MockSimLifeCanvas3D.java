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
package barsuift.simLife.j3d;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class MockSimLifeCanvas3D extends SimLifeCanvas3D {

    private static final long serialVersionUID = 6220056297209904283L;

    private SimLifeCanvas3DState state;

    private int nbSynchronizedCalled;

    private boolean fpsShowing;

    public MockSimLifeCanvas3D() {
        super(SimpleUniverse.getPreferredConfiguration());
        resetMock();
    }

    public void resetMock() {
        this.state = new SimLifeCanvas3DState();
        this.nbSynchronizedCalled = 0;
        this.fpsShowing = false;
    }

    @Override
    public SimLifeCanvas3DState getState() {
        return state;
    }

    public void setState(SimLifeCanvas3DState state) {
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
    public void setFpsShowing(boolean fpsShowing) {
        this.fpsShowing = fpsShowing;
    }

    @Override
    public boolean isFpsShowing() {
        return fpsShowing;
    }

}
