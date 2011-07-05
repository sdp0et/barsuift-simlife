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

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Group;

import barsuift.simLife.j3d.Mobile;


public class MockWind3D implements Wind3D {

    private Wind3DState state;

    private int synchronizeCalled;

    private List<Mobile> movingMobiles;

    private List<Mobile> groundedMobiles;

    private Group group;

    public MockWind3D() {
        reset();
    }

    public void reset() {
        state = new Wind3DState();
        synchronizeCalled = 0;
        movingMobiles = new ArrayList<Mobile>();
        groundedMobiles = new ArrayList<Mobile>();
        group = new Group();
    }

    @Override
    public Wind3DState getState() {
        return state;
    }

    public void setState(Wind3DState state) {
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
    public void move(Mobile mobile) {
        movingMobiles.add(mobile);
    }

    public List<Mobile> getFallingMobiles() {
        return movingMobiles;
    }

    @Override
    public void isGrounded(Mobile mobile) {
        groundedMobiles.add(mobile);
    }

    public List<Mobile> getGroundedMobiles() {
        return groundedMobiles;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
