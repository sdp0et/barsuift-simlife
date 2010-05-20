package barsuift.simLife.tree;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;



public class TreeTrunkStateTest extends JaxbTestCase {

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
        TreeTrunkState trunkState = CoreDataCreatorForTests.createRandomTreeTrunkState();
        write(trunkState);
        TreeTrunkState trunkState2 = (TreeTrunkState) read();
        assertEquals(trunkState, trunkState2);
    }

}
