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
        Point3f translationVector = new Point3f((float) Math.random(), (float) Math.random(), (float) Math.random());
        TreeState treeState = factory.createRandomTreeState(translationVector);
        List<TreeBranchState> branches = treeState.getBranches();
        assertTrue(30 <= branches.size());
        assertTrue(50 >= branches.size());
        float height = treeState.getHeight();
        assertTrue(3 <= height);
        assertTrue(5 >= height);
        TreeTrunkState trunkState = treeState.getTrunkState();
        assertEquals(height / 8, trunkState.getRadius());
        assertNotNull(treeState.getTree3DState());

        assertTrue(treeState.getCreationMillis() >= 0);
        assertTrue(treeState.getCreationMillis() <= 100000);
        assertTrue(treeState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(treeState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
    }

    public void testComputeBranchTranslationVector() {
        float treeRadius = 6;
        float treeHeight = 8;
        Vector3f translationVector = factory.computeBranchTranslationVector(treeRadius, treeHeight);
        PointTestHelper.assertPointIsWithinBounds(new Point3f(translationVector), new Point3f(-6, 8, -6), new Point3f(
                6, 8, 6));
        treeRadius = 0.2f;
        treeHeight = 15;
        translationVector = factory.computeBranchTranslationVector(treeRadius, treeHeight);
        PointTestHelper.assertPointIsWithinBounds(new Point3f(translationVector), new Point3f(-0.2f, 15, -0.2f),
                new Point3f(0.2f, 15, 0.2f));
    }

    public void testComputeBranchEndPoint() {
        float treeHeight = 12;
        boolean goingToPositiveX = true;
        boolean goingToPositiveZ = false;
        Point3f branchEndPoint = factory.computeBranchEndPoint(treeHeight, goingToPositiveX, goingToPositiveZ);
        PointTestHelper.assertPointIsWithinBounds(branchEndPoint, new Point3f(0, 0, 0), new Point3f(6, 12, -6));
        treeHeight = 2.4f;
        goingToPositiveX = false;
        goingToPositiveZ = true;
        branchEndPoint = factory.computeBranchEndPoint(treeHeight, goingToPositiveX, goingToPositiveZ);
        PointTestHelper.assertPointIsWithinBounds(branchEndPoint, new Point3f(0, 0, 0), new Point3f(-1.2f, 2.4f, 1.2f));
    }

}