package barsuift.simLife.environment;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;


public class SunStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.environment";
    }

    public void testJaxb() throws Exception {
        SunState sunState = CoreDataCreatorForTests.createRandomSunState();
        write(sunState);
        SunState sunState2 = (SunState) read();
        assertEquals(sunState, sunState2);
    }

}
