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

import barsuift.simLife.DimensionParameters;
import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.Tuple3dState;


public final class BoundingBoxHelper {

    private BoundingBoxHelper() {
        // private constructor to enforce static access
    }

    public static BoundingBoxState createBoundingBox(DimensionParameters dimension) {
        return new BoundingBoxState(new Tuple3dState(0, 0, 0), new Tuple3dState(dimension.getSize(),
                dimension.getMaximumHeight(), dimension.getSize()));
    }

}