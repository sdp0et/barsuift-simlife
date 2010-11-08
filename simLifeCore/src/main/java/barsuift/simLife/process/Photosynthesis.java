package barsuift.simLife.process;

import barsuift.simLife.tree.Photosynthetic;

public class Photosynthesis extends ConditionalTask {

    private final Photosynthetic photosynthetic;

    public Photosynthesis(ConditionalTaskState state, Photosynthetic photosynthetic) {
        super(state);
        this.photosynthetic = photosynthetic;
    }

    @Override
    public void executeConditionalStep() {
        photosynthetic.collectSolarEnergy();
    }
}
