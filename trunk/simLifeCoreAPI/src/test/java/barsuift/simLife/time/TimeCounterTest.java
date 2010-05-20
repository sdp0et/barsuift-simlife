package barsuift.simLife.time;

import junit.framework.TestCase;


public class TimeCounterTest extends TestCase {

    private ObservableTestHelper observerHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        observerHelper = new ObservableTestHelper();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        observerHelper = null;
    }

    public void testIncrement() {
        TimeCounter counter = new TimeCounter(new TimeCounterState());
        TimeCounterTestHelper.assertEquals(0, 0, 0, 0, counter);

        counter.increment();
        TimeCounterTestHelper.assertEquals(0, 0, 0, 1, counter);

        for (int i = 0; i < 58; i++) {
            counter.increment();
        }
        TimeCounterTestHelper.assertEquals(0, 0, 0, 59, counter);

        counter.increment();
        TimeCounterTestHelper.assertEquals(0, 0, 1, 0, counter);

        counter.increment();
        TimeCounterTestHelper.assertEquals(0, 0, 1, 1, counter);

        for (int i = 0; i < 59; i++) {
            counter.increment();
        }
        TimeCounterTestHelper.assertEquals(0, 0, 2, 0, counter);

        for (int i = 0; i < 60 * 58; i++) {
            counter.increment();
        }
        TimeCounterTestHelper.assertEquals(0, 1, 0, 0, counter);

        for (int i = 0; i < 60 * 60 * 23; i++) {
            counter.increment();
        }
        TimeCounterTestHelper.assertEquals(1, 0, 0, 0, counter);
    }

    public void testObserver() {
        TimeCounter counter = new TimeCounter(new TimeCounterState());
        observerHelper.addObserver(counter);
        TimeCounterTestHelper.assertEquals(0, 0, 0, 0, counter);
        counter.increment();
        TimeCounterTestHelper.assertEquals(0, 0, 0, 1, counter);
        assertEquals(1, observerHelper.nbUpdated());
        assertEquals(null, observerHelper.getUpdateObjects().get(0));
    }

    public void testToString() {
        TimeCounter counter = new TimeCounter(new TimeCounterState());
        assertEquals("0 days 00:00:00", counter.toString());
        counter.increment();
        assertEquals("0 days 00:00:01", counter.toString());
        for (int i = 0; i < 59; i++) {
            counter.increment();
        }
        assertEquals("0 days 00:01:00", counter.toString());
        for (int i = 0; i < 60 * 59; i++) {
            counter.increment();
        }
        assertEquals("0 days 01:00:00", counter.toString());
        for (int i = 0; i < (60 * 13 + 23); i++) {
            counter.increment();
        }
        assertEquals("0 days 01:13:23", counter.toString());
        for (int i = 0; i < 60 * 60 * 35; i++) {
            counter.increment();
        }
        assertEquals("1 days 12:13:23", counter.toString());
    }

    public void testConstructors() {
        try {
            new TimeCounter((TimeCounterState) null);
            fail("Should throw an NullPointerException");
        } catch (NullPointerException e) {
            // OK expected exception
        }
        try {
            new TimeCounter(new TimeCounterState(-1));
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // OK expected exception
        }
    }

    public void testCompareTo() {
        TimeCounter counter1 = new TimeCounter(new TimeCounterState(0));
        TimeCounter counter2 = new TimeCounter(new TimeCounterState(0));
        TimeCounter counter3 = new TimeCounter(new TimeCounterState(12));
        TimeCounter counter4 = new TimeCounter(new TimeCounterState(0));
        for (int i = 0; i < 12; i++) {
            counter4.increment();
        }

        // 1 == 2
        // 3 == 4

        assertEquals(0, counter1.compareTo(counter1));
        assertEquals(0, counter1.compareTo(counter2));
        assertEquals(0, counter2.compareTo(counter1));
        assertEquals(0, counter2.compareTo(counter2));

        assertEquals(0, counter3.compareTo(counter3));
        assertEquals(0, counter3.compareTo(counter4));
        assertEquals(0, counter4.compareTo(counter3));
        assertEquals(0, counter4.compareTo(counter4));

        assertEquals(-12, counter1.compareTo(counter3));
        assertEquals(12, counter3.compareTo(counter1));

    }

    public void testGetState() {
        TimeCounterState state = new TimeCounterState(47);
        TimeCounter counter = new TimeCounter(state);
        assertEquals(state, counter.getState());
    }

}
