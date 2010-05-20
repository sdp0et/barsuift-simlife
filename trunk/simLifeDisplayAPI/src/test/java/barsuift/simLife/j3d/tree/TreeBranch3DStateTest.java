package barsuift.simLife.j3d.tree;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class TreeBranch3DStateTest extends JaxbTestCase {

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
        TreeBranch3DState branch3DState = DisplayDataCreatorForTests.createRandomTreeBranch3DState();
        write(branch3DState);
        TreeBranch3DState branch3DState2 = (TreeBranch3DState) read();
        assertEquals(branch3DState, branch3DState2);
    }

}
