package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.time.TimeController;


// FIXME remove this class once another real one is used
public class FakeJob extends UnfrequentRunnable {

    @Override
    public void init(SynchronizedRunnableState state, CyclicBarrier barrier, TimeController timeController) {
        super.init(state, barrier, timeController);
    }

    @Override
    public void executeUnfrequentStep() {
        System.out.println("Executing Unfrequent FakeJob");
    }

}
