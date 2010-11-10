package barsuift.simLife.process;



/**
 * A split task is a task split in increments. The {@code stepSize} parameter allow to run more than one increment in a
 * row. Note that executing more than one increment in a row does NOT mean executing them one after the other, but
 * executing the whole increment range in one action.
 */
public interface SplitConditionalTask extends ConditionalTask {

    public void setStepSize(int stepSize);

    public void executeSplitStep(int stepSize);

}