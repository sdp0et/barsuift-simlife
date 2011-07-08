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
package barsuift.simLife.tree;

import org.testng.Assert;
import org.testng.annotations.Test;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.universe.MockUniverse;

import static org.fest.assertions.Assertions.assertThat;


public class BasicTreeTrunkTest {

    @Test
    public void testBasicTreeTrunk() {
        try {
            new BasicTreeTrunk(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            BasicTreeTrunk trunk = new BasicTreeTrunk(new TreeTrunkState());
            trunk.init(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    @Test
    public void testGetState() {
        TreeTrunkState trunkState = CoreDataCreatorForTests.createSpecificTreeTrunkState();
        BasicTreeTrunk treeTrunk = new BasicTreeTrunk(trunkState);
        treeTrunk.init(new MockUniverse());
        assertThat(treeTrunk.getHeight()).isEqualTo(4.0f);
        assertThat(treeTrunk.getRadius()).isEqualTo(0.5f);

        assertThat(treeTrunk.getState()).isEqualTo(trunkState);
        assertThat(treeTrunk.getState()).isSameAs(trunkState);
        // nothing to do here
        assertThat(treeTrunk.getState()).isEqualTo(trunkState);
        assertThat(treeTrunk.getState()).isSameAs(trunkState);
    }

}
