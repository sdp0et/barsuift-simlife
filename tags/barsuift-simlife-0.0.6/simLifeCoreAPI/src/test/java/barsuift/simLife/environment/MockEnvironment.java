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

import barsuift.simLife.j3d.environment.Environment3D;
import barsuift.simLife.j3d.environment.MockEnvironment3D;
import barsuift.simLife.landscape.Landscape;
import barsuift.simLife.landscape.MockLandscape;


public class MockEnvironment implements Environment {

    private Sky sky;

    private EnvironmentState state;

    private int synchronizedCalled;

    private Environment3D env3D;

    private Landscape landscape;

    public MockEnvironment() {
        reset();
    }

    public void reset() {
        sky = new MockSky();
        state = new EnvironmentState();
        synchronizedCalled = 0;
        env3D = new MockEnvironment3D();
        landscape = new MockLandscape();
    }

    @Override
    public Sky getSky() {
        return sky;
    }

    public void setSky(Sky sky) {
        this.sky = sky;
    }

    @Override
    public EnvironmentState getState() {
        return state;
    }

    public void setEnvironmentState(EnvironmentState state) {
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
    public Environment3D getEnvironment3D() {
        return env3D;
    }

    public void setEnvironment3D(Environment3D env3D) {
        this.env3D = env3D;
    }

    @Override
    public Landscape getLandscape() {
        return landscape;
    }

    public void setLandscape(Landscape landscape) {
        this.landscape = landscape;
    }

}
