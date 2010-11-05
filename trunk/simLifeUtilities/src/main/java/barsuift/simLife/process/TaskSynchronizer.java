package barsuift.simLife.process;


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
