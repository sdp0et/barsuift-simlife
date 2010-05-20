package barsuift.simLife.universe;

import java.util.Set;

import barsuift.simLife.Randomizer;
import barsuift.simLife.tree.TreeState;
import junit.framework.TestCase;


public class UniverseStateFactoryTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreateRandomUniverseState() {
        UniverseStateFactory factory = new UniverseStateFactory();
        int nbTrees = Randomizer.randomBetween(1, 4);
        UniverseState universeState = factory.createRandomUniverseState(nbTrees);
        assertNotNull(universeState.getEnvironment());

        Set<TreeState> trees = universeState.getTrees();
        for (TreeState treeState : trees) {
            int nbBranches = treeState.getBranches().size();
            assertTrue(nbBranches >= 30);
            assertTrue(nbBranches <= 50);
            float height = treeState.getHeight();
            assertTrue(height >= 3);
            assertTrue(height <= 5);
        }

        Long id1 = universeState.getId();
        assertNotNull(id1);
        assertTrue(id1.longValue() > 0);
        universeState = factory.createRandomUniverseState(nbTrees);
        Long id2 = universeState.getId();
        assertEquals(id1.longValue() + 1, id2.longValue());
    }

}
