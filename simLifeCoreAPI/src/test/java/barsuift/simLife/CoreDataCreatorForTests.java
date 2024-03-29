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
package barsuift.simLife;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import barsuift.simLife.environment.EnvironmentState;
import barsuift.simLife.environment.SkyState;
import barsuift.simLife.environment.SunState;
import barsuift.simLife.environment.WindState;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.environment.Environment3DState;
import barsuift.simLife.j3d.environment.Sky3DState;
import barsuift.simLife.j3d.environment.Sun3DState;
import barsuift.simLife.j3d.environment.Wind3DState;
import barsuift.simLife.j3d.landscape.Landscape3DState;
import barsuift.simLife.j3d.tree.Tree3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeTrunk3DState;
import barsuift.simLife.j3d.universe.Universe3DState;
import barsuift.simLife.j3d.universe.UniverseContext3DState;
import barsuift.simLife.j3d.universe.physic.Gravity3DState;
import barsuift.simLife.j3d.universe.physic.Physics3DState;
import barsuift.simLife.landscape.LandscapeState;
import barsuift.simLife.process.ConditionalTaskState;
import barsuift.simLife.process.MainSynchronizerState;
import barsuift.simLife.time.DateHandlerState;
import barsuift.simLife.time.SimLifeDateState;
import barsuift.simLife.tree.TreeBranchState;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;
import barsuift.simLife.tree.TreeTrunkState;
import barsuift.simLife.universe.UniverseContextState;
import barsuift.simLife.universe.UniverseState;
import barsuift.simLife.universe.physic.GravityState;
import barsuift.simLife.universe.physic.PhysicsState;

public final class CoreDataCreatorForTests {

    private CoreDataCreatorForTests() {
        // private constructor to enforce static access
    }

    public static UniverseContextState createRandomUniverseContextState() {
        UniverseState universeState = createRandomUniverseState();
        boolean fpsShowing = UtilDataCreatorForTests.createRandomBoolean();
        UniverseContext3DState universeContext3DState = DisplayDataCreatorForTests.createRandomUniverseContext3DState();
        return new UniverseContextState(universeState, fpsShowing, universeContext3DState);
    }

    /**
     * Create a specific universe context state with
     * <ul>
     * <li>specific universe state made through the {@link #createSpecificUniverseState()} method</li>
     * <li>fpsShowing = false</li>
     * <li>specific universe context 3D state made through the
     * {@link DisplayDataCreatorForTests#createSpecificUniverseContext3DState()} method</li>
     * </ul>
     * 
     */
    public static UniverseContextState createSpecificUniverseContextState() {
        UniverseState universeState = createSpecificUniverseState();
        boolean fpsShowing = false;
        UniverseContext3DState universeContext3DState = DisplayDataCreatorForTests
                .createSpecificUniverseContext3DState();
        return new UniverseContextState(universeState, fpsShowing, universeContext3DState);
    }

    public static PhysicsState createRandomPhysicsState() {
        GravityState gravity = createRandomGravityState();
        Physics3DState physics3D = DisplayDataCreatorForTests.createRandomPhysics3DState();
        return new PhysicsState(gravity, physics3D);
    }

    public static PhysicsState createSpecificPhysicsState() {
        GravityState gravity = createSpecificGravityState();
        Physics3DState physics3D = DisplayDataCreatorForTests.createSpecificPhysics3DState();
        return new PhysicsState(gravity, physics3D);
    }

    public static GravityState createRandomGravityState() {
        Gravity3DState gravity3D = DisplayDataCreatorForTests.createRandomGravity3DState();
        int nbFallingLeaves = Randomizer.randomBetween(0, 40);
        Set<TreeLeafState> fallingLeaves = new HashSet<TreeLeafState>(nbFallingLeaves);
        for (int i = 0; i < nbFallingLeaves; i++) {
            fallingLeaves.add(createRandomTreeLeafState());
        }
        return new GravityState(gravity3D, fallingLeaves);
    }

    public static GravityState createSpecificGravityState() {
        Gravity3DState gravity3D = DisplayDataCreatorForTests.createSpecificGravity3DState();
        int nbFallingLeaves = 20;
        Set<TreeLeafState> fallingLeaves = new HashSet<TreeLeafState>(nbFallingLeaves);
        for (int i = 0; i < nbFallingLeaves; i++) {
            fallingLeaves.add(createSpecificTreeLeafState());
        }
        return new GravityState(gravity3D, fallingLeaves);
    }

