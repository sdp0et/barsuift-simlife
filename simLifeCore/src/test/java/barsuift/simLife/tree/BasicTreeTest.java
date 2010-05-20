package barsuift.simLife.tree;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.Percent;
import barsuift.simLife.environment.MockSun;
import barsuift.simLife.universe.MockUniverse;

public class BasicTreeTest extends TestCase {

    private MockUniverse universe;

    private TreeState treeState;

    private BasicTree tree;

    protected void setUp() throws Exception {
        super.setUp();
        universe = new MockUniverse();
        treeState = CoreDataCreatorForTests.createSpecificTreeState();
        tree = new BasicTree(universe, treeState);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        universe = null;
        treeState = null;
        tree = null;
    }

    public void testBasicTree() {
        assertEquals(treeState.getBranches().size(), tree.getNbBranches());
        try {
            new BasicTree(null, treeState);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected excpetion
        }
        try {
            new BasicTree(universe, null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected excpetion
        }
    }

    public void testGetState() {
        assertEquals(treeState, tree.getState());
    }


    public void testSpendTime() {
        ((MockSun) universe.getEnvironment().getSun()).setLuminosity(new Percent(70));
        tree.spendTime();
        assertEquals(16, tree.getAge());
        assertEquals(40, tree.getNbBranches());
        // as computed in BasicTreeBranchTest#testSpendTime
        // -> freeEnergy in branches=50.7792
        // collected energy from branches = 40 * 50.7792 + 10 = 2041.168
        assertEquals(2041.168, tree.getEnergy().doubleValue(), 0.0001);
    }

}
