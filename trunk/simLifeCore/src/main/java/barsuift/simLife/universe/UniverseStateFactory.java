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
package barsuift.simLife.universe;

import java.util.HashSet;
import java.util.Set;

import barsuift.simLife.environment.EnvironmentState;
import barsuift.simLife.environment.EnvironmentStateFactory;
import barsuift.simLife.j3d.landscape.LandscapeParameters;
import barsuift.simLife.j3d.universe.Universe3DState;
import barsuift.simLife.j3d.universe.Universe3DStateFactory;
import barsuift.simLife.process.SynchronizerCoreState;
import barsuift.simLife.process.SynchronizerCoreStateFactory;
import barsuift.simLife.time.DateHandlerState;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;
import barsuift.simLife.universe.physic.PhysicsState;
import barsuift.simLife.universe.physic.PhysicsStateFactory;


public class UniverseStateFactory {

    /**
     * Creates an empty universe state. there is no living part in this universe, only the environment, synchronizer,
     * physics, ...
     */
    public UniverseState createEmptyRandomUniverseState() {
        EnvironmentStateFactory envStateFactory = new EnvironmentStateFactory();
        EnvironmentState environment = envStateFactory.createRandomEnvironmentState();
        PhysicsStateFactory physicsStateFactory = new PhysicsStateFactory();
        PhysicsState physics = physicsStateFactory.createPhysicsState();
        SynchronizerCoreStateFactory synchronizerStateFactory = new SynchronizerCoreStateFactory();
        SynchronizerCoreState synchronizerState = synchronizerStateFactory.createSynchronizerCoreState();
        DateHandlerState dateHandler = new DateHandlerState();

        Set<TreeState> trees = new HashSet<TreeState>(0);
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(0);

        Universe3DStateFactory univ3DStateFactory = new Universe3DStateFactory();
        Universe3DState univ3DState = univ3DStateFactory.createUniverse3DState();

        return new UniverseState(trees, fallenLeaves, environment, physics, synchronizerState, dateHandler, univ3DState);
    }

    /**
     * Creates an empty universe state. there is no living part in this universe, only the environment, synchronizer,
     * physics, ...
     */
    public UniverseState createEmptyRandomUniverseStateWithParameters(LandscapeParameters parameters) {
        EnvironmentStateFactory envStateFactory = new EnvironmentStateFactory();
        EnvironmentState environment = envStateFactory.createRandomEnvironmentStateWithParameters(parameters);
        PhysicsStateFactory physicsStateFactory = new PhysicsStateFactory();
        PhysicsState physics = physicsStateFactory.createPhysicsState();
        SynchronizerCoreStateFactory synchronizerStateFactory = new SynchronizerCoreStateFactory();
        SynchronizerCoreState synchronizerState = synchronizerStateFactory.createSynchronizerCoreState();
        DateHandlerState dateHandler = new DateHandlerState();

        Set<TreeState> trees = new HashSet<TreeState>(0);
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(0);

        Universe3DStateFactory univ3DStateFactory = new Universe3DStateFactory();
        Universe3DState univ3DState = univ3DStateFactory.createUniverse3DState();

        return new UniverseState(trees, fallenLeaves, environment, physics, synchronizerState, dateHandler, univ3DState);
    }

}
