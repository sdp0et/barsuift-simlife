package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;


public class UnfrequentRunnableTest extends TestCase {

    private MockUnfrequentRunnable unfrequentRun;

    private UnfrequentRunnableState state;

    private MockSingleRunSynchronizedRunnable mockSynchroRun;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        state = new UnfrequentRunnableState(3, 0);
        mockSynchroRun = new MockSingleRunSynchronizedRunnable();
        mockSynchroRun.changeBarrier(barrier);
        unfrequentRun = new MockUnfrequentRunnable(state);
        unfrequentRun.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws Exception {
        (new Thread(unfrequentRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(unfrequentRun.isRunning());
        // not executed once as it should wait 3 times before executing
        assertEquals(0, unfrequentRun.getNbExecuted());

        // test we can not run the same runnable again
        try {
            unfrequentRun.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        unfrequentRun.stop();
        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(unfrequentRun.isRunning());

        // test we can start it again
        (new Thread(unfrequentRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(unfrequentRun.isRunning());
        // still not run
        assertEquals(0, unfrequentRun.getNbExecuted());

        unfrequentRun.stop();
        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        (new Thread(unfrequentRun)).start();
        Thread.sleep(100);
        assertTrue(unfrequentRun.isRunning());
        // now it has run once
        assertEquals(1, unfrequentRun.getNbExecuted());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, unfrequentRun.getState());
        assertSame(state, unfrequentRun.getState());
        assertEquals(0, unfrequentRun.getState().getCount());
        (new Thread(unfrequentRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertEquals(state, unfrequentRun.getState());
        assertSame(state, unfrequentRun.getState());
        assertEquals(1, unfrequentRun.getState().getCount());
    }

}
