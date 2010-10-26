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


public class MockPhysics3D implements Physics3D {

    private Physics3DState state;

    private int synchronizeCalled;

    private GravityInterpolator gravity;

    private Gravity3D gravity3D;

    public MockPhysics3D() {
        reset();
    }

    public void reset() {
        state = new Physics3DState();
        synchronizeCalled = 0;
        gravity = new MockGravityInterpolator();
        gravity3D = new MockGravity3D();
    }

    @Override
    public Physics3DState getState() {
        return state;
    }

    public void setState(Physics3DState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

    @Override
    public GravityInterpolator getGravityInterpolator() {
        return gravity;
    }

    public void setGravity(GravityInterpolator gravity) {
        this.gravity = gravity;
    }

    @Override
    public Gravity3D getGravity3D() {
        return gravity3D;
    }

    public void setGravity3D(Gravity3D gravity3D) {
        this.gravity3D = gravity3D;
    }

}
