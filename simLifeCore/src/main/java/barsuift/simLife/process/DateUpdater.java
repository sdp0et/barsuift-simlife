package barsuift.simLife.process;

import barsuift.simLife.time.SimLifeDate;
import barsuift.simLife.time.TimeController;


public class DateUpdater extends AbstractSynchronizedRunnable implements SynchronizedRunnable {

    private SimLifeDate date;

    @Override
    public void init(SynchronizedRunnableState state, TimeController timeController) {
        super.init(state, timeController);
        this.date = timeController.getDate();
    }

    @Override
    public void executeStep() {
        date.addMillis(100);
    }

}
