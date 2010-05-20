package barsuift.simLife.environment;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;


public class EnvironmentStateTest extends JaxbTestCase {

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
        EnvironmentState envState = CoreDataCreatorForTests.createRandomEnvironmentState();
        write(envState);
        EnvironmentState envState2 = (EnvironmentState) read();
        assertEquals(envState, envState2);
    }

}
