package barsuift.simLife.process;

import barsuift.simLife.Persistent;

/**
 * The main synchronizer allows to synchronize the {@link SynchronizerCore} and the Synchronizer3D.
 * 
 */
// TODO 002. update javadoc (once Synchronizer3D exists)
public interface MainSynchronizer extends Synchronizer, Persistent<MainSynchronizerState> {

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