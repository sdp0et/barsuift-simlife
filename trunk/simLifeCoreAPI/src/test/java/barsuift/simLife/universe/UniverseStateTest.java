package barsuift.simLife.universe;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;

public class UniverseStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.universe";
    }

    public void testJaxb() throws Exception {
        UniverseState universeState = CoreDataCreatorForTests.createRandomUniverseState();
        write(universeState);
        UniverseState universeState2 = (UniverseState) read();
        assertEquals(universeState, universeState2);
    }

}
