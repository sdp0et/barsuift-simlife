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
package barsuift.simLife.j3d.landscape;

import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.Tuple3fState;
import barsuift.simLife.j3d.util.BoundingBoxHelper;
import barsuift.simLife.landscape.LandscapeParameters;


public class NavigatorStateFactory {

    public static final float VIEWER_SIZE = 2f;

    public static final double ORIGINAL_ROTATION_X = 0;

    public static final double ORIGINAL_ROTATION_Y = 0;

    /**
     * Creates a navigator at its default position : in the middle of the landscape, at 2 meters high. The navigation
     * mode is the default one.
     */
    public NavigatorState createNavigatorState(LandscapeParameters landscapeParameters) {
        Tuple3fState originalPosition = new Tuple3fState(landscapeParameters.getSize() / 2, VIEWER_SIZE,
                landscapeParameters.getSize() / 2);
        BoundingBoxState bounds = BoundingBoxHelper.createBoundingBox(landscapeParameters);
        return new NavigatorState(originalPosition, originalPosition, ORIGINAL_ROTATION_X, ORIGINAL_ROTATION_Y,
                NavigationMode.DEFAULT, bounds);
    }

}
