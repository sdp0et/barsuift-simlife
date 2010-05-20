package barsuift.simLife.j3d.tree;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class TreeLeaf3DStateTest extends JaxbTestCase {

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
        TreeLeaf3DState leaf3DState = DisplayDataCreatorForTests.createRandomTreeLeaf3DState();
        write(leaf3DState);
        TreeLeaf3DState leaf3DState2 = (TreeLeaf3DState) read();
        assertEquals(leaf3DState, leaf3DState2);
    }

}
