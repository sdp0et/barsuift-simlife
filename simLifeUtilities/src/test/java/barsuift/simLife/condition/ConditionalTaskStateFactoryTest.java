package barsuift.simLife.condition;

import junit.framework.TestCase;
import barsuift.simLife.process.ConditionalTaskState;
import barsuift.simLife.process.MockSingleSynchronizedTask;
import barsuift.simLife.process.MockSplitBoundedTask;
import barsuift.simLife.process.MockSynchronizedTask;


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
        assertEquals(-1, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        taskState = factory.createConditionalTaskState(MockSplitBoundedTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(2, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(-1, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());


    }

}
