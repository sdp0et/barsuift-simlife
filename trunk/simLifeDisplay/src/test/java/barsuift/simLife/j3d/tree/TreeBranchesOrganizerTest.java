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

import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TreeBranchesOrganizerTest {

    private TreeBranchesOrganizer branchesOrganizer;

    @BeforeMethod
    protected void setUp() {
        branchesOrganizer = new TreeBranchesOrganizer();
    }

    @AfterMethod
    protected void tearDown() {
        branchesOrganizer = null;
    }

    @Test
    public void testOrganizeBranches() {
        float treeHeight = 4;
        List<TreeBranch3DState> branchesStates = new ArrayList<TreeBranch3DState>();
        TreeBranch3DState branch3DState1 = new TreeBranch3DState();
        branchesStates.add(branch3DState1);
        TreeBranch3DState branch3DState2 = new TreeBranch3DState();
        branchesStates.add(branch3DState2);
        branchesOrganizer.organizeBranches(branchesStates, treeHeight);

        Vector3f expectedTranslation = new Vector3f(0, treeHeight, 0);

        Transform3D transform3D1 = branch3DState1.getTransform().toTransform3D();
        Vector3f translation1 = new Vector3f();
        transform3D1.get(translation1);
        AssertJUnit.assertEquals(expectedTranslation, translation1);

        Transform3D transform3D2 = branch3DState2.getTransform().toTransform3D();
        Vector3f translation2 = new Vector3f();
        transform3D2.get(translation2);
        AssertJUnit.assertEquals(expectedTranslation, translation2);
    }

}
