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
package barsuift.simLife.environment;

import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.environment.MockWind3D;
import barsuift.simLife.j3d.environment.Wind3D;
import barsuift.simLife.message.MockSubscriber;
import barsuift.simLife.tree.TreeLeaf;


public class MockWind extends MockSubscriber implements Wind {

    private WindState state;

    private int synchronizedCalled;

    private Wind3D wind3D;

    private Set<TreeLeaf> movingLeaves;

    public MockWind() {
        reset();
    }

    public void reset() {
        this.state = new WindState();
        this.synchronizedCalled = 0;
        this.wind3D = new MockWind3D();
        this.movingLeaves = new HashSet<TreeLeaf>();
    }

    @Override
    public WindState getState() {
        return state;
    }

    public void setState(WindState state) {
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
    public Wind3D getWind3D() {
        return wind3D;
    }

    public void setWind3D(Wind3D wind3D) {
        this.wind3D = wind3D;
    }

    @Override
    public Set<TreeLeaf> getMovingLeaves() {
        return movingLeaves;
    }

    @Override
    public void addMovingLeaf(TreeLeaf treeLeaf) {
        movingLeaves.add(treeLeaf);
    }


}
