package barsuift.simLife.j3d.environment;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class Environment3DStateTest extends JaxbTestCase {

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
        Environment3DState env3DState = DisplayDataCreatorForTests.createRandomEnvironment3DState();
        write(env3DState);
        Environment3DState env3DState2 = (Environment3DState) read();
        assertEquals(env3DState, env3DState2);
    }

}
