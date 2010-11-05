package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.message.PublisherTestHelper;


public class SplitBoundedRunnableTest extends TestCase {

    private MockSplitBoundedRunnable splitRun;

    private SplitBoundedRunnableState state;

    private MockSingleRunSynchronizedRunnable mockSynchroRun;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        state = new SplitBoundedRunnableState(60, 0, 1);
        mockSynchroRun = new MockSingleRunSynchronizedRunnable();
        mockSynchroRun.changeBarrier(barrier);
        splitRun = new MockSplitBoundedRunnable(state);
        splitRun.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(splitRun);

        (new Thread(splitRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(splitRun.isRunning());
        // executed once
        assertEquals(1, splitRun.getNbExecuted());
        assertEquals(1, splitRun.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // test we can not run the same runnable again
        try {
            splitRun.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        splitRun.stop();
        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(splitRun.isRunning());

        // test we can start it again
        (new Thread(splitRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(splitRun.isRunning());
        // executed once again
        assertEquals(2, splitRun.getNbExecuted());
        assertEquals(2, splitRun.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitRun.isRunning());
        // executed once again
        assertEquals(3, splitRun.getNbExecuted());
        assertEquals(3, splitRun.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitRun.setStepSize(3);

        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitRun.isRunning());
        // executed once again
        assertEquals(4, splitRun.getNbExecuted());
        assertEquals(6, splitRun.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitRun.isRunning());
        // executed once again
        assertEquals(5, splitRun.getNbExecuted());
        assertEquals(9, splitRun.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitRun.setStepSize(25);

        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitRun.isRunning());
        // executed once again
        assertEquals(6, splitRun.getNbExecuted());
        assertEquals(34, splitRun.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitRun.isRunning());
        // executed once again
        assertEquals(7, splitRun.getNbExecuted());
        assertEquals(59, splitRun.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitRun.setStepSize(1);

        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitRun.isRunning());
        // executed once again
        assertEquals(8, splitRun.getNbExecuted());
        assertEquals(60, splitRun.getNbIncrementExecuted());
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        // release the barrier
        mockSynchroRun.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        // this time, the split task should have stopped
        assertFalse(splitRun.isRunning());
        assertEquals(8, splitRun.getNbExecuted());
        assertEquals(60, splitRun.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, splitRun.getState());
        assertSame(state, splitRun.getState());
        assertEquals(0, splitRun.getState().getCount());
        (new Thread(splitRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertEquals(state, splitRun.getState());
        assertSame(state, splitRun.getState());
        assertEquals(1, splitRun.getState().getCount());
    }

}
