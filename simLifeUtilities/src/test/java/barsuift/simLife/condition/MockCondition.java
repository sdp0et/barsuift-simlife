package barsuift.simLife.condition;



public class MockCondition implements Condition {

    private boolean evaluateResult;

    private int evaluateCalled;

    public MockCondition() {
        reset();
    }

    public void reset() {
        evaluateResult = false;
        evaluateCalled = 0;
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