    public static DateHandlerState createRandomDateHandlerState() {
        SimLifeDateState date = UtilDataCreatorForTests.createRandomDateState();
        return new DateHandlerState(date);
    }

    public static DateHandlerState createSpecificDateHandlerState() {
        SimLifeDateState date = UtilDataCreatorForTests.createSpecificDateState();
        return new DateHandlerState(date);
    }

    public static UniverseState createRandomUniverseState() {
        Set<TreeState> trees = new HashSet<TreeState>();
        trees.add(createRandomTreeState());
        trees.add(createRandomTreeState());
        trees.add(createRandomTreeState());
        int nbFallenLeaves = Randomizer.randomBetween(0, 40);
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(nbFallenLeaves);
        for (int i = 0; i < nbFallenLeaves; i++) {
            fallenLeaves.add(createRandomTreeLeafState());
        }
        EnvironmentState environmentState = createRandomEnvironmentState();
        PhysicsState physicsState = createRandomPhysicsState();
        MainSynchronizerState synchroState = UtilDataCreatorForTests.createRandomMainSynchronizerState();
        DateHandlerState dateHandler = createRandomDateHandlerState();
        Universe3DState univ3DState = DisplayDataCreatorForTests.createRandomUniverse3DState();
        return new UniverseState(trees, fallenLeaves, environmentState, physicsState, synchroState, dateHandler,
                univ3DState);
    }

    /**
     * Create a specific universe state with
     * <ul>
     * <li>nb trees=3 (made through the {@link #createSpecificTreeState()} method)</li>
     * <li>nb fallen leaves=20 (made through the {@link #createSpecificTreeLeafState()} method)</li>
     * </ul>
     * The environment is made through the {@link #createSpecificEnvironmentState()} method. The date is made through
     * the {@link UtilDataCreatorForTests#createSpecificDateState()} method.
     * 
     * @return
     */
    public static UniverseState createSpecificUniverseState() {
        Set<TreeState> trees = new HashSet<TreeState>();
        trees.add(createSpecificTreeState());
        trees.add(createSpecificTreeState());
        trees.add(createSpecificTreeState());
        int nbFallenLeaves = 20;
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(nbFallenLeaves);
        for (int i = 0; i < nbFallenLeaves; i++) {
            fallenLeaves.add(createSpecificTreeLeafState());
        }
        EnvironmentState environmentState = createSpecificEnvironmentState();
        PhysicsState physicsState = createSpecificPhysicsState();
        Universe3DState univ3DState = DisplayDataCreatorForTests.createSpecificUniverse3DState();
        MainSynchronizerState synchroState = UtilDataCreatorForTests.createSpecificMainSynchronizerState();
        DateHandlerState dateHandler = createSpecificDateHandlerState();
        return new UniverseState(trees, fallenLeaves, environmentState, physicsState, synchroState, dateHandler,
                univ3DState);
    }

    public static EnvironmentState createRandomEnvironmentState() {
        SkyState skyState = createRandomSkyState();
        WindState windState = createRandomWindState();
        Environment3DState env3DState = DisplayDataCreatorForTests.createRandomEnvironment3DState();
        LandscapeState landscape = createRandomLandscapeState();
        return new EnvironmentState(skyState, windState, landscape, env3DState);
    }

    /**
     * Create a specific environment state. The sky state is made through the {@link #createSpecificSkyState()} method.
     * 
     * @return
     */
    public static EnvironmentState createSpecificEnvironmentState() {
        SkyState skyState = createSpecificSkyState();
        WindState windState = createSpecificWindState();
        Environment3DState env3DState = DisplayDataCreatorForTests.createSpecificEnvironment3DState();
        LandscapeState landscape = createSpecificLandscapeState();
        return new EnvironmentState(skyState, windState, landscape, env3DState);
    }

    public static SkyState createRandomSkyState() {
        SunState sunState = createRandomSunState();
        Sky3DState env3DState = DisplayDataCreatorForTests.createRandomSky3DState();
        return new SkyState(sunState, env3DState);
    }

