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

import barsuift.simLife.j3d.universe.physic.MockPhysics3D;
import barsuift.simLife.j3d.universe.physic.Physics3D;


public class MockPhysics implements Physics {

    private PhysicsState state;

    private int synchronizedCalled;

    private Gravity gravity;

    private Physics3D physics3D;


    public MockPhysics() {
        reset();
    }

    public void reset() {
        this.state = new PhysicsState();
        this.synchronizedCalled = 0;
        this.gravity = new MockGravity();
        this.physics3D = new MockPhysics3D();
    }

    @Override
    public PhysicsState getState() {
        return state;
    }

    public void setState(PhysicsState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

    @Override
    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    @Override
    public Physics3D getPhysics3D() {
        return physics3D;
    }

    public void setPhysics3D(Physics3D physics3D) {
        this.physics3D = physics3D;
    }

}
