package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.time.MockTimeController;
import barsuift.simLife.time.TimeController;


public class SynchronizedRunnableTest extends TestCase {

    private MockSingleRunSynchronizedRunnable barrierReleaser;

    private MockSynchronizedRunnable synchroRun;

    private CyclicBarrier barrier;

    protected void setUp() throws Exception {
        super.setUp();
        barrier = new CyclicBarrier(2);
        TimeController timeController = new MockTimeController();
        synchroRun = new MockSynchronizedRunnable();
        synchroRun.init(new SynchronizedRunnableState(), timeController);
        synchroRun.setBarrier(barrier);
        barrierReleaser = new MockSingleRunSynchronizedRunnable();
        barrierReleaser.setBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws InterruptedException {
        (new Thread(synchroRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(synchroRun.isRunning());
        assertEquals(1, synchroRun.getNbExecuted());

        // test we can not run the same runnable again
        try {
            synchroRun.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        synchroRun.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(synchroRun.isRunning());

        synchroRun.resetNbExecuted();
        // test we can start it again
        (new Thread(synchroRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(synchroRun.isRunning());
        assertEquals(1, synchroRun.getNbExecuted());
    }

    public void testSetBarrier() throws Exception {
        // the process is not running, so it is still OK to change the barrier
        synchroRun.setBarrier(barrier);

        // start the process
        (new Thread(synchroRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(synchroRun.isRunning());
        // now, the process is running, so we can't change the barrier
        try {
            synchroRun.setBarrier(barrier);
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // stop the process
        synchroRun.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(synchroRun.isRunning());
        // now, the process is stopped, so we can change again the barrier
        synchroRun.setBarrier(barrier);

        // test with null parameter
        try {
            synchroRun.setBarrier(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

}
