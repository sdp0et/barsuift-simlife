package barsuift.simLife.condition;


public class BoundConditionFactory {

    public BoundCondition createBoundCondition(BoundConditionState state) {
        if (state.getBound() == -1) {
            return new UnboundedCondition();
        }
        return new BasicBoundCondition(state);
    }

}
