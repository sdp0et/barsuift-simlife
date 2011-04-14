package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;

import barsuift.simLife.UtilDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;


public class MockMainSynchronizer extends BasicPublisher implements MainSynchronizer {

    private Speed speed;

    private boolean running;

    private long startCalled;

    private long oneStepCalled;

    private long stopCalled;

    private long stopAndWaitCalled;

    private MainSynchronizerState state;

    private int synchronizeCalled;

    private List<SplitConditionalTask> fastScheduled;

    private List<SplitConditionalTask> fastUnscheduled;

    private List<ConditionalTask> slowScheduled;

    private List<ConditionalTask> slowUnscheduled;

    private SynchronizerFast synchronizerFast;

    private SynchronizerSlow synchronizerSlow;

    public MockMainSynchronizer() {
        super(null);
        reset();
    }

    public void reset() {
        speed = Speed.DEFAULT_SPEED;
        running = false;
        startCalled = 0;
        oneStepCalled = 0;
        stopCalled = 0;
        stopAndWaitCalled = 0;
        state = UtilDataCreatorForTests.createSpecificMainSynchronizerState();
        synchronizeCalled = 0;
        fastScheduled = new ArrayList<SplitConditionalTask>();
        fastUnscheduled = new ArrayList<SplitConditionalTask>();
        slowScheduled = new ArrayList<ConditionalTask>();
        slowUnscheduled = new ArrayList<ConditionalTask>();
        synchronizerFast = new MockSynchronizerFast();
        synchronizerSlow = new MockSynchronizerSlow();
    }

    @Override
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public Speed getSpeed() {
        return speed;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void start() throws IllegalStateException {
        startCalled++;
    }

    @Override
    public long getNbStarts() {
        return startCalled;
    }

    @Override
    public void oneStep() {
        oneStepCalled++;
    }

    public long getNbOneStepCalled() {
        return oneStepCalled;
    }

    @Override
    public void stop() {
        stopCalled++;
    }

    @Override
    public long getNbStops() {
        return stopCalled;
    }

    @Override
    public void stopAndWait() {
        stopAndWaitCalled++;
    }

    public long getNbStopAndWaitCalled() {
        return stopAndWaitCalled;
    }

    @Override
    public MainSynchronizerState getState() {
        return state;
    }

    public void setState(MainSynchronizerState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

    @Override
    public void scheduleFast(SplitConditionalTask task) {
        fastScheduled.add(task);
    }

    public List<SplitConditionalTask> getFastScheduled() {
        return fastScheduled;
    }

    @Override
    public void unscheduleFast(SplitConditionalTask task) {
        fastUnscheduled.add(task);
    }

    public List<SplitConditionalTask> getFastUnscheduled() {
        return fastUnscheduled;
    }

    @Override
    public void scheduleSlow(ConditionalTask task) {
        slowScheduled.add(task);
    }

    public List<ConditionalTask> getSlowScheduled() {
        return slowScheduled;
    }

    @Override
    public void unscheduleSlow(ConditionalTask task) {
        slowUnscheduled.add(task);
    }

    public List<ConditionalTask> getSlowUnscheduled() {
        return slowUnscheduled;
    }

    @Override
    public SynchronizerFast getSynchronizerFast() {
        return synchronizerFast;
    }

    public void setSynchronizerFast(SynchronizerFast synchronizerFast) {
        this.synchronizerFast = synchronizerFast;
    }

    @Override
    public SynchronizerSlow getSynchronizerSlow() {
        return synchronizerSlow;
    }

    public void setSynchronizerSlow(SynchronizerSlow synchronizerSlow) {
        this.synchronizerSlow = synchronizerSlow;
    }

}
