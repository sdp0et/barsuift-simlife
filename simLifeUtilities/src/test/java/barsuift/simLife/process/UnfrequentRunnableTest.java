package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;


public class UnfrequentRunnableTest extends TestCase {

    private MockUnfrequentRunnable unfrequentRun;

    private UnfrequentRunnableState state;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block after all the run
        CyclicBarrier barrier = new CyclicBarrier(4);
        state = new UnfrequentRunnableState(3, 0);
        unfrequentRun = new MockUnfrequentRunnable(barrier, state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        unfrequentRun = null;
    }

    public void testRun() throws InterruptedException {
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
        assertFalse(unfrequentRun.isRunning());

        // test we can start it again
        (new Thread(unfrequentRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(unfrequentRun.isRunning());
        // still not run
        assertEquals(0, unfrequentRun.getNbExecuted());

        unfrequentRun.stop();
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
