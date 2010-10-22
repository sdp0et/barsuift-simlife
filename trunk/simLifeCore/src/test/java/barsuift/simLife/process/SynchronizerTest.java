package barsuift.simLife.process;

import junit.framework.TestCase;
import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.InitException;
import barsuift.simLife.time.BasicTimeController;
import barsuift.simLife.time.TimeController;
import barsuift.simLife.time.TimeControllerState;
import barsuift.simLife.universe.MockUniverse;


public class SynchronizerTest extends TestCase {

    private Synchronizer synchro;

    private SynchronizerState state;

    protected void setUp() throws Exception {
        super.setUp();
        TimeControllerState timeControllerState = CoreDataCreatorForTests.createRandomTimeControllerState();
        TimeController timeController = new BasicTimeController(new MockUniverse(), timeControllerState);

        state = new SynchronizerState(1);
        synchro = new Synchronizer(state, timeController);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        synchro = null;
    }

    public void testSetSpeed() throws InitException {
        assertEquals(1, synchro.getSpeed());
        synchro.setSpeed(20);
        assertEquals(20, synchro.getSpeed());
        synchro.setSpeed(1);
        assertEquals(1, synchro.getSpeed());
    }

    public void testOneStep() throws InterruptedException {
        synchro.oneStep();
        assertTrue(synchro.isRunning());

        Thread.sleep(100);
        assertFalse(synchro.isRunning());
    }

    public void testStart() throws InterruptedException {
        synchro.start();
        Thread.sleep(100);
        assertTrue(synchro.isRunning());

        synchro.stop();
        Thread.sleep(100);
        assertFalse(synchro.isRunning());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());
        assertEquals(1, synchro.getState().getSpeed());
        synchro.setSpeed(10);
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());
        assertEquals(10, synchro.getState().getSpeed());
    }

}
