package barsuift.simLife.process;

import barsuift.simLife.time.TimeController;


public class FakeJob extends UnfrequentRunnable {

    private static int CLASS_COUNTER = 0;

    private final int counter;

    public FakeJob() {
        counter = CLASS_COUNTER++;
    }

    @Override
    public void init(SynchronizedRunnableState state, TimeController timeController) {
        super.init(state, timeController);
    }

    @Override
    public void executeUnfrequentStep() {
        System.out.println("Executing Unfrequent FakeJob " + counter);
    }

}
