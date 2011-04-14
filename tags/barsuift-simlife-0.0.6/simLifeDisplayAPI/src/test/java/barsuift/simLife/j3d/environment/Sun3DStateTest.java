package barsuift.simLife.j3d.environment;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class Sun3DStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.j3d.environment";
    }

    public void testJaxb() throws Exception {
        Sun3DState sun3DState = DisplayDataCreatorForTests.createRandomSun3DState();
        write(sun3DState);
        Sun3DState sun3DState2 = (Sun3DState) read();
        assertEquals(sun3DState, sun3DState2);
    }

}
