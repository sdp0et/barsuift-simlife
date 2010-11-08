package barsuift.simLife.condition;

import junit.framework.TestCase;


public class BoundConditionStateFactoryTest extends TestCase {

    public void testCreateBoundConditionState() {
        BoundConditionStateFactory factory = new BoundConditionStateFactory();
        BoundConditionState unknownState = factory.createBoundConditionState(MockCondition.class);
        assertEquals(1, unknownState.getBound());
        assertEquals(0, unknownState.getCount());
    }

}
