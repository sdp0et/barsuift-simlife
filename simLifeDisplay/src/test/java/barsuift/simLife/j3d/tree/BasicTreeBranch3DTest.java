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
package barsuift.simLife.j3d.tree;

import java.math.BigDecimal;
import java.util.Enumeration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.Structure3DHelper;
import barsuift.simLife.j3d.tree.helper.BasicTreeBranch3DTestHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.j3d.util.ColorConstants;
import barsuift.simLife.tree.MockTreeBranch;
import barsuift.simLife.tree.MockTreeLeaf;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;

import static barsuift.simLife.j3d.assertions.AppearanceAssert.assertThat;

import static org.fest.assertions.Assertions.assertThat;

public class BasicTreeBranch3DTest {

    private int nbLeaves;

    private MockUniverse3D mockUniverse3D;

    private MockTreeBranch mockBranch;

    private TreeBranch3DState branch3DState;

    @BeforeMethod
    protected void setUp() {
        mockBranch = new MockTreeBranch();
        nbLeaves = 5;
        for (int index = 0; index < nbLeaves; index++) {
            MockTreeLeaf mockLeaf = new MockTreeLeaf();
            mockBranch.addLeaf(mockLeaf);
        }
        mockUniverse3D = new MockUniverse3D();
        branch3DState = DisplayDataCreatorForTests.createRandomTreeBranch3DState();
    }

