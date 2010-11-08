package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.condition.CyclicConditionState;


public class CyclicTaskTest extends TestCase {

    private MockCyclicTask cyclicRun;

    private CyclicTaskState state;

    private MockSingleSynchronizedTask barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        CyclicConditionState executionConditionState = new CyclicConditionState(3, 0);
        state = new CyclicTaskState(executionConditionState);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
        cyclicRun = new MockCyclicTask(state);
        cyclicRun.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRun() throws Exception {
        (new Thread(cyclicRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(cyclicRun.isRunning());
        // not executed once as it should wait 3 times before executing
        assertEquals(0, cyclicRun.getNbExecuted());

        // test we can not run the same task again
        try {
            cyclicRun.run();
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        cyclicRun.stop();
        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertFalse(cyclicRun.isRunning());

        // test we can start it again
        (new Thread(cyclicRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertTrue(cyclicRun.isRunning());
        // still not run
        assertEquals(0, cyclicRun.getNbExecuted());

        cyclicRun.stop();
        // release the barrier
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        (new Thread(cyclicRun)).start();
        Thread.sleep(100);
        assertTrue(cyclicRun.isRunning());
        // now it has run once
        assertEquals(1, cyclicRun.getNbExecuted());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, cyclicRun.getState());
        assertSame(state, cyclicRun.getState());
        assertEquals(0, cyclicRun.getState().getExecutionCondition().getCount());
        (new Thread(cyclicRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertEquals(state, cyclicRun.getState());
        assertSame(state, cyclicRun.getState());
        assertEquals(1, cyclicRun.getState().getExecutionCondition().getCount());
    }

}
