package barsuift.simLife.time;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import barsuift.simLife.universe.Universe;

/**
 * Controller for time control on the universe.
 * 
 */
public class UniverseTimeController {

    private final ScheduledExecutorService scheduledThreadPool;

    private ScheduledFuture<?> runningProcess;

    private final TimeMessenger timeMessenger;

    private boolean running;

    public UniverseTimeController(Universe universe) {
        super();
        int poolSize = 1;
        this.scheduledThreadPool = Executors.newScheduledThreadPool(poolSize);
        this.timeMessenger = new TimeMessenger(universe);
        this.running = false;
    }

    /**
     * Start the controller.
     * <p>
     * A {@link TimeMessenger} instance is scheduled to be run every seconds.
     * </p>
     * 
     * @throws IllegalStateException if the controller is already running
     */
    public void start() throws IllegalStateException {
        if (running == true) {
            throw new IllegalStateException("The controller is already running");
        }
        running = true;
        // start immediately
        int initialDelay = 0;
        // wakeup every seconds
        long period = 1;
        runningProcess = scheduledThreadPool.scheduleAtFixedRate(timeMessenger, initialDelay, period, TimeUnit.SECONDS);
    }

    /**
     * Execute one step of the controller.
     * <p>
     * A {@link TimeMessenger} instance is run once.
     * </p>
     * 
     * @throws IllegalStateException if the controller is already running
     */
    public void oneStep() {
        if (running == true) {
            throw new IllegalStateException("The controller is already running");
        }
        timeMessenger.run();
    }

    /**
     * Pause the controller.
     * <p>
     * The running process is aksed to stop, once it has completede its current execution.
     * </p>
     * 
     * @throws IllegalStateException if the controller is not running
     */
    public void pause() {
        if (running == false) {
            throw new IllegalStateException("The controller is not running");
        }
        running = false;
        runningProcess.cancel(false);
    }

    public boolean isRunning() {
        return running;
    }

    public TimeCounter getTimeCounter() {
        return timeMessenger.getTimeCounter();
    }

}
