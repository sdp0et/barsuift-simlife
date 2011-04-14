package barsuift.simLife.j3d.universe.physic;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class Physics3DStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.j3d.universe.physic";
    }

    public void testJaxb() throws Exception {
        Physics3DState physics3DState = DisplayDataCreatorForTests.createRandomPhysics3DState();
        write(physics3DState);
        Physics3DState physics3DState2 = (Physics3DState) read();
        assertEquals(physics3DState, physics3DState2);
    }

}
