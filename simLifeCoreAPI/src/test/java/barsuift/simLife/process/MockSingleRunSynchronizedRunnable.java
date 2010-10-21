package barsuift.simLife.process;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.time.TimeController;



public class MockSingleRunSynchronizedRunnable implements SynchronizedRunnable {

    private SynchronizedRunnableState state;

    private CyclicBarrier barrier;

    private TimeController timeController;

    private int nbExecuted;

    private boolean running;

    private int synchronizeCalled;

    private int stopCalled;

    @Override
    public void init(SynchronizedRunnableState state, TimeController timeController) {
        this.state = state;
        this.timeController = timeController;
    }

    public int getNbExecuted() {
        return nbExecuted;
    }

    public void reset() {
        nbExecuted = 0;
        stopCalled = 0;
        synchronizeCalled = 0;
    }

    @Override
    public SynchronizedRunnableState getState() {
        return state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    @Override
    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    @Override
    public void run() {
        nbExecuted++;
        try {
            barrier.await();
        } catch (InterruptedException e) {
            // nothing to do
        } catch (BrokenBarrierException e) {
            // nothing to do
        }
    }

    @Override
    public void stop() {
        stopCalled++;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public TimeController getTimeController() {
        return timeController;
    }

}
