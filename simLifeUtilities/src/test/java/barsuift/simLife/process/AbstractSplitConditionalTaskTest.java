package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;


public class AbstractSplitConditionalTaskTest extends TestCase {

    private MockSplitConditionalTask splitTask;

    private SplitConditionalTaskState state;

    private MockSingleSynchronizedTask barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        CyclicConditionState executionCondition = new CyclicConditionState(3, 6);
        BoundConditionState endingCondition = new BoundConditionState(60, 6);
        ConditionalTaskState conditionalTaskState = new ConditionalTaskState(executionCondition, endingCondition);
        state = new SplitConditionalTaskState(conditionalTaskState, 2);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
        splitTask = new MockSplitConditionalTask(state);
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
        assertEquals(2, splitTask.getNbIncrementExecuted());
        assertEquals(2, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(8, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
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
        (new Thread(barrierReleaser)).start();
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
        assertEquals(4, splitTask.getNbIncrementExecuted());
        assertEquals(1, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(10, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(3, splitTask.getNbExecuted());
        assertEquals(6, splitTask.getNbIncrementExecuted());
        assertEquals(0, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(12, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitTask.setStepSize(3);

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(4, splitTask.getNbExecuted());
        assertEquals(9, splitTask.getNbIncrementExecuted());
        assertEquals(0, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(15, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(5, splitTask.getNbExecuted());
        assertEquals(12, splitTask.getNbIncrementExecuted());
        assertEquals(0, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(18, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitTask.setStepSize(20);

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(6, splitTask.getNbExecuted());
        assertEquals(32, splitTask.getNbIncrementExecuted());
        assertEquals(2, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(38, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(7, splitTask.getNbExecuted());
        assertEquals(52, splitTask.getNbIncrementExecuted());
        assertEquals(1, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(58, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        splitTask.setStepSize(1);

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(7, splitTask.getNbExecuted());
        assertEquals(52, splitTask.getNbIncrementExecuted());
        assertEquals(2, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(59, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(splitTask.isRunning());
        // executed once again
        assertEquals(8, splitTask.getNbExecuted());
        assertEquals(53, splitTask.getNbIncrementExecuted());
        assertEquals(0, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(60, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertFalse(splitTask.isRunning());
        // this time, the split task should have stopped
        assertEquals(8, splitTask.getNbExecuted());
        assertEquals(53, splitTask.getNbIncrementExecuted());
        assertEquals(0, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(60, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        // still stopped
        assertFalse(splitTask.isRunning());
        assertEquals(8, splitTask.getNbExecuted());
        assertEquals(53, splitTask.getNbIncrementExecuted());
        assertEquals(0, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(60, splitTask.getState().getConditionalTask().getEndingCondition().getCount());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, splitTask.getState());
        assertSame(state, splitTask.getState());
        assertEquals(6, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(6, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        (new Thread(splitTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertEquals(state, splitTask.getState());
        assertSame(state, splitTask.getState());
        // 2 because the counter is reseted every time it is greater than the cycle size
        assertEquals(2, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
        assertEquals(2, splitTask.getState().getConditionalTask().getExecutionCondition().getCount());
    }

}
