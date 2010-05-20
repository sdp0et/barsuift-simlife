package barsuift.simLife.time;

import junit.framework.Assert;


public final class TimeCounterTestHelper extends Assert {

    private TimeCounterTestHelper() {
        // private constructor to enforce static access
    }

    public static void assertEquals(int days, int hours, int minutes, int seconds, TimeCounter counter) {
        assertEquals(days, counter.getDays());
        assertEquals(hours, counter.getHours());
        assertEquals(minutes, counter.getMinutes());
        assertEquals(seconds, counter.getSeconds());
    }

}
