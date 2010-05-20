package barsuift.simLife.time;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.JaxbTestCase;


public class TimeCounterStateTest extends JaxbTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Override
    protected String getPackage() {
        return "barsuift.simLife.time";
    }

    public void testJaxb() throws Exception {
        TimeCounterState timeCounterState = CoreDataCreatorForTests.createRandomTimeCounterState();
        write(timeCounterState);
        TimeCounterState timeCounterState2 = (TimeCounterState) read();
        assertEquals(timeCounterState, timeCounterState2);
    }

}
