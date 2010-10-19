package barsuift.simLife.process;

import java.util.Calendar;
import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.time.SimLifeCalendar;
import barsuift.simLife.time.TimeController;


public class CalendarUpdater extends AbstractSynchronizedRunnable implements SynchronizedRunnable {

    private SimLifeCalendar calendar;

    @Override
    public void init(SynchronizedRunnableState state, CyclicBarrier barrier, TimeController timeController) {
        super.init(state, barrier, timeController);
        this.calendar = timeController.getUniverse().getCalendar();
    }

    @Override
    public void executeStep() {
        calendar.add(Calendar.MILLISECOND, 100);
    }

}
