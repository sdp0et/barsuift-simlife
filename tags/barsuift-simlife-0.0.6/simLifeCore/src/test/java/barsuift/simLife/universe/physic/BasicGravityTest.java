package barsuift.simLife.universe.physic;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.j3d.MobileEvent;
import barsuift.simLife.j3d.tree.TreeLeaf3D;
import barsuift.simLife.tree.LeafEvent;
import barsuift.simLife.tree.MockTreeLeaf;
import barsuift.simLife.tree.TreeLeaf;
import barsuift.simLife.tree.TreeLeafState;
import barsuift.simLife.universe.BasicUniverse;
import barsuift.simLife.universe.UniverseState;


public class BasicGravityTest extends TestCase {

    private BasicUniverse universe;

    private GravityState gravityState;

    protected void setUp() throws Exception {
        super.setUp();
        UniverseState universeState = CoreDataCreatorForTests.createRandomUniverseState();
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
            TreeLeaf3D treeLeaf3D = treeLeaf.getTreeLeaf3D();

            // leaf3D and gravity are subscribers of the leaf
            assertEquals(2, treeLeaf.countSubscribers());

            // leaf is subscriber of the leaf3D
            assertEquals(1, treeLeaf3D.countSubscribers());

            // assert the gravity is really one of the subscribers of the leaf
            treeLeaf.deleteSubscriber(gravity);
            assertEquals(1, treeLeaf.countSubscribers());
            // assert the leaf3D is really one of the subscribers of the leaf
            treeLeaf.deleteSubscriber(treeLeaf3D);
            assertEquals(0, treeLeaf.countSubscribers());

            // assert the leaf is really one of the subscribers of the leaf3D
            treeLeaf3D.deleteSubscriber(treeLeaf);
            assertEquals(0, treeLeaf3D.countSubscribers());

        }

        MockTreeLeaf treeLeaf = new MockTreeLeaf();
        gravity.addFallingLeaf(treeLeaf);
        assertEquals(3, gravity.getFallingLeaves().size());
        assertEquals(3, gravity.getGravity3D().getGroup().numChildren());
        // gravity is subscriber of the leaf
        assertEquals(1, treeLeaf.countSubscribers());
        treeLeaf.deleteSubscriber(gravity);
        // assert the gravity is really one of the subscribers of the leaf
        assertEquals(0, treeLeaf.countSubscribers());
    }

    public void testUpdate() {
        BasicGravity gravity = new BasicGravity(gravityState, universe);
        MockTreeLeaf treeLeaf = new MockTreeLeaf();

        gravity.addFallingLeaf(treeLeaf);
        assertTrue(gravity.getFallingLeaves().contains(treeLeaf));
        assertFalse(universe.getFallenLeaves().contains(treeLeaf));
        assertEquals(1, gravity.getGravity3D().getGroup().numChildren());

        // test with wrong argument
        gravity.update(treeLeaf, MobileEvent.FALLING);
        assertTrue(gravity.getFallingLeaves().contains(treeLeaf));
        assertFalse(universe.getFallenLeaves().contains(treeLeaf));
        assertEquals(1, gravity.getGravity3D().getGroup().numChildren());

        // test with wrong argument
        gravity.update(treeLeaf, null);
        assertTrue(gravity.getFallingLeaves().contains(treeLeaf));
        assertFalse(universe.getFallenLeaves().contains(treeLeaf));
        assertEquals(1, gravity.getGravity3D().getGroup().numChildren());

        // test with wrong argument
        gravity.update(treeLeaf, LeafEvent.EFFICIENCY);
        assertTrue(gravity.getFallingLeaves().contains(treeLeaf));
        assertFalse(universe.getFallenLeaves().contains(treeLeaf));
        assertEquals(1, gravity.getGravity3D().getGroup().numChildren());

        // test with good argument
        gravity.update(treeLeaf, MobileEvent.FALLEN);
        assertFalse(gravity.getFallingLeaves().contains(treeLeaf));
        assertTrue(universe.getFallenLeaves().contains(treeLeaf));
        assertEquals(0, gravity.getGravity3D().getGroup().numChildren());
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
