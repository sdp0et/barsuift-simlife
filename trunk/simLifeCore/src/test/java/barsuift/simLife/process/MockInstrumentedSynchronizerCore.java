package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;


public class MockInstrumentedSynchronizerCore extends BasicSynchronizerCore {

    private CyclicBarrier barrier;

    private int scheduleCalled;

    private List<Runnable> runnablesToSchedule;

    private int unscheduleCalled;

    private List<Runnable> runnablesToUnschedule;

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
        runnablesToSchedule = new ArrayList<Runnable>();
        unscheduleCalled = 0;
        runnablesToUnschedule = new ArrayList<Runnable>();
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
    public void schedule(SynchronizedRunnable runnable) {
        super.schedule(runnable);
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
        super.unschedule(runnable);
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
