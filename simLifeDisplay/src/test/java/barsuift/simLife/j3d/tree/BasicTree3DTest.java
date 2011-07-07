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
import javax.media.j3d.TransformGroup;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.helper.CompilerHelper;
import barsuift.simLife.j3d.helper.Structure3DHelper;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.tree.MockTree;
import barsuift.simLife.tree.MockTreeBranch;

import static org.fest.assertions.Assertions.assertThat;

public class BasicTree3DTest {

    private int nbBranches;

    private MockUniverse3D mockUniverse3D;

    private MockTree mockTree;

    private Tree3DState tree3DState;

    @BeforeMethod
    protected void setUp() {
        mockTree = new MockTree();
        nbBranches = 5;
        for (int index = 0; index < nbBranches; index++) {
            mockTree.addBranch(new MockTreeBranch());
        }
        mockUniverse3D = new MockUniverse3D();
        tree3DState = DisplayDataCreatorForTests.createRandomTree3DState();
    }

    @AfterMethod
    protected void tearDown() {
        nbBranches = 0;
        mockUniverse3D = null;
        mockTree = null;
        tree3DState = null;
    }

    @Test
    public void testConstructor() {
        try {
            new BasicTree3D(null);
            Assert.fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            BasicTree3D tree3D = new BasicTree3D(tree3DState);
            tree3D.init(mockUniverse3D, null);
            Assert.fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            BasicTree3D tree3D = new BasicTree3D(tree3DState);
            tree3D.init(null, mockTree);
            Assert.fail("Should throw new IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    @Test
    public void testGetState() {
        BasicTree3D tree3D = new BasicTree3D(tree3DState);
        tree3D.init(mockUniverse3D, mockTree);
        AssertJUnit.assertEquals(tree3DState, tree3D.getState());
        AssertJUnit.assertSame(tree3DState, tree3D.getState());
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void testTree3D() {
        BasicTree3D tree3D = new BasicTree3D(tree3DState);
        tree3D.init(mockUniverse3D, mockTree);
        BranchGroup branchGroup = tree3D.getBranchGroup();
        CompilerHelper.compile(branchGroup);
        assertThat(tree3D.getBranches()).hasSize(nbBranches);

        Structure3DHelper.assertExactlyOneTransformGroup(branchGroup);
        TransformGroup tg = (TransformGroup) branchGroup.getChild(0);
        int nbTimesTrunkGroupIsFound = 0;
        int nbBranchesFound = 0;
        for (Enumeration enumeration = tg.getAllChildren(); enumeration.hasMoreElements();) {
            Object child = enumeration.nextElement();
            if (child.getClass().equals(BranchGroup.class)) {
                // we found a branch
                nbBranchesFound++;
            } else {
                if (child.getClass().equals(Group.class)) {
                    // we found the trunk
                    nbTimesTrunkGroupIsFound++;
                } else {
                    Assert.fail("There should be no other children. child is instance of " + child.getClass());
                }
            }
        }
        AssertJUnit.assertEquals("We should have exactly one trunk", 1, nbTimesTrunkGroupIsFound);
        AssertJUnit.assertEquals(nbBranches, nbBranchesFound);
    }

}