    /**
     * Create a specific sky state. The sun state is made through the {@link #createSpecificSunState()} method.
     * 
     * @return
     */
    public static SkyState createSpecificSkyState() {
        SunState sunState = createSpecificSunState();
        Sky3DState env3DState = DisplayDataCreatorForTests.createSpecificSky3DState();
        return new SkyState(sunState, env3DState);
    }

    public static WindState createRandomWindState() {
        Wind3DState wind3D = DisplayDataCreatorForTests.createRandomWind3DState();
        int nbMovingLeaves = Randomizer.randomBetween(0, 40);
        Set<TreeLeafState> movingLeaves = new HashSet<TreeLeafState>(nbMovingLeaves);
        for (int i = 0; i < nbMovingLeaves; i++) {
            movingLeaves.add(createRandomTreeLeafState());
        }
        return new WindState(wind3D, movingLeaves);
    }

    public static WindState createSpecificWindState() {
        Wind3DState wind3D = DisplayDataCreatorForTests.createSpecificWind3DState();
        int nbMovingLeaves = 20;
        Set<TreeLeafState> movingLeaves = new HashSet<TreeLeafState>(nbMovingLeaves);
        for (int i = 0; i < nbMovingLeaves; i++) {
            movingLeaves.add(createSpecificTreeLeafState());
        }
        return new WindState(wind3D, movingLeaves);
    }

    public static LandscapeState createRandomLandscapeState() {
        Landscape3DState landscape3DState = DisplayDataCreatorForTests.createRandomLandscape3DState();
        return new LandscapeState(landscape3DState);
    }

    /**
     * Create a specific landscape state. The landscape3D state is made through the
     * {@link DisplayDataCreatorForTests#createSpecificLandscape3DState()} method.
     * 
     * @return
     */
    public static LandscapeState createSpecificLandscapeState() {
        Landscape3DState landscape3DState = DisplayDataCreatorForTests.createSpecificLandscape3DState();
        return new LandscapeState(landscape3DState);
    }

    public static SunState createRandomSunState() {
        Sun3DState sun3DState = DisplayDataCreatorForTests.createRandomSun3DState();
        return new SunState(sun3DState);
    }

    /**
     * Create specific sun state. The sun3D state is created through the
     * {@link DisplayDataCreatorForTests#createSpecificSun3DState()} method.
     */
    public static SunState createSpecificSunState() {
        Sun3DState sun3DState = DisplayDataCreatorForTests.createSpecificSun3DState();
        return new SunState(sun3DState);
    }

    public static TreeState createRandomTreeState() {
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        int nbBranches = Randomizer.randomBetween(30, 50);
        List<TreeBranchState> branches = new ArrayList<TreeBranchState>(nbBranches);
        for (int i = 0; i < nbBranches; i++) {
            branches.add(createRandomTreeBranchState());
        }
        ConditionalTaskState photosynthesis = UtilDataCreatorForTests.createRandomConditionalTaskState();
        ConditionalTaskState aging = UtilDataCreatorForTests.createRandomConditionalTaskState();
        ConditionalTaskState growth = UtilDataCreatorForTests.createRandomConditionalTaskState();
        TreeTrunkState trunkState = createRandomTreeTrunkState();
        float height = (float) Math.random();
        Tree3DState tree3dState = DisplayDataCreatorForTests.createRandomTree3DState();
        return new TreeState(age, energy, branches, photosynthesis, aging, growth, trunkState, height, tree3dState);
    }

    /**
     * Create specific tree with
     * <ul>
     * <li>age=15</li>
     * <li>energy=10</li>
     * <li>nb branches=40</li>
     * <li>height=4</li>
     * </ul>
     * All parts are made through {@link #createSpecificTreeBranchState()} method. The trunk is made through the
     * {@link #createSpecificTreeTrunkState()} method.
     * 
     * @return
     */
    public static TreeState createSpecificTreeState() {
        BigDecimal energy = new BigDecimal(10);
        int age = 15;
        int nbBranches = 40;
        List<TreeBranchState> branches = new ArrayList<TreeBranchState>(nbBranches);
        for (int i = 0; i < nbBranches; i++) {
            branches.add(createSpecificTreeBranchState());
        }
        ConditionalTaskState photosynthesis = UtilDataCreatorForTests.createSpecificConditionalTaskState();
        ConditionalTaskState aging = UtilDataCreatorForTests.createSpecificConditionalTaskState();
        ConditionalTaskState growth = UtilDataCreatorForTests.createSpecificConditionalTaskState();
        TreeTrunkState trunkState = createSpecificTreeTrunkState();
        float height = (float) 4;
        Tree3DState tree3dState = DisplayDataCreatorForTests.createRandomTree3DState();
        return new TreeState(age, energy, branches, photosynthesis, aging, growth, trunkState, height, tree3dState);
    }

