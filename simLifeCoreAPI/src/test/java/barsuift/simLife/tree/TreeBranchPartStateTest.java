package barsuift.simLife.tree;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;



public class TreeBranchPartStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.tree";
    }

    public void testJaxb() throws Exception {
        TreeBranchPartState branchPartState = CoreDataCreatorForTests.createRandomTreeBranchPartState();
        write(branchPartState);
        TreeBranchPartState branchPartState2 = (TreeBranchPartState) read();
        assertEquals(branchPartState, branchPartState2);
    }

}
