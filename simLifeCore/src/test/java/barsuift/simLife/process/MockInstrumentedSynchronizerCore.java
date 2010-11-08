package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;


public class MockInstrumentedSynchronizerCore extends BasicSynchronizerCore {

    private CyclicBarrier barrier;

    private int scheduleCalled;

    private List<SynchronizedTask> tasksToSchedule;

    private int unscheduleCalled;

    private List<SynchronizedTask> tasksToUnschedule;

    private int startCalled;

    private int stopCalled;

    private int synchronizeCalled;

    public MockInstrumentedSynchronizerCore(SynchronizerCoreState state) {
        super(state);
        reset();
    }

    public void reset() {
        barrier = new CyclicBarrier(1);
        scheduleCalled = 0;
        tasksToSchedule = new ArrayList<SynchronizedTask>();
        unscheduleCalled = 0;
        tasksToUnschedule = new ArrayList<SynchronizedTask>();
        startCalled = 0;
        stopCalled = 0;
        synchronizeCalled = 0;
    }

    public void setBarrier(CyclicBarrier barrier) {
        super.setBarrier(barrier);
        this.barrier = barrier;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    @Override
    public void setSpeed(Speed speed) {
        super.setSpeed(speed);
    }

    @Override
    public Speed getSpeed() {
        return super.getSpeed();
    }

    @Override
    public boolean isRunning() {
        return super.isRunning();
    }

    @Override
    public void schedule(SynchronizedTask task) {
        super.schedule(task);
        scheduleCalled++;
        tasksToSchedule.add(task);
    }

    public int getNbScheduleCalled() {
        return scheduleCalled;
    }

    public List<SynchronizedTask> getScheduledTasks() {
        return tasksToSchedule;
    }

    @Override
    public void unschedule(SynchronizedTask task) {
        super.unschedule(task);
        unscheduleCalled++;
        tasksToUnschedule.add(task);
    }

    public int getNbUnscheduleCalled() {
        return unscheduleCalled;
    }

    public List<SynchronizedTask> getUnscheduledTasks() {
        return tasksToUnschedule;
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
        startCalled++;
    }

    public int getNbStartCalled() {
        return startCalled;
    }

    @Override
    public void stop() {
        super.stop();
        stopCalled++;
    }

    public int getNbStopCalled() {
        return stopCalled;
    }

    @Override
    public SynchronizerCoreState getState() {
        return super.getState();
    }

    @Override
    public void synchronize() {
        super.synchronize();
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

}
