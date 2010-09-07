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
package barsuift.simLife.j3d.tree.helper;

import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.LineArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import junit.framework.Assert;
import barsuift.simLife.j3d.helper.PointTestHelper;
import barsuift.simLife.j3d.util.ColorConstants;

public final class BasicTreeBranchPart3DTestHelper extends Assert {

    private BasicTreeBranchPart3DTestHelper() {
        // private constructor to enforce static access
    }

    /**
     * Test the given Geometry is a LineArray, starting from the given start point and ending at the given end point.
     * 
     * @param branchGeometry the geometry to test
     * @param expectedStartPoint the expected start point
     * @param expectedEndPoint the expected end point
     */
    public static void testGeometry(Geometry branchGeometry, Point3d expectedStartPoint, Point3d expectedEndPoint) {
        assertTrue(branchGeometry instanceof LineArray);
        LineArray branchLine = (LineArray) branchGeometry;
        Point3d actualStartPoint = new Point3d();
        Point3d actualEndPoint = new Point3d();
        branchLine.getCoordinate(0, actualStartPoint);
        branchLine.getCoordinate(1, actualEndPoint);
        PointTestHelper.assertPointEquals(expectedStartPoint, actualStartPoint);
        PointTestHelper.assertPointEquals(expectedEndPoint, actualEndPoint);
        assertEquals("Ony 2 vertices for a branch line", 2, branchLine.getVertexCount());
    }

    public static void testAppearance(Appearance branchAppearance) {
        Color3f actualColor = new Color3f();
        branchAppearance.getColoringAttributes().getColor(actualColor);
        Color3f expectedColor = ColorConstants.brown;
        assertEquals(expectedColor, actualColor);
    }

}
