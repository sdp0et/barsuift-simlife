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

import javax.vecmath.Point3f;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;


public class TreeLeaf3DComparatorTest {

    private TreeLeaf3DComparator comparator;

    @BeforeMethod
    protected void setUp() {
        comparator = new TreeLeaf3DComparator();
    }

    @AfterMethod
    protected void tearDown() {
        comparator = null;
    }

    @Test
    public void testCompare() {
        MockTreeLeaf3D o1 = new MockTreeLeaf3D();
        o1.setPosition(new Point3f(2, 0, 0));
        MockTreeLeaf3D o2 = new MockTreeLeaf3D();
        o2.setPosition(new Point3f(3, 0, 0));
        MockTreeLeaf3D o3 = new MockTreeLeaf3D();
        o3.setPosition(new Point3f(5.0005f, 0, 0));
        MockTreeLeaf3D o4 = new MockTreeLeaf3D();
        o4.setPosition(new Point3f(5.001f, 0, 0));

        assertThat(comparator.compare(o1, o1)).isEqualTo(0);
        assertThat(comparator.compare(o1, o2)).isEqualTo(-1000);
        assertThat(comparator.compare(o1, o3)).isEqualTo(-3000);
        assertThat(comparator.compare(o1, o4)).isEqualTo(-3001);

        assertThat(comparator.compare(o2, o1)).isEqualTo(1000);
        assertThat(comparator.compare(o2, o2)).isEqualTo(0);
        assertThat(comparator.compare(o2, o3)).isEqualTo(-2000);
        assertThat(comparator.compare(o2, o4)).isEqualTo(-2001);

        assertThat(comparator.compare(o3, o1)).isEqualTo(3000);
        assertThat(comparator.compare(o3, o2)).isEqualTo(2000);
        assertThat(comparator.compare(o3, o3)).isEqualTo(0);
        assertThat(comparator.compare(o3, o4)).isEqualTo(0);

        assertThat(comparator.compare(o4, o1)).isEqualTo(3001);
        assertThat(comparator.compare(o4, o2)).isEqualTo(2001);
        assertThat(comparator.compare(o4, o3)).isEqualTo(0);
        assertThat(comparator.compare(o4, o4)).isEqualTo(0);
    }
}
