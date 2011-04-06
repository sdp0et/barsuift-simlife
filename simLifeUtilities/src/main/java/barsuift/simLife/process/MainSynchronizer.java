package barsuift.simLife.process;

import barsuift.simLife.Persistent;

/**
 * The main synchronizer allows to synchronize the {@link SynchronizerSlow} and the {@link SynchronizerFast}.
 * 
 */
public interface MainSynchronizer extends Synchronizer, Persistent<MainSynchronizerState> {

    public SynchronizerFast getSynchronizerFast();

    /**
     * Adds the given fast task to the list of synchronized tasks.
     * 
     * @param task the task to synchronize
     */
    public void scheduleFast(SplitConditionalTask task);

    /**
     * Remove the given fast task from the list of synchronized tasks.
     * 
     * @param task the task to desynchronize
     */
    public void unscheduleFast(SplitConditionalTask task);

    public SynchronizerSlow getSynchronizerSlow();

    /**
     * Adds the given slow task to the list of synchronized tasks.
     * 
     * @param task the task to synchronize
     */
    public void scheduleSlow(ConditionalTask task);

    /**
     * Remove the given slow task from the list of synchronized tasks.
     * 
     * @param task the task to desynchronize
     */
    public void unscheduleSlow(ConditionalTask task);

    /**
     * Sets the speed of the current main synchronizer.
     * 
     * @param speed the speed
     */
    public void setSpeed(Speed speed);

    /**
     * Returns the speed of the main synchronizer.
     * 
     * @return the speed of the synchronizer
     */
    public Speed getSpeed();

    /**
     * Execute one step of the synchronizer.
     * <p>
     * It is more or less equivalent to calling {@link #start()} method, followed by the {@link #stop()} method. The
     * additional value of this method is that it guarantees that only one step is executed.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is already running
     */
    public void oneStep();

    /**
     * Stop the synchronizer and sleep until the synchronizer is fully stopped.
     * 
     * @throws IllegalStateException if the synchronizer is not running
     */
    public void stopAndWait();

}