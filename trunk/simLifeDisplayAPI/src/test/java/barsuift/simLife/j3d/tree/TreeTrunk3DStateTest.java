package barsuift.simLife.j3d.tree;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class TreeTrunk3DStateTest extends JaxbTestCase {

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
        TreeTrunk3DState trunk3DState = DisplayDataCreatorForTests.createRandomTreeTrunk3DState();
        write(trunk3DState);
        TreeTrunk3DState trunk3DState2 = (TreeTrunk3DState) read();
        assertEquals(trunk3DState, trunk3DState2);
    }

}
