package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import barsuift.simLife.Persistent;

/**
 * The core synchronizer allows to run a list of given {@link SynchronizedRunnable} at a given rate.
 * 
 */
public interface SynchronizerCore extends Synchronizer, Persistent<SynchronizerCoreState> {

    /**
     * Sets the speed of the current core synchronizer.
     * 
     * @param speed the speed
     */
    public void setSpeed(Speed speed);

    /**
     * Returns the speed of the core synchronizer.
     * 
     * @return the speed of the synchronizer
     */
    public Speed getSpeed();

    /**
     * Adds the given runnable to the list of synchronized tasks.
     * 
     * @param runnable the runnable to synchronize
     */
    public void schedule(SynchronizedRunnable runnable);

    /**
     * Remove the given runnable from the list of synchronized tasks.
     * 
     * @param runnable the runnable to desynchronize
     */
    public void unschedule(SynchronizedRunnable runnable);

    /**
     * Set the barrier to use for synchronization purpose.
     * 
     * @param barrier the cyclic barrier used to synchronize the synchronizer with other synchronizers
     * @throws IllegalArgumentException if the given barrier is null
     * @throws IllegalStateException if a barrier has already been set
     */
    public void setBarrier(CyclicBarrier barrier);

}