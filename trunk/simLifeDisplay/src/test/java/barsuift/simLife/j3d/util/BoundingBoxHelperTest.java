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

import org.testng.annotations.Test;

import barsuift.simLife.landscape.LandscapeParameters;
import static barsuift.simLife.landscape.LandscapeParameters.SIZE_MIN;

import static org.fest.assertions.Assertions.assertThat;


public class BoundingBoxHelperTest {

    @Test
    public void testCreateBoundingSphere() {
        LandscapeParameters landscapeParameters = new LandscapeParameters();
        BoundingBox boundingBox;

        landscapeParameters.setSize(SIZE_MIN);
        landscapeParameters.setMaximumHeight(10);
        boundingBox = BoundingBoxHelper.createBoundingBox(landscapeParameters).toBoundingBox();

        // given bounds
        assertThat(boundingBox.intersect(new Point3d(0, 0, 0))).isTrue();
        assertThat(boundingBox.intersect(new Point3d(SIZE_MIN, 10, SIZE_MIN))).isTrue();

        // enlarged bounds
        assertThat(boundingBox.intersect(new Point3d(0, -1, 0))).isTrue();
        assertThat(boundingBox.intersect(new Point3d(SIZE_MIN, 60, SIZE_MIN))).isTrue();

        // other points
        assertThat(boundingBox.intersect(new Point3d(SIZE_MIN / 2, 5, SIZE_MIN / 2))).isTrue();

        // outside points
        assertThat(boundingBox.intersect(new Point3d(-1, 0, 0))).isFalse();
        assertThat(boundingBox.intersect(new Point3d(0, -2, 0))).isFalse();
        assertThat(boundingBox.intersect(new Point3d(0, 0, -1))).isFalse();

        assertThat(boundingBox.intersect(new Point3d(SIZE_MIN + 1, 0, 0))).isFalse();
        assertThat(boundingBox.intersect(new Point3d(0, 61, 0))).isFalse();
        assertThat(boundingBox.intersect(new Point3d(0, 0, SIZE_MIN + 1))).isFalse();
    }

}
