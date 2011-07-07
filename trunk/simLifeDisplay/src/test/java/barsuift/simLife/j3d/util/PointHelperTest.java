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
package barsuift.simLife.j3d.util;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import javax.vecmath.Point3f;

public class PointHelperTest {

    @Test
    public void testShiftPoint() {
        Point3f p1 = new Point3f(0, 0, 0);
        Point3f actualPoint = PointHelper.shiftPoint(p1, 10);
        AssertJUnit.assertTrue(actualPoint.getX() <= 10);
        AssertJUnit.assertTrue(actualPoint.getX() >= -10);
        AssertJUnit.assertTrue(actualPoint.getY() <= 10);
        AssertJUnit.assertTrue(actualPoint.getY() >= -10);
        AssertJUnit.assertTrue(actualPoint.getZ() <= 10);
        AssertJUnit.assertTrue(actualPoint.getZ() >= -10);

        Point3f p2 = new Point3f(1, -1, 2);
        Point3f actualPoint2 = PointHelper.shiftPoint(p2, 5);
        AssertJUnit.assertTrue(actualPoint2.getX() <= 6);
        AssertJUnit.assertTrue(actualPoint2.getX() >= -4);
        AssertJUnit.assertTrue(actualPoint2.getY() <= 4);
        AssertJUnit.assertTrue(actualPoint2.getY() >= -6);
        AssertJUnit.assertTrue(actualPoint2.getZ() <= 7);
        AssertJUnit.assertTrue(actualPoint2.getZ() >= -3);

        Point3f p3 = new Point3f(0, 0, 0);
        Point3f actualPoint3 = PointHelper.shiftPoint(p3, 0.6f);
        AssertJUnit.assertTrue(actualPoint3.getX() <= 0.6);
        AssertJUnit.assertTrue(actualPoint3.getX() >= -0.6);
        AssertJUnit.assertTrue(actualPoint3.getY() <= 0.6);
        AssertJUnit.assertTrue(actualPoint3.getY() >= -0.6);
        AssertJUnit.assertTrue(actualPoint3.getZ() <= 0.6);
        AssertJUnit.assertTrue(actualPoint3.getZ() >= -0.6);

        Point3f p4 = new Point3f(1, -1, 2);
        Point3f actualPoint4 = PointHelper.shiftPoint(p4, 0.6f);
        AssertJUnit.assertTrue(actualPoint4.getX() <= 1.6);
        AssertJUnit.assertTrue(actualPoint4.getX() >= 0.4);
        AssertJUnit.assertTrue(actualPoint4.getY() <= -0.4);
        AssertJUnit.assertTrue(actualPoint4.getY() >= -1.6);
        AssertJUnit.assertTrue(actualPoint4.getZ() <= 2.6);
        AssertJUnit.assertTrue(actualPoint4.getZ() >= 1.4);
    }

}
