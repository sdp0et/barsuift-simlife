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

import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.j3d.tree.BasicTreeLeaf3D;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DStateFactory;
import barsuift.simLife.tree.MockTree;
import barsuift.simLife.tree.MockTreeLeaf;
import barsuift.simLife.universe.MockUniverse;
import barsuift.simLife.universe.Universe;

import static org.fest.assertions.Assertions.assertThat;


public class BasicUniverse3DTest {

    @Test
    public void testBasicUniverse3D() {
        BasicUniverse3D universe3D = new BasicUniverse3D(new Universe3DState());
        universe3D.init(new MockUniverse());
        assertThat(universe3D.getElements3D()).isNotNull();
        assertThat(universe3D.getPhysics3D()).isNotNull();
        BranchGroup root = universe3D.getUniverseRoot();
        assertThat(root).isNotNull();
        // should already contain the environment3D and Physics3D
        assertThat(root.numChildren()).isEqualTo(2);
        assertThat(root.getCapability(Group.ALLOW_CHILDREN_EXTEND)).isTrue();
    }

    @Test
    public void init() {
        Universe universe = new MockUniverse();
        MockTreeLeaf treeLeaf = new MockTreeLeaf();
        treeLeaf.setState(CoreDataCreatorForTests.createSpecificTreeLeafState());
        universe.addFallenLeaf(treeLeaf);
        MockTree tree = new MockTree();
        tree.setState(CoreDataCreatorForTests.createSpecificTreeState());
        universe.addTree(tree);
        BasicUniverse3D universe3D = new BasicUniverse3D(new Universe3DState());
        universe3D.init(universe);

        assertThat(universe3D.getElements3D()).contains(universe.getEnvironment().getEnvironment3D().getGroup());
        assertThat(universe3D.getElements3D()).contains(universe.getPhysics().getPhysics3D().getGroup());

        Set<Node> elements3d = universe3D.getElements3D();
        assertThat(elements3d).isNotNull();
        assertThat(elements3d).hasSize(4);
        assertThat(elements3d).contains(tree.getTree3D().getBranchGroup());
        assertThat(elements3d).contains(treeLeaf.getTreeLeaf3D().getBranchGroup());

    }

    @Test
    public void testAddElement3D() {
        BasicUniverse3D universe3D = new BasicUniverse3D(new Universe3DState());
        universe3D.init(new MockUniverse());
        TreeLeaf3DStateFactory stateFactory = new TreeLeaf3DStateFactory();
        TreeLeaf3DState leaf3dState = stateFactory.createRandomTreeLeaf3DState();
        Set<Node> elements3d = universe3D.getElements3D();
        assertThat(elements3d).isNotNull();
        // should already contain the environment3D and Physics3D
        assertThat(elements3d).hasSize(2);

        BasicTreeLeaf3D treeLeaf3D = new BasicTreeLeaf3D(leaf3dState);
        treeLeaf3D.init(universe3D, new MockTreeLeaf());
        universe3D.addElement3D(treeLeaf3D.getBranchGroup());
        elements3d = universe3D.getElements3D();
        assertThat(elements3d).isNotNull();
        assertThat(elements3d).hasSize(3);
        assertThat(elements3d).contains(treeLeaf3D.getBranchGroup());

        BranchGroup root = universe3D.getUniverseRoot();
        assertThat(root).isNotNull();
        assertThat(root.numChildren()).isEqualTo(3);
        assertThat(root.getChild(2)).isEqualTo(treeLeaf3D.getBranchGroup());
    }
}
