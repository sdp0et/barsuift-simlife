package barsuift.simLife.process;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.process.ConditionalTaskState;


public class ConditionalTaskStateFactoryTest extends TestCase {

    public void testCreateConditionalTaskState() {
        ConditionalTaskState taskState;
        CyclicConditionState executionCondition;
        BoundConditionState endingCondition;
        ConditionalTaskStateFactory factory = new ConditionalTaskStateFactory();

        taskState = factory.createConditionalTaskState(MockSynchronizedTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(5, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(10, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        taskState = factory.createConditionalTaskState(MockSingleSynchronizedTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(1, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(0, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        taskState = factory.createConditionalTaskState(MockSplitBoundedTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(2, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(0, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());


    }

}
