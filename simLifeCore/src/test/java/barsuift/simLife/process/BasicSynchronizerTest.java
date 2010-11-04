package barsuift.simLife.process;

import junit.framework.TestCase;
import barsuift.simLife.InitException;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.time.DateHandlerState;
import barsuift.simLife.time.SimLifeDate;


public class BasicSynchronizerTest extends TestCase {

    private BasicSynchronizer synchro;

    private SynchronizerState state;

    protected void setUp() throws Exception {
        super.setUp();

        state = new SynchronizerState(Speed.VERY_FAST, new DateHandlerState());
        synchro = new BasicSynchronizer(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        synchro = null;
    }

    public void testSetSpeed() throws InitException {
        assertEquals(Speed.VERY_FAST, synchro.getSpeed());
        synchro.setSpeed(Speed.FAST);
        assertEquals(Speed.FAST, synchro.getSpeed());
        synchro.setSpeed(Speed.NORMAL);
        assertEquals(Speed.NORMAL, synchro.getSpeed());
    }

    public void testOneStep() throws InterruptedException {
        synchro.oneStep();
        assertTrue(synchro.isRunning());

        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
    }

    public void testStart() throws InterruptedException {
        assertFalse(synchro.isRunning());
        assertEquals(new SimLifeDate(), synchro.getDate());
        assertEquals(0, synchro.getDate().getTimeInMillis());

        synchro.start();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertTrue(synchro.isRunning());
        assertTrue(synchro.getDate().getTimeInMillis() > 0);

        synchro.stop();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        long time = synchro.getDate().getTimeInMillis();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        // assert the time does not change anymore once stopped
        assertEquals(time, synchro.getDate().getTimeInMillis());
    }

    public void testPublisher() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(synchro);

        synchro.start();
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.stop();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        synchro.oneStep();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertEquals(2, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));
    }


    public void testIllegalStateException() throws InterruptedException {
        Speed speed = synchro.getSpeed();
        try {
            synchro.stop();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        synchro.start();
        // waiting 2 cycles
        Thread.sleep(200 / speed.getSpeed() + 10);
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
        assertEquals(Speed.VERY_FAST, synchro.getState().getSpeed());
        synchro.setSpeed(Speed.FAST);
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());
        assertEquals(Speed.FAST, synchro.getState().getSpeed());
    }

    public void testSchedule() throws Exception {
        MockSynchronizedRunnable mockRun1 = new MockSynchronizedRunnable();
        MockSynchronizedRunnable mockRun2 = new MockSynchronizedRunnable();
        MockSynchronizedRunnable mockRun3 = new MockSynchronizedRunnable();

        try {
            synchro.unschedule(mockRun1);
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected Exception
        }

        synchro.schedule(mockRun1);
        synchro.oneStep();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertEquals(1, mockRun1.getNbExecuted());
        assertEquals(0, mockRun2.getNbExecuted());
        assertEquals(0, mockRun3.getNbExecuted());

        mockRun1.resetNbExecuted();
        synchro.schedule(mockRun2);
        synchro.unschedule(mockRun2);
        synchro.oneStep();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertEquals(1, mockRun1.getNbExecuted());
        assertEquals(0, mockRun2.getNbExecuted());
        assertEquals(0, mockRun3.getNbExecuted());

        mockRun1.resetNbExecuted();
        synchro.schedule(mockRun2);
        synchro.oneStep();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.unschedule(mockRun2);
        synchro.oneStep();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertEquals(2, mockRun1.getNbExecuted());
        assertEquals(1, mockRun2.getNbExecuted());
        assertEquals(0, mockRun3.getNbExecuted());

        mockRun1.resetNbExecuted();
        mockRun2.resetNbExecuted();
        synchro.schedule(mockRun3);
        synchro.unschedule(mockRun1);
        synchro.start();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.stop();
        Thread.sleep(BasicSynchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertEquals(0, mockRun1.getNbExecuted());
        assertEquals(0, mockRun2.getNbExecuted());
        assertTrue(mockRun3.getNbExecuted() > 0);

    }

}
