package barsuift.simLife.condition;

import junit.framework.TestCase;


public class CyclicConditionTest extends TestCase {

    private CyclicCondition condition;

    private CyclicConditionState state;

    protected void setUp() throws Exception {
        super.setUp();
        state = new CyclicConditionState(5, 2);
        condition = new CyclicCondition(state);
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

        // 1/5
        assertFalse(condition.evaluate());
        // 2/5
        assertFalse(condition.evaluate());
        // 3/5
        assertFalse(condition.evaluate());
        // 4/5
        assertFalse(condition.evaluate());

        // 5/5
        assertTrue(condition.evaluate());
    }

    public void testGetState() throws InterruptedException {
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(2, condition.getState().getCount());
        assertEquals(5, condition.getState().getCycle());
        condition.evaluate();
        assertEquals(state, condition.getState());
        assertSame(state, condition.getState());
        assertEquals(3, condition.getState().getCount());
        assertEquals(5, condition.getState().getCycle());
    }

}
