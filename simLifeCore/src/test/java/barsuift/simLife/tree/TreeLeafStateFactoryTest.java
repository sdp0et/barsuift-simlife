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

import org.testng.annotations.Test;

import barsuift.simLife.PercentHelper;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;

import static org.fest.assertions.Assertions.assertThat;


public class TreeLeafStateFactoryTest {

    @Test
    public void testCreateRandomTreeLeafState() {
        TreeLeafStateFactory factory = new TreeLeafStateFactory();
        TreeLeafState treeLeafState = factory.createRandomTreeLeafState();
        assertThat(treeLeafState).isNotNull();
        assertThat(treeLeafState.getLeaf3DState()).isNotNull();
        assertThat(PercentHelper.getDecimalValue(90).compareTo(treeLeafState.getEfficiency())).isLessThanOrEqualTo(0);
        assertThat(PercentHelper.getDecimalValue(100).compareTo(treeLeafState.getEfficiency())).isGreaterThanOrEqualTo(
                0);
        assertThat(treeLeafState.getCreationMillis()).isGreaterThanOrEqualTo(0);
        assertThat(treeLeafState.getCreationMillis()).isLessThanOrEqualTo(100000);
        assertThat(treeLeafState.getEnergy().compareTo(new BigDecimal(0))).isGreaterThanOrEqualTo(0);
        assertThat(treeLeafState.getEnergy().compareTo(new BigDecimal(100))).isLessThanOrEqualTo(0);
        assertThat(treeLeafState.getFreeEnergy().compareTo(new BigDecimal(0))).isGreaterThanOrEqualTo(0);
        assertThat(treeLeafState.getFreeEnergy().compareTo(new BigDecimal(50))).isLessThanOrEqualTo(0);
    }

    @Test
    public void testCreateNewTreeLeafState() {
        TreeLeafStateFactory factory = new TreeLeafStateFactory();
        BigDecimal energy = new BigDecimal(30);
        long creationMillis = 200;
        TreeLeafState treeLeafState = factory.createNewTreeLeafState(energy, creationMillis);
        assertThat(treeLeafState).isNotNull();
        assertThat(treeLeafState.getCreationMillis()).isEqualTo(creationMillis);
        TreeLeaf3DState leaf3dState = treeLeafState.getLeaf3DState();
        assertThat(leaf3dState).isNotNull();
        // check it is an newly created leaf 3D
        assertThat(leaf3dState.getEndPoint1()).isEqualTo(leaf3dState.getInitialEndPoint1());
        assertThat(leaf3dState.getEndPoint2()).isEqualTo(leaf3dState.getInitialEndPoint2());
        assertThat(PercentHelper.getDecimalValue(90).compareTo(treeLeafState.getEfficiency())).isLessThanOrEqualTo(0);
        assertThat(PercentHelper.getDecimalValue(100).compareTo(treeLeafState.getEfficiency())).isGreaterThanOrEqualTo(
                0);
        assertThat(treeLeafState.getEnergy()).isEqualTo(energy);
        assertThat(treeLeafState.getFreeEnergy()).isEqualTo(new BigDecimal(0));
    }

}