    @AfterMethod
    protected void tearDown() {
        nbLeaves = 0;
        mockUniverse3D = null;
        mockBranch = null;
        branch3DState = null;
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void constructor_exception() {
        new BasicTreeBranch3D(null);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void init_exception_onNullBranch() {
        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, null);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void init_exception_onNullUniverse() {
        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(null, mockBranch);
    }

    @Test
    public void testGetState() {
        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, mockBranch);
        AssertJUnit.assertEquals(branch3DState, branch3D.getState());
        AssertJUnit.assertSame(branch3DState, branch3D.getState());
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void testTreeBranch3D() {
        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, mockBranch);
        BranchGroup bg = branch3D.getBranchGroup();
        CompilerHelper.compile(bg);
        assertThat(branch3D.getLeaves()).hasSize(nbLeaves);

        float length = branch3DState.getLength();
        AssertJUnit.assertEquals(length, branch3D.getLength());
        Structure3DHelper.assertExactlyOneTransformGroup(bg);
        TransformGroup tg = (TransformGroup) bg.getChild(0);

        AssertJUnit.assertTrue(tg.getCapability(Group.ALLOW_CHILDREN_WRITE));
        AssertJUnit.assertTrue(tg.getCapability(Group.ALLOW_CHILDREN_EXTEND));
        int nbTimesNoLeafShapeIsFound = 0;
        int nbLeavesFound = 0;
        for (Enumeration enumeration = tg.getAllChildren(); enumeration.hasMoreElements();) {
            Object child = enumeration.nextElement();
            if (child.getClass().equals(BranchGroup.class)) {
                nbLeavesFound++;
            } else {
                if (child.getClass().equals(TransformGroup.class)) {
                    nbTimesNoLeafShapeIsFound++;
                    TransformGroup branchTg = (TransformGroup) child;
                    Structure3DHelper.assertExactlyOnePrimitive(branchTg);
                    Primitive branchPrimitive = (Primitive) branchTg.getChild(0);
                    assert (branchPrimitive.getClass().equals(Cylinder.class));
                    Cylinder branchCylinder = (Cylinder) branchPrimitive;

                    float radius = branch3DState.getRadius();
                    Point3d expectedLowerBottom = new Point3d(-radius, -length / 2, -radius);
                    Point3d expectedUpperBottom = new Point3d(radius, -length / 2, radius);
                    Point3d expectedMovedLowerBottom = new Point3d(-radius, 0, -radius);
                    Point3d expectedMovedUpperBottom = new Point3d(radius, 0, radius);
                    Point3d expectedLowerTop = new Point3d(-radius, length / 2, -radius);
                    Point3d expectedUpperTop = new Point3d(radius, length / 2, radius);
                    Point3d expectedMovedLowerTop = new Point3d(-radius, length, -radius);
                    Point3d expectedMovedUpperTop = new Point3d(radius, length, radius);
                    BasicTreeBranch3DTestHelper.checkTreeBranch3D(branch3D, length, radius, expectedLowerBottom,
                            expectedUpperBottom, expectedMovedLowerBottom, expectedMovedUpperBottom, expectedLowerTop,
                            expectedUpperTop, expectedMovedLowerTop, expectedMovedUpperTop);
                    assertThat(branchCylinder.getAppearance()).hasAmbientColor(ColorConstants.brown);
                    assertThat(branchCylinder.getAppearance()).hasSpecularColor(new Color3f(0.05f, 0.05f, 0.05f));
                    assertThat(branchCylinder.getAppearance()).hasDiffuseColor(new Color3f(0.15f, 0.15f, 0.15f));
                } else {
                    Assert.fail("There should be no other children. child is instance of " + child.getClass());
                }
            }
        }
        AssertJUnit.assertEquals("We should have only one branch", 1, nbTimesNoLeafShapeIsFound);
        AssertJUnit.assertEquals(nbLeaves, nbLeavesFound);
    }

    private MockTreeLeaf3D getLeaf3D(int index) {
        return (MockTreeLeaf3D) ((MockTreeLeaf) mockBranch.getLeaves().get(index)).getTreeLeaf3D();
    }

    @Test
    public void testIncreaseOneLeafSize() {
        mockBranch.setEnergy(new BigDecimal(150));
        // set all the leaves at their maximum size, so that they can not be increased anymore
        for (int index = 0; index < nbLeaves; index++) {
            getLeaf3D(index).setMaxSizeReached(true);
        }
        // ensure the second leaf can be increased
        getLeaf3D(1).setMaxSizeReached(false);
        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, mockBranch);

        branch3D.increaseOneLeafSize();

        AssertJUnit.assertEquals(0, getLeaf3D(0).getNbTimesIncreaseSizeCalled());
        AssertJUnit.assertEquals(1, getLeaf3D(1).getNbTimesIncreaseSizeCalled());
        AssertJUnit.assertEquals(0, getLeaf3D(2).getNbTimesIncreaseSizeCalled());
        AssertJUnit.assertEquals(0, getLeaf3D(3).getNbTimesIncreaseSizeCalled());
        AssertJUnit.assertEquals(0, getLeaf3D(4).getNbTimesIncreaseSizeCalled());

        branch3D.increaseOneLeafSize();

        AssertJUnit.assertEquals(0, getLeaf3D(0).getNbTimesIncreaseSizeCalled());
        AssertJUnit.assertEquals(2, getLeaf3D(1).getNbTimesIncreaseSizeCalled());
        AssertJUnit.assertEquals(0, getLeaf3D(2).getNbTimesIncreaseSizeCalled());
        AssertJUnit.assertEquals(0, getLeaf3D(3).getNbTimesIncreaseSizeCalled());
        AssertJUnit.assertEquals(0, getLeaf3D(4).getNbTimesIncreaseSizeCalled());
    }


