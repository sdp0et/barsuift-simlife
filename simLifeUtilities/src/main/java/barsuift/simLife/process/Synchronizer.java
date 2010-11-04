package barsuift.simLife.process;

import barsuift.simLife.message.Publisher;

/**
 * A synchronizer is used to synchronized elements. It can basically be started, stopped and asked about its current
 * running status.
 * <p>
 * The specific synchronized mechanism depends on the implementing classes.
 * </p>
 */
public interface Synchronizer extends Publisher {

    /**
     * Length of a core cycle, used to schedule the core temporizer, and to know how much to add to the date at each
     * iteration.
     */
    public static final int CYCLE_LENGTH_CORE_MS = 500;

    /**
     * Length of a 3D cycle, used to schedule the 3D temporizer.
     */
    public static final int CYCLE_LENGTH_3D_MS = 25;

    /**
     * Tests if the synchronizer is running.
     * 
     * @return true if the synchronizer is running, false otherwise
     */
    public boolean isRunning();

    /**
     * Start the synchronizer.
     * <p>
     * The synchronized elements are started.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is already running
     */
    public void start();

    /**
     * Stop the synchronizer.
     * <p>
     * The synchronized elements are asked to stop, once they have completed their current execution.
     * </p>
     * 
     * @throws IllegalStateException if the synchronizer is not running
     */
    public void stop();

}