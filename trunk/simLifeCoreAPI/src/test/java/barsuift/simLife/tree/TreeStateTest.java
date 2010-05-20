package barsuift.simLife.tree;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;



public class TreeStateTest extends JaxbTestCase {

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
        TreeState treeState = CoreDataCreatorForTests.createRandomTreeState();
        write(treeState);
        TreeState treeState2 = (TreeState) read();
        assertEquals(treeState, treeState2);
    }

}
