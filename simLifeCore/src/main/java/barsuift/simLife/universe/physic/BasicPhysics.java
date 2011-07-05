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
package barsuift.simLife.universe.physic;

import barsuift.simLife.j3d.universe.physic.BasicPhysics3D;
import barsuift.simLife.j3d.universe.physic.Physics3D;
import barsuift.simLife.universe.Universe;


public class BasicPhysics implements Physics {

    private final PhysicsState state;

    private final BasicGravity gravity;

    private final BasicPhysics3D physics3D;

    public BasicPhysics(PhysicsState state) {
        this.state = state;
        this.gravity = new BasicGravity(state.getGravity());
        this.physics3D = new BasicPhysics3D(state.getPhysics3D());
    }

    public void init(Universe universe) {
        this.gravity.init(universe);
        this.physics3D.init(this);
    }

    @Override
    public PhysicsState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        physics3D.synchronize();
        gravity.synchronize();
    }

    @Override
    public Physics3D getPhysics3D() {
        return physics3D;
    }

    @Override
    public Gravity getGravity() {
        return gravity;
    }

}
