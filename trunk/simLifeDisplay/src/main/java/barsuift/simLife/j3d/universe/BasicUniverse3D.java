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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;

import barsuift.simLife.j3d.environment.Environment3D;
import barsuift.simLife.j3d.universe.physic.Physics3D;
import barsuift.simLife.process.MainSynchronizer;
import barsuift.simLife.time.SimLifeDate;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.universe.Universe;

public class BasicUniverse3D implements Universe3D {

    private final Universe3DState state;

    private BranchGroup root;

    private Set<Node> elements3D;

    private Universe universe;

    public BasicUniverse3D(Universe3DState state) {
        this.state = state;
        root = new BranchGroup();
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        elements3D = new HashSet<Node>();
    }

    public void init(Universe universe) {
        this.universe = universe;
        addElement3D(universe.getEnvironment().getEnvironment3D().getGroup());
        addElement3D(universe.getPhysics().getPhysics3D().getGroup());

        for (Tree tree : universe.getTrees()) {
            addElement3D(tree.getTree3D().getBranchGroup());
        }

        for (TreeLeaf treeLeaf : universe.getFallenLeaves()) {
            addElement3D(treeLeaf.getTreeLeaf3D().getBranchGroup());
        }
    }

    public void addElement3D(Node element3D) {
        elements3D.add(element3D);
        root.addChild(element3D);
    }

    public Set<Node> getElements3D() {
        return Collections.unmodifiableSet(elements3D);
    }

    @Override
    public SimLifeDate getDate() {
        return universe.getDate();
    }

    @Override
    public MainSynchronizer getSynchronizer() {
        return universe.getSynchronizer();
    }

    @Override
    public Environment3D getEnvironment3D() {
        return universe.getEnvironment().getEnvironment3D();
    }

    @Override
    public Physics3D getPhysics3D() {
        return universe.getPhysics().getPhysics3D();
    }

    @Override
    public BranchGroup getUniverseRoot() {
        return root;
    }

    @Override
    public Universe3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }

}
