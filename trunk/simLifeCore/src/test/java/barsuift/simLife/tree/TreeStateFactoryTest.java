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

import java.math.BigDecimal;
import java.util.List;

import junit.framework.TestCase;


public class TreeStateFactoryTest extends TestCase {

    private TreeStateFactory factory;

    protected void setUp() throws Exception {
        super.setUp();
        factory = new TreeStateFactory();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        factory = null;
    }

    public void testCreateRandomTreeState() {
        TreeState treeState = factory.createRandomTreeState();
        List<TreeBranchState> branches = treeState.getBranches();
        assertTrue(20 <= branches.size());
        assertTrue(40 >= branches.size());
        float height = treeState.getHeight();
        assertTrue(3 <= height);
        assertTrue(5 >= height);
        TreeTrunkState trunkState = treeState.getTrunkState();
        assertEquals(height / 16, trunkState.getRadius());
        assertNotNull(treeState.getTree3DState());

        assertTrue(treeState.getCreationMillis() >= 0);
        assertTrue(treeState.getCreationMillis() <= 100000);
        assertTrue(treeState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(treeState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
    }

}
