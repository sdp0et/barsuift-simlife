package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;


public class MockSynchronizedRunnable extends AbstractSynchronizedRunnable {

    private int nbExecuted;

    public MockSynchronizedRunnable(CyclicBarrier barrier, SynchronizedRunnableState state) {
        super(barrier, state);
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
