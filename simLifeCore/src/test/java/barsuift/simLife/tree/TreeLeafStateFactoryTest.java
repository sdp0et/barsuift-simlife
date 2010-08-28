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

import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;


public class TreeLeafStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomTreeLeafState() {
        TreeLeafStateFactory factory = new TreeLeafStateFactory();
        Point3d leafAttachPoint = new Point3d(Math.random(), Math.random(), Math.random());
        TreeLeafState treeLeafState = factory.createRandomTreeLeafState(leafAttachPoint);
        assertNotNull(treeLeafState);
        Long id1 = treeLeafState.getId();
        assertNotNull(id1);
        assertTrue(id1.longValue() > 0);
        assertNotNull(treeLeafState.getLeaf3DState());
        assertTrue(new BigDecimal("0.90").compareTo(treeLeafState.getEfficiency().getValue()) <= 0);
        assertTrue(new BigDecimal("1.00").compareTo(treeLeafState.getEfficiency().getValue()) >= 0);
        assertTrue(treeLeafState.getAge() >= 0);
        assertTrue(treeLeafState.getAge() <= 100);
        assertTrue(treeLeafState.getEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(treeLeafState.getEnergy().compareTo(new BigDecimal(100)) <= 0);
        assertTrue(treeLeafState.getFreeEnergy().compareTo(new BigDecimal(0)) >= 0);
        assertTrue(treeLeafState.getFreeEnergy().compareTo(new BigDecimal(50)) <= 0);
        TreeLeafState treeLeafState2 = factory.createRandomTreeLeafState(leafAttachPoint);
        Long id2 = treeLeafState2.getId();
        assertEquals(id1.longValue() + 1, id2.longValue());
    }

    public void testCreateNewTreeLeafState() {
        TreeLeafStateFactory factory = new TreeLeafStateFactory();
        Point3d leafAttachPoint = new Point3d(Math.random(), Math.random(), Math.random());
        BigDecimal energy = new BigDecimal(30);
        TreeLeafState treeLeafState = factory.createNewTreeLeafState(leafAttachPoint, energy);
        assertNotNull(treeLeafState);
        Long id1 = treeLeafState.getId();
        assertNotNull(id1);
        assertTrue(id1.longValue() > 0);
        TreeLeaf3DState leaf3dState = treeLeafState.getLeaf3DState();
        assertNotNull(leaf3dState);
        // check it is an newly created leaf 3D
        assertEquals(leaf3dState.getInitialEndPoint1(), leaf3dState.getEndPoint1());
        assertEquals(leaf3dState.getInitialEndPoint2(), leaf3dState.getEndPoint2());
        assertTrue(new BigDecimal("0.90").compareTo(treeLeafState.getEfficiency().getValue()) <= 0);
        assertTrue(new BigDecimal("1.00").compareTo(treeLeafState.getEfficiency().getValue()) >= 0);
        assertEquals(0, treeLeafState.getAge());
        assertEquals(energy, treeLeafState.getEnergy());
        assertEquals(new BigDecimal(0), treeLeafState.getFreeEnergy());
        TreeLeafState treeLeafState2 = factory.createNewTreeLeafState(leafAttachPoint, energy);
        Long id2 = treeLeafState2.getId();
        assertEquals(id1.longValue() + 1, id2.longValue());
    }

}
