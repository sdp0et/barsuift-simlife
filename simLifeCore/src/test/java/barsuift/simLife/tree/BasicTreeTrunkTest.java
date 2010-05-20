package barsuift.simLife.tree;

import junit.framework.TestCase;
import barsuift.simLife.universe.MockUniverse;


public class BasicTreeTrunkTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testBasicTreeTrunk() {
        try {
            new BasicTreeTrunk(new MockUniverse(), null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
        try {
            new BasicTreeTrunk(null, new TreeTrunkState());
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testGetState() {
        TreeTrunkStateFactory factory = new TreeTrunkStateFactory();
        float radius = 4.2f;
        float height = 6.8f;
        TreeTrunkState trunkState = factory.createRandomTreeTrunkState(radius, height);
        BasicTreeTrunk treeTrunk = new BasicTreeTrunk(new MockUniverse(), trunkState);
        assertEquals(height, treeTrunk.getHeight());
        assertEquals(radius, treeTrunk.getRadius());
        assertEquals(trunkState, treeTrunk.getState());
    }

    public void testSpendTime() {
        TreeTrunkState trunkState = new TreeTrunkState();
        trunkState.setAge(12);
        BasicTreeTrunk treeTrunk = new BasicTreeTrunk(new MockUniverse(), trunkState);
        treeTrunk.spendTime();
        assertEquals(13, treeTrunk.getAge());
    }

}
