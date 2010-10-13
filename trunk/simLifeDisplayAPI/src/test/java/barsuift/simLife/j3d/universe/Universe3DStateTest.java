package barsuift.simLife.j3d.universe;

import barsuift.simLife.JaxbTestCase;
import barsuift.simLife.j3d.DisplayDataCreatorForTests;


public class Universe3DStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.j3d.universe";
    }

    public void testJaxb() throws Exception {
        Universe3DState univ3DState = DisplayDataCreatorForTests.createRandomUniverse3DState();
        write(univ3DState);
        Universe3DState univ3DState2 = (Universe3DState) read();
        assertEquals(univ3DState, univ3DState2);
    }

}
