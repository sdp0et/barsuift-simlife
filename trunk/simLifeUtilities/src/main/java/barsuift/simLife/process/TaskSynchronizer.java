package barsuift.simLife.process;

/**
 * A task synchronizer is a synchronizer able to schedule or unschedule tasks.
 * 
 * @param <E> the sub-type of SynchronizedRunnable to use
 */
public interface TaskSynchronizer<E extends SynchronizedRunnable> extends Synchronizer {

    /**
     * Adds the given runnable to the list of synchronized tasks.
     * 
     * @param runnable the runnable to synchronize
     */
    public void schedule(E runnable);

    /**
     * Remove the given runnable from the list of synchronized tasks.
     * 
     * @param runnable the runnable to desynchronize
     */
    public void unschedule(E runnable);

}
