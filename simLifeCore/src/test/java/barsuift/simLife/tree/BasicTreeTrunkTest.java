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

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.universe.MockUniverse;


public class BasicTreeTrunkTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBasicTreeTrunk() {
        try {
            new BasicTreeTrunk(new MockUniverse(), null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeTrunk(null, new TreeTrunkState());
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        TreeTrunkState trunkState = CoreDataCreatorForTests.createSpecificTreeTrunkState();
        BasicTreeTrunk treeTrunk = new BasicTreeTrunk(new MockUniverse(), trunkState);
        assertEquals(4.0f, treeTrunk.getHeight());
        assertEquals(0.5f, treeTrunk.getRadius());

        assertEquals(trunkState, treeTrunk.getState());
        assertSame(trunkState, treeTrunk.getState());
        treeTrunk.spendTime();
        assertEquals(trunkState, treeTrunk.getState());
        assertSame(trunkState, treeTrunk.getState());
    }

}
