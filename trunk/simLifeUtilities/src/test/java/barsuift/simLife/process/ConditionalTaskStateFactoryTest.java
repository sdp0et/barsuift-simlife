package barsuift.simLife.process;

import junit.framework.TestCase;
import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;


public class ConditionalTaskStateFactoryTest extends TestCase {

    public void testCreateConditionalTaskState() {
        ConditionalTaskState taskState;
        CyclicConditionState executionCondition;
        BoundConditionState endingCondition;
        ConditionalTaskStateFactory factory = new ConditionalTaskStateFactory();

        // existing class in properties file
        taskState = factory.createConditionalTaskState(AbstractSplitConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(5, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(10, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        // non existing class in properties file
        taskState = factory.createConditionalTaskState(SplitConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(1, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(0, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());

        // existing class in properties file
        taskState = factory.createConditionalTaskState(AbstractConditionalTask.class);
        executionCondition = taskState.getExecutionCondition();
        assertEquals(2, executionCondition.getCycle());
        assertEquals(0, executionCondition.getCount());
        endingCondition = taskState.getEndingCondition();
        assertEquals(0, endingCondition.getBound());
        assertEquals(0, endingCondition.getCount());


    }

}
