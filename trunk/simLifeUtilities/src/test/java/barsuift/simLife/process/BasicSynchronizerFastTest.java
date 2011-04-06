package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;


public class BasicSynchronizerFastTest extends TestCase {

    private BasicSynchronizerFast synchro;

    private SynchronizerFastState state;

    private MockSplitConditionalTask task;

    private SynchronizedTask barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();
        setUpWithBound(0);
    }

    private void setUpWithBound(int bound) {
        state = new SynchronizerFastState(10);
        synchro = new BasicSynchronizerFast(state);
        ConditionalTaskState conditionalTaskState = new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(bound, 0));
        task = new MockSplitConditionalTask(new SplitConditionalTaskState(conditionalTaskState, 10));
        synchro.schedule(task);

        CyclicBarrier barrier = new CyclicBarrier(2);
        synchro.setBarrier(barrier);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        synchro = null;
        task = null;
        barrierReleaser = null;
    }

    public void testSetBarrier() {
        try {
            synchro.setBarrier(new CyclicBarrier(1));
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        synchro = new BasicSynchronizerFast(state);
        try {
            synchro.setBarrier(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

    public void testSetStepSize() throws Exception {
        // test step size on scheduled task
        assertEquals(10, synchro.getStepSize());
        assertEquals(10, task.getState().getStepSize());
        synchro.setStepSize(5);
        assertEquals(5, synchro.getStepSize());
        assertEquals(5, task.getState().getStepSize());
        synchro.setStepSize(30);
        assertEquals(30, synchro.getStepSize());
        assertEquals(30, task.getState().getStepSize());

        setUp();
        synchro.start();

        // test step size ion started task
        assertEquals(10, synchro.getStepSize());
        assertEquals(10, task.getState().getStepSize());
        synchro.setStepSize(5);
        assertEquals(5, synchro.getStepSize());
        assertEquals(5, task.getState().getStepSize());
        synchro.setStepSize(30);
        assertEquals(30, synchro.getStepSize());
        assertEquals(30, task.getState().getStepSize());
    }

    public void testStart() throws InterruptedException {
        assertFalse(synchro.isRunning());
        assertEquals(0, task.getNbExecuted());
        assertEquals(0, task.getNbIncrementExecuted());

        synchro.start();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        assertTrue(synchro.isRunning());
        assertTrue(task.getNbExecuted() > 0);
        assertTrue(task.getNbIncrementExecuted() > 0);

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        assertFalse(synchro.isRunning());
        int nbExecuted = task.getNbExecuted();
        int nbIncrementExecuted = task.getNbIncrementExecuted();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        // assert the task is not called anymore
        assertEquals(nbExecuted, task.getNbExecuted());
        assertEquals(nbIncrementExecuted, task.getNbIncrementExecuted());
        // even if we release the barrier once more
        new Thread(barrierReleaser).start();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        assertEquals(nbExecuted, task.getNbExecuted());
        assertEquals(nbIncrementExecuted, task.getNbIncrementExecuted());
    }

    public void testStartWithBoundedTask() throws InterruptedException {
        // with this bound, the tasks should execute only once
        setUpWithBound(10);

        assertFalse(synchro.isRunning());
        assertEquals(0, task.getNbExecuted());
        assertEquals(0, task.getNbIncrementExecuted());
        // the task in not yet in the list because the synchronizer is not running
        assertFalse(synchro.getTasks().contains(task));

        synchro.start();
        // the synchronizer should be in the list now
        assertTrue(synchro.getTasks().contains(task));
        Thread.sleep(2 * BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        assertTrue(synchro.isRunning());
        assertEquals(1, task.getNbExecuted());
        assertEquals(10, task.getNbIncrementExecuted());

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        assertFalse(synchro.isRunning());
        // the task has already stopped anyway
        assertEquals(1, task.getNbExecuted());
        assertEquals(10, task.getNbIncrementExecuted());
        // the task is not even in the list of tasks
        assertFalse(synchro.getTasks().contains(task));
    }

    public void testPublisher() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(synchro);

        synchro.start();
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));
    }


    public void testIllegalStateException() throws InterruptedException {
        try {
            synchro.stop();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        synchro.start();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        try {
            synchro.start();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
    }

    public void testGetState() {
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());
        assertEquals(10, synchro.getState().getStepSize());
        synchro.setStepSize(35);
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());
        assertEquals(35, synchro.getState().getStepSize());
    }

    public void testSchedule() throws Exception {
        // create mocks with no bound to be sure they won't stop before the end of the test
        ConditionalTaskState conditionalTaskState = new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(0, 0));
        SplitConditionalTaskState splitConditionalTaskState = new SplitConditionalTaskState(conditionalTaskState, 10);
        MockSplitConditionalTask mockRun1 = new MockSplitConditionalTask(splitConditionalTaskState);
        MockSplitConditionalTask mockRun2 = new MockSplitConditionalTask(splitConditionalTaskState);
        MockSplitConditionalTask mockRun3 = new MockSplitConditionalTask(splitConditionalTaskState);

        try {
            synchro.unschedule(mockRun1);
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected Exception
        }

        synchro.schedule(mockRun1);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);

        // only mockRun1 is scheduled
        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() == 0);
        assertTrue(mockRun1.getNbIncrementExecuted() >= 10);
        assertTrue(mockRun2.getNbIncrementExecuted() == 0);
        assertTrue(mockRun3.getNbIncrementExecuted() == 0);

        mockRun1.reset();
        synchro.schedule(mockRun2);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);

        // only mockRun1 is scheduled
        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() == 0);
        assertTrue(mockRun1.getNbIncrementExecuted() >= 10);
        assertTrue(mockRun2.getNbIncrementExecuted() == 0);
        assertTrue(mockRun3.getNbIncrementExecuted() == 0);

        mockRun1.reset();
        synchro.schedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);

        // mockRun1 and mockRun2 have run
        // only mockRun1 is still scheduled
        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() >= 1);
        assertTrue(mockRun3.getNbExecuted() == 0);
        assertTrue(mockRun1.getNbExecuted() >= mockRun2.getNbExecuted());
        assertTrue(mockRun1.getNbIncrementExecuted() >= 10);
        assertTrue(mockRun2.getNbIncrementExecuted() >= 10);
        assertTrue(mockRun3.getNbIncrementExecuted() == 0);
        assertTrue(mockRun1.getNbIncrementExecuted() >= mockRun2.getNbIncrementExecuted());

        mockRun1.reset();
        mockRun2.reset();
        synchro.unschedule(mockRun1);
        synchro.schedule(mockRun3);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);

        // only mockRun3 is scheduled
        assertTrue(mockRun1.getNbExecuted() == 0);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() >= 1);
        assertTrue(mockRun1.getNbIncrementExecuted() == 0);
        assertTrue(mockRun2.getNbIncrementExecuted() == 0);
        assertTrue(mockRun3.getNbIncrementExecuted() >= 10);

    }
}
