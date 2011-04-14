package barsuift.simLife.j3d.universe.physic;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class Gravity3DStateTest extends JaxbTestCase {

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
        Gravity3DState gravity3DState = DisplayDataCreatorForTests.createRandomGravity3DState();
        write(gravity3DState);
        Gravity3DState gravity3DState2 = (Gravity3DState) read();
        assertEquals(gravity3DState, gravity3DState2);
    }

}
