package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.message.PublisherTestHelper;


public class SplitBoundedTaskTest extends TestCase {

    private MockSplitBoundedTask splitTask;

    private SplitBoundedTaskState state;

    private MockSingleSynchronizedTask barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        state = new SplitBoundedTaskState(60, 0, 1);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
        splitTask = new MockSplitBoundedTask(state);
        splitTask.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(splitTask);

        (new Thread(splitTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once
        assertEquals(1, splitTask.getNbExecuted());
        assertEquals(1, splitTask.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // test we can not run the same task again
        try {
            splitTask.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        splitTask.stop();
        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(splitTask.isRunning());

        // test we can start it again
        (new Thread(splitTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(2, splitTask.getNbExecuted());
        assertEquals(2, splitTask.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(3, splitTask.getNbExecuted());
        assertEquals(3, splitTask.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitTask.setStepSize(3);

        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(4, splitTask.getNbExecuted());
        assertEquals(6, splitTask.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(5, splitTask.getNbExecuted());
        assertEquals(9, splitTask.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitTask.setStepSize(25);

        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(6, splitTask.getNbExecuted());
        assertEquals(34, splitTask.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(7, splitTask.getNbExecuted());
        assertEquals(59, splitTask.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitTask.setStepSize(1);

        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(8, splitTask.getNbExecuted());
        assertEquals(60, splitTask.getNbIncrementExecuted());
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to execute
        Thread.sleep(100);
        // this time, the split task should have stopped
        assertFalse(splitTask.isRunning());
        assertEquals(8, splitTask.getNbExecuted());
        assertEquals(60, splitTask.getNbIncrementExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, splitTask.getState());
        assertSame(state, splitTask.getState());
        assertEquals(0, splitTask.getState().getCount());
        (new Thread(splitTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertEquals(state, splitTask.getState());
        assertSame(state, splitTask.getState());
        assertEquals(1, splitTask.getState().getCount());
    }

}
