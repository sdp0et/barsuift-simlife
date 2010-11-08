package barsuift.simLife.condition;

import junit.framework.TestCase;


public class SplitCyclicConditionTest extends TestCase {

    private SplitCyclicCondition condition;

    private CyclicConditionState state;

    protected void setUp() throws Exception {
        super.setUp();
        state = new CyclicConditionState(3, 2);
        condition = new SplitCyclicCondition(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        condition = null;
    }

    public void testEvaluate() {
        // 4/3
        assertTrue(condition.evaluate(2));
        // 6/3
        assertTrue(condition.evaluate(2));
        // 8/3
        assertFalse(condition.evaluate(2));
        // 10/3
        assertTrue(condition.evaluate(2));
        // 12/3
        assertTrue(condition.evaluate(2));
        // 14/3
        assertFalse(condition.evaluate(2));
        // 16/3
        assertTrue(condition.evaluate(2));
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(2, condition.getState().getCount());
        assertEquals(3, condition.getState().getCycle());
        condition.evaluate(2);
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(1, condition.getState().getCount());
        assertEquals(3, condition.getState().getCycle());
    }

}
