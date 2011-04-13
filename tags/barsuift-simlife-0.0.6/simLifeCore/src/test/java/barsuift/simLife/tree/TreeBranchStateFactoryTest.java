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
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.util.DistanceHelper;


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
        List<TreeBranchPartState> branchPartStates = branchState.getBranchPartStates();
        assertEquals(3, branchPartStates.size());
        for (TreeBranchPartState treeBranchPartState : branchPartStates) {
            assertNotNull(treeBranchPartState);
            checkEndPoint(treeBranchPartState.getBranchPart3DState().getEndPoint().toPointValue());
        }
        assertTrue(branchState.getCreationMillis() >= 0);
        assertTrue(branchState.getCreationMillis() <= 100000);
        assertTrue(branchState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
        assertTrue(branchState.getFreeEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchState.getFreeEnergy().compareTo(new BigDecimal(50)) <= 0);
    }

    public void testComputeBranchPartEndPoint() {
        int nbParts = branchState.getBranchPartStates().size();
        Point3f partEndPoint = factory.computeBranchPartEndPoint(branchEndPoint, nbParts);
        checkEndPoint(partEndPoint);
    }

    private void checkEndPoint(Point3f partEndPoint) {
        int nbParts = branchState.getBranchPartStates().size();
        float branchLength = DistanceHelper.distanceFromOrigin(branchEndPoint);

        float xLength = Math.abs(branchEndPoint.getX());
        float yLength = Math.abs(branchEndPoint.getY());
        float zLength = Math.abs(branchEndPoint.getZ());

        int xCoeff = (0 < branchEndPoint.getX()) ? 1 : -1;
        int yCoeff = (0 < branchEndPoint.getY()) ? 1 : -1;
        int zCoeff = (0 < branchEndPoint.getZ()) ? 1 : -1;

        float xbounds1 = (xCoeff * (0.5f) * xLength / nbParts) - (xCoeff * 0.1f * branchLength);
        float ybounds1 = (yCoeff * (0.5f) * yLength / nbParts) - (yCoeff * 0.1f * branchLength);
        float zbounds1 = (zCoeff * (0.5f) * zLength / nbParts) - (zCoeff * 0.1f * branchLength);
        Point3f bound1 = new Point3f(xbounds1, ybounds1, zbounds1);

        float xbounds2 = (xCoeff * (1.5f) * xLength / nbParts) + (xCoeff * 0.1f * branchLength);
        float ybounds2 = (yCoeff * (1.5f) * yLength / nbParts) + (yCoeff * 0.1f * branchLength);
        float zbounds2 = (zCoeff * (1.5f) * zLength / nbParts) + (zCoeff * 0.1f * branchLength);
        Point3f bound2 = new Point3f(xbounds2, ybounds2, zbounds2);

        PointTestHelper.assertPointIsWithinBounds(partEndPoint, bound1, bound2);
    }

}
