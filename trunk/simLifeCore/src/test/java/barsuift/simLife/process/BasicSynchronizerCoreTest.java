package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;
import barsuift.simLife.time.SimLifeDate;


public class BasicSynchronizerCoreTest extends TestCase {

    private BasicSynchronizerCore synchro;

    private SynchronizerCoreState state;

    private SimLifeDate date;

    private SynchronizedTask barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();

        state = new SynchronizerCoreState(Speed.VERY_FAST);
        synchro = new BasicSynchronizerCore(state);
        date = new SimLifeDate();
        DateUpdater dateUpdater = new DateUpdater(new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(0, 0)), date);
        synchro.schedule(dateUpdater);

        CyclicBarrier barrier = new CyclicBarrier(2);
        synchro.setBarrier(barrier);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        synchro = null;
        date = null;
    }

    public void testSetBarrier() {
        try {
            synchro.setBarrier(new CyclicBarrier(1));
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        synchro = new BasicSynchronizerCore(state);
        try {
            synchro.setBarrier(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

    public void testSetSpeed() {
        assertEquals(Speed.VERY_FAST, synchro.getSpeed());
        synchro.setSpeed(Speed.FAST);
        assertEquals(Speed.FAST, synchro.getSpeed());
        synchro.setSpeed(Speed.NORMAL);
        assertEquals(Speed.NORMAL, synchro.getSpeed());
    }

    public void testStart() throws InterruptedException {
        assertFalse(synchro.isRunning());
        assertEquals(new SimLifeDate(), date);
        assertEquals(0, date.getTimeInMillis());

        synchro.start();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertTrue(synchro.isRunning());
        assertTrue(date.getTimeInMillis() > 0);

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        long time = date.getTimeInMillis();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        // assert the time does not change anymore once stopped
        assertEquals(time, date.getTimeInMillis());
        // even if we release the barrier once more
        new Thread(barrierReleaser).start();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertEquals(time, date.getTimeInMillis());
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
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertEquals(1, publisherHelper.nbUpdated());
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
        MockSynchronizedTask mockRun1 = new MockSynchronizedTask();
        MockSynchronizedTask mockRun2 = new MockSynchronizedTask();
        MockSynchronizedTask mockRun3 = new MockSynchronizedTask();

        try {
            synchro.unschedule(mockRun1);
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected Exception
        }

        synchro.schedule(mockRun1);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() == 0);

        mockRun1.resetNbExecuted();
        synchro.schedule(mockRun2);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() == 0);

        mockRun1.resetNbExecuted();
        synchro.schedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() >= 1);
        assertTrue(mockRun3.getNbExecuted() == 0);
        assertTrue(mockRun1.getNbExecuted() > mockRun2.getNbExecuted());

        mockRun1.resetNbExecuted();
        mockRun2.resetNbExecuted();
        synchro.schedule(mockRun3);
        synchro.unschedule(mockRun1);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertTrue(mockRun1.getNbExecuted() == 0);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() >= 1);

    }

}
