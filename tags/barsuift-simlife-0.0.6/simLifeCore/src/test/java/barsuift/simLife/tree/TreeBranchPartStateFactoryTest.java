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

import javax.vecmath.Point3f;

import junit.framework.TestCase;
import barsuift.simLife.j3d.Transform3DState;
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
        Point3f branchPartEndPoint = new Point3f((float) Math.random(), (float) Math.random(), (float) Math.random());
        TreeBranchPartState branchPartState = factory.createRandomBranchPartState(branchPartEndPoint);
        assertNotNull(branchPartState.getBranchPart3DState());

        List<TreeLeafState> leaveStates = branchPartState.getLeaveStates();
        int nbLeaves = leaveStates.size();
        assertTrue(nbLeaves >= 2);
        assertTrue(nbLeaves <= 4);
        for (int index = 0; index < nbLeaves; index++) {
            TreeLeafState leafState = leaveStates.get(index);
            Transform3DState transform = leafState.getLeaf3DState().getTransform();
            Point3f leafAttachPoint = new Point3f(transform.getMatrix()[3], transform.getMatrix()[7],
                    transform.getMatrix()[11]);

            PointTestHelper.assertPointIsWithinBounds(leafAttachPoint, new Point3f(0, 0, 0), branchPartEndPoint);
        }

        assertTrue(branchPartState.getCreationMillis() >= 0);
        assertTrue(branchPartState.getCreationMillis() <= 100000);
        assertTrue(branchPartState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchPartState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
        assertTrue(branchPartState.getFreeEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchPartState.getFreeEnergy().compareTo(new BigDecimal(50)) <= 0);
    }

}