package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;


public class BoundedRunnableTest extends TestCase {

    private MockBoundedRunnable boundedRun;

    private BoundedRunnableState state;

    private MockSingleRunSynchronizedRunnable mockSynchroRun;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        state = new BoundedRunnableState(3, 0);
        mockSynchroRun = new MockSingleRunSynchronizedRunnable();
        mockSynchroRun.changeBarrier(barrier);
        boundedRun = new MockBoundedRunnable(state);
        boundedRun.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws Exception {
        (new Thread(boundedRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(boundedRun.isRunning());
        // executed once (1/3)
        assertEquals(1, boundedRun.getNbExecuted());

        // test we can not run the same runnable again
        try {
            boundedRun.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        boundedRun.stop();
        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(boundedRun.isRunning());

        // test we can start it again
        (new Thread(boundedRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(boundedRun.isRunning());
        // executed once again (2/3)
        assertEquals(2, boundedRun.getNbExecuted());

        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(boundedRun.isRunning());
        // executed once again (3/3)
        assertEquals(3, boundedRun.getNbExecuted());

        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertFalse(boundedRun.isRunning());
        // it should not have executed this time
        assertEquals(3, boundedRun.getNbExecuted());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, boundedRun.getState());
        assertSame(state, boundedRun.getState());
        assertEquals(0, boundedRun.getState().getCount());
        (new Thread(boundedRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertEquals(state, boundedRun.getState());
        assertSame(state, boundedRun.getState());
        assertEquals(1, boundedRun.getState().getCount());
    }

}
