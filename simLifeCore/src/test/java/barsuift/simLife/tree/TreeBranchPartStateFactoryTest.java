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

import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.Tuple3dState;
import barsuift.simLife.j3d.helper.PointTestHelper;


public class TreeBranchPartStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomBranchPartState() {

        TreeBranchPartStateFactory factory = new TreeBranchPartStateFactory();
        Point3d branchPartEndPoint = new Point3d(Math.random(), Math.random(), Math.random());
        TreeBranchPartState branchPartState = factory.createRandomBranchPartState(branchPartEndPoint);
        assertNotNull(branchPartState.getBranchPart3DState());

        List<TreeLeafState> leaveStates = branchPartState.getLeaveStates();
        int nbLeaves = leaveStates.size();
        assertTrue(nbLeaves >= 2);
        assertTrue(nbLeaves <= 4);
        for (int index = 0; index < nbLeaves; index++) {
            TreeLeafState leafState = leaveStates.get(index);
            Tuple3dState leafAttachPoint = leafState.getLeaf3DState().getLeafAttachPoint();
            PointTestHelper.assertPointIsWithinBounds(leafAttachPoint.toPointValue(), new Point3d(0, 0, 0),
                    branchPartEndPoint);
        }

        assertTrue(branchPartState.getCreationMillis() >= 0);
        assertTrue(branchPartState.getCreationMillis() <= 100000);
        assertTrue(branchPartState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchPartState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
        assertTrue(branchPartState.getFreeEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchPartState.getFreeEnergy().compareTo(new BigDecimal(50)) <= 0);
    }

}
