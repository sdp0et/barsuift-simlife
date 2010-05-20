package barsuift.simLife.tree;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;



public class TreeLeafStateTest extends JaxbTestCase {

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
        TreeLeafState leafState = CoreDataCreatorForTests.createRandomTreeLeafState();
        write(leafState);
        TreeLeafState leafState2 = (TreeLeafState) read();
        assertEquals(leafState, leafState2);
    }

}
