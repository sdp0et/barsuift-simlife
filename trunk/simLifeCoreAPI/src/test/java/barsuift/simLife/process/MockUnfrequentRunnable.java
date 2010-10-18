package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.time.TimeController;


public class MockUnfrequentRunnable extends UnfrequentRunnable {

    private int nbExecuted;

    @Override
    public void init(SynchronizedRunnableState state, CyclicBarrier barrier, TimeController timeController) {
        super.init(state, barrier, timeController);
        reset();
    }

    @Override
    public void executeUnfrequentStep() {
        nbExecuted++;
    }

    public int getNbExecuted() {
        return nbExecuted;
    }

    public void reset() {
        nbExecuted = 0;
    }

}
