package barsuift.simLife.process;

import junit.framework.TestCase;
import barsuift.simLife.InitException;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.universe.MockUniverse;


public class BasicMainSynchronizerTest extends TestCase {

    private BasicMainSynchronizer synchro;

    private MainSynchronizerState state;

    private MockInstrumentedSynchronizerCore synchronizerCore;

    protected void setUp() throws Exception {
        super.setUp();
        state = new MainSynchronizerState();
        MockUniverse mockUniverse = new MockUniverse();
        synchronizerCore = new MockInstrumentedSynchronizerCore(new SynchronizerCoreState(Speed.VERY_FAST));
        mockUniverse.setSynchronizer(synchronizerCore);
        synchro = new BasicMainSynchronizer(state, mockUniverse);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        synchro = null;
        synchronizerCore = null;
    }

    public void testSetSpeed() throws InitException {
        assertEquals(Speed.VERY_FAST, synchro.getSpeed());
        assertEquals(Speed.VERY_FAST, synchronizerCore.getSpeed());
        synchro.setSpeed(Speed.FAST);
        assertEquals(Speed.FAST, synchro.getSpeed());
        assertEquals(Speed.FAST, synchronizerCore.getSpeed());
        synchro.setSpeed(Speed.NORMAL);
        assertEquals(Speed.NORMAL, synchro.getSpeed());
        assertEquals(Speed.NORMAL, synchronizerCore.getSpeed());
    }

    public void testOneStep() throws InterruptedException {
        synchro.oneStep();
        Thread.sleep(BasicSynchronizerCore.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(1, synchronizerCore.getNbStartCalled());
        assertEquals(1, synchronizerCore.getNbStopCalled());
    }

    public void testStart() throws InterruptedException {
        assertFalse(synchro.isRunning());

        synchro.start();
        Thread.sleep(BasicSynchronizerCore.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertTrue(synchro.isRunning());

        synchro.stop();
        Thread.sleep(BasicSynchronizerCore.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
    }

    public void testPublisher() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(synchro);

        synchro.start();
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        Thread.sleep(BasicSynchronizerCore.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.stop();
        Thread.sleep(BasicSynchronizerCore.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        synchro.oneStep();
        Thread.sleep(BasicSynchronizerCore.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertEquals(2, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));
    }


    public void testIllegalStateException() throws InterruptedException {
        try {
            synchro.stop();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        synchro.start();
        Thread.sleep(BasicSynchronizerCore.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        try {
            synchro.start();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        try {
            synchro.oneStep();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());
    }

}
