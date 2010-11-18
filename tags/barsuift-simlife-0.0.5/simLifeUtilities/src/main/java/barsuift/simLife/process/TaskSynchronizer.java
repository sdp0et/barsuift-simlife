package barsuift.simLife.process;

import barsuift.simLife.message.Subscriber;

/**
 * A task synchronizer is a synchronizer able to schedule or unschedule tasks.
 * 
 * @param <E> the sub-type of ConditionalTask to use
 */
public interface TaskSynchronizer<E extends ConditionalTask> extends Synchronizer, Subscriber {

    /**
     * Adds the given task to the list of synchronized tasks.
     * 
     * @param task the task to synchronize
     */
    public void schedule(E task);

    /**
     * Remove the given task from the list of synchronized tasks.
     * 
     * @param task the task to desynchronize
     */
    public void unschedule(E task);

}
