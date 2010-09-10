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
package barsuift.simLife.j3d.tree;

import javax.media.j3d.Group;

import com.sun.j3d.utils.geometry.Cylinder;


public class MockTreeTrunk3D implements TreeTrunk3D {

    private Group group = new Group();

    private TreeTrunk3DState state = new TreeTrunk3DState();

    private Cylinder cylinder = new Cylinder();

    private int synchronizedCalled;

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public TreeTrunk3DState getState() {
        return state;
    }

    public void setState(TreeTrunk3DState state) {
        this.state = state;
    }

    @Override
    public Cylinder getTrunk() {
        return cylinder;
    }

    public void setTrunk(Cylinder cylinder) {
        this.cylinder = cylinder;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

}
