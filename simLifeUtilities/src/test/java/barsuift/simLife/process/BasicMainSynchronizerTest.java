package barsuift.simLife.process;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;


public class BasicMainSynchronizerTest extends TestCase {

    private BasicMainSynchronizer synchro;

    private MainSynchronizerState state;

    private MockSplitConditionalTask taskFast;

    private MockConditionalTask taskSlow;

    protected void setUp() throws Exception {
        super.setUp();
        Speed speed = Speed.VERY_FAST;
        setUpFromSpeed(speed);
    }

    private void setUpFromSpeed(Speed speed) {
        int stepSize = speed.getSpeed();

        ConditionalTaskState conditionalTaskState = new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(4, 0));
        taskSlow = new MockConditionalTask(conditionalTaskState);

        ConditionalTaskState conditionalTask3DState = new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(60, 0));
        taskFast = new MockSplitConditionalTask(new SplitConditionalTaskState(conditionalTask3DState, stepSize));

        SynchronizerSlowState synchronizerSlowState = new SynchronizerSlowState(speed);
        SynchronizerFastState synchronizerFastState = new SynchronizerFastState(stepSize);
        state = new MainSynchronizerState(synchronizerSlowState, synchronizerFastState);
        synchro = new BasicMainSynchronizer(state);

        synchro.scheduleSlow(taskSlow);
        synchro.scheduleFast(taskFast);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        synchro = null;
        taskFast = null;
        taskSlow = null;
    }

    public void testSetSpeed() {
        assertEquals(Speed.VERY_FAST, synchro.getSpeed());
        assertEquals(Speed.VERY_FAST, synchro.getSynchronizerSlow().getSpeed());
        assertEquals(Speed.VERY_FAST.getSpeed(), synchro.getSynchronizerFast().getStepSize());
        synchro.setSpeed(Speed.FAST);
        assertEquals(Speed.FAST, synchro.getSpeed());
        assertEquals(Speed.FAST, synchro.getSynchronizerSlow().getSpeed());
        assertEquals(Speed.FAST.getSpeed(), synchro.getSynchronizerFast().getStepSize());
        synchro.setSpeed(Speed.NORMAL);
        assertEquals(Speed.NORMAL, synchro.getSpeed());
        assertEquals(Speed.NORMAL, synchro.getSynchronizerSlow().getSpeed());
        assertEquals(Speed.NORMAL.getSpeed(), synchro.getSynchronizerFast().getStepSize());
    }

    public void testOneStepNormal() throws InterruptedException {
        internalTestOneStep(Speed.NORMAL);
    }

    public void testOneStepFast() throws InterruptedException {
        internalTestOneStep(Speed.FAST);
    }

    public void testOneStepVeryFast() throws InterruptedException {
        internalTestOneStep(Speed.VERY_FAST);
    }

    private void internalTestOneStep(Speed speed) throws InterruptedException {
        setUpFromSpeed(speed);

        synchro.oneStep();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        assertFalse(synchro.isRunning());
        assertEquals(1, synchro.getNbStarts());
        assertEquals(1, synchro.getNbStops());
        assertEquals(1, synchro.getSynchronizerSlow().getNbStarts());
        assertEquals(1, synchro.getSynchronizerSlow().getNbStops());
        assertEquals(1, taskSlow.getNbExecuted());
        assertEquals(1, synchro.getSynchronizerFast().getNbStarts());
        assertEquals(1, synchro.getSynchronizerFast().getNbStops());
        assertEquals(20 / synchro.getSpeed().getSpeed(), taskFast.getNbExecuted());
        assertEquals(20, taskFast.getNbIncrementExecuted());

        synchro.oneStep();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        assertFalse(synchro.isRunning());
        assertEquals(2, synchro.getNbStarts());
        assertEquals(2, synchro.getNbStops());
        assertEquals(2, synchro.getSynchronizerSlow().getNbStarts());
        assertEquals(2, synchro.getSynchronizerSlow().getNbStops());
        assertEquals(2, taskSlow.getNbExecuted());
        assertEquals(2, synchro.getSynchronizerFast().getNbStarts());
        assertEquals(2, synchro.getSynchronizerFast().getNbStops());
        assertEquals(40 / synchro.getSpeed().getSpeed(), taskFast.getNbExecuted());
        assertEquals(40, taskFast.getNbIncrementExecuted());

        synchro.oneStep();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        assertFalse(synchro.isRunning());
        assertEquals(3, synchro.getNbStarts());
        assertEquals(3, synchro.getNbStops());
        assertEquals(3, synchro.getSynchronizerSlow().getNbStarts());
        assertEquals(3, synchro.getSynchronizerSlow().getNbStops());
        assertEquals(3, taskSlow.getNbExecuted());
        assertEquals(3, synchro.getSynchronizerFast().getNbStarts());
        assertEquals(3, synchro.getSynchronizerFast().getNbStops());
        assertEquals(60 / synchro.getSpeed().getSpeed(), taskFast.getNbExecuted());
        assertEquals(60, taskFast.getNbIncrementExecuted());

        synchro.oneStep();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        assertFalse(synchro.isRunning());
        assertEquals(4, synchro.getNbStarts());
        assertEquals(4, synchro.getNbStops());
        assertEquals(4, synchro.getSynchronizerSlow().getNbStarts());
        assertEquals(4, synchro.getSynchronizerSlow().getNbStops());
        assertEquals(4, taskSlow.getNbExecuted());
        assertEquals(4, synchro.getSynchronizerFast().getNbStarts());
        assertEquals(4, synchro.getSynchronizerFast().getNbStops());
        // the task3D should not be executed anymore, as it has reach its bound
        assertEquals(60 / synchro.getSpeed().getSpeed(), taskFast.getNbExecuted());
        assertEquals(60, taskFast.getNbIncrementExecuted());

        synchro.oneStep();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        assertFalse(synchro.isRunning());
        assertEquals(5, synchro.getNbStarts());
        assertEquals(5, synchro.getNbStops());
        assertEquals(5, synchro.getSynchronizerSlow().getNbStarts());
        assertEquals(5, synchro.getSynchronizerSlow().getNbStops());
        // the taskCore should not be executed anymore, as it has reach its bound
        assertEquals(4, taskSlow.getNbExecuted());
        assertEquals(5, synchro.getSynchronizerFast().getNbStarts());
        assertEquals(5, synchro.getSynchronizerFast().getNbStops());
        // the task3D should not be executed anymore, as it has reach its bound
        assertEquals(60 / synchro.getSpeed().getSpeed(), taskFast.getNbExecuted());
        assertEquals(60, taskFast.getNbIncrementExecuted());
    }

    public void testStartNormal() throws InterruptedException {
        internalTestStart(Speed.NORMAL);
    }

    public void testStartFast() throws InterruptedException {
        internalTestStart(Speed.FAST);
    }

    public void testStartVeryFast() throws InterruptedException {
        internalTestStart(Speed.VERY_FAST);
    }

    private void internalTestStart(Speed speed) throws InterruptedException {
        setUpFromSpeed(speed);

        assertFalse(synchro.isRunning());

        synchro.start();
        // wait a little longer to be sure the tasks complete (5 core cycles should be enough)
        Thread.sleep(5 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        assertTrue(synchro.isRunning());
        assertEquals(1, synchro.getNbStarts());
        assertEquals(0, synchro.getNbStops());
        assertEquals(1, synchro.getSynchronizerSlow().getNbStarts());
        assertEquals(0, synchro.getSynchronizerSlow().getNbStops());
        assertEquals(4, taskSlow.getNbExecuted());
        assertEquals(1, synchro.getSynchronizerFast().getNbStarts());
        assertEquals(0, synchro.getSynchronizerFast().getNbStops());
        assertEquals(60 / synchro.getSpeed().getSpeed(), taskFast.getNbExecuted());
        assertEquals(60, taskFast.getNbIncrementExecuted());


        synchro.stop();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        assertFalse(synchro.isRunning());
        assertEquals(1, synchro.getNbStarts());
        assertEquals(1, synchro.getNbStops());
        assertEquals(1, synchro.getSynchronizerSlow().getNbStarts());
        assertEquals(1, synchro.getSynchronizerSlow().getNbStops());
        assertEquals(4, taskSlow.getNbExecuted());
        assertEquals(1, synchro.getSynchronizerFast().getNbStarts());
        assertEquals(1, synchro.getSynchronizerFast().getNbStops());
        assertEquals(60 / synchro.getSpeed().getSpeed(), taskFast.getNbExecuted());
        assertEquals(60, taskFast.getNbIncrementExecuted());
    }

    public void testPublisher() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(synchro);

        synchro.start();
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        synchro.stop();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        assertEquals(1, publisherHelper.nbUpdated());
        assertNull(publisherHelper.getUpdateObjects().get(0));

        publisherHelper.reset();
        synchro.oneStep();
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        assertEquals(2, publisherHelper.nbUpdated());
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
        Thread.sleep(2 * BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 50);
        try {
            synchro.start();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        try {
            synchro.oneStep();
            fail("IllegalStateException expected");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
    }

    public void testGetState() {
        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());

        assertEquals(Speed.VERY_FAST.getSpeed(), synchro.getState().getSynchronizerFastState().getStepSize());
        assertEquals(Speed.VERY_FAST.getSpeed(), synchro.getState().getSynchronizerSlowState().getSpeed().getSpeed());

        synchro.setSpeed(Speed.NORMAL);

        assertEquals(state, synchro.getState());
        assertSame(state, synchro.getState());

        assertEquals(Speed.NORMAL.getSpeed(), synchro.getState().getSynchronizerFastState().getStepSize());
        assertEquals(Speed.NORMAL.getSpeed(), synchro.getState().getSynchronizerSlowState().getSpeed().getSpeed());


    }

}
