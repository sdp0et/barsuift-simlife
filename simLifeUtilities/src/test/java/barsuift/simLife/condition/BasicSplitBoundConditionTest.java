package barsuift.simLife.condition;

import junit.framework.TestCase;


public class BasicSplitBoundConditionTest extends TestCase {

    private BasicSplitBoundCondition condition;

    private BoundConditionState state;

    protected void setUp() throws Exception {
        super.setUp();
        state = new BoundConditionState(7, 2);
        condition = new BasicSplitBoundCondition(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        condition = null;
    }

    public void testEvaluate() {
        // 4/7
        assertFalse(condition.evaluate(2));
        // 6/7
        assertFalse(condition.evaluate(2));

        // 8/7
        assertTrue(condition.evaluate(2));
        // 10/7
        assertTrue(condition.evaluate(2));
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(2, condition.getState().getCount());
        assertEquals(7, condition.getState().getBound());
        condition.evaluate(2);
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(4, condition.getState().getCount());
        assertEquals(7, condition.getState().getBound());
    }

}
