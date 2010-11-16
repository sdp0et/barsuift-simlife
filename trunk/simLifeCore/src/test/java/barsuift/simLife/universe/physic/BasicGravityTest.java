package barsuift.simLife.universe.physic;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.tree.MockTreeLeaf;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.universe.BasicUniverse;
import barsuift.simLife.universe.UniverseState;
import barsuift.simLife.universe.UniverseStateFactory;


public class BasicGravityTest extends TestCase {

    private BasicUniverse universe;

    private GravityState gravityState;

    protected void setUp() throws Exception {
        super.setUp();
        UniverseStateFactory universeStateFactory = new UniverseStateFactory();
        UniverseState universeState = universeStateFactory.createRandomUniverseState();
        universe = new BasicUniverse(universeState);

        GravityStateFactory gravityStateFactory = new GravityStateFactory();
        gravityState = gravityStateFactory.createGravityState();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        universe = null;
        gravityState = null;
    }

    public void testAddFallingLeaf() {
        Set<TreeLeafState> fallingLeaves = new HashSet<TreeLeafState>();
        fallingLeaves.add(CoreDataCreatorForTests.createRandomTreeLeafState());
        fallingLeaves.add(CoreDataCreatorForTests.createRandomTreeLeafState());
        gravityState.setFallingLeaves(fallingLeaves);

        BasicGravity gravity = new BasicGravity(gravityState, universe);
        assertEquals(2, gravity.getFallingLeaves().size());
        assertEquals(2, gravity.getGravity3D().getGroup().numChildren());
        for (TreeLeaf treeLeaf : gravity.getFallingLeaves()) {
            // leaf3D and gravity are subscribers
            assertEquals(2, treeLeaf.countSubscribers());
            treeLeaf.deleteSubscriber(gravity);
            // assert the gravity is really one of the subscribers
            assertEquals(1, treeLeaf.countSubscribers());
        }

        MockTreeLeaf treeLeaf = new MockTreeLeaf();
        gravity.addFallingLeaf(treeLeaf);
        assertEquals(3, gravity.getFallingLeaves().size());
        assertEquals(3, gravity.getGravity3D().getGroup().numChildren());
        // gravity is subscriber
        assertEquals(1, treeLeaf.countSubscribers());
        treeLeaf.deleteSubscriber(gravity);
        // assert the gravity is really one of the subscribers
        assertEquals(0, treeLeaf.countSubscribers());
    }

    public void testUpdate() {
        // test with wrong arg
        // test with good arg
    }

    public void testGetState() {
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
