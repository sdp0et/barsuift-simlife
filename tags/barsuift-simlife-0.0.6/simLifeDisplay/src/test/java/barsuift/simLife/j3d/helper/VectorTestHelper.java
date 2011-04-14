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
package barsuift.simLife.j3d.helper;

import javax.vecmath.Vector3f;

import junit.framework.Assert;

public final class VectorTestHelper extends Assert {

    private VectorTestHelper() {
        // private constructor to enforce static access
    }

    public static final void assertVectorEquals(Vector3f v1, Vector3f v2) {
        assertVectorEquals(v1, v2, 0.0001f, 0.0001f, 0.0001f);
    }

    public static final void assertVectorEquals(Vector3f v1, Vector3f v2, float xPrecision, float yPrecision,
            float zPrecision) {
        assertEquals(v1.getX(), v2.getX(), xPrecision);
        assertEquals(v1.getY(), v2.getY(), yPrecision);
        assertEquals(v1.getZ(), v2.getZ(), zPrecision);
    }

}
