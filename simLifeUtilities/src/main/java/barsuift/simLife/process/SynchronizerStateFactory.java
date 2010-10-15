package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;


public class SynchronizerStateFactory {

    public SynchronizerState createSynchronizerState() {
        int speed = 1;
        List<SynchronizedRunnableState> runnables = new ArrayList<SynchronizedRunnableState>();
        SynchronizedRunnableState fakeJobState = new SynchronizedRunnableState(FakeJob.class);
        runnables.add(fakeJobState);
        // FIXME also try with an unfrequent fake job
        return new SynchronizerState(speed, runnables);
    }

}
