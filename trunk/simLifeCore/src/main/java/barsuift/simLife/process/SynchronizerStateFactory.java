package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;


public class SynchronizerStateFactory {

    public SynchronizerState createSynchronizerState() {
        int speed = 1;

        List<SynchronizedRunnableState> synchroRunnables = new ArrayList<SynchronizedRunnableState>();
        SynchronizedRunnableState calendarUpdaterState = new SynchronizedRunnableState(BasicCalendarUpdater.class);
        synchroRunnables.add(calendarUpdaterState);

        List<UnfrequentRunnableState> unfrequentRunnables = new ArrayList<UnfrequentRunnableState>();
        UnfrequentRunnableState fakeJobState = new UnfrequentRunnableState(FakeJob.class, 5, 2);
        unfrequentRunnables.add(fakeJobState);

        return new SynchronizerState(speed, synchroRunnables, unfrequentRunnables);
    }

}
