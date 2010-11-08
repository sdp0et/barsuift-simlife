package barsuift.simLife.condition;

import barsuift.simLife.MockState;
import barsuift.simLife.State;


public class MockCondition implements Condition<State> {

    private State state;

    private int synchronizeCalled;

    private boolean evaluateResult;

    private int evaluateCalled;

    public MockCondition() {
        reset();
    }

    public void reset() {
        state = new MockState();
        synchronizeCalled = 0;
        evaluateResult = false;
        evaluateCalled = 0;
    }

    @Override
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

    @Override
    public boolean evaluate() {
        evaluateCalled++;
        return evaluateResult;
    }

    public void setEvaluateResult(boolean evaluateResult) {
        this.evaluateResult = evaluateResult;
    }

    public int getNbEvaluateCalled() {
        return evaluateCalled;
    }

}
