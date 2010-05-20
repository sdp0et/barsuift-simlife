package barsuift.simLife;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import barsuift.simLife.environment.EnvironmentState;
import barsuift.simLife.environment.SunState;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;
import barsuift.simLife.j3d.tree.Tree3DState;
import barsuift.simLife.j3d.tree.TreeBranch3DState;
import barsuift.simLife.j3d.tree.TreeBranchPart3DState;
import barsuift.simLife.j3d.tree.TreeLeaf3DState;
import barsuift.simLife.j3d.tree.TreeTrunk3DState;
import barsuift.simLife.time.TimeCounterState;
import barsuift.simLife.tree.TreeBranchPartState;
import barsuift.simLife.tree.TreeBranchState;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;
import barsuift.simLife.tree.TreeTrunkState;
import barsuift.simLife.universe.UniverseState;

public final class CoreDataCreatorForTests {

    private CoreDataCreatorForTests() {
        // private constructor to enforce static access
    }

    public static Long createRandomId() {
        return new Long(Randomizer.randomBetween(1, 50));
    }

    public static UniverseState createRandomUniverseState() {
        int age = Randomizer.randomBetween(0, 100);
        Set<TreeState> trees = new HashSet<TreeState>();
        trees.add(createRandomTreeState());
        trees.add(createRandomTreeState());
        trees.add(createRandomTreeState());
        int nbFallenLeaves = Randomizer.randomBetween(0, 40);
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(nbFallenLeaves);
        for (int i = 0; i < nbFallenLeaves; i++) {
            fallenLeaves.add(createRandomTreeLeafState());
        }
        return new UniverseState(createRandomId(), age, trees, fallenLeaves, createRandomEnvironmentState(),
                createRandomTimeCounterState());
    }

    /**
     * Create a specific universe state with
     * <ul>
     * <li>age=15</li>
     * <li>nb trees=3 (made through the {@link #createSpecificTreeState()} method)</li>
     * <li>nb fallen leaves=20 (made through the {@link #createSpecificTreeLeafState()} method)</li>
     * </ul>
     * The environment is made through the {@link #createSpecificEnvironmentState()} method. The tiem counter is made
     * through the {@link #createSpecificTimeCounterState()} method.
     * 
     * @return
     */
    public static UniverseState createSpecificUniverseState() {
        int age = 15;
        Set<TreeState> trees = new HashSet<TreeState>();
        trees.add(createSpecificTreeState());
        trees.add(createSpecificTreeState());
        trees.add(createSpecificTreeState());
        int nbFallenLeaves = 20;
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(nbFallenLeaves);
        for (int i = 0; i < nbFallenLeaves; i++) {
            fallenLeaves.add(createSpecificTreeLeafState());
        }
        return new UniverseState(createRandomId(), age, trees, fallenLeaves, createSpecificEnvironmentState(),
                createSpecificTimeCounterState());
    }

    public static EnvironmentState createRandomEnvironmentState() {
        return new EnvironmentState(createRandomSunState());
    }

    /**
     * Create a specific enviroment state. The sun state is made through the {@link #createSpecificSunState()} method.
     * 
     * @return
     */
    public static EnvironmentState createSpecificEnvironmentState() {
        return new EnvironmentState(createSpecificSunState());
    }

    public static SunState createRandomSunState() {
        PercentState luminosity = UtilDataCreatorForTests.createPercentState();
        PercentState riseAngle = UtilDataCreatorForTests.createPercentState();
        PercentState zenithAngle = UtilDataCreatorForTests.createPercentState();
        return new SunState(luminosity, riseAngle, zenithAngle);
    }

    /**
     * Create specific sun state with
     * <ul>
     * <li>luminosity=70%</li>
     * <li>rise angle=25%</li>
     * <li>zenith angle = 50%</li>
     * </ul>
     * 
     * @return
     */
    public static SunState createSpecificSunState() {
        PercentState luminosity = new PercentState(PercentHelper.getDecimalValue(70));
        PercentState riseAngle = new PercentState(PercentHelper.getDecimalValue(25));
        PercentState zenithAngle = new PercentState(PercentHelper.getDecimalValue(50));
        return new SunState(luminosity, riseAngle, zenithAngle);
    }

    public static TimeCounterState createRandomTimeCounterState() {
        return new TimeCounterState(Randomizer.randomBetween(0, 60));
    }

    /**
     * Create specific time counter state with seconds = 22
     * 
     * @return
     */
    public static TimeCounterState createSpecificTimeCounterState() {
        return new TimeCounterState(22);
    }

    public static TreeState createRandomTreeState() {
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        int nbBranches = Randomizer.randomBetween(30, 50);
        List<TreeBranchState> branches = new ArrayList<TreeBranchState>(nbBranches);
        for (int i = 0; i < nbBranches; i++) {
            branches.add(createRandomTreeBranchState());
        }
        TreeTrunkState trunkState = createRandomTreeTrunkState();
        float height = (float) Math.random();
        Tree3DState tree3dState = DisplayDataCreatorForTests.createRandomTree3DState();
        return new TreeState(createRandomId(), age, energy, branches, trunkState, height, tree3dState);
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
        TreeTrunkState trunkState = createSpecificTreeTrunkState();
        float height = (float) 4;
        Tree3DState tree3dState = DisplayDataCreatorForTests.createRandomTree3DState();
        return new TreeState(createRandomId(), age, energy, branches, trunkState, height, tree3dState);
    }

    public static TreeBranchState createRandomTreeBranchState() {
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        int nbParts = Randomizer.randomBetween(2, 4);
        List<TreeBranchPartState> branchPartStates = new ArrayList<TreeBranchPartState>(nbParts);
        for (int i = 0; i < nbParts; i++) {
            branchPartStates.add(createRandomTreeBranchPartState());
        }
        TreeBranch3DState branch3DState = DisplayDataCreatorForTests.createRandomTreeBranch3DState();
        return new TreeBranchState(createRandomId(), age, energy, freeEnergy, branchPartStates, branch3DState);
    }

    /**
     * Create specific branch with
     * <ul>
     * <li>age=15</li>
     * <li>energy=10</li>
     * <li>freeEnergy=3</li>
     * <li>nb parts=3</li>
     * </ul>
     * All parts are made through {@link #createSpecificTreeBranchPartState()} method
     * 
     * @return
     */
    public static TreeBranchState createSpecificTreeBranchState() {
        int age = 15;
        BigDecimal energy = new BigDecimal(10);
        BigDecimal freeEnergy = new BigDecimal(3);
        int nbParts = 3;
        List<TreeBranchPartState> branchPartStates = new ArrayList<TreeBranchPartState>(nbParts);
        for (int i = 0; i < nbParts; i++) {
            branchPartStates.add(createSpecificTreeBranchPartState());
        }
        TreeBranch3DState branch3DState = DisplayDataCreatorForTests.createRandomTreeBranch3DState();
        return new TreeBranchState(createRandomId(), age, energy, freeEnergy, branchPartStates, branch3DState);
    }

    public static TreeBranchPartState createRandomTreeBranchPartState() {
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        int nbLeaves = Randomizer.randomBetween(1, 6);
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(nbLeaves);
        for (int i = 0; i < nbLeaves; i++) {
            leaveStates.add(createRandomTreeLeafState());
        }
        TreeBranchPart3DState branchPart3DState = DisplayDataCreatorForTests.createRandomTreeBranchPart3DState();
        return new TreeBranchPartState(createRandomId(), age, energy, freeEnergy, leaveStates, branchPart3DState);
    }

    /**
     * Create specific branch part with
     * <ul>
     * <li>age=15</li>
     * <li>energy=10</li>
     * <li>freeEnergy=3</li>
     * <li>nb leaves=5</li>
     * </ul>
     * All leaves are made through {@link #createSpecificTreeLeafState()} method
     * 
     * @return
     */
    public static TreeBranchPartState createSpecificTreeBranchPartState() {
        int age = 15;
        BigDecimal energy = new BigDecimal(10);
        BigDecimal freeEnergy = new BigDecimal(3);
        int nbLeaves = 5;
        List<TreeLeafState> leaveStates = new ArrayList<TreeLeafState>(nbLeaves);
        for (int i = 0; i < nbLeaves; i++) {
            leaveStates.add(createSpecificTreeLeafState());
        }
        TreeBranchPart3DState branchPart3DState = DisplayDataCreatorForTests.createRandomTreeBranchPart3DState();
        return new TreeBranchPartState(createRandomId(), age, energy, freeEnergy, leaveStates, branchPart3DState);
    }

    public static TreeTrunkState createRandomTreeTrunkState() {
        int age = Randomizer.randomBetween(0, 100);
        float radius = (float) Math.random();
        float height = (float) Math.random();
        TreeTrunk3DState trunk3DState = DisplayDataCreatorForTests.createRandomTreeTrunk3DState();
        return new TreeTrunkState(createRandomId(), age, radius, height, trunk3DState);
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
        return new TreeTrunkState(createRandomId(), age, radius, height, trunk3DState);
    }

    public static TreeLeafState createRandomTreeLeafState() {
        PercentState efficiency = UtilDataCreatorForTests.createPercentState();
        TreeLeaf3DState leafd3DState = DisplayDataCreatorForTests.createRandomTreeLeaf3DState();
        int age = Randomizer.randomBetween(0, 100);
        BigDecimal energy = new BigDecimal(Randomizer.randomBetween(0, 100));
        BigDecimal freeEnergy = new BigDecimal(Randomizer.randomBetween(0, 50));
        return new TreeLeafState(createRandomId(), age, energy, freeEnergy, efficiency, leafd3DState);
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
        PercentState efficiency = new PercentState(PercentHelper.getDecimalValue(80));
        int age = 15;
        BigDecimal energy = new BigDecimal(10);
        BigDecimal freeEnergy = new BigDecimal(3);
        TreeLeaf3DState leafd3DState = DisplayDataCreatorForTests.createSpecificTreeLeaf3DState();
        return new TreeLeafState(createRandomId(), age, energy, freeEnergy, efficiency, leafd3DState);
    }

}
