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

import java.util.Set;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.j3d.tree.BasicTreeLeaf3D;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DStateFactory;
import barsuift.simLife.tree.MockTree;
import barsuift.simLife.tree.MockTreeLeaf;
import barsuift.simLife.universe.MockUniverse;
import barsuift.simLife.universe.Universe;


public class BasicUniverse3DTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBasicUniverse3D() {
        BasicUniverse3D universe3D = new BasicUniverse3D();
        assertNotNull(universe3D.getElements3D());
        assertNotNull(universe3D.getPhysics());
        BranchGroup root = universe3D.getUniverseRoot();
        assertNotNull(root);
        assertEquals(0, root.numChildren());
        assertTrue(root.getCapability(Group.ALLOW_CHILDREN_EXTEND));
    }

    public void testInitFromUniverse() {
        BasicUniverse3D universe3D = new BasicUniverse3D();
        Universe universe = new MockUniverse();
        MockTreeLeaf treeLeaf = new MockTreeLeaf();
        treeLeaf.setState(CoreDataCreatorForTests.createSpecificTreeLeafState());
        universe.addFallenLeaf(treeLeaf);
        MockTree tree = new MockTree();
        tree.setState(CoreDataCreatorForTests.createSpecificTreeState());
        universe.addTree(tree);
        universe3D.initFromUniverse(universe);

        assertNotNull(universe3D.getLights());

        Set<Node> elements3d = universe3D.getElements3D();
        assertNotNull(elements3d);
        assertEquals(3, elements3d.size());
        // the 3D node is added to a transform group, itself added to a branch group
        // that's why i test the parent of the parent
        assertTrue(elements3d.contains(tree.getTree3D().getBranchGroup().getParent().getParent()));
        assertTrue(elements3d.contains(treeLeaf.getTreeLeaf3D().getNode().getParent().getParent()));

    }

    public void testAddElement3D() {
        BasicUniverse3D universe3D = new BasicUniverse3D();
        TreeLeaf3DStateFactory stateFactory = new TreeLeaf3DStateFactory();
        Point3d leafAttachPoint = new Point3d(Math.random(), Math.random(), Math.random());
        TreeLeaf3DState leaf3dState = stateFactory.createRandomTreeLeaf3DState(leafAttachPoint);
        BasicTreeLeaf3D treeLeaf3D = new BasicTreeLeaf3D(universe3D, leaf3dState, new MockTreeLeaf());
        universe3D.addElement3D(treeLeaf3D.getNode());
        Set<Node> elements3d = universe3D.getElements3D();
        assertNotNull(elements3d);
        assertEquals(1, elements3d.size());
        assertTrue(elements3d.contains(treeLeaf3D.getNode()));
        BranchGroup root = universe3D.getUniverseRoot();
        assertNotNull(root);
        assertEquals(1, root.numChildren());
        assertEquals(treeLeaf3D.getNode(), root.getChild(0));
    }
}
