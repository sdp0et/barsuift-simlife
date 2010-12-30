package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;


public class BasicSynchronizerCoreTest extends TestCase {

    private BasicSynchronizerCore synchro;

    private SynchronizerCoreState state;

    private MockConditionalTask task;

    private SynchronizedTask barrierReleaser;

    protected void setUp() throws Exception {
        super.setUp();
        setUpWithBound(0);
    }

    private void setUpWithBound(int bound) {
        state = new SynchronizerCoreState(Speed.VERY_FAST);
        synchro = new BasicSynchronizerCore(state);
        task = new MockConditionalTask(new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(bound, 0)));
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
    }

    public void testSetBarrier() {
        try {
            synchro.setBarrier(new CyclicBarrier(1));
            fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        synchro = new BasicSynchronizerCore(state);
        try {
            synchro.setBarrier(null);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

    public void testSetSpeed() {
        assertEquals(Speed.VERY_FAST, synchro.getSpeed());
        synchro.setSpeed(Speed.FAST);
        assertEquals(Speed.FAST, synchro.getSpeed());
        synchro.setSpeed(Speed.NORMAL);
        assertEquals(Speed.NORMAL, synchro.getSpeed());
    }

    public void testStart() throws InterruptedException {
        assertFalse(synchro.isRunning());
        assertEquals(0, task.getNbExecuted());

        synchro.start();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertTrue(synchro.isRunning());
        assertTrue(task.getNbExecuted() > 0);

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertFalse(synchro.isRunning());
        int nbExecuted = task.getNbExecuted();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        // assert the task is not called anymore
        assertEquals(nbExecuted, task.getNbExecuted());
        // even if we release the barrier once more
        new Thread(barrierReleaser).start();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        assertEquals(nbExecuted, task.getNbExecuted());
    }

    public void testStartWithBoundedTask() throws InterruptedException {
        // with this bound, the tasks should execute only once
        setUpWithBound(1);

        assertFalse(synchro.isRunning());
        assertEquals(0, task.getNbExecuted());
        // the task in not yet in the list because the synchronizer is not running
        assertFalse(synchro.getTasks().contains(task));

        synchro.start();
        // the synchronizer should be in the list now
        assertTrue(synchro.getTasks().contains(task));
        Thread.sleep(2 * Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        assertTrue(synchro.isRunning());
        assertEquals(1, task.getNbExecuted());

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(Synchronizer.CYCLE_LENGTH_3D_MS + 100);
        assertFalse(synchro.isRunning());
        // the task has already stopped anyway
        assertEquals(1, task.getNbExecuted());
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
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
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
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
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
        assertEquals(Speed.VERY_FAST, synchro.getState().getSpeed());
        synchro.setSpeed(Speed.FAST);
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());
        assertEquals(Speed.FAST, synchro.getState().getSpeed());
    }

    public void testSchedule() throws Exception {
        // create mocks with no bound to be sure they won't stop before the end of the test
        ConditionalTaskState conditionalTaskState = new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(0, 0));
        MockConditionalTask mockRun1 = new MockConditionalTask(conditionalTaskState);
        MockConditionalTask mockRun2 = new MockConditionalTask(conditionalTaskState);
        MockConditionalTask mockRun3 = new MockConditionalTask(conditionalTaskState);

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
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() == 0);

        mockRun1.resetNbExecuted();
        synchro.schedule(mockRun2);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() == 0);

        mockRun1.resetNbExecuted();
        synchro.schedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertTrue(mockRun1.getNbExecuted() >= 1);
        assertTrue(mockRun2.getNbExecuted() >= 1);
        assertTrue(mockRun3.getNbExecuted() == 0);
        assertTrue(mockRun1.getNbExecuted() > mockRun2.getNbExecuted());

        mockRun1.resetNbExecuted();
        mockRun2.resetNbExecuted();
        synchro.schedule(mockRun3);
        synchro.unschedule(mockRun1);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(Synchronizer.CYCLE_LENGTH_CORE_MS / synchro.getSpeed().getSpeed() + 100);

        assertTrue(mockRun1.getNbExecuted() == 0);
        assertTrue(mockRun2.getNbExecuted() == 0);
        assertTrue(mockRun3.getNbExecuted() >= 1);

    }

}
