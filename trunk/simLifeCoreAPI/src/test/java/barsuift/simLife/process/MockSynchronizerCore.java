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

    private List<Runnable> runnablesToSchedule;

    private int unscheduleCalled;

    private List<Runnable> runnablesToUnschedule;

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
        runnablesToSchedule = new ArrayList<Runnable>();
        unscheduleCalled = 0;
        runnablesToUnschedule = new ArrayList<Runnable>();
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
    public void schedule(SynchronizedRunnable runnable) {
        scheduleCalled++;
        runnablesToSchedule.add(runnable);
    }

    public int getNbScheduleCalled() {
        return scheduleCalled;
    }

    public List<Runnable> getScheduledRunnables() {
        return runnablesToSchedule;
    }

    @Override
    public void unschedule(SynchronizedRunnable runnable) {
        unscheduleCalled++;
        runnablesToUnschedule.add(runnable);
    }

    public int getNbUnscheduleCalled() {
        return unscheduleCalled;
    }

    public List<Runnable> getUnscheduledRunnables() {
        return runnablesToUnschedule;
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
