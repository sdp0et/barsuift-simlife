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
package barsuift.simLife.j3d.environment;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;

import barsuift.simLife.environment.Environment;
import barsuift.simLife.j3d.landscape.Landscape3D;


public class BasicEnvironment3D implements Environment3D {

    private final Environment3DState state;

    private Environment environment;

    private final Group group;

    /**
     * Creates the environment3D with given state
     * 
     * @param state the environment3D state
     * @throws IllegalArgumentException if the given environment3D state is null
     */
    public BasicEnvironment3D(Environment3DState state) {
        if (state == null) {
            throw new IllegalArgumentException("Null environment3D state");
        }
        this.state = state;
        group = new BranchGroup();
    }

    public void init(Environment environment) {
        this.environment = environment;
        group.addChild(getSky3D().getGroup());
        group.addChild(getLandscape3D().getBranchGroup());
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public Sky3D getSky3D() {
        return environment.getSky().getSky3D();
    }

    @Override
    public Wind3D getWind3D() {
        return environment.getWind().getWind3D();
    }

    @Override
    public Landscape3D getLandscape3D() {
        return environment.getLandscape().getLandscape3D();
    }

    public Environment3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }

}
