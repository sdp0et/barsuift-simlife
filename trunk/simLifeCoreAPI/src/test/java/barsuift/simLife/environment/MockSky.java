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

import barsuift.simLife.j3d.environment.MockSky3D;
import barsuift.simLife.j3d.environment.Sky3D;


public class MockSky implements Sky {

    private Sun sun;

    private SkyState state;

    private int synchronizedCalled;

    private Sky3D sky3D;

    public MockSky() {
        reset();
    }

    public void reset() {
        sun = new MockSun();
        state = new SkyState();
        synchronizedCalled = 0;
        sky3D = new MockSky3D();
    }

    @Override
    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    @Override
    public SkyState getState() {
        return state;
    }

    public void setSkyState(SkyState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

    public void setNbSynchronize(int nbSynchronize) {
        this.synchronizedCalled = nbSynchronize;
    }

    @Override
    public Sky3D getSky3D() {
        return sky3D;
    }

    public void setSky3D(Sky3D sky3D) {
        this.sky3D = sky3D;
    }

}
