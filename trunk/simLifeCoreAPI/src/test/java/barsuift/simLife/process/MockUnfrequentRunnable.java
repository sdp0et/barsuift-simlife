package barsuift.simLife.process;

import barsuift.simLife.time.TimeController;


public class MockUnfrequentRunnable extends UnfrequentRunnable {

    private int nbExecuted;

    @Override
    public void init(SynchronizedRunnableState state, TimeController timeController) {
        super.init(state, timeController);
        resetNbExecuted();
    }

    @Override
    public void executeUnfrequentStep() {
        nbExecuted++;
    }

    public int getNbExecuted() {
        return nbExecuted;
    }

    public void resetNbExecuted() {
        nbExecuted = 0;
    }

}
