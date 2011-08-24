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
import static barsuift.simLife.j3d.assertions.GroupAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;


public class BasicWindTest {

    private BasicUniverse universe;

    private WindState windState;

    @BeforeMethod
    protected void setUp() {
        UniverseState universeState = CoreDataCreatorForTests.createRandomUniverseState();
        universe = new BasicUniverse(universeState);
        universe.init();

        WindStateFactory windStateFactory = new WindStateFactory();
        windState = windStateFactory.createWindState();
    }

    @AfterMethod
    protected void tearDown() {
        universe = null;
        windState = null;
    }

    @Test
    public void testAddMovingLeaf() {
        Set<TreeLeafState> movingLeaves = new HashSet<TreeLeafState>();
        movingLeaves.add(CoreDataCreatorForTests.createRandomTreeLeafState());
        movingLeaves.add(CoreDataCreatorForTests.createRandomTreeLeafState());
        windState.setMovingLeaves(movingLeaves);

        BasicWind wind = new BasicWind(windState);
        wind.init(universe);
        assertThat(wind.getMovingLeaves()).hasSize(2);
        assertThat(wind.getWind3D().getGroup()).hasSize(2);
        for (TreeLeaf treeLeaf : wind.getMovingLeaves()) {
            TreeLeaf3D treeLeaf3D = treeLeaf.getTreeLeaf3D();

            // leaf3D and wind are subscribers of the leaf
            assertThat(treeLeaf.countSubscribers()).isEqualTo(2);

            // leaf is subscriber of the leaf3D
            assertThat(treeLeaf3D.countSubscribers()).isEqualTo(1);

            // assert the wind is really one of the subscribers of the leaf
            treeLeaf.deleteSubscriber(wind);
            assertThat(treeLeaf.countSubscribers()).isEqualTo(1);
            // assert the leaf3D is really one of the subscribers of the leaf
            treeLeaf.deleteSubscriber(treeLeaf3D);
            assertThat(treeLeaf.countSubscribers()).isEqualTo(0);

            // assert the leaf is really one of the subscribers of the leaf3D
            treeLeaf3D.deleteSubscriber(treeLeaf);
            assertThat(treeLeaf3D.countSubscribers()).isEqualTo(0);

        }

        MockTreeLeaf treeLeaf = new MockTreeLeaf();
        wind.addMovingLeaf(treeLeaf);
        assertThat(wind.getMovingLeaves()).hasSize(3);
        assertThat(wind.getWind3D().getGroup()).hasSize(3);
        // wind is subscriber of the leaf
        assertThat(treeLeaf.countSubscribers()).isEqualTo(1);
        treeLeaf.deleteSubscriber(wind);
        // assert the wind is really one of the subscribers of the leaf
        assertThat(treeLeaf.countSubscribers()).isEqualTo(0);
    }

    @Test
    public void testUpdate() {
        BasicWind wind = new BasicWind(windState);
        wind.init(universe);
        MockTreeLeaf treeLeaf = new MockTreeLeaf();

        wind.addMovingLeaf(treeLeaf);
        assertThat(wind.getMovingLeaves()).contains(treeLeaf);
        assertThat(universe.getFallenLeaves()).excludes(treeLeaf);
        assertThat(wind.getWind3D().getGroup()).hasSize(1);

        // test with wrong argument
        wind.update(treeLeaf, MobileEvent.FALLING);
        assertThat(wind.getMovingLeaves()).contains(treeLeaf);
        assertThat(universe.getFallenLeaves()).excludes(treeLeaf);
        assertThat(wind.getWind3D().getGroup()).hasSize(1);

        // test with wrong argument
        wind.update(treeLeaf, null);
        assertThat(wind.getMovingLeaves()).contains(treeLeaf);
        assertThat(universe.getFallenLeaves()).excludes(treeLeaf);
        assertThat(wind.getWind3D().getGroup()).hasSize(1);

        // test with wrong argument
        wind.update(treeLeaf, LeafEvent.EFFICIENCY);
        assertThat(wind.getMovingLeaves()).contains(treeLeaf);
        assertThat(universe.getFallenLeaves()).excludes(treeLeaf);
        assertThat(wind.getWind3D().getGroup()).hasSize(1);

        // test with good argument
        wind.update(treeLeaf, MobileEvent.FALLEN);
        assertThat(wind.getMovingLeaves()).excludes(treeLeaf);
        assertThat(universe.getFallenLeaves()).contains(treeLeaf);
        assertThat(wind.getWind3D().getGroup()).hasSize(0);
    }

    @Test
    public void testGetState() {
        BasicWind wind = new BasicWind(windState);
        wind.init(universe);

        assertThat(wind.getState()).isEqualTo(windState);
        assertThat(wind.getState()).isSameAs(windState);
        assertThat(wind.getState().getMovingLeaves()).isEmpty();
        wind.addMovingLeaf(new MockTreeLeaf());
        assertThat(wind.getState()).isEqualTo(windState);
        assertThat(wind.getState()).isSameAs(windState);
        assertThat(wind.getState().getMovingLeaves()).hasSize(1);
    }

}
