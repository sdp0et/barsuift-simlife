package barsuift.simLife.process;

import barsuift.simLife.time.FpsCounter;


public class FpsTicker extends AbstractConditionalTask {

    private final FpsCounter fps;

    public FpsTicker(ConditionalTaskState state, FpsCounter fps) {
        super(state);
        this.fps = fps;
    }

    @Override
    public void executeConditionalStep() {
        fps.tick();
    }

}
