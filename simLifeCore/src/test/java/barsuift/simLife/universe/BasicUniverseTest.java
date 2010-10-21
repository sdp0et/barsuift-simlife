package barsuift.simLife.universe;

import junit.framework.TestCase;
import barsuift.simLife.InitException;


public class BasicUniverseTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetState() throws InitException {
        UniverseStateFactory factory = new UniverseStateFactory();
        UniverseState state = factory.createRandomUniverseState();
        BasicUniverse universe = new BasicUniverse(state);
        assertEquals(state, universe.getState());
        assertSame(state, universe.getState());
        assertFalse(universe.getState().isFpsShowing());
        universe.setFpsShowing(true);
        assertEquals(state, universe.getState());
        assertSame(state, universe.getState());
        assertTrue(universe.getState().isFpsShowing());
    }

}
