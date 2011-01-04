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
package barsuift.simLife.landscape;

import barsuift.simLife.j3d.landscape.Landscape3D;
import barsuift.simLife.j3d.landscape.MockLandscape3D;
import barsuift.simLife.landscape.Landscape;
import barsuift.simLife.landscape.LandscapeState;


public class MockLandscape implements Landscape {

    private LandscapeState state;

    private int nbSynchronizedCalled;

    private Landscape3D landscape3D;

    public MockLandscape() {
        reset();
    }

    public void reset() {
        state = new LandscapeState();
        nbSynchronizedCalled = 0;
        landscape3D = new MockLandscape3D();
    }

    @Override
    public LandscapeState getState() {
        return state;
    }

    public void setState(LandscapeState state) {
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
    public Landscape3D getLandscape3D() {
        return landscape3D;
    }

    public void setLandscape3D(Landscape3D landscape3D) {
        this.landscape3D = landscape3D;
    }

}
