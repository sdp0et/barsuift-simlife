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

public class ProjectionHelperTest {

    @Test
    public void testGetProjectionPoint3f() {
        Point3f resultPoint1 = ProjectionHelper.getProjectionPoint(new Point3f(0, 0, 0));
        AssertJUnit.assertEquals(new Point3f(0, 0, 0), resultPoint1);
        Point3f resultPoint2 = ProjectionHelper.getProjectionPoint(new Point3f(1, 2, 3));
        AssertJUnit.assertEquals(new Point3f(1, 0, 3), resultPoint2);
    }

}
