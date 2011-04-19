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

import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.universe.physic.Gravity3D;
import barsuift.simLife.j3d.universe.physic.MockGravity3D;
import barsuift.simLife.message.MockSubscriber;
import barsuift.simLife.tree.TreeLeaf;


public class MockGravity extends MockSubscriber implements Gravity {

    private GravityState state;

    private int synchronizedCalled;

    private Gravity3D gravity3D;

    private Set<TreeLeaf> fallingLeaves;

    public MockGravity() {
        reset();
    }

    public void reset() {
        this.state = new GravityState();
        this.synchronizedCalled = 0;
        this.gravity3D = new MockGravity3D();
        this.fallingLeaves = new HashSet<TreeLeaf>();
    }

    @Override
    public GravityState getState() {
        return state;
    }

    public void setState(GravityState state) {
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
    public Gravity3D getGravity3D() {
        return gravity3D;
    }

    public void setGravity3D(Gravity3D gravity3D) {
        this.gravity3D = gravity3D;
    }

    @Override
    public Set<TreeLeaf> getFallingLeaves() {
        return fallingLeaves;
    }

    @Override
    public void addFallingLeaf(TreeLeaf treeLeaf) {
        fallingLeaves.add(treeLeaf);
    }

}
