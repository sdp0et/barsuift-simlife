package barsuift.simLife.j3d.tree;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class TreeBranchPart3DStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.j3d.tree";
    }

    public void testJaxb() throws Exception {
        TreeBranchPart3DState branchPart3DState = DisplayDataCreatorForTests.createRandomTreeBranchPart3DState();
        write(branchPart3DState);
        TreeBranchPart3DState branchPart3DState2 = (TreeBranchPart3DState) read();
        assertEquals(branchPart3DState, branchPart3DState2);
    }

}
