package barsuift.simLife.process;

import junit.framework.TestCase;
import barsuift.simLife.InitException;
import barsuift.simLife.j3d.universe.MockUniverse3D;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.universe.MockUniverse;


public class BasicMainSynchronizerTest extends TestCase {

    private BasicMainSynchronizer synchro;

    private MainSynchronizerState state;

    private MockInstrumentedSynchronizerCore synchronizerCore;

    private MockInstrumentedSynchronizer3D synchronizer3D;

    protected void setUp() throws Exception {
        super.setUp();
        state = new MainSynchronizerState();
        MockUniverse mockUniverse = new MockUniverse();
        synchronizerCore = new MockInstrumentedSynchronizerCore(new SynchronizerCoreState(Speed.VERY_FAST));
        mockUniverse.setSynchronizer(synchronizerCore);
        synchronizer3D = new MockInstrumentedSynchronizer3D(new Synchronizer3DState());
        synchronizer3D.setStepSize(Speed.VERY_FAST.getSpeed());
        ((MockUniverse3D) mockUniverse.getUniverse3D()).setSynchronizer(synchronizer3D);
        synchro = new BasicMainSynchronizer(state, mockUniverse);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        synchro = null;
        synchronizerCore = null;
        synchronizer3D = null;
    }

    public void testSetSpeed() throws InitException {
        assertEquals(Speed.VERY_FAST, synchro.getSpeed());
        assertEquals(Speed.VERY_FAST, synchronizerCore.getSpeed());
        assertEquals(Speed.VERY_FAST.getSpeed(), synchronizer3D.getStepSize());
        synchro.setSpeed(Speed.FAST);
        assertEquals(Speed.FAST, synchro.getSpeed());
        assertEquals(Speed.FAST, synchronizerCore.getSpeed());
        assertEquals(Speed.FAST.getSpeed(), synchronizer3D.getStepSize());
        synchro.setSpeed(Speed.NORMAL);
        assertEquals(Speed.NORMAL, synchro.getSpeed());
        assertEquals(Speed.NORMAL, synchronizerCore.getSpeed());
        assertEquals(Speed.NORMAL.getSpeed(), synchronizer3D.getStepSize());
    }

    public void testOneStep() throws InterruptedException {
        synchro.oneStep();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(1, synchronizerCore.getNbStartCalled());
        assertEquals(1, synchronizerCore.getNbStopCalled());
        assertEquals(1, synchronizer3D.getNbStartCalled());
        assertEquals(1, synchronizer3D.getNbStopCalled());
    }

    public void testStart() throws InterruptedException {
        assertFalse(synchro.isRunning());

        synchro.start();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertTrue(synchro.isRunning());
        assertEquals(1, synchronizerCore.getNbStartCalled());
        assertEquals(1, synchronizer3D.getNbStartCalled());

        synchro.stop();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(1, synchronizerCore.getNbStopCalled());
        assertEquals(1, synchronizer3D.getNbStopCalled());
    }

    public void testPublisher() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(synchro);

        synchro.start();
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.stop();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        synchro.oneStep();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
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
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
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
