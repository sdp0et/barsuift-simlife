package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

// FIXME remove this class once another real one is used
public class FakeJob extends AbstractSynchronizedRunnable {

    public FakeJob(CyclicBarrier barrier, SynchronizedRunnableState state) {
        super(barrier, state);
    }

    @Override
    public void executeStep() {
        System.out.println("Executing FakeJob");
    }

}
