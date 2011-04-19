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
import javax.vecmath.Vector3f;

import junit.framework.TestCase;
import barsuift.simLife.j3d.Transform3DState;
import barsuift.simLife.j3d.helper.PointTestHelper;


public class TreeBranchStateFactoryTest extends TestCase {

    private Vector3f translationVector;

    private Point3f branchEndPoint;

    private TreeBranchState branchState;

    private TreeBranchStateFactory factory;

    protected void setUp() throws Exception {
        super.setUp();
        translationVector = new Vector3f(1.5f, 3.6f, 8.9f);
        branchEndPoint = new Point3f(1, 2.7f, 3);
        factory = new TreeBranchStateFactory();
        branchState = factory.createRandomBranchState(translationVector, branchEndPoint);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        translationVector = null;
        branchEndPoint = null;
        factory = null;
        branchState = null;
    }

    public void testCreateBranchState() {
        assertNotNull(branchState.getBranch3DState());
        List<TreeLeafState> leaveStates = branchState.getLeaveStates();
        int nbLeaves = leaveStates.size();
        assertTrue(nbLeaves >= 6);
        assertTrue(nbLeaves <= 12);
        for (int index = 0; index < nbLeaves; index++) {
            TreeLeafState leafState = leaveStates.get(index);
            Transform3DState transform = leafState.getLeaf3DState().getTransform();
            Point3f leafAttachPoint = new Point3f(transform.getMatrix()[3], transform.getMatrix()[7],
                    transform.getMatrix()[11]);
            PointTestHelper.assertPointIsWithinBounds(leafAttachPoint, new Point3f(0, 0, 0), branchEndPoint);
        }
        assertTrue(branchState.getCreationMillis() >= 0);
        assertTrue(branchState.getCreationMillis() <= 100000);
        assertTrue(branchState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
        assertTrue(branchState.getFreeEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchState.getFreeEnergy().compareTo(new BigDecimal(50)) <= 0);
    }

}
