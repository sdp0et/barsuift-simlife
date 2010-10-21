package barsuift.simLife.process;

import barsuift.simLife.time.TimeController;


public class FakeJob extends UnfrequentRunnable {

    @Override
    public void init(SynchronizedRunnableState state, TimeController timeController) {
        super.init(state, timeController);
    }

    @Override
    public void executeUnfrequentStep() {
        System.out.println("Executing Unfrequent FakeJob");
    }

}
