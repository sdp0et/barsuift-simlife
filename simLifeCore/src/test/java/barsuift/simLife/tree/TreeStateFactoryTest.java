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

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class TreeStateFactoryTest {

    private TreeStateFactory factory;

    @BeforeMethod
    protected void setUp() {
        factory = new TreeStateFactory();
    }

    @AfterMethod
    protected void tearDown() {
        factory = null;
    }

    @Test
    public void testCreateRandomTreeState() {
        TreeState treeState = factory.createRandomTreeState();
        List<TreeBranchState> branches = treeState.getBranches();
        assertThat(branches.size()).isGreaterThanOrEqualTo(20);
        assertThat(branches.size()).isLessThanOrEqualTo(40);
        float height = treeState.getHeight();
        assertThat(height).isGreaterThanOrEqualTo(3);
        assertThat(height).isLessThanOrEqualTo(5);
        TreeTrunkState trunkState = treeState.getTrunkState();
        assertThat(trunkState.getRadius()).isEqualTo(height / 16);
        assertThat(treeState.getTree3DState()).isNotNull();

        assertThat(treeState.getCreationMillis()).isGreaterThanOrEqualTo(0);
        assertThat(treeState.getCreationMillis()).isLessThanOrEqualTo(100000);
        assertThat(treeState.getEnergy().compareTo(new BigDecimal(0))).isGreaterThanOrEqualTo(0);
        assertThat(treeState.getEnergy().compareTo(new BigDecimal(100))).isLessThanOrEqualTo(0);
    }
}
