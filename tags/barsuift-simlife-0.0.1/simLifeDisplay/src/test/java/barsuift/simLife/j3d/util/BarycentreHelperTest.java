/**
 * barsuift-simlife is a life simulator programm
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
package barsuift.simLife.j3d.util;

import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.j3d.helper.PointTestHelper;

public class BarycentreHelperTest extends TestCase {

    public void testGetBarycentre() {
        Point3d actualPoint;
        Point3d expectedPoint;
        Point3d p1 = new Point3d(0, 0, 0);
        Point3d p2 = new Point3d(1, 0, 0);
        Point3d p3 = new Point3d(0, 1, 0);
        Point3d p4 = new Point3d(0, 0, 1);
        Point3d p5 = new Point3d(1, 1, 0);
        double d1 = 1;
        double d2 = 0.5d;
        double d3 = 2;
        double d4 = -1;
        try {
            BarycentreHelper.getBarycentre(null, p2, d1);
            fail("Should throw a NPE");
        } catch (NullPointerException npe) {
            // OK expected exception
        }
        try {
            BarycentreHelper.getBarycentre(null, p2, d1);
            fail("Should throw a NPE");
        } catch (NullPointerException npe) {
            // OK expected exception
        }

        // //////// between p1 and p2
        actualPoint = BarycentreHelper.getBarycentre(p1, p2, d1);
        expectedPoint = new Point3d(1, 0, 0);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p2, d2);
        expectedPoint = new Point3d(0.5, 0, 0);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p2, d3);
        expectedPoint = new Point3d(1, 0, 0);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p2, d4);
        expectedPoint = new Point3d(0, 0, 0);
        assertEquals(expectedPoint, actualPoint);

        // //////// between p1 and p3
        actualPoint = BarycentreHelper.getBarycentre(p1, p3, d1);
        expectedPoint = new Point3d(0, 1, 0);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p3, d2);
        expectedPoint = new Point3d(0, 0.5, 0);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p3, d3);
        expectedPoint = new Point3d(0, 1, 0);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p3, d4);
        expectedPoint = new Point3d(0, 0, 0);
        assertEquals(expectedPoint, actualPoint);

        // //////// between p1 and p4
        actualPoint = BarycentreHelper.getBarycentre(p1, p4, d1);
        expectedPoint = new Point3d(0, 0, 1);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p4, d2);
        expectedPoint = new Point3d(0, 0, 0.5d);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p4, d3);
        expectedPoint = new Point3d(0, 0, 1);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p4, d4);
        expectedPoint = new Point3d(0, 0, 0);
        assertEquals(expectedPoint, actualPoint);

        // //////// between p1 and p5
        actualPoint = BarycentreHelper.getBarycentre(p1, p5, d1);
        expectedPoint = new Point3d(Math.cos(Math.PI / 4), Math.sin(Math.PI / 4), 0);
        PointTestHelper.assertPointEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p5, d2);
        expectedPoint = new Point3d(Math.cos(Math.PI / 4) / 2, Math.sin(Math.PI / 4) / 2, 0);
        PointTestHelper.assertPointEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p5, d3);
        expectedPoint = new Point3d(1, 1, 0);
        assertEquals(expectedPoint, actualPoint);

        actualPoint = BarycentreHelper.getBarycentre(p1, p5, d4);
        expectedPoint = new Point3d(0, 0, 0);
        assertEquals(expectedPoint, actualPoint);
    }

    public void testComputeDistanceToUse() {
        assertEquals(5d, BarycentreHelper.computeDistanceToUse(10, 5));
        assertEquals(10d, BarycentreHelper.computeDistanceToUse(10, 10));
        assertEquals(10d, BarycentreHelper.computeDistanceToUse(10, 15));
        assertEquals(0d, BarycentreHelper.computeDistanceToUse(10, -5));
        assertEquals(0d, BarycentreHelper.computeDistanceToUse(-10, -5));
        assertEquals(0d, BarycentreHelper.computeDistanceToUse(-1, -5));
    }

}
