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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.environment.BasicWind3D;
import barsuift.simLife.j3d.environment.Wind3D;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.tree.BasicTreeLeaf;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.universe.Universe;


public class BasicWind implements Wind {

    private final WindState state;

    private final BasicWind3D wind3D;

    private final Set<TreeLeaf> movingLeaves;

    public BasicWind(WindState state) {
        this.state = state;
        this.movingLeaves = new HashSet<TreeLeaf>();
        this.wind3D = new BasicWind3D(state.getWind3D());
        Set<TreeLeafState> movingLeafStates = state.getMovingLeaves();
        for (TreeLeafState movingLeafState : movingLeafStates) {
            addMovingLeaf(new BasicTreeLeaf(movingLeafState));
        }
    }

    public void init(Universe universe) {
        this.wind3D.init(universe.getUniverse3D());
        for (TreeLeaf movingLeaf : movingLeaves) {
            ((BasicTreeLeaf) movingLeaf).init(universe);
        }
    }

    @Override
    public Set<TreeLeaf> getMovingLeaves() {
        return Collections.unmodifiableSet(movingLeaves);
    }

    @Override
    public void addMovingLeaf(TreeLeaf treeLeaf) {
        treeLeaf.addSubscriber(this);
        movingLeaves.add(treeLeaf);
        wind3D.move(treeLeaf.getTreeLeaf3D());
    }

    @Override
    public void update(Publisher publisher, Object arg) {
        if (arg == MobileEvent.FALLEN) {
            TreeLeaf leaf = (TreeLeaf) publisher;
            movingLeaves.remove(leaf);
            wind3D.isGrounded(leaf.getTreeLeaf3D());
        }
    }

    @Override
    public WindState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        Set<TreeLeafState> movingLeavesStates = new HashSet<TreeLeafState>();
        for (TreeLeaf leaf : movingLeaves) {
            movingLeavesStates.add((TreeLeafState) leaf.getState());
        }
        state.setMovingLeaves(movingLeavesStates);
        wind3D.synchronize();
    }

    @Override
    public Wind3D getWind3D() {
        return wind3D;
    }

}
