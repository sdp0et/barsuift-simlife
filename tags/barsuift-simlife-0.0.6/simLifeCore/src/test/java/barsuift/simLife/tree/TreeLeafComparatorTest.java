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

import javax.vecmath.Point3f;

import junit.framework.TestCase;
import barsuift.simLife.j3d.tree.MockTreeLeaf3D;


public class TreeLeafComparatorTest extends TestCase {

    private TreeLeafComparator comparator;

    protected void setUp() throws Exception {
        super.setUp();
        comparator = new TreeLeafComparator();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        comparator = null;
    }

    public void testCompare() {
        MockTreeLeaf o1 = new MockTreeLeaf();
        ((MockTreeLeaf3D) o1.getTreeLeaf3D()).setAttachPoint(new Point3f(2, 0, 0));
        MockTreeLeaf o2 = new MockTreeLeaf();
        ((MockTreeLeaf3D) o2.getTreeLeaf3D()).setAttachPoint(new Point3f(3, 0, 0));
        MockTreeLeaf o3 = new MockTreeLeaf();
        ((MockTreeLeaf3D) o3.getTreeLeaf3D()).setAttachPoint(new Point3f(5.0005f, 0, 0));
        MockTreeLeaf o4 = new MockTreeLeaf();
        ((MockTreeLeaf3D) o4.getTreeLeaf3D()).setAttachPoint(new Point3f(5.001f, 0, 0));

        assertEquals(0, comparator.compare(o1, o1));
        assertEquals(-1000, comparator.compare(o1, o2));
        assertEquals(-3000, comparator.compare(o1, o3));
        assertEquals(-3001, comparator.compare(o1, o4));

        assertEquals(1000, comparator.compare(o2, o1));
        assertEquals(0, comparator.compare(o2, o2));
        assertEquals(-2000, comparator.compare(o2, o3));
        assertEquals(-2001, comparator.compare(o2, o4));

        assertEquals(3000, comparator.compare(o3, o1));
        assertEquals(2000, comparator.compare(o3, o2));
        assertEquals(0, comparator.compare(o3, o3));
        assertEquals(0, comparator.compare(o3, o4));

        assertEquals(3001, comparator.compare(o4, o1));
        assertEquals(2001, comparator.compare(o4, o2));
        assertEquals(0, comparator.compare(o4, o3));
        assertEquals(0, comparator.compare(o4, o4));
    }
}
