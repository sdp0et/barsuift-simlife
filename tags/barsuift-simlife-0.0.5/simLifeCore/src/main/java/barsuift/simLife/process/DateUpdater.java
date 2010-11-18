package barsuift.simLife.process;

import barsuift.simLife.time.SimLifeDate;


public class DateUpdater extends AbstractConditionalTask {

    private final SimLifeDate date;

    public DateUpdater(ConditionalTaskState state, SimLifeDate date) {
        super(state);
        this.date = date;
    }

    @Override
    public void executeConditionalStep() {
        date.addMillis(Synchronizer.CYCLE_LENGTH_CORE_MS);
    }

}
