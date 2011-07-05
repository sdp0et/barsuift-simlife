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

import barsuift.simLife.j3d.environment.BasicSun3D;
import barsuift.simLife.j3d.environment.Sun3D;
import barsuift.simLife.universe.Universe;

/**
 * Class representing the sun.
 */
public class BasicSun implements Sun {

    private final SunState state;

    private final BasicSun3D sun3D;

    public BasicSun(SunState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null sun state");
        }
        this.state = state;
        sun3D = new BasicSun3D(state.getSun3DState());
    }

    public void init(Universe universe) {
        sun3D.init(universe.getUniverse3D());
    }

    @Override
    public SunState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        sun3D.synchronize();
    }

    @Override
    public Sun3D getSun3D() {
        return sun3D;
    }

}
