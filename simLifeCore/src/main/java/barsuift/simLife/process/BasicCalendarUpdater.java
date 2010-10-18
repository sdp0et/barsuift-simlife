package barsuift.simLife.process;

import java.util.Calendar;
import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.time.SimLifeCalendar;
import barsuift.simLife.time.TimeController;


public class BasicCalendarUpdater extends AbstractSynchronizedRunnable implements SynchronizedRunnable {

    private SimLifeCalendar calendar;

    @Override
    public void init(SynchronizedRunnableState state, CyclicBarrier barrier, TimeController timeController) {
        super.init(state, barrier, timeController);
        this.calendar = timeController.getCalendar();
    }

    @Override
    public void executeStep() {
        System.out.println("Executing CalendarUpdater");
        calendar.add(Calendar.MILLISECOND, 100);
    }

}
