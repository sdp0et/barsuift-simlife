package barsuift.simLife.tree;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;



public class TreeBranchStateTest extends JaxbTestCase {

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
        TreeBranchState branchState = CoreDataCreatorForTests.createRandomTreeBranchState();
        write(branchState);
        TreeBranchState branchState2 = (TreeBranchState) read();
        assertEquals(branchState, branchState2);
    }

}
