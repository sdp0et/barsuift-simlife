package barsuift.simLife.process;

import barsuift.simLife.time.TimeController;



public class MockSynchronizedRunnable extends AbstractSynchronizedRunnable {

    private int nbExecuted;

    @Override
    public void init(SynchronizedRunnableState state, TimeController timeController) {
        super.init(state, timeController);
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
