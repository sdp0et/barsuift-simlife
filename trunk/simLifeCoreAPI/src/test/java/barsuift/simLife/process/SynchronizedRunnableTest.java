package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.time.MockTimeController;
import barsuift.simLife.time.TimeController;


public class SynchronizedRunnableTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws InterruptedException {
        // make sure the barrier will block after the first and even second run
        CyclicBarrier barrier = new CyclicBarrier(3);
        TimeController timeController = new MockTimeController();
        MockSynchronizedRunnable synchroRun = new MockSynchronizedRunnable();
        synchroRun.init(new SynchronizedRunnableState(), timeController);
        synchroRun.setBarrier(barrier);
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
        assertFalse(synchroRun.isRunning());

        // test we can start it again
        (new Thread(synchroRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(synchroRun.isRunning());
        assertEquals(2, synchroRun.getNbExecuted());
    }

    public void testSetBarrier() throws Exception {
        TimeController timeController = new MockTimeController();
        MockSynchronizedRunnable synchroRun = new MockSynchronizedRunnable();
        synchroRun.init(new SynchronizedRunnableState(), timeController);
        synchroRun.setBarrier(new CyclicBarrier(3));
        // the process is not running, so it is still OK to change the barrier
        synchroRun.setBarrier(new CyclicBarrier(3));

        // start the process
        (new Thread(synchroRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(synchroRun.isRunning());
        // now, the process is running, so we can't change the barrier
        try {
            synchroRun.setBarrier(new CyclicBarrier(3));
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // stop the process
        synchroRun.stop();
        assertFalse(synchroRun.isRunning());
        // now, the process is stopped, so we can change again the barrier
        synchroRun.setBarrier(new CyclicBarrier(3));

        // test with null parameter
        try {
            synchroRun.setBarrier(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

}
