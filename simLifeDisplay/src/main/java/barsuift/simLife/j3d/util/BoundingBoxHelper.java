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

import barsuift.simLife.PlanetParameters;
import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.Tuple3dState;


public final class BoundingBoxHelper {

    private BoundingBoxHelper() {
        // private constructor to enforce static access
    }

    /**
     * Create a bounding box from given size and height. The box width and length are equal to the size. The box height
     * is equal to the given height + 50. This is to ensure any objects up to 50 meters above the ighest point is still
     * under the influence of the generated bounding box.
     * 
     * @param planetParameters the planet parameters for which to create the bounds
     * @return a bounding box
     */
    public static BoundingBoxState createBoundingBox(PlanetParameters planetParameters) {
        return new BoundingBoxState(new Tuple3dState(0, -1, 0), new Tuple3dState(planetParameters.getSize(),
                planetParameters.getMaximumHeight() + 50, planetParameters.getSize()));
    }

}