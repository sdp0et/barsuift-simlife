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
package barsuift.simLife.j3d.universe;

import java.util.HashSet;
import java.util.Set;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;

import barsuift.simLife.j3d.environment.Environment3D;
import barsuift.simLife.j3d.environment.MockEnvironment3D;
import barsuift.simLife.j3d.universe.physic.MockPhysics3D;
import barsuift.simLife.j3d.universe.physic.Physics3D;
import barsuift.simLife.process.MainSynchronizer;
import barsuift.simLife.process.MockMainSynchronizer;
import barsuift.simLife.time.SimLifeDate;


public class MockUniverse3D implements Universe3D {

    private Set<Node> elements3D;

    private Physics3D physics3D;

    private Environment3D environment3D;

    private BranchGroup branchGroup;

    private Universe3DState state;

    private int synchronizedCalled;

    private MainSynchronizer synchronizer;

    private SimLifeDate date;

    public MockUniverse3D() {
        super();
        reset();
    }

    public void reset() {
        elements3D = new HashSet<Node>();
        physics3D = new MockPhysics3D();
        environment3D = new MockEnvironment3D();
        branchGroup = new BranchGroup();
        state = new Universe3DState();
        synchronizedCalled = 0;
        synchronizer = new MockMainSynchronizer();
        date = new SimLifeDate();
    }

    @Override
    public void addElement3D(Node element3d) {
        elements3D.add(element3d);
    }

    @Override
    public Set<Node> getElements3D() {
        return elements3D;
    }

    @Override
    public Physics3D getPhysics3D() {
        return physics3D;
    }

    public void setPhysics(Physics3D physics) {
        this.physics3D = physics;
    }

    @Override
    public Environment3D getEnvironment3D() {
        return environment3D;
    }

    public void setEnvironment3D(Environment3D environment3D) {
        this.environment3D = environment3D;
    }

    @Override
    public BranchGroup getUniverseRoot() {
        return branchGroup;
    }

    public void setBranchGroup(BranchGroup branchGroup) {
        this.branchGroup = branchGroup;
    }

    @Override
    public Universe3DState getState() {
        return state;
    }

    public void setState(Universe3DState state) {
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
    public MainSynchronizer getSynchronizer() {
        return synchronizer;
    }

    public void setSynchronizer(MainSynchronizer synchronizer) {
        this.synchronizer = synchronizer;
    }

    @Override
    public SimLifeDate getDate() {
        return date;
    }

    public void setDate(SimLifeDate date) {
        this.date = date;
    }

}
