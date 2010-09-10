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

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.Tuple3dState;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.Structure3DHelper;
import barsuift.simLife.j3d.helper.VectorTestHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.tree.MockTreeBranch;
import barsuift.simLife.tree.MockTreeBranchPart;
import barsuift.simLife.tree.TreeBranchPart;

public class BasicTreeBranch3DTest extends TestCase {

    private int nbParts;

    private MockUniverse3D mockUniverse3D;

    private MockTreeBranch mockBranch;

    private TreeBranch3DState branch3DState;

    private List<Tuple3dState> previousPartEndPoints;

    protected void setUp() throws Exception {
        super.setUp();
        mockBranch = new MockTreeBranch();
        nbParts = 5;
        previousPartEndPoints = new ArrayList<Tuple3dState>();
        Tuple3dState partEndPoint = new Tuple3dState();
        for (int index = 0; index < nbParts; index++) {
            previousPartEndPoints.add(partEndPoint);
            MockTreeBranchPart mockBranchPart = new MockTreeBranchPart();
            partEndPoint = DisplayDataCreatorForTests.createRandomTupleState();
            MockTreeBranchPart3D mockBranchPart3D = (MockTreeBranchPart3D) mockBranchPart.getBranchPart3D();
            mockBranchPart3D.getState().setEndPoint(partEndPoint);
            mockBranchPart3D.setEndPoint(partEndPoint.toPointValue());
            mockBranch.addPart(mockBranchPart);
        }
        mockUniverse3D = new MockUniverse3D();
        branch3DState = DisplayDataCreatorForTests.createRandomTreeBranch3DState();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        nbParts = 0;
        mockUniverse3D = null;
        mockBranch = null;
        branch3DState = null;
        previousPartEndPoints = null;
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

    public void testTreeBranch3D() {
        BasicTreeBranch3D branch3D = new BasicTreeBranch3D(mockUniverse3D, branch3DState, mockBranch);
        CompilerHelper.compile(branch3D.getGroup());
        assertEquals(nbParts, branch3D.getBranchParts().size());
        List<TreeBranchPart> parts = mockBranch.getParts();
        assertEquals(parts.get(parts.size() - 1).getBranchPart3D().getEndPoint(), branch3D.getEndPoint());
        Node firstChild = branch3D.getGroup().getChild(0);
        assertTrue(firstChild instanceof BranchGroup);
        BranchGroup branchGroup = (BranchGroup) firstChild;
        int nbPartsFound = 0;
        Structure3DHelper.assertExactlyOneTransformGroup(branchGroup);
        TransformGroup firstTransformGroup = (TransformGroup) branchGroup.getChild(0);

        nbPartsFound = checkCurrentTransformGroup(nbPartsFound, firstTransformGroup);
        assertEquals(nbParts, nbPartsFound);
    }

    private int checkCurrentTransformGroup(int nbPartsFound, TransformGroup transformGroup) {
        if (nbPartsFound == nbParts) {
            // we have reached the end of the parts structure
            return nbPartsFound;
        }
        BranchGroup branchGroupPart;
        if (nbPartsFound == 0) {
            Structure3DHelper.assertExactlyOneBranchGroup(transformGroup);
            branchGroupPart = (BranchGroup) transformGroup.getChild(0);
        } else {
            branchGroupPart = (BranchGroup) transformGroup.getChild(1);
        }

        Structure3DHelper.assertExactlyOneTransformGroup(branchGroupPart);
        TransformGroup transformGroupPart = (TransformGroup) branchGroupPart.getChild(0);

        Transform3D transform3D = new Transform3D();
        transformGroupPart.getTransform(transform3D);
        Vector3d translationVector = new Vector3d();
        transform3D.get(translationVector);
        Vector3d expectedTranslationVector = new Vector3d(previousPartEndPoints.get(nbPartsFound).toPointValue());
        VectorTestHelper.assertVectorEquals(expectedTranslationVector, translationVector);

        Group groupPart = (Group) transformGroupPart.getChild(0);
        assertNotNull(groupPart);

        return checkCurrentTransformGroup(nbPartsFound + 1, transformGroupPart);
    }

}
