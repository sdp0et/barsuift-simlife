package barsuift.simLife.time;

import junit.framework.TestCase;


public class FpsCounterTest extends TestCase {

    private FpsCounter counter;

    protected void setUp() throws Exception {
        super.setUp();
        counter = new FpsCounter();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        counter = null;
    }

    public void testSingleTick() {
        // test default values
        assertEquals(1f, counter.getExecTime());
        assertEquals(1f, counter.getAvgExecTime());
        assertEquals(1, counter.getFps());
        assertEquals(1, counter.getAvgFps());
        // one single tick
        counter.tick();
        // nothing should have changed
        assertEquals(1f, counter.getExecTime());
        assertEquals(1f, counter.getAvgExecTime());
        assertEquals(1, counter.getFps());
        assertEquals(1, counter.getAvgFps());
    }

    public void testSeveralTicks() {
        // test default values
        assertEquals(1f, counter.getExecTime());
        assertEquals(1f, counter.getAvgExecTime());
        assertEquals(1, counter.getFps());
        assertEquals(1, counter.getAvgFps());
        // 10 ticks
        tenTicks();
        assertTrue("just ensure it does not take more than 10 seconds", counter.getExecTime() < 10000);
        assertTrue("the exec time must always be positive", counter.getExecTime() > 0);
        assertEquals("the average time should not have been recomputed", 1f, counter.getAvgExecTime());
        assertTrue(counter.getFps() > 0); // we can not assume anything else
        assertEquals("the average fps should not have been recomputed", 1, counter.getAvgFps());
    }

    public void testHundredTicks() {
        // test default values
        assertEquals(1f, counter.getExecTime());
        assertEquals(1f, counter.getAvgExecTime());
        assertEquals(1, counter.getFps());
        assertEquals(1, counter.getAvgFps());
        // 100 ticks
        for (int i = 0; i < 10; i++) {
            tenTicks();
        }
        assertTrue("just ensure it does not take more than 10 seconds", counter.getExecTime() < 10000);
        assertTrue("the exec time must always be positive", counter.getExecTime() > 0);
        assertTrue("just ensure it does not take more than 10 seconds", counter.getAvgExecTime() < 10000);
        assertTrue("the exec time must always be positive", counter.getAvgExecTime() > 0);
        assertTrue(counter.getFps() > 0); // we can not assume anything else
        assertTrue(counter.getAvgFps() > 0);// we can not assume anything else
    }

    private void tenTicks() {
        for (int i = 0; i < 10; i++) {
            counter.tick();
        }
    }

}