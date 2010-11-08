package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.message.PublisherTestHelper;


public class BoundedTaskTest extends TestCase {

    private MockBoundedTask boundedRun;

    private BoundedTaskState state;

    private MockSingleSynchronizedTask barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        BoundConditionState endingConditionState = new BoundConditionState(3, 0);
        state = new BoundedTaskState(endingConditionState);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
        boundedRun = new MockBoundedTask(state);
        boundedRun.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(boundedRun);

        (new Thread(boundedRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(boundedRun.isRunning());
        // executed once (1/3)
        assertEquals(1, boundedRun.getNbExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // test we can not run the same task again
        try {
            boundedRun.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        boundedRun.stop();
        // release the barrier
        (new Thread(barrierReleaser)).start();
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
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(boundedRun.isRunning());
        // executed once again (3/3)
        assertEquals(3, boundedRun.getNbExecuted());
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertFalse(boundedRun.isRunning());
        // it should not have executed this time
        assertEquals(3, boundedRun.getNbExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // even if we do it yet another time
        publisherHelper.reset();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertFalse(boundedRun.isRunning());
        // it should not have executed this time
        assertEquals(3, boundedRun.getNbExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, boundedRun.getState());
        assertSame(state, boundedRun.getState());
        assertEquals(0, boundedRun.getState().getEndingCondition().getCount());
        (new Thread(boundedRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertEquals(state, boundedRun.getState());
        assertSame(state, boundedRun.getState());
        assertEquals(1, boundedRun.getState().getEndingCondition().getCount());
    }

}
