package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;


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
        MockSynchronizedRunnable synchroRun = new MockSynchronizedRunnable(barrier, new SynchronizedRunnableState());
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

}
