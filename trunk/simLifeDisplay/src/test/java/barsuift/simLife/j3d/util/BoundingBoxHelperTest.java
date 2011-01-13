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

import javax.media.j3d.BoundingBox;
import javax.vecmath.Point3d;

import junit.framework.TestCase;
import barsuift.simLife.DimensionParameters;


public class BoundingBoxHelperTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateBoundingSphere() {
        DimensionParameters dimension = new DimensionParameters();
        BoundingBox boundingBox;

        dimension.setSize(8);
        dimension.setMaximumHeight(10);
        boundingBox = BoundingBoxHelper.createBoundingBox(dimension).toBoundingBox();

        assertTrue(boundingBox.intersect(new Point3d(0, 0, 0)));
        assertTrue(boundingBox.intersect(new Point3d(4, 5, 4)));
        assertTrue(boundingBox.intersect(new Point3d(8, 10, 8)));

        assertFalse(boundingBox.intersect(new Point3d(-1, 0, 0)));
        assertFalse(boundingBox.intersect(new Point3d(0, -1, 0)));
        assertFalse(boundingBox.intersect(new Point3d(0, 0, -1)));

        assertFalse(boundingBox.intersect(new Point3d(9, 0, 0)));
        assertFalse(boundingBox.intersect(new Point3d(0, 11, 0)));
        assertFalse(boundingBox.intersect(new Point3d(0, 0, 9)));
    }

}
