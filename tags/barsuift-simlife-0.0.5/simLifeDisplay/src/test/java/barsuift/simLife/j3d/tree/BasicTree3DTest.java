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
import barsuift.simLife.tree.MockTree;
import barsuift.simLife.tree.MockTreeBranch;
import barsuift.simLife.tree.TreeBranch;

public class BasicTree3DTest extends TestCase {

    private int nbBranches;

    private MockUniverse3D mockUniverse3D;

    private MockTree mockTree;

    private Tree3DState tree3DState;

    protected void setUp() throws Exception {
        super.setUp();
        mockTree = new MockTree();
        nbBranches = 5;
        for (int index = 0; index < nbBranches; index++) {
            MockTreeBranch mockBranch = new MockTreeBranch();
            Tuple3dState translationVector = DisplayDataCreatorForTests.createRandomTupleState();
            MockTreeBranch3D mockBranch3D = (MockTreeBranch3D) mockBranch.getBranch3D();
            mockBranch3D.getState().setTranslationVector(translationVector);
            mockTree.addBranch(mockBranch);
        }
        mockUniverse3D = new MockUniverse3D();
        tree3DState = DisplayDataCreatorForTests.createRandomTree3DState();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        nbBranches = 0;
        mockUniverse3D = null;
        mockTree = null;
        tree3DState = null;
    }

    public void testConstructor() {
        try {
            new BasicTree3D(mockUniverse3D, null, mockTree);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTree3D(mockUniverse3D, tree3DState, null);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTree3D(null, tree3DState, mockTree);
            fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        BasicTree3D tree3D = new BasicTree3D(mockUniverse3D, tree3DState, mockTree);
        assertEquals(tree3DState, tree3D.getState());
        assertSame(tree3DState, tree3D.getState());
    }

    @SuppressWarnings("rawtypes")
    public void testTree3D() {
        BasicTree3D tree3D = new BasicTree3D(mockUniverse3D, tree3DState, mockTree);
        BranchGroup branchGroup = tree3D.getBranchGroup();
        CompilerHelper.compile(branchGroup);
        assertEquals(nbBranches, tree3D.getBranches().size());

        int nbTimesTrunkGroupIsFound = 0;
        int nbBranchesFound = 0;
        for (Enumeration enumeration = branchGroup.getAllChildren(); enumeration.hasMoreElements();) {
            Object child = enumeration.nextElement();
            if (child instanceof BranchGroup) {
                BranchGroup branchBranchGroup = (BranchGroup) child;

                Structure3DHelper.assertExactlyOneTransformGroup(branchBranchGroup);
                TransformGroup transformGroup = (TransformGroup) branchBranchGroup.getChild(0);

                // test translation
                Transform3D transform3D = new Transform3D();
                transformGroup.getTransform(transform3D);
                Vector3d translationVector = new Vector3d();
                transform3D.get(translationVector);
                TreeBranch treeBranch = mockTree.getBranches().get(nbBranchesFound);
                Vector3d expectedTranslationVector = treeBranch.getBranch3D().getTranslationVector();
                VectorTestHelper.assertVectorEquals(expectedTranslationVector, translationVector);

                // test one branch found
                Structure3DHelper.assertExactlyOneGroup(transformGroup);
                Group specificGroupForTheBranch = (Group) transformGroup.getChild(0);
                assertNotNull(specificGroupForTheBranch);
                nbBranchesFound++;
            } else {
                if (child instanceof Group) {
                    nbTimesTrunkGroupIsFound++;
                    assertEquals("We should have only one trunk", 1, nbTimesTrunkGroupIsFound);
                } else {
                    fail("There should be no other children. child is instance of " + child.getClass());
                }
            }
        }
        assertEquals(nbBranches, nbBranchesFound);
    }

}
