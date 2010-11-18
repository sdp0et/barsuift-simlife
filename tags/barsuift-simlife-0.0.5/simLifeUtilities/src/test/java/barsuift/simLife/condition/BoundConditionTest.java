package barsuift.simLife.condition;

import junit.framework.TestCase;


public class BoundConditionTest extends TestCase {

    private BoundCondition condition;

    private BoundConditionState state;

    protected void setUp() throws Exception {
        super.setUp();
        setUpFromParams(5, 2);
    }

    private void setUpFromParams(int bound, int count) {
        state = new BoundConditionState(bound, count);
        condition = new BoundCondition(state);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        state = null;
        condition = null;
    }

    /*
     * No bound : will always return false
     */
    public void testEvaluate0() {
        setUpFromParams(0, 0);
        // 1/0
        assertFalse(condition.evaluate());
        // 2/0
        assertFalse(condition.evaluate());
        // 3/0
        assertFalse(condition.evaluate());
        // 4/0
        assertFalse(condition.evaluate());
    }

    /*
     * One execution : return true the first time
     */
    public void testEvaluate1() {
        setUpFromParams(1, 0);
        // 1/1
        assertTrue(condition.evaluate());
        // 2/1
        assertTrue(condition.evaluate());
        // 3/1
        assertTrue(condition.evaluate());
        // 4/1
        assertTrue(condition.evaluate());
    }

    /*
     * Two execution : will return false, and true
     */
    public void testEvaluate2() {
        setUpFromParams(2, 0);
        // 1/2
        assertFalse(condition.evaluate());
        // 2/2
        assertTrue(condition.evaluate());
        // 3/2
        assertTrue(condition.evaluate());
        // 4/2
        assertTrue(condition.evaluate());
    }

    /*
     * 3 executions : false, false, true
     */
    public void testEvaluate3() {
        setUpFromParams(5, 2);
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
