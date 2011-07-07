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

import javax.vecmath.Point3f;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PointHelperTest {

    @Test
    public void testShiftPoint() {
        Point3f p1 = new Point3f(0, 0, 0);
        Point3f actualPoint = PointHelper.shiftPoint(p1, 10);
        assertThat(actualPoint.getX() <= 10).isTrue();
        assertThat(actualPoint.getX() >= -10).isTrue();
        assertThat(actualPoint.getY() <= 10).isTrue();
        assertThat(actualPoint.getY() >= -10).isTrue();
        assertThat(actualPoint.getZ() <= 10).isTrue();
        assertThat(actualPoint.getZ() >= -10).isTrue();

        Point3f p2 = new Point3f(1, -1, 2);
        Point3f actualPoint2 = PointHelper.shiftPoint(p2, 5);
        assertThat(actualPoint2.getX() <= 6).isTrue();
        assertThat(actualPoint2.getX() >= -4).isTrue();
        assertThat(actualPoint2.getY() <= 4).isTrue();
        assertThat(actualPoint2.getY() >= -6).isTrue();
        assertThat(actualPoint2.getZ() <= 7).isTrue();
        assertThat(actualPoint2.getZ() >= -3).isTrue();

        Point3f p3 = new Point3f(0, 0, 0);
        Point3f actualPoint3 = PointHelper.shiftPoint(p3, 0.6f);
        assertThat(actualPoint3.getX() <= 0.6).isTrue();
        assertThat(actualPoint3.getX() >= -0.6).isTrue();
        assertThat(actualPoint3.getY() <= 0.6).isTrue();
        assertThat(actualPoint3.getY() >= -0.6).isTrue();
        assertThat(actualPoint3.getZ() <= 0.6).isTrue();
        assertThat(actualPoint3.getZ() >= -0.6).isTrue();

        Point3f p4 = new Point3f(1, -1, 2);
        Point3f actualPoint4 = PointHelper.shiftPoint(p4, 0.6f);
        assertThat(actualPoint4.getX() <= 1.6).isTrue();
        assertThat(actualPoint4.getX() >= 0.4).isTrue();
        assertThat(actualPoint4.getY() <= -0.4).isTrue();
        assertThat(actualPoint4.getY() >= -1.6).isTrue();
        assertThat(actualPoint4.getZ() <= 2.6).isTrue();
        assertThat(actualPoint4.getZ() >= 1.4).isTrue();
    }

}
