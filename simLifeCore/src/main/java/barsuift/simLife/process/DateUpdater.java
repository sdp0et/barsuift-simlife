package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.time.SimLifeDate;
import barsuift.simLife.time.TimeController;


public class DateUpdater extends AbstractSynchronizedRunnable implements SynchronizedRunnable {

    private SimLifeDate date;

    @Override
    public void init(SynchronizedRunnableState state, CyclicBarrier barrier, TimeController timeController) {
        super.init(state, barrier, timeController);
        this.date = timeController.getUniverse().getDate();
    }

    @Override
    public void executeStep() {
        date.addMillis(100);
    }

}
