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

import org.fest.assertions.Delta;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class TreeBranchStateFactoryTest {

    private float treeHeight;

    private TreeBranchState branchState;

    private TreeBranchStateFactory factory;

    @BeforeMethod
    protected void setUp() {
        treeHeight = 6;
        factory = new TreeBranchStateFactory();
        branchState = factory.createRandomBranchState(treeHeight);
    }

    @AfterMethod
    protected void tearDown() {
        treeHeight = 0;
        factory = null;
        branchState = null;
    }

    @Test
    public void testCreateBranchState() {
        assertThat(branchState.getBranch3DState()).isNotNull();
        List<TreeLeafState> leavesStates = branchState.getLeavesStates();
        int nbLeaves = leavesStates.size();
        assertThat(nbLeaves).isGreaterThanOrEqualTo(6);
        assertThat(nbLeaves).isLessThanOrEqualTo(12);
        float expectedRadius = branchState.getBranch3DState().getLength() / 40;
        assertThat(branchState.getBranch3DState().getRadius()).isEqualTo(expectedRadius, Delta.delta(0.0001));
        assertThat(branchState.getCreationMillis()).isGreaterThanOrEqualTo(0);
        assertThat(branchState.getCreationMillis()).isLessThanOrEqualTo(100000);
        assertThat(branchState.getEnergy().compareTo(new BigDecimal(0))).isGreaterThanOrEqualTo(0);
        assertThat(branchState.getEnergy().compareTo(new BigDecimal(100))).isLessThanOrEqualTo(0);
        assertThat(branchState.getFreeEnergy().compareTo(new BigDecimal(0))).isGreaterThanOrEqualTo(0);
        assertThat(branchState.getFreeEnergy().compareTo(new BigDecimal(50))).isLessThanOrEqualTo(0);
    }

    @Test
    public void testComputeLength() {
        float treeHeight = 12;
        float branchLength = factory.computeLength(treeHeight);
        assertThat(branchLength).isGreaterThanOrEqualTo(3);
        assertThat(branchLength).isLessThanOrEqualTo(6);
        treeHeight = 2.4f;
        branchLength = factory.computeLength(treeHeight);
        assertThat(branchLength).isGreaterThanOrEqualTo(0.6f);
        assertThat(branchLength).isLessThanOrEqualTo(1.2f);
    }

}
