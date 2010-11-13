package barsuift.simLife.universe.physic;

import junit.framework.TestCase;
import barsuift.simLife.tree.MockTreeLeaf;
import barsuift.simLife.universe.BasicUniverse;
import barsuift.simLife.universe.UniverseState;
import barsuift.simLife.universe.UniverseStateFactory;


public class BasicGravityTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetState() {
        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createRandomUniverseState();
        BasicUniverse universe = new BasicUniverse(universeState);

        GravityStateFactory gravityStateFactory = new GravityStateFactory();
        GravityState gravityState = gravityStateFactory.createGravityState();
        BasicGravity gravity = new BasicGravity(gravityState, universe);

        assertEquals(gravityState, gravity.getState());
        assertSame(gravityState, gravity.getState());
        assertEquals(0, gravity.getState().getFallingLeaves().size());
        gravity.addFallingLeaf(new MockTreeLeaf());
        assertEquals(gravityState, gravity.getState());
        assertSame(gravityState, gravity.getState());
        assertEquals(1, gravity.getState().getFallingLeaves().size());
    }

}
