package barsuift.simLife.condition;

import junit.framework.TestCase;
import barsuift.simLife.process.MockSingleRunSynchronizedRunnable;
import barsuift.simLife.process.MockSynchronizedRunnable;


public class BoundConditionStateFactoryTest extends TestCase {

    public void testCreateBoundConditionState() {
        BoundConditionStateFactory factory = new BoundConditionStateFactory();

        BoundConditionState mockSynchronizedState = factory.createBoundConditionState(MockSynchronizedRunnable.class);
        assertEquals(10, mockSynchronizedState.getBound());
        assertEquals(0, mockSynchronizedState.getCount());

        BoundConditionState unknownState = factory.createBoundConditionState(MockSingleRunSynchronizedRunnable.class);
        assertEquals(1, unknownState.getBound());
        assertEquals(0, unknownState.getCount());
    }

}
