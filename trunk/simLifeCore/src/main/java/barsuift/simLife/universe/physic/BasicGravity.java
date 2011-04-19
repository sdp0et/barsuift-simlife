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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.universe.physic.BasicGravity3D;
import barsuift.simLife.j3d.universe.physic.Gravity3D;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.tree.BasicTreeLeaf;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.universe.Universe;


public class BasicGravity implements Gravity {

    private final GravityState state;

    private final Gravity3D gravity3D;

    private final Set<TreeLeaf> fallingLeaves;

    private final Universe universe;

    public BasicGravity(GravityState state, Universe universe) {
        this.state = state;
        this.universe = universe;
        this.fallingLeaves = new HashSet<TreeLeaf>();
        this.gravity3D = new BasicGravity3D(state.getGravity3D(), universe.getUniverse3D());
        Set<TreeLeafState> fallingLeafStates = state.getFallingLeaves();
        for (TreeLeafState fallingLeafState : fallingLeafStates) {
            addFallingLeaf(new BasicTreeLeaf(universe, fallingLeafState));
        }
    }

    @Override
    public Set<TreeLeaf> getFallingLeaves() {
        return Collections.unmodifiableSet(fallingLeaves);
    }

    @Override
    public void addFallingLeaf(TreeLeaf treeLeaf) {
        treeLeaf.addSubscriber(this);
        fallingLeaves.add(treeLeaf);
        gravity3D.fall(treeLeaf.getTreeLeaf3D());
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == MobileEvent.FALLEN) {
            TreeLeaf leaf = (TreeLeaf) publisher;
            fallingLeaves.remove(leaf);
            gravity3D.isFallen(leaf.getTreeLeaf3D());
            universe.addFallenLeaf(leaf);
        }
    }

    @Override
    public GravityState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        Set<TreeLeafState> fallingLeaveStates = new HashSet<TreeLeafState>();
        for (TreeLeaf leaf : fallingLeaves) {
            fallingLeaveStates.add((TreeLeafState) leaf.getState());
        }
        state.setFallingLeaves(fallingLeaveStates);
        gravity3D.synchronize();
    }

    @Override
    public Gravity3D getGravity3D() {
        return gravity3D;
    }

}