    @Test
    public void testGetRandomLeafToIncrease1() {
        mockBranch = new MockTreeBranch();

        MockTreeLeaf leaf1 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D1 = (MockTreeLeaf3D) leaf1.getTreeLeaf3D();
        leaf3D1.setArea(2);
        mockBranch.addLeaf(leaf1);

        MockTreeLeaf leaf2 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D2 = (MockTreeLeaf3D) leaf2.getTreeLeaf3D();
        leaf3D2.setArea(4);
        mockBranch.addLeaf(leaf2);

        MockTreeLeaf leaf3 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D3 = (MockTreeLeaf3D) leaf3.getTreeLeaf3D();
        leaf3D3.setArea(6);
        mockBranch.addLeaf(leaf3);

        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, mockBranch);
        // area1 = 2
        // area2 = 4
        // area3 = 6
        // sum (area) = 12
        // diffArea1 = 12 - 2 = 10
        // diffArea2 = 12 - 4 = 8
        // diffArea3 = 12 - 6 = 6
        // sum (diffArea) = 24
        // ratio1 = 10 / 24 = 41.66%
        // ratio2 = 8 / 24 = 33.33%
        // ratio3 = 6 / 24 = 25.00%

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf3D leaf3D = branch3D.getRandomLeafToIncrease();
            if (leaf3D == leaf3D1) {
                sum1++;
            }
            if (leaf3D == leaf3D2) {
                sum2++;
            }
            if (leaf3D == leaf3D3) {
                sum3++;
            }
        }
        AssertJUnit.assertTrue("sum1=" + sum1, sum1 > 310);
        AssertJUnit.assertTrue("sum1=" + sum1, sum1 < 510);
        AssertJUnit.assertTrue("sum2=" + sum2, sum2 > 280);
        AssertJUnit.assertTrue("sum2=" + sum2, sum2 < 386);
        AssertJUnit.assertTrue("sum3=" + sum3, sum3 > 200);
        AssertJUnit.assertTrue("sum3=" + sum3, sum3 < 300);
    }

    /**
     * Test with one leaf at its maximum size
     */
    @Test
    public void testGetRandomLeafToIncrease2() {
        mockBranch = new MockTreeBranch();

        MockTreeLeaf leaf1 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D1 = (MockTreeLeaf3D) leaf1.getTreeLeaf3D();
        leaf3D1.setArea(2);
        leaf3D1.setMaxSizeReached(true);
        mockBranch.addLeaf(leaf1);

        MockTreeLeaf leaf2 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D2 = (MockTreeLeaf3D) leaf2.getTreeLeaf3D();
        leaf3D2.setArea(4);
        mockBranch.addLeaf(leaf2);

        MockTreeLeaf leaf3 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D3 = (MockTreeLeaf3D) leaf3.getTreeLeaf3D();
        leaf3D3.setArea(6);
        mockBranch.addLeaf(leaf3);

        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, mockBranch);
        // area1 = 2 (not taken into account, because leaf is already at its maximum size)
        // area2 = 4
        // area3 = 6
        // sum (area) = 10
        // diffArea2 = 10 - 4 = 6
        // diffArea3 = 10 - 6 = 4
        // sum (diffArea) = 10
        // ratio2 = 6 / 10 = 60%
        // ratio3 = 4 / 10 = 40%

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf3D leaf3D = branch3D.getRandomLeafToIncrease();
            if (leaf3D == leaf3D1) {
                sum1++;
            }
            if (leaf3D == leaf3D2) {
                sum2++;
            }
            if (leaf3D == leaf3D3) {
                sum3++;
            }
        }
        AssertJUnit.assertEquals(0, sum1);
        AssertJUnit.assertTrue("sum2=" + sum2, sum2 > 540);
        AssertJUnit.assertTrue("sum2=" + sum2, sum2 < 660);
        AssertJUnit.assertTrue("sum3=" + sum3, sum3 > 340);
        AssertJUnit.assertTrue("sum3=" + sum3, sum3 < 460);
    }

    /**
     * Test with all but one leaves at their maximum sizes
     */
    @Test
    public void testGetRandomLeafToIncrease3() {
        mockBranch = new MockTreeBranch();

        MockTreeLeaf leaf1 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D1 = (MockTreeLeaf3D) leaf1.getTreeLeaf3D();
        leaf3D1.setArea(2);
        leaf3D1.setMaxSizeReached(true);
        mockBranch.addLeaf(leaf1);

        MockTreeLeaf leaf2 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D2 = (MockTreeLeaf3D) leaf2.getTreeLeaf3D();
        leaf3D2.setArea(4);
        leaf3D2.setMaxSizeReached(true);
        mockBranch.addLeaf(leaf2);

        MockTreeLeaf leaf3 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D3 = (MockTreeLeaf3D) leaf3.getTreeLeaf3D();
        leaf3D3.setArea(6);
        mockBranch.addLeaf(leaf3);

        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, mockBranch);
        // area1 = 2 (not taken into account, because leaf is already at its maximum size)
        // area2 = 4 (not taken into account, because leaf is already at its maximum size)
        // area3 = 6
        // sum (area) = 6
        // diffArea3 = 6 - 6 = 0
        // sum (diffArea) = 0
        // ratio3 = 0 / 0 = 100%

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf3D leaf3D = branch3D.getRandomLeafToIncrease();
            if (leaf3D == leaf3D1) {
                sum1++;
            }
            if (leaf3D == leaf3D2) {
                sum2++;
            }
            if (leaf3D == leaf3D3) {
                sum3++;
            }
        }
        AssertJUnit.assertEquals(0, sum1);
        AssertJUnit.assertEquals(0, sum2);
        AssertJUnit.assertEquals(1000, sum3);
    }

    /**
     * Test with 3 leaves, one of which has 0 for area
     */
    @Test
    public void testGetRandomLeafToIncrease4() {
        mockBranch = new MockTreeBranch();

        MockTreeLeaf leaf1 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D1 = (MockTreeLeaf3D) leaf1.getTreeLeaf3D();
        leaf3D1.setArea(0);
        mockBranch.addLeaf(leaf1);

        MockTreeLeaf leaf2 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D2 = (MockTreeLeaf3D) leaf2.getTreeLeaf3D();
        leaf3D2.setArea(4);
        mockBranch.addLeaf(leaf2);

        MockTreeLeaf leaf3 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D3 = (MockTreeLeaf3D) leaf3.getTreeLeaf3D();
        leaf3D3.setArea(6);
        mockBranch.addLeaf(leaf3);

        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, mockBranch);
        // area1 = 0
        // area2 = 4
        // area3 = 6
        // sum (area) = 10
        // diffArea1 = 10 - 0 = 10
        // diffArea2 = 10 - 4 = 6
        // diffArea3 = 10 - 6 = 4
        // sum (diffArea) = 20
        // ratio1 = 10 / 20 = 50%
        // ratio2 = 6 / 20 = 30%
        // ratio3 = 4 / 20 = 20%

        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf3D leaf3D = branch3D.getRandomLeafToIncrease();
            if (leaf3D == leaf3D1) {
                sum1++;
            }
            if (leaf3D == leaf3D2) {
                sum2++;
            }
            if (leaf3D == leaf3D3) {
                sum3++;
            }
        }
        AssertJUnit.assertTrue("sum1=" + sum1, sum1 > 450);
        AssertJUnit.assertTrue("sum1=" + sum1, sum1 < 550);
        AssertJUnit.assertTrue("sum2=" + sum2, sum2 > 240);
        AssertJUnit.assertTrue("sum2=" + sum2, sum2 < 360);
        AssertJUnit.assertTrue("sum3=" + sum3, sum3 > 150);
        AssertJUnit.assertTrue("sum3=" + sum3, sum3 < 250);
    }

    /**
     * Test with 2 leaves, one of which has 0 for area
     */
    @Test
    public void testGetRandomLeafToIncrease5() {
        mockBranch = new MockTreeBranch();

        MockTreeLeaf leaf1 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D1 = (MockTreeLeaf3D) leaf1.getTreeLeaf3D();
        leaf3D1.setArea(0);
        mockBranch.addLeaf(leaf1);

        MockTreeLeaf leaf2 = new MockTreeLeaf();
        MockTreeLeaf3D leaf3D2 = (MockTreeLeaf3D) leaf2.getTreeLeaf3D();
        leaf3D2.setArea(4);
        mockBranch.addLeaf(leaf2);

        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(branch3DState);
        branch3D.init(mockUniverse3D, mockBranch);
        // area1 = 0
        // area2 = 4
        // sum (area) = 4
        // diffArea1 = 4 - 0 = 4
        // diffArea2 = 4 - 4 = 0
        // sum (diffArea) = 4
        // ratio1 = 4 / 4 = 100%
        // ratio2 = 0 / 4 = 0%

        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < 1000; i++) {
            TreeLeaf3D leaf3D = branch3D.getRandomLeafToIncrease();
            if (leaf3D == leaf3D1) {
                sum1++;
            }
            if (leaf3D == leaf3D2) {
                sum2++;
            }
        }
        AssertJUnit.assertEquals(1000, sum1);
        AssertJUnit.assertEquals(0, sum2);
    }

}
