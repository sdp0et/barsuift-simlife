package barsuift.simLife.j3d;


/**
 * Utility class to provide a more accurate replacement for System.currentTimeMillis().
 */
public class SimLifeTime {


    private static long deltaTime;

    private static final long nsecPerMsec = 1000000;

    /**
     * Private constructor, since no instance should ever be created.
     */
    private SimLifeTime() {
    }

    /**
     * Returns the current time in milliseconds. This is a more accurate version of System.currentTimeMillis and should
     * be used in its place.
     * 
     * @return the current time in milliseconds.
     */
    public static long currentTimeMillis() {
        return (System.nanoTime() / nsecPerMsec) + deltaTime;
    }

    static {
        // Call time methods once without using their values to ensure that
        // the methods are "warmed up". We need to make sure that the actual
        // calls that we use take place as close together as possible in time.
        System.currentTimeMillis();
        System.nanoTime();

        // Compute deltaTime between System.currentTimeMillis()
        // and the high-res timer, use a synchronized block to force both calls
        // to be made before the integer divide
        long baseTime, baseTimerValue;
        synchronized (SimLifeTime.class) {
            baseTime = System.currentTimeMillis();
            baseTimerValue = System.nanoTime();
        }
        deltaTime = baseTime - (baseTimerValue / nsecPerMsec);
    }


}
