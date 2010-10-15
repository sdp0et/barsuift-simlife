package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;


public class SynchronizerTest extends TestCase {

    private Synchronizer synchro;

    private SynchronizerState state;

    protected void setUp() throws Exception {
        super.setUp();
        List<SynchronizedRunnableState> runnables = new ArrayList<SynchronizedRunnableState>();
        SynchronizedRunnableState mockRunnableState = new SynchronizedRunnableState(MockSynchronizedRunnable.class);
        runnables.add(mockRunnableState);

        state = new SynchronizerState(1, runnables);
        synchro = new Synchronizer(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        synchro = null;
    }

    public void testSetSpeed() throws SynchronizerException {
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
