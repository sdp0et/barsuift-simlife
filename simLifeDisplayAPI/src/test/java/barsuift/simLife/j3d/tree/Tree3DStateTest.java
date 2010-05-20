package barsuift.simLife.j3d.tree;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class Tree3DStateTest extends JaxbTestCase {

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
        Tree3DState tree3DState = DisplayDataCreatorForTests.createRandomTree3DState();
        write(tree3DState);
        Tree3DState tree3DState2 = (Tree3DState) read();
        assertEquals(tree3DState, tree3DState2);
    }

}
