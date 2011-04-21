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

import java.util.Enumeration;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;

import junit.framework.TestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.Structure3DHelper;
import barsuift.simLife.j3d.tree.helper.BasicTreeBranch3DTestHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.tree.MockTreeBranch;
import barsuift.simLife.tree.MockTreeLeaf;

public class BasicTreeBranch3DTest extends TestCase {

    private int nbLeaves;

    private MockUniverse3D mockUniverse3D;

    private MockTreeBranch mockBranch;

    private TreeBranch3DState branch3DState;

    protected void setUp() throws Exception {
        super.setUp();
        mockBranch = new MockTreeBranch();
        nbLeaves = 5;
        nbLeaves = 5;
        for (int index = 0; index < nbLeaves; index++) {
            MockTreeLeaf mockLeaf = new MockTreeLeaf();
            mockLeaf.getTreeLeaf3D().getState().setTransform(DisplayDataCreatorForTests.createRandomTransform3DState());
            mockBranch.addLeaf(mockLeaf);
        }
        mockUniverse3D = new MockUniverse3D();
        branch3DState = DisplayDataCreatorForTests.createRandomTreeBranch3DState();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        nbLeaves = 0;
        mockUniverse3D = null;
        mockBranch = null;
        branch3DState = null;
    }

    public void testConstructor() {
        try {
            new BasicTreeBranch3D(mockUniverse3D, null, mockBranch);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranch3D(mockUniverse3D, branch3DState, null);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranch3D(null, branch3DState, mockBranch);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(mockUniverse3D, branch3DState, mockBranch);
        assertEquals(branch3DState, branch3D.getState());
        assertSame(branch3DState, branch3D.getState());
    }

    @SuppressWarnings("rawtypes")
    public void testTreeBranch3D() {
        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(mockUniverse3D, branch3DState, mockBranch);
        BranchGroup bg = branch3D.getBranchGroup();
        CompilerHelper.compile(bg);
        assertEquals(nbLeaves, branch3D.getLeaves().size());

        assertEquals(branch3DState.getEndPoint().toPointValue(), branch3D.getEndPoint());
        Structure3DHelper.assertExactlyOneTransformGroup(bg);
        TransformGroup tg = (TransformGroup) bg.getChild(0);

        assertTrue(tg.getCapability(Group.ALLOW_CHILDREN_WRITE));
        assertTrue(tg.getCapability(Group.ALLOW_CHILDREN_EXTEND));
        int nbTimesNoLeafShapeIsFound = 0;
        int nbLeavesFound = 0;
        for (Enumeration enumeration = tg.getAllChildren(); enumeration.hasMoreElements();) {
            Object child = enumeration.nextElement();
            if (child.getClass().equals(BranchGroup.class)) {
                nbLeavesFound++;
            } else {
                if (child.getClass().equals(Shape3D.class)) {
                    nbTimesNoLeafShapeIsFound++;
                    Shape3D branchScape = (Shape3D) child;
                    BasicTreeBranch3DTestHelper.testGeometry(branchScape.getGeometry(), new Point3f(0, 0, 0),
                            branch3DState.getEndPoint().toPointValue());
                    BasicTreeBranch3DTestHelper.testAppearance(branchScape.getAppearance());
                } else {
                    fail("There should be no other children. child is instance of " + child.getClass());
                }
            }
        }
        assertEquals("We should have only one shape (the branch)", 1, nbTimesNoLeafShapeIsFound);
        assertEquals(nbLeaves, nbLeavesFound);
    }


}
