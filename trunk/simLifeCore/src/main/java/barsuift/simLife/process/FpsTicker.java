package barsuift.simLife.process;

import barsuift.simLife.time.FpsCounter;


public class FpsTicker extends AbstractSynchronizedRunnable {

    private final FpsCounter fps;

    public FpsTicker(FpsCounter fps) {
        super();
        this.fps = fps;
    }

    @Override
    public void executeStep() {
        fps.tick();
    }

}
