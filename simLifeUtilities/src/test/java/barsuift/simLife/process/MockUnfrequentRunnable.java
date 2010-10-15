package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

public class MockUnfrequentRunnable extends UnfrequentRunnable {

    private int nbExecuted;

    public MockUnfrequentRunnable(CyclicBarrier barrier, UnfrequentRunnableState state) {
        super(barrier, state);
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
