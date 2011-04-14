package barsuift.simLife.process;

import barsuift.simLife.time.SimLifeDate;


public class DateUpdater extends AbstractSplitConditionalTask {

    private final SimLifeDate date;

    public DateUpdater(SplitConditionalTaskState state, SimLifeDate date) {
        super(state);
        this.date = date;
    }

    @Override
    public void executeSplitConditionalStep(int stepSize) {
        date.addMillis(stepSize * BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS);

    }

}
