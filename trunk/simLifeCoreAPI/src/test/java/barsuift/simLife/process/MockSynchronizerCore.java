package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;


public class MockSynchronizerCore extends BasicPublisher implements SynchronizerCore {

    private CyclicBarrier barrier;

    private Speed speed;

    private boolean running;

    private int scheduleCalled;

    private List<SynchronizedTask> tasksToSchedule;

    private int unscheduleCalled;

    private List<SynchronizedTask> tasksToUnschedule;

    private int startCalled;

    private int stopCalled;

    private SynchronizerCoreState state;

    private int synchronizeCalled;

    public MockSynchronizerCore() {
        super(null);
        reset();
    }

    public void reset() {
        barrier = new CyclicBarrier(1);
        speed = Speed.NORMAL;
        running = false;
        scheduleCalled = 0;
        tasksToSchedule = new ArrayList<SynchronizedTask>();
        unscheduleCalled = 0;
        tasksToUnschedule = new ArrayList<SynchronizedTask>();
        startCalled = 0;
        stopCalled = 0;
        state = CoreDataCreatorForTests.createSpecificSynchronizerCoreState();
        synchronizeCalled = 0;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
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
    public void schedule(SynchronizedTask task) {
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
        startCalled++;
    }

    public int getNbStartCalled() {
        return startCalled;
    }

    @Override
    public void stop() {
        stopCalled++;
    }

    public int getNbStopCalled() {
        return stopCalled;
    }

    @Override
    public SynchronizerCoreState getState() {
        return state;
    }

    public void setState(SynchronizerCoreState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

}
