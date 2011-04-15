package barsuift.simLife.j3d.environment;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class Sky3DStateTest extends JaxbTestCase {

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
        Sky3DState sky3DState = DisplayDataCreatorForTests.createRandomSky3DState();
        write(sky3DState);
        Sky3DState sky3DState2 = (Sky3DState) read();
        assertEquals(sky3DState, sky3DState2);
    }

}
