package barsuift.simLife.condition;

import barsuift.simLife.Persistent;

/**
 * A cyclic condition evaluates to false except on a cyclic period.
 * <p>
 * For example, with a cycle of 3, successive calls to evaluate returns the following values :
 * <ol>
 * <li>{@code false}</li>
 * <li>{@code false}</li>
 * <li>{@code true}</li>
 * <li>{@code false}</li>
 * <li>{@code false}</li>
 * <li>{@code true}</li>
 * <li>...</li>
 * </ol>
 * </p>
 * 
 */
//TODO 001. create SplitCyclicCondition
public class CyclicCondition implements Condition, Persistent<CyclicConditionState> {

    private final CyclicConditionState state;

    private final int cycle;

    private int count;

    public CyclicCondition(CyclicConditionState state) {
        super();
        this.state = state;
        this.cycle = state.getCycle();
        this.count = state.getCount();
    }

    /**
     * Increment a counter and then test if the counter is equal to the cycle. If true, the counter is reseted.
     */
    @Override
    public boolean evaluate() {
        count++;
        if (count == cycle) {
            count = 0;
            return true;
        }
        return false;
    }

    @Override
    public CyclicConditionState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        state.setCount(count);
        state.setCycle(cycle);
    }

}
