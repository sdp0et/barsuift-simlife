package barsuift.simLife.j2d;

import junit.framework.TestCase;
import barsuift.simLife.time.TimeCounter;
import barsuift.simLife.time.TimeCounterState;


public class TimerDisplayTest extends TestCase {

    private TimerDisplay display;

    private TimeCounter counter;

    protected void setUp() throws Exception {
        super.setUp();
        counter = new TimeCounter(new TimeCounterState());
        display = new TimerDisplay(counter);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        counter = null;
        display = null;
    }

    public void testUpdate() {
        assertEquals("0 days 00:00:00", display.getText());
        counter.increment();
        assertEquals("0 days 00:00:01", display.getText());
        for (int i = 0; i < 59; i++) {
            counter.increment();
        }
        assertEquals("0 days 00:01:00", display.getText());
        for (int i = 0; i < 60 * 59; i++) {
            counter.increment();
        }
        assertEquals("0 days 01:00:00", display.getText());
        for (int i = 0; i < (60 * 13 + 23); i++) {
            counter.increment();
        }
        assertEquals("0 days 01:13:23", display.getText());
        for (int i = 0; i < 60 * 60 * 35; i++) {
            counter.increment();
        }
        assertEquals("1 days 12:13:23", display.getText());
    }

}
