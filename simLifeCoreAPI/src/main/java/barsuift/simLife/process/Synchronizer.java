package barsuift.simLife.process;

import barsuift.simLife.Persistent;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.time.SimLifeDate;



public interface Synchronizer extends Publisher, Persistent<SynchronizerState> {

    public SimLifeDate getDate();

    public void setSpeed(int speed);

    public int getSpeed();

    public boolean isRunning();

    public void schedule(SynchronizedRunnable runnable);

    public void unschedule(SynchronizedRunnable runnable);

    /**
     * Execute one step of the synchronizer.
     * 
     * @throws IllegalStateException if the synchronizer is already running
     */
    public void oneStep();

    /**
     * Start the synchronizer.
     * <p>
     * All the SynchronizedRunnable objects given at initialization time are started.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is already running
     */
    public void start();

    /**
     * Stop the synchronizer.
     * <p>
     * The running processes are asked to stop, once they have completed their current execution.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is not running
     */
    public void stop();

    /**
     * Stop the synchronizer and sleep until the synchronizer is fully stopped.
     */
    public void stopAndWait();

}