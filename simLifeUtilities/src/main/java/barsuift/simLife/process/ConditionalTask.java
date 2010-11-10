package barsuift.simLife.process;

import barsuift.simLife.Persistent;
import barsuift.simLife.message.Publisher;



public interface ConditionalTask extends Publisher, Persistent<ConditionalTaskState>, SynchronizedTask {

    /**
     * Method potentially called at each iteration, if the conditions are met.
     */
    public void executeConditionalStep();

}