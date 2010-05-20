package barsuift.simLife.universe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Point3d;

import barsuift.simLife.Randomizer;
import barsuift.simLife.environment.EnvironmentState;
import barsuift.simLife.environment.EnvironmentStateFactory;
import barsuift.simLife.time.TimeCounterState;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.tree.TreeState;
import barsuift.simLife.tree.TreeStateFactory;


public class UniverseStateFactory {

    private static long UNIVERSE_COUNT = 1;

    private static final Map<Long, Point3d> originPoints = new HashMap<Long, Point3d>();

    static {
        // TODO 999. improve this random placement code
        originPoints.put(new Long(0), new Point3d(0, 0, 0));
        originPoints.put(new Long(1), new Point3d(5, 0, 0));
        originPoints.put(new Long(2), new Point3d(2, 0, -4));
        originPoints.put(new Long(3), new Point3d(-4, 0, 2));
    }

    public UniverseState createRandomUniverseState(int nbTrees) {
        TreeStateFactory treeStateFactory = new TreeStateFactory();
        Set<TreeState> trees = new HashSet<TreeState>(nbTrees);
        for (int i = 0; i < nbTrees; i++) {
            int nbBranches = Randomizer.randomBetween(30, 50);
            float height = Randomizer.randomBetween(3, 5);
            Point3d translationVector = originPoints.get(new Long(i));
            trees.add(treeStateFactory.createRandomTreeState(translationVector, nbBranches, height));
        }
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(0);

        EnvironmentStateFactory envStateFactory = new EnvironmentStateFactory();
        EnvironmentState environment = envStateFactory.createEnvironmentState();

        return new UniverseState(UNIVERSE_COUNT++, 0, trees, fallenLeaves, environment, new TimeCounterState());
    }

    public UniverseState createEmptyUniverseState() {
        Set<TreeState> trees = new HashSet<TreeState>(0);
        Set<TreeLeafState> fallenLeaves = new HashSet<TreeLeafState>(0);
        EnvironmentStateFactory envStateFactory = new EnvironmentStateFactory();
        EnvironmentState environment = envStateFactory.createEnvironmentState();
        return new UniverseState(UNIVERSE_COUNT++, 0, trees, fallenLeaves, environment, new TimeCounterState());
    }

}
