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
package barsuift.simLife.j3d.environment;

import barsuift.simLife.DimensionParameters;
import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.util.BoundingBoxHelper;

// TODO unit test
public class Sky3DStateFactory {

    public Sky3DState createSky3DState(DimensionParameters dimension) {
        BoundingBoxState ambientLightBounds = BoundingBoxHelper.createBoundingBox(dimension);
        BoundingBoxState skyBounds = BoundingBoxHelper.createBoundingBox(dimension);
        return new Sky3DState(skyBounds, ambientLightBounds);
    }

}
