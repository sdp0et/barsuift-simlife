package barsuift.simLife.process;

import barsuift.simLife.time.SimLifeDate;


public class DateUpdater extends AbstractSynchronizedRunnable {

    private final SimLifeDate date;

    public DateUpdater(SimLifeDate date) {
        super();
        this.date = date;
    }

    @Override
    public void executeStep() {
        date.addMillis(Synchronizer.CYCLE_LENGTH_MS);
    }

}
