package barsuift.simLife.time;

import barsuift.simLife.Persistent;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.process.SynchronizedRunnable;



public interface TimeController extends Persistent<TimeControllerState>, Publisher {

    public SimLifeDate getDate();

    public void schedule(SynchronizedRunnable runnable);

    public void unschedule(SynchronizedRunnable runnable);

    public void setSpeed(int speed);

    public int getSpeed();

    /**
     * Start the controller.
     * <p>
     * A {@link TimeMessenger} instance is scheduled to be run every seconds.
     * </p>
     * 
     * @throws IllegalStateException if the controller is already running
     */
    public void start() throws IllegalStateException;

    /**
     * Execute one step of the controller.
     * <p>
     * A {@link TimeMessenger} instance is run once.
     * </p>
     * 
     * @throws IllegalStateException if the controller is already running
     */
    public void oneStep();

    /**
     * Stop the controller.
     * <p>
     * The running process is asked to stop, once it has completed its current execution.
     * </p>
     * 
     * @throws IllegalStateException if the controller is not running
     */
    public void stop();

    public boolean isRunning();

}