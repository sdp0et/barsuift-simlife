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
package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;

import barsuift.simLife.universe.physic.Physics;


public class BasicPhysics3D implements Physics3D {

    private final Physics3DState state;

    private Physics physics;

    private final Group group;

    public BasicPhysics3D(Physics3DState state) {
        this.state = state;
        group = new BranchGroup();
    }

    public void init(Physics physics) {
        this.physics = physics;
        group.addChild(getGravity3D().getGroup());
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public Gravity3D getGravity3D() {
        return physics.getGravity().getGravity3D();
    }

    @Override
    public Physics3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }

}
