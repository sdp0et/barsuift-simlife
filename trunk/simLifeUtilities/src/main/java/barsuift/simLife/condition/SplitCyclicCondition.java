package barsuift.simLife.condition;

import barsuift.simLife.Persistent;

/**
 * A split cyclic condition evaluates to false except on a cyclic period.
 * <p>
 * For example, with a cycle of 3, and a stepSize of 2, successive calls to evaluate returns the following values :
 * <ol>
 * <li>{@code false} : (2/3)</li>
 * <li>{@code true} : (4/3)</li>
 * <li>{@code true} : (6/3)</li>
 * <li>{@code false} : (8/3)</li>
 * <li>{@code true} : (10/3)</li>
 * <li>{@code true} : (12/3)</li>
 * <li>{@code false} : (14/3)</li>
 * <li>...</li>
 * </ol>
 * </p>
 * 
 */
public class SplitCyclicCondition implements SplitCondition, Persistent<CyclicConditionState> {

    private final CyclicConditionState state;

    private final int cycle;

    private int count;

    public SplitCyclicCondition(CyclicConditionState state) {
        super();
        this.state = state;
        this.cycle = state.getCycle();
        this.count = state.getCount();
    }

    /**
     * Increment a counter and then test if the counter is equal to the cycle. If true, the counter is reseted.
     */
    @Override
    public boolean evaluate(int stepSize) {
        count += stepSize;
        if (count >= cycle) {
            count = count % cycle;
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
