package barsuift.simLife.universe;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.tree.MockTree;


public class BasicUniverseTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetState() {
        UniverseState state = CoreDataCreatorForTests.createRandomUniverseState();
        BasicUniverse universe = new BasicUniverse(state);
        assertEquals(state, universe.getState());
        assertSame(state, universe.getState());
        int nbTrees = universe.getState().getTrees().size();
        universe.addTree(new MockTree());
        assertEquals(state, universe.getState());
        assertSame(state, universe.getState());
        assertEquals(nbTrees + 1, universe.getState().getTrees().size());
    }

}
