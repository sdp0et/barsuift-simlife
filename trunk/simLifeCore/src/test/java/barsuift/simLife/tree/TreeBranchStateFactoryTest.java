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
import javax.vecmath.Vector3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.util.DistanceHelper;


public class TreeBranchStateFactoryTest extends TestCase {

    private Vector3d translationVector;

    private Point3d branchEndPoint;

    private TreeBranchState branchState;

    private TreeBranchStateFactory factory;

    protected void setUp() throws Exception {
        super.setUp();
        translationVector = new Vector3d(1.5, 3.6, 8.9);
        branchEndPoint = new Point3d(1, 2.7, 3);
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
        Long id1 = branchState.getId();
        assertNotNull(id1);
        assertTrue(id1.longValue() > 0);
        assertTrue(branchState.getAge() >= 0);
        assertTrue(branchState.getAge() <= 100);
        assertTrue(branchState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
        assertTrue(branchState.getFreeEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(branchState.getFreeEnergy().compareTo(new BigDecimal(50)) <= 0);

        branchState = factory.createRandomBranchState(translationVector, branchEndPoint);
        Long id2 = branchState.getId();
        assertEquals(id1.longValue() + 1, id2.longValue());
    }

    public void testComputeBranchPartEndPoint() {
        int nbParts = branchState.getBranchPartStates().size();
        Point3d partEndPoint = factory.computeBranchPartEndPoint(branchEndPoint, nbParts);
        checkEndPoint(partEndPoint);
    }

    private void checkEndPoint(Point3d partEndPoint) {
        int nbParts = branchState.getBranchPartStates().size();
        double branchLength = DistanceHelper.distanceFromOrigin(branchEndPoint);

        double xLength = Math.abs(branchEndPoint.getX());
        double yLength = Math.abs(branchEndPoint.getY());
        double zLength = Math.abs(branchEndPoint.getZ());

        int xCoeff = (0 < branchEndPoint.getX()) ? 1 : -1;
        int yCoeff = (0 < branchEndPoint.getY()) ? 1 : -1;
        int zCoeff = (0 < branchEndPoint.getZ()) ? 1 : -1;

        double xbounds1 = (xCoeff * (0.5) * xLength / nbParts) - (xCoeff * 0.1 * branchLength);
        double ybounds1 = (yCoeff * (0.5) * yLength / nbParts) - (yCoeff * 0.1 * branchLength);
        double zbounds1 = (zCoeff * (0.5) * zLength / nbParts) - (zCoeff * 0.1 * branchLength);
        Point3d bound1 = new Point3d(xbounds1, ybounds1, zbounds1);

        double xbounds2 = (xCoeff * (1.5) * xLength / nbParts) + (xCoeff * 0.1 * branchLength);
        double ybounds2 = (yCoeff * (1.5) * yLength / nbParts) + (yCoeff * 0.1 * branchLength);
        double zbounds2 = (zCoeff * (1.5) * zLength / nbParts) + (zCoeff * 0.1 * branchLength);
        Point3d bound2 = new Point3d(xbounds2, ybounds2, zbounds2);

        PointTestHelper.assertPointIsWithinBounds(partEndPoint, bound1, bound2);
    }

}
