package barsuift.simLife.condition;

import junit.framework.TestCase;
import barsuift.simLife.process.MockSingleSynchronizedTask;
import barsuift.simLife.process.MockSynchronizedTask;


public class BoundConditionStateFactoryTest extends TestCase {

    public void testCreateBoundConditionState() {
        BoundConditionStateFactory factory = new BoundConditionStateFactory();

        BoundConditionState mockSynchronizedState = factory.createBoundConditionState(MockSynchronizedTask.class);
        assertEquals(10, mockSynchronizedState.getBound());
        assertEquals(0, mockSynchronizedState.getCount());

        BoundConditionState unknownState = factory.createBoundConditionState(MockSingleSynchronizedTask.class);
        assertEquals(1, unknownState.getBound());
        assertEquals(0, unknownState.getCount());
    }

}
