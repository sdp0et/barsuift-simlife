package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;


public class SynchronizedTaskTest extends TestCase {

    private MockSingleSynchronizedTask barrierReleaser;

    private MockSynchronizedTask synchroTask;

    private CyclicBarrier barrier;

    protected void setUp() throws Exception {
        super.setUp();
        barrier = new CyclicBarrier(2);
        synchroTask = new MockSynchronizedTask();
        synchroTask.changeBarrier(barrier);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws InterruptedException {
        (new Thread(synchroTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(synchroTask.isRunning());
        assertEquals(1, synchroTask.getNbExecuted());

        // test we can not run the same task again
        try {
            synchroTask.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        synchroTask.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(synchroTask.isRunning());

        synchroTask.resetNbExecuted();
        // test we can start it again
        (new Thread(synchroTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(synchroTask.isRunning());
        assertEquals(1, synchroTask.getNbExecuted());
    }

    public void testSetBarrier() throws Exception {
        // the process is not running, so it is still OK to change the barrier
        synchroTask.changeBarrier(barrier);

        // start the process
        (new Thread(synchroTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(synchroTask.isRunning());
        // now, the process is running, but we can still change the barrier
        synchroTask.changeBarrier(barrier);

        // stop the process
        synchroTask.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(synchroTask.isRunning());
        // now, the process is stopped, so we can change again the barrier
        synchroTask.changeBarrier(barrier);

        // test with null parameter
        try {
            synchroTask.changeBarrier(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

}
