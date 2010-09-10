package barsuift.simLife.universe;

import junit.framework.TestCase;


public class BasicUniverseTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetState() {
        UniverseStateFactory factory = new UniverseStateFactory();
        UniverseState state = factory.createRandomUniverseState();
        BasicUniverse universe = new BasicUniverse(state);
        assertEquals(state, universe.getState());
        assertSame(state, universe.getState());
        assertEquals(0, universe.getState().getAge());
        universe.spendTime();
        assertEquals(state, universe.getState());
        assertSame(state, universe.getState());
        assertEquals(1, universe.getState().getAge());
    }

}
