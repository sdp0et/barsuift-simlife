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

import barsuift.simLife.j3d.environment.BasicEnvironment3D;
import barsuift.simLife.j3d.environment.Environment3D;
import barsuift.simLife.landscape.BasicLandscape;
import barsuift.simLife.landscape.Landscape;

public class BasicEnvironment implements Environment {

    private final EnvironmentState state;

    private final Sky sky;

    private final Landscape landscape;

    private final Environment3D env3D;

    /**
     * Creates the environment with given state
     * 
     * @param state the environment state
     * @throws IllegalArgumentException if the given environment state is null
     */
    public BasicEnvironment(EnvironmentState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null environment state");
        }
        this.state = state;
        this.sky = new BasicSky(state.getSkyState());
        this.landscape = new BasicLandscape(state.getLandscape());
        this.env3D = new BasicEnvironment3D(state.getEnvironment3DState(), this);
    }

    @Override
    public Sky getSky() {
        return sky;
    }

    @Override
    public Landscape getLandscape() {
        return landscape;
    }

    @Override
    public Environment3D getEnvironment3D() {
        return env3D;
    }

    public EnvironmentState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        sky.synchronize();
        landscape.synchronize();
        env3D.synchronize();
    }

}
