package barsuift.simLife.process;

import barsuift.simLife.time.TimeController;



public class MockSynchronizedRunnable extends AbstractSynchronizedRunnable {

    private int nbExecuted;

    @Override
    public void init(SynchronizedRunnableState state, TimeController timeController) {
        super.init(state, timeController);
        resetNbExecuted();
    }

    @Override
    public void executeStep() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // nothing to do
        }
        nbExecuted++;
    }

    public int getNbExecuted() {
        return nbExecuted;
    }

    public void resetNbExecuted() {
        nbExecuted = 0;
    }

}
