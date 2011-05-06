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

import barsuift.simLife.j3d.Mobile;
import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.process.GravityTask;
import barsuift.simLife.process.WindTask;


public class BasicGravity3D implements Gravity3D {

    private final Gravity3DState state;

    private final Group group;

    private GravityTask gravityTask;

    private WindTask windTask;

    public BasicGravity3D(Gravity3DState state, Universe3D universe3D) {
        this.state = state;
        group = new BranchGroup();
        group.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        group.setCapability(Group.ALLOW_CHILDREN_WRITE);
        gravityTask = new GravityTask(state.getGravityTask(), universe3D.getEnvironment3D().getLandscape3D());
        universe3D.getSynchronizer().scheduleFast(gravityTask);
        // FIXME not the right state
        windTask = new WindTask(state.getGravityTask(), universe3D.getEnvironment3D().getLandscape3D());
        universe3D.getSynchronizer().scheduleFast(windTask);
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void fall(Mobile mobile) {
        gravityTask.fall(mobile);
        windTask.fall(mobile);
        group.addChild(mobile.getBranchGroup());
    }

    @Override
    public void isFallen(Mobile mobile) {
        group.removeChild(mobile.getBranchGroup());
    }

    @Override
    public Gravity3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        gravityTask.synchronize();
        windTask.synchronize();
    }

}
