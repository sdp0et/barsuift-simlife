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
        date.addMillis(BasicSynchronizerCore.CYCLE_LENGTH_CORE_MS);
    }

}
