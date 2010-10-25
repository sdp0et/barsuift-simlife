package barsuift.simLife.process;

import barsuift.simLife.time.FpsCounter;


public class FpsTicker extends UnfrequentRunnable {

    private final FpsCounter fps;

    public FpsTicker(FpsCounter fps) {
        super(new UnfrequentRunnableState(10, 0));
        this.fps = fps;
    }

    @Override
    public void executeUnfrequentStep() {
        fps.tick();
    }

}
