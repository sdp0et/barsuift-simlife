package barsuift.simLife.process;

import barsuift.simLife.time.SimLifeDate;


public class DateUpdater extends AbstractSplitConditionalTask {

    private final SimLifeDate date;

    public DateUpdater(SplitConditionalTaskState state, SimLifeDate date) {
        super(state);
        this.date = date;
    }

    @Override
    // public void executeConditionalStep() {
    // date.addMillis(Synchronizer.CYCLE_LENGTH_CORE_MS);
    //
    // }
    public void executeSplitConditionalStep(int stepSize) {
        date.addMillis(stepSize * Synchronizer.CYCLE_LENGTH_3D_MS);

    }

}
