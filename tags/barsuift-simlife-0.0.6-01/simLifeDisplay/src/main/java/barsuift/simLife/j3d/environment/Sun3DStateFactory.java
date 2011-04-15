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

import barsuift.simLife.PlanetParameters;
import barsuift.simLife.j3d.BoundingBoxState;
import barsuift.simLife.j3d.util.BoundingBoxHelper;
import barsuift.simLife.landscape.LandscapeParameters;
import barsuift.simLife.process.ConditionalTaskStateFactory;
import barsuift.simLife.process.EarthRevolutionTask;
import barsuift.simLife.process.EarthRotationTask;
import barsuift.simLife.process.SplitConditionalTaskState;


public class Sun3DStateFactory {

    /**
     * Creates a default sun 3D state with following values :
     * <ul>
     * <li>earth rotation = 0%</li>
     * <li>earth revolution = 0%</li>
     * </ul>
     */
    public Sun3DState createSun3DState(PlanetParameters planetParameters, LandscapeParameters landscapeParameters) {
        BoundingBoxState bounds = BoundingBoxHelper.createBoundingBox(landscapeParameters);
        float earthRotation = 0f;
        float earthRevolution = 0f;
        ConditionalTaskStateFactory taskStateFactory = new ConditionalTaskStateFactory();
        SplitConditionalTaskState earthRotationTask = taskStateFactory
                .createSplitConditionalTaskState(EarthRotationTask.class);
        SplitConditionalTaskState earthRevolutionTask = taskStateFactory
                .createSplitConditionalTaskState(EarthRevolutionTask.class);
        boolean earthRotationTaskAutomatic = true;
        boolean earthRevolutionTaskAutomatic = true;
        return new Sun3DState(bounds, planetParameters.getLatitude(), planetParameters.getEclipticObliquity(),
                earthRotation, earthRotationTask, earthRotationTaskAutomatic, earthRevolution, earthRevolutionTask,
                earthRevolutionTaskAutomatic);
    }

}
