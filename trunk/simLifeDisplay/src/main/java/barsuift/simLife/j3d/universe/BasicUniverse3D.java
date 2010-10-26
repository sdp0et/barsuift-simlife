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

import barsuift.simLife.j3d.Axis;
import barsuift.simLife.j3d.tree.Tree3D;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.j3d.universe.physic.BasicPhysics3D;
import barsuift.simLife.j3d.universe.physic.Physics3D;
import barsuift.simLife.j3d.util.TransformerHelper;
import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.universe.Universe;


public class BasicUniverse3D implements Universe3D {

    private BranchGroup root;

    private Set<Node> elements3D;

    private Physics3D physics;

    public BasicUniverse3D() {
        root = new BranchGroup();
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);

        elements3D = new HashSet<Node>();
        physics = new BasicPhysics3D(this);
    }

    public void initFromUniverse(Universe universe) {
        addElement3D(universe.getEnvironment().getEnvironment3D().getGroup());

        for (Tree tree : universe.getTrees()) {
            addTree(tree);
        }

        for (TreeLeaf treeLeaf : universe.getFallenLeaves()) {
            addFallenLeaf(treeLeaf.getTreeLeaf3D());
        }
    }

    private void addTree(Tree tree) {
        Tree3D tree3D = tree.getTree3D();
        Point3d treeOriginPoint = tree3D.getState().getTranslationVector().toPointValue();
        Transform3D translation = TransformerHelper.getTranslationTransform3D(new Vector3d(treeOriginPoint));
        TransformGroup transformGroup = new TransformGroup(translation);

        BranchGroup treeBranchGroup = new BranchGroup();
        treeBranchGroup.addChild(transformGroup);
        transformGroup.addChild(tree3D.getBranchGroup());

        addElement3D(treeBranchGroup);
    }

    private void addFallenLeaf(TreeLeaf3D treeLeaf3D) {
        Point3d treeLeafAttachPoint = treeLeaf3D.getAttachPoint();
        double treeLeafRotation = treeLeaf3D.getRotation();
        Transform3D translation = TransformerHelper.getTranslationTransform3D(new Vector3d(treeLeafAttachPoint));
        Transform3D rotation = TransformerHelper.getRotationTransform3D(treeLeafRotation, Axis.Y);
        translation.mul(rotation);
        TransformGroup transformGroup = new TransformGroup(translation);

        BranchGroup treeLeafBranchGroup = new BranchGroup();
        treeLeafBranchGroup.addChild(transformGroup);
        transformGroup.addChild(treeLeaf3D.getNode());

        addElement3D(treeLeafBranchGroup);
    }

    public void addElement3D(Node element3D) {
        elements3D.add(element3D);
        root.addChild(element3D);
    }

    public Set<Node> getElements3D() {
        return Collections.unmodifiableSet(elements3D);
    }

    @Override
    public Physics3D getPhysics() {
        return physics;
    }

    @Override
    public BranchGroup getUniverseRoot() {
        return root;
    }

}
