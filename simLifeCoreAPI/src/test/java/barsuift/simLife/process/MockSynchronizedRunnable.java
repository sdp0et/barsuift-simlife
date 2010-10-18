package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.time.TimeController;



public class MockSynchronizedRunnable extends AbstractSynchronizedRunnable {

    private int nbExecuted;

    @Override
    public void init(SynchronizedRunnableState state, CyclicBarrier barrier, TimeController timeController) {
        super.init(state, barrier, timeController);
        reset();
    }

    @Override
    public void executeStep() {
        nbExecuted++;
    }

    public int getNbExecuted() {
        return nbExecuted;
    }

    public void reset() {
        nbExecuted = 0;
    }

}
