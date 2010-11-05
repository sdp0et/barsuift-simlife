package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.InitException;
import barsuift.simLife.message.PublisherTestHelper;


public class BasicSynchronizer3DTest extends TestCase {

    private BasicSynchronizer3D synchro;

    private Synchronizer3DState state;

    private MockSplitBoundedRunnable runnable;

    private SynchronizedRunnable barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();
        setUpwithBound(Integer.MAX_VALUE);
    }

    private void setUpwithBound(int bound) {
        state = new Synchronizer3DState(10);
        synchro = new BasicSynchronizer3D(state);
        runnable = new MockSplitBoundedRunnable(new SplitBoundedRunnableState(bound, 0, 10));
        synchro.schedule(runnable);

        CyclicBarrier barrier = new CyclicBarrier(2);
        synchro.setBarrier(barrier);
        barrierReleaser = new MockSingleRunSynchronizedRunnable();
        barrierReleaser.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        synchro = null;
        runnable = null;
        barrierReleaser = null;
    }

    public void testSetBarrier() {
        try {
            synchro.setBarrier(new CyclicBarrier(1));
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        synchro = new BasicSynchronizer3D(state);
        try {
            synchro.setBarrier(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

    public void testSetSpeed() throws InitException {
        assertEquals(10, synchro.getStepSize());
        synchro.setStepSize(5);
        assertEquals(5, synchro.getStepSize());
        synchro.setStepSize(30);
        assertEquals(30, synchro.getStepSize());
    }

    public void testStart() throws InterruptedException {
        assertFalse(synchro.isRunning());
        assertEquals(0, runnable.getNbExecuted());
        assertEquals(0, runnable.getNbIncrementExecuted());

        synchro.start();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        assertTrue(synchro.isRunning());
        assertTrue(runnable.getNbExecuted() > 0);
        assertTrue(runnable.getNbIncrementExecuted() > 0);

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        assertFalse(synchro.isRunning());
        int nbExecuted = runnable.getNbExecuted();
        int nbIncrementExecuted = runnable.getNbIncrementExecuted();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        // assert the runnable is not called anymore
        assertEquals(nbExecuted, runnable.getNbExecuted());
        assertEquals(nbIncrementExecuted, runnable.getNbIncrementExecuted());
        // even if we release the barrier once more
        new Thread(barrierReleaser).start();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        assertEquals(nbExecuted, runnable.getNbExecuted());
        assertEquals(nbIncrementExecuted, runnable.getNbIncrementExecuted());
    }

    public void testStartWithBoundedTask() throws InterruptedException {
        // with this bound, the tasks should execute only once
        setUpwithBound(10);

        assertFalse(synchro.isRunning());
        assertEquals(0, runnable.getNbExecuted());
        assertEquals(0, runnable.getNbIncrementExecuted());
        // the task in not yet in the list because the synchronizer is not running
        assertFalse(synchro.getRunnables().contains(runnable));

        synchro.start();
        // ths synchronizer should be in the list now
        assertTrue(synchro.getRunnables().contains(runnable));
        Thread.sleep(2 * Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        assertTrue(synchro.isRunning());
        assertEquals(1, runnable.getNbExecuted());
        assertEquals(10, runnable.getNbIncrementExecuted());

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        assertFalse(synchro.isRunning());
        // the runnable has already stopped anyway
        assertEquals(1, runnable.getNbExecuted());
        assertEquals(10, runnable.getNbIncrementExecuted());
        // the runnable is not even in the list of runnables
        assertFalse(synchro.getRunnables().contains(runnable));
    }

    public void testPublisher() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(synchro);

        synchro.start();
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
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
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
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
        assertEquals(10, synchro.getState().getStepSize());
        synchro.setStepSize(35);
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());
        assertEquals(35, synchro.getState().getStepSize());
    }

    public void testSchedule() throws Exception {
        // create mocks with a very high bound to be sure they won't stop before the end of the test
        MockSplitBoundedRunnable mockRun1 = new MockSplitBoundedRunnable(new SplitBoundedRunnableState(
                Integer.MAX_VALUE, 0, 10));
        MockSplitBoundedRunnable mockRun2 = new MockSplitBoundedRunnable(new SplitBoundedRunnableState(
                Integer.MAX_VALUE, 0, 10));
        MockSplitBoundedRunnable mockRun3 = new MockSplitBoundedRunnable(new SplitBoundedRunnableState(
                Integer.MAX_VALUE, 0, 10));

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
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);

        // only mockRun1 is scheduled
        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() == 0);
        assertTrue(mockRun1.getNbIncrementExecuted() >= 10);
        assertTrue(mockRun2.getNbIncrementExecuted() == 0);
        assertTrue(mockRun3.getNbIncrementExecuted() == 0);

        mockRun1.reset();
        synchro.schedule(mockRun2);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);

        // only mockRun1 is scheduled
        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() == 0);
        assertTrue(mockRun1.getNbIncrementExecuted() >= 10);
        assertTrue(mockRun2.getNbIncrementExecuted() == 0);
        assertTrue(mockRun3.getNbIncrementExecuted() == 0);

        mockRun1.reset();
        synchro.schedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);

        // mockRun1 and mockRun2 have run
        // only mockRun1 is still scheduled
        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() >= 1);
        assertTrue(mockRun3.getNbExecuted() == 0);
        assertTrue(mockRun1.getNbExecuted() >= mockRun2.getNbExecuted());
        assertTrue(mockRun1.getNbIncrementExecuted() >= 10);
        assertTrue(mockRun2.getNbIncrementExecuted() >= 10);
        assertTrue(mockRun3.getNbIncrementExecuted() == 0);
        assertTrue(mockRun1.getNbIncrementExecuted() >= mockRun2.getNbIncrementExecuted());

        mockRun1.reset();
        mockRun2.reset();
        synchro.unschedule(mockRun1);
        synchro.schedule(mockRun3);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);

        // only mockRun3 is scheduled
        assertTrue(mockRun1.getNbExecuted() == 0);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() >= 1);
        assertTrue(mockRun1.getNbIncrementExecuted() == 0);
        assertTrue(mockRun2.getNbIncrementExecuted() == 0);
        assertTrue(mockRun3.getNbIncrementExecuted() >= 10);

    }
}
