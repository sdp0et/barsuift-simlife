package barsuift.simLife.condition;

import junit.framework.TestCase;


public class BasicBoundConditionTest extends TestCase {

    private BasicBoundCondition condition;

    private BoundConditionState state;

    protected void setUp() throws Exception {
        super.setUp();
        state = new BoundConditionState(5, 2);
        condition = new BasicBoundCondition(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        condition = null;
    }

    public void testEvaluate() {
        // 3/5
        assertFalse(condition.evaluate());
        // 4/5
        assertFalse(condition.evaluate());

        // 5/5
        assertTrue(condition.evaluate());
        // 6/5
        assertTrue(condition.evaluate());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(2, condition.getState().getCount());
        assertEquals(5, condition.getState().getBound());
        condition.evaluate();
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(3, condition.getState().getCount());
        assertEquals(5, condition.getState().getBound());
    }

}
