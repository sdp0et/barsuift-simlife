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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.tree.LeafEvent;
import barsuift.simLife.tree.MockTreeLeaf;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.universe.BasicUniverse;
import barsuift.simLife.universe.UniverseState;

import static org.fest.assertions.Assertions.assertThat;


public class BasicGravityTest {

    private BasicUniverse universe;

    private GravityState gravityState;

    @BeforeMethod
    protected void setUp() {
        UniverseState universeState = CoreDataCreatorForTests.createRandomUniverseState();
        universe = new BasicUniverse(universeState);
        universe.init();

        GravityStateFactory gravityStateFactory = new GravityStateFactory();
        gravityState = gravityStateFactory.createGravityState();
    }

    @AfterMethod
    protected void tearDown() {
        universe = null;
        gravityState = null;
    }

    @Test
    public void testAddFallingLeaf() {
        Set<TreeLeafState> fallingLeaves = new HashSet<TreeLeafState>();
        fallingLeaves.add(CoreDataCreatorForTests.createRandomTreeLeafState());
        fallingLeaves.add(CoreDataCreatorForTests.createRandomTreeLeafState());
        gravityState.setFallingLeaves(fallingLeaves);

        BasicGravity gravity = new BasicGravity(gravityState);
        gravity.init(universe);
        assertThat(gravity.getFallingLeaves()).hasSize(2);
        assertThat(gravity.getGravity3D().getGroup().numChildren()).isEqualTo(2);
        for (TreeLeaf treeLeaf : gravity.getFallingLeaves()) {
            TreeLeaf3D treeLeaf3D = treeLeaf.getTreeLeaf3D();

            // leaf3D and gravity are subscribers of the leaf
            assertThat(treeLeaf.countSubscribers()).isEqualTo(2);

            // leaf is subscriber of the leaf3D
            assertThat(treeLeaf3D.countSubscribers()).isEqualTo(1);

            // assert the gravity is really one of the subscribers of the leaf
            treeLeaf.deleteSubscriber(gravity);
            assertThat(treeLeaf.countSubscribers()).isEqualTo(1);
            // assert the leaf3D is really one of the subscribers of the leaf
            treeLeaf.deleteSubscriber(treeLeaf3D);
            assertThat(treeLeaf.countSubscribers()).isEqualTo(0);

            // assert the leaf is really one of the subscribers of the leaf3D
            treeLeaf3D.deleteSubscriber(treeLeaf);
            assertThat(treeLeaf3D.countSubscribers()).isEqualTo(0);

        }

        MockTreeLeaf treeLeaf = new MockTreeLeaf();
        gravity.addFallingLeaf(treeLeaf);
        assertThat(gravity.getFallingLeaves()).hasSize(3);
        assertThat(gravity.getGravity3D().getGroup().numChildren()).isEqualTo(3);
        // gravity is subscriber of the leaf
        assertThat(treeLeaf.countSubscribers()).isEqualTo(1);
        treeLeaf.deleteSubscriber(gravity);
        // assert the gravity is really one of the subscribers of the leaf
        assertThat(treeLeaf.countSubscribers()).isEqualTo(0);
    }

    @Test
    public void testUpdate() {
        BasicGravity gravity = new BasicGravity(gravityState);
        gravity.init(universe);
        MockTreeLeaf treeLeaf = new MockTreeLeaf();

        gravity.addFallingLeaf(treeLeaf);
        assertThat(gravity.getFallingLeaves()).contains(treeLeaf);
        assertThat(universe.getFallenLeaves()).excludes(treeLeaf);
        assertThat(gravity.getGravity3D().getGroup().numChildren()).isEqualTo(1);

        // test with wrong argument
        gravity.update(treeLeaf, MobileEvent.FALLING);
        assertThat(gravity.getFallingLeaves()).contains(treeLeaf);
        assertThat(universe.getFallenLeaves()).excludes(treeLeaf);
        assertThat(gravity.getGravity3D().getGroup().numChildren()).isEqualTo(1);

        // test with wrong argument
        gravity.update(treeLeaf, null);
        assertThat(gravity.getFallingLeaves()).contains(treeLeaf);
        assertThat(universe.getFallenLeaves()).excludes(treeLeaf);
        assertThat(gravity.getGravity3D().getGroup().numChildren()).isEqualTo(1);

        // test with wrong argument
        gravity.update(treeLeaf, LeafEvent.EFFICIENCY);
        assertThat(gravity.getFallingLeaves()).contains(treeLeaf);
        assertThat(universe.getFallenLeaves()).excludes(treeLeaf);
        assertThat(gravity.getGravity3D().getGroup().numChildren()).isEqualTo(1);

        // test with good argument
        gravity.update(treeLeaf, MobileEvent.FALLEN);
        assertThat(gravity.getFallingLeaves()).excludes(treeLeaf);
        assertThat(universe.getFallenLeaves()).contains(treeLeaf);
        assertThat(gravity.getGravity3D().getGroup().numChildren()).isEqualTo(0);
    }

    @Test
    public void testGetState() {
        BasicGravity gravity = new BasicGravity(gravityState);
        gravity.init(universe);

        assertThat(gravity.getState()).isEqualTo(gravityState);
        assertThat(gravity.getState()).isSameAs(gravityState);
        assertThat(gravity.getState().getFallingLeaves()).isEmpty();
        gravity.addFallingLeaf(new MockTreeLeaf());
        assertThat(gravity.getState()).isEqualTo(gravityState);
        assertThat(gravity.getState()).isSameAs(gravityState);
        assertThat(gravity.getState().getFallingLeaves()).hasSize(1);
    }

}
