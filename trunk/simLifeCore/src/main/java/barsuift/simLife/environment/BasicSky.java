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

import barsuift.simLife.j3d.environment.BasicSky3D;
import barsuift.simLife.j3d.environment.Sky3D;

public class BasicSky implements Sky {

    private final SkyState state;

    private final Sun sun;

    private final Sky3D sky3D;

    /**
     * Creates the sky with given state
     * 
     * @param state the sky state
     * @throws IllegalArgumentException if the given sky state is null
     */
    public BasicSky(SkyState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null sky state");
        }
        this.state = state;
        this.sun = new BasicSun(state.getSunState());
        this.sky3D = new BasicSky3D(state.getSky3DState(), this);
    }

    @Override
    public Sun getSun() {
        return sun;
    }

    @Override
    public Sky3D getSky3D() {
        return sky3D;
    }

    public SkyState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        sun.synchronize();
        sky3D.synchronize();
    }

}
