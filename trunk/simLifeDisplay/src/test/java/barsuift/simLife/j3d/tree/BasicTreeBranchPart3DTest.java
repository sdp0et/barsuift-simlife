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
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Matrix3d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import junit.framework.TestCase;
import barsuift.simLife.Randomizer;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.MatrixTestHelper;
import barsuift.simLife.j3d.helper.Structure3DHelper;
import barsuift.simLife.j3d.helper.VectorTestHelper;
import barsuift.simLife.j3d.tree.helper.BasicTreeBranchPart3DTestHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.tree.MockTreeBranchPart;
import barsuift.simLife.tree.MockTreeLeaf;
import barsuift.simLife.tree.TreeLeaf;

public class BasicTreeBranchPart3DTest extends TestCase {

    private int nbLeaves;

    private MockUniverse3D mockUniverse3D;

    private MockTreeBranchPart mockBranchPart;

    private TreeBranchPart3DState part3DState;

    protected void setUp() throws Exception {
        super.setUp();
        mockBranchPart = new MockTreeBranchPart();
        nbLeaves = 5;
        for (int index = 0; index < nbLeaves; index++) {
            MockTreeLeaf mockLeaf = new MockTreeLeaf();
            mockLeaf.getTreeLeaf3D().getState().setRotation(Randomizer.randomRotation());
            mockLeaf.getTreeLeaf3D().getState().setLeafAttachPoint(DisplayDataCreatorForTests.createRandomTupleState());
            mockBranchPart.addLeaf(mockLeaf);
        }
        mockUniverse3D = new MockUniverse3D();
        part3DState = DisplayDataCreatorForTests.createRandomTreeBranchPart3DState();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        nbLeaves = 0;
        mockUniverse3D = null;
        mockBranchPart = null;
        part3DState = null;
    }

    public void testConstructor() {
        try {
            new BasicTreeBranchPart3D(mockUniverse3D, null, mockBranchPart);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranchPart3D(mockUniverse3D, part3DState, null);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeBranchPart3D(null, part3DState, mockBranchPart);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        BasicTreeBranchPart3D part3D = new BasicTreeBranchPart3D(mockUniverse3D, part3DState, mockBranchPart);
        assertEquals(part3DState, part3D.getState());
        assertSame(part3DState, part3D.getState());
    }

    @SuppressWarnings("rawtypes")
    public void testTreeBranchPart3D() {
        BasicTreeBranchPart3D part3D = new BasicTreeBranchPart3D(mockUniverse3D, part3DState, mockBranchPart);
        CompilerHelper.compile(part3D.getGroup());
        assertEquals(nbLeaves, part3D.getLeaves().size());
        assertEquals(part3DState.getEndPoint().toPointValue(), part3D.getEndPoint());
        Group partGroup = part3D.getGroup();
        assertTrue(partGroup.getCapability(Group.ALLOW_CHILDREN_WRITE));
        assertTrue(partGroup.getCapability(Group.ALLOW_CHILDREN_EXTEND));
        int nbTimesNoLeafShapeIsFound = 0;
        int nbLeavesFound = 0;
        for (Enumeration enumeration = partGroup.getAllChildren(); enumeration.hasMoreElements();) {
            Object child = enumeration.nextElement();
            if (child instanceof BranchGroup) {
                BranchGroup leafBranchGroup = (BranchGroup) child;

                Structure3DHelper.assertExactlyOneTransformGroup(leafBranchGroup);
                TransformGroup transformGroup = (TransformGroup) leafBranchGroup.getChild(0);

                // test translation and rotation
                Transform3D transform3D = new Transform3D();
                transformGroup.getTransform(transform3D);
                Matrix3d rotationMatrix = new Matrix3d();
                Vector3d translationVector = new Vector3d();
                transform3D.get(rotationMatrix, translationVector);
                Matrix3d expectedRotationMatrix = new Matrix3d();
                TreeLeaf treeLeaf = mockBranchPart.getLeaves().get(nbLeavesFound);
                double rotation = treeLeaf.getTreeLeaf3D().getState().getRotation();
                expectedRotationMatrix.rotY(rotation);
                MatrixTestHelper.assertMatrixEquals(expectedRotationMatrix, rotationMatrix);
                Point3d leafAttachPoint = treeLeaf.getTreeLeaf3D().getState().getLeafAttachPoint().toPointValue();
                Vector3d expectedTranslationVector = new Vector3d(leafAttachPoint);
                VectorTestHelper.assertVectorEquals(expectedTranslationVector, translationVector);

                // test one leaf found
                Structure3DHelper.assertExactlyOneBranchGroup(transformGroup);
                BranchGroup specificLeafBranchGroup = (BranchGroup) transformGroup.getChild(0);
                assertNotNull(specificLeafBranchGroup);
                nbLeavesFound++;
            } else {
                if (child instanceof Shape3D) {
                    nbTimesNoLeafShapeIsFound++;
                    assertEquals("We should have only one shape (the branch part)", 1, nbTimesNoLeafShapeIsFound);
                    Shape3D branchScape = (Shape3D) child;
                    BasicTreeBranchPart3DTestHelper.testGeometry(branchScape.getGeometry(), new Point3d(0, 0, 0),
                            part3DState.getEndPoint().toPointValue());
                    BasicTreeBranchPart3DTestHelper.testAppearance(branchScape.getAppearance());
                } else {
                    fail("There should be no other children. child is instance of " + child.getClass());
                }
            }
        }
        assertEquals(nbLeaves, nbLeavesFound);
    }

}
