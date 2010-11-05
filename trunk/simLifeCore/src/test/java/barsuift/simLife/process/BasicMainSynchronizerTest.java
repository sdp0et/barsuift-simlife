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

    private MockSplitBoundedRunnable task3D;

    private MockSynchronizedRunnable taskCore;

    protected void setUp() throws Exception {
        super.setUp();
        Speed speed = Speed.VERY_FAST;
        setUpFromSpeed(speed);
    }

    private void setUpFromSpeed(Speed speed) {
        int stepSize = speed.getSpeed();

        MockUniverse mockUniverse = new MockUniverse();
        synchronizerCore = new MockInstrumentedSynchronizerCore(new SynchronizerCoreState(speed));
        mockUniverse.setSynchronizer(synchronizerCore);
        taskCore = new MockSynchronizedRunnable();
        synchronizerCore.schedule(taskCore);

        synchronizer3D = new MockInstrumentedSynchronizer3D(new Synchronizer3DState());
        synchronizer3D.setStepSize(stepSize);
        ((MockUniverse3D) mockUniverse.getUniverse3D()).setSynchronizer(synchronizer3D);
        task3D = new MockSplitBoundedRunnable(new SplitBoundedRunnableState(60, 0, stepSize));
        synchronizer3D.schedule(task3D);

        state = new MainSynchronizerState();
        synchro = new BasicMainSynchronizer(state, mockUniverse);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        synchro = null;
        synchronizerCore = null;
        synchronizer3D = null;
        task3D = null;
        taskCore = null;
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

    public void testOneStepNormal() throws InterruptedException {
        internalTestOneStep(Speed.NORMAL);
    }

    public void testOneStepFast() throws InterruptedException {
        internalTestOneStep(Speed.FAST);
    }

    public void testOneStepVeryFast() throws InterruptedException {
        internalTestOneStep(Speed.VERY_FAST);
    }

    private void internalTestOneStep(Speed speed) throws InterruptedException {
        setUpFromSpeed(speed);

        synchro.oneStep();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(1, synchronizerCore.getNbStartCalled());
        assertEquals(1, synchronizerCore.getNbStopCalled());
        assertEquals(1, taskCore.getNbExecuted());
        assertEquals(1, synchronizer3D.getNbStartCalled());
        assertEquals(1, synchronizer3D.getNbStopCalled());
        assertEquals(20 / synchro.getSpeed().getSpeed(), task3D.getNbExecuted());
        assertEquals(20, task3D.getNbIncrementExecuted());

        synchro.oneStep();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(2, synchronizerCore.getNbStartCalled());
        assertEquals(2, synchronizerCore.getNbStopCalled());
        assertEquals(2, taskCore.getNbExecuted());
        assertEquals(2, synchronizer3D.getNbStartCalled());
        assertEquals(2, synchronizer3D.getNbStopCalled());
        assertEquals(40 / synchro.getSpeed().getSpeed(), task3D.getNbExecuted());
        assertEquals(40, task3D.getNbIncrementExecuted());

        synchro.oneStep();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(3, synchronizerCore.getNbStartCalled());
        assertEquals(3, synchronizerCore.getNbStopCalled());
        assertEquals(3, taskCore.getNbExecuted());
        assertEquals(3, synchronizer3D.getNbStartCalled());
        assertEquals(3, synchronizer3D.getNbStopCalled());
        assertEquals(60 / synchro.getSpeed().getSpeed(), task3D.getNbExecuted());
        assertEquals(60, task3D.getNbIncrementExecuted());

        synchro.oneStep();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(4, synchronizerCore.getNbStartCalled());
        assertEquals(4, synchronizerCore.getNbStopCalled());
        assertEquals(4, taskCore.getNbExecuted());
        assertEquals(4, synchronizer3D.getNbStartCalled());
        assertEquals(4, synchronizer3D.getNbStopCalled());
        // the task3D should not be executed anymore, as it has reach its bound
        assertEquals(60 / synchro.getSpeed().getSpeed(), task3D.getNbExecuted());
        assertEquals(60, task3D.getNbIncrementExecuted());

        synchro.oneStep();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(5, synchronizerCore.getNbStartCalled());
        assertEquals(5, synchronizerCore.getNbStopCalled());
        assertEquals(5, taskCore.getNbExecuted());
        assertEquals(5, synchronizer3D.getNbStartCalled());
        assertEquals(5, synchronizer3D.getNbStopCalled());
        // the task3D should not be executed anymore, as it has reach its bound
        assertEquals(60 / synchro.getSpeed().getSpeed(), task3D.getNbExecuted());
        assertEquals(60, task3D.getNbIncrementExecuted());
    }

    public void testStartNormal() throws InterruptedException {
        internalTestStart(Speed.NORMAL);
    }

    public void testStartFast() throws InterruptedException {
        internalTestStart(Speed.FAST);
    }

    public void testStartVeryFast() throws InterruptedException {
        internalTestStart(Speed.VERY_FAST);
    }

    private void internalTestStart(Speed speed) throws InterruptedException {
        setUpFromSpeed(speed);

        assertFalse(synchro.isRunning());

        synchro.start();
        // wait a little longer to be sure the task3D completes (3 core cycles should be enough)
        Thread.sleep(5 * Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertTrue(synchro.isRunning());
        assertEquals(1, synchronizerCore.getNbStartCalled());
        assertEquals(0, synchronizerCore.getNbStopCalled());
        assertTrue(taskCore.getNbExecuted() > 3);
        assertEquals(1, synchronizer3D.getNbStartCalled());
        assertEquals(0, synchronizer3D.getNbStopCalled());
        assertEquals(60 / synchro.getSpeed().getSpeed(), task3D.getNbExecuted());
        assertEquals(60, task3D.getNbIncrementExecuted());


        synchro.stop();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        assertEquals(1, synchronizerCore.getNbStopCalled());
        assertEquals(1, synchronizer3D.getNbStopCalled());
        assertEquals(60 / synchro.getSpeed().getSpeed(), task3D.getNbExecuted());
        assertEquals(60, task3D.getNbIncrementExecuted());
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
