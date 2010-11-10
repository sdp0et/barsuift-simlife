package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;


public class AbstractConditionalTaskTest extends TestCase {

    private MockConditionalTask conditionalRun;

    private ConditionalTaskState state;

    private MockSingleSynchronizedTask barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        CyclicConditionState executionConditionState = new CyclicConditionState(3, 0);
        BoundConditionState endingConditionState = new BoundConditionState(5, 0);
        state = new ConditionalTaskState(executionConditionState, endingConditionState);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
        conditionalRun = new MockConditionalTask(state);
        conditionalRun.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Expected results are :
     * <ol>
     * <li>not executed</li>
     * <li>not executed</li>
     * <li>not executed</li>
     * <li>executed</li>
     * <li>not executed</li>
     * <li>not executed and stopped</li>
     * <li>not executed</li>
     * <li>
     * </ol>
     */
    public void testRun() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(conditionalRun);

        (new Thread(conditionalRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(conditionalRun.isRunning());
        // run 1
        assertEquals(0, conditionalRun.getNbExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // test we can not run the same task again
        try {
            conditionalRun.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        conditionalRun.stop();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(conditionalRun.isRunning());

        // test we can start it again
        (new Thread(conditionalRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(conditionalRun.isRunning());
        // run 2
        assertEquals(0, conditionalRun.getNbExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(conditionalRun.isRunning());
        // run 3
        assertEquals(1, conditionalRun.getNbExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());


        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(conditionalRun.isRunning());
        // run 4
        assertEquals(1, conditionalRun.getNbExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertTrue(conditionalRun.isRunning());
        // run 5
        assertEquals(1, conditionalRun.getNbExecuted());
        assertEquals(1, publisherHelper.nbUpdated());
        assertEquals(null, publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertFalse(conditionalRun.isRunning());
        // run 6
        assertEquals(1, conditionalRun.getNbExecuted());
        assertEquals(0, publisherHelper.nbUpdated());
        assertEquals(0, publisherHelper.getUpdateObjects().size());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, conditionalRun.getState());
        assertSame(state, conditionalRun.getState());
        assertEquals(0, conditionalRun.getState().getEndingCondition().getCount());
        assertEquals(0, conditionalRun.getState().getExecutionCondition().getCount());
        (new Thread(conditionalRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertEquals(state, conditionalRun.getState());
        assertSame(state, conditionalRun.getState());
        assertEquals(1, conditionalRun.getState().getEndingCondition().getCount());
        assertEquals(1, conditionalRun.getState().getExecutionCondition().getCount());
    }

}
