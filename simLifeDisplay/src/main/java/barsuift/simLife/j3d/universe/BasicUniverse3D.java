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
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import barsuift.simLife.j3d.tree.Tree3D;
import barsuift.simLife.j3d.universe.physic.Physics3D;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.process.BasicSynchronizer3D;
import barsuift.simLife.process.Synchronizer3D;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.universe.Universe;

public class BasicUniverse3D implements Universe3D {

    private final BasicSynchronizer3D synchronizer;

    private final Universe3DState state;

    private BranchGroup root;

    private Set<Node> elements3D;

    private final Universe universe;

    public BasicUniverse3D(Universe3DState state, Universe universe) {
        this.state = state;
        this.universe = universe;
        this.synchronizer = new BasicSynchronizer3D(state.getSynchronizerState());
        root = new BranchGroup();
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        elements3D = new HashSet<Node>();
    }

    // TODO 888 see if this can be improved
    public void initFromUniverse(Universe universe) {
        addElement3D(universe.getEnvironment().getEnvironment3D().getGroup());
        addElement3D(universe.getPhysics().getPhysics3D().getGroup());

        for (Tree tree : universe.getTrees()) {
            addTree(tree.getTree3D());
        }

        for (TreeLeaf treeLeaf : universe.getFallenLeaves()) {
            addElement3D(treeLeaf.getTreeLeaf3D().getBranchGroup());
        }
    }

    private void addTree(Tree3D tree3D) {
        // TODO 001. this code should be move into BasicTree3D, as done for BasicTreeLeaf3D (to be done for everyone)
        Point3d treeOriginPoint = tree3D.getState().getTranslationVector().toPointValue();
        Transform3D translation = TransformerHelper.getTranslationTransform3D(new Vector3d(treeOriginPoint));
        TransformGroup transformGroup = new TransformGroup(translation);

        BranchGroup treeBranchGroup = new BranchGroup();
        treeBranchGroup.addChild(transformGroup);
        transformGroup.addChild(tree3D.getBranchGroup());

        addElement3D(treeBranchGroup);
    }

    public void addElement3D(Node element3D) {
        elements3D.add(element3D);
        root.addChild(element3D);
    }

    public Set<Node> getElements3D() {
        return Collections.unmodifiableSet(elements3D);
    }

    @Override
    public Synchronizer3D getSynchronizer() {
        return synchronizer;
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
        synchronizer.synchronize();
    }

}