    public static TreeBranchState createRandomTreeBranchState() {
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        int nbLeaves = Randomizer.randomBetween(1, 18);
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(nbLeaves);
        for (int i = 0; i < nbLeaves; i++) {
            leavesStates.add(createRandomTreeLeafState());
        }
        TreeBranch3DState branch3DState = DisplayDataCreatorForTests.createRandomTreeBranch3DState();
        return new TreeBranchState(age, energy, freeEnergy, leavesStates, branch3DState);
    }

    /**
     * Create specific branch with
     * <ul>
     * <li>age=15</li>
     * <li>energy=10</li>
     * <li>freeEnergy=3</li>
     * <li>nb leaves=15</li>
     * </ul>
     * All leaves are made through {@link #createSpecificTreeLeafState()} method
     * 
     * @return
     */
    public static TreeBranchState createSpecificTreeBranchState() {
        int age = 15;
        BigDecimal energy = new BigDecimal(10);
        BigDecimal freeEnergy = new BigDecimal(3);
        int nbLeaves = 15;
        List<TreeLeafState> leavesStates = new ArrayList<TreeLeafState>(nbLeaves);
        for (int i = 0; i < nbLeaves; i++) {
            leavesStates.add(createSpecificTreeLeafState());
        }
        TreeBranch3DState branch3DState = DisplayDataCreatorForTests.createRandomTreeBranch3DState();
        return new TreeBranchState(age, energy, freeEnergy, leavesStates, branch3DState);
    }

    public static TreeTrunkState createRandomTreeTrunkState() {
        int age = Randomizer.randomBetween(0, 100);
        float radius = (float) Math.random();
        float height = (float) Math.random();
        TreeTrunk3DState trunk3DState = DisplayDataCreatorForTests.createRandomTreeTrunk3DState();
        return new TreeTrunkState(age, radius, height, trunk3DState);
    }

    /**
     * Create a trunk state with
     * <ul>
     * <li>age=15</li>
     * <li>radius=0.5</li>
     * <li>height=4</li>
     * </ul>
     * 
     * @return
     */
    public static TreeTrunkState createSpecificTreeTrunkState() {
        int age = 15;
        float radius = (float) 0.5;
        float height = (float) 4;
        TreeTrunk3DState trunk3DState = DisplayDataCreatorForTests.createRandomTreeTrunk3DState();
        return new TreeTrunkState(age, radius, height, trunk3DState);
    }

    public static TreeLeafState createRandomTreeLeafState() {
        BigDecimal efficiency = UtilDataCreatorForTests.createRandomBigDecimal();
        TreeLeaf3DState leafd3DState = DisplayDataCreatorForTests.createRandomTreeLeaf3DState();
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        return new TreeLeafState(age, energy, freeEnergy, efficiency, leafd3DState);
    }

    /**
     * Create a leaf state with
     * <ul>
     * <li>efficiency=80%</li>
     * <li>age=15</li>
     * <li>energy=10</li>
     * <li>freeEnergy=3</li>
     * </ul>
     * 
     * The leaf3D is created through the {@link DisplayDataCreatorForTests#createSpecificTreeLeaf3DState()} method.
     * 
     * @return
     */
    public static TreeLeafState createSpecificTreeLeafState() {
        BigDecimal efficiency = PercentHelper.getDecimalValue(80);
        int age = 15;
        BigDecimal energy = new BigDecimal(10);
        BigDecimal freeEnergy = new BigDecimal(3);
        TreeLeaf3DState leafd3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        return new TreeLeafState(age, energy, freeEnergy, efficiency, leafd3DState);
    }

}
