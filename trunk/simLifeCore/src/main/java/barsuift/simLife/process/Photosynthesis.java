package barsuift.simLife.process;

import barsuift.simLife.tree.Photosynthetic;

public class Photosynthesis extends CyclicTask {

    private final Photosynthetic photosynthetic;

    public Photosynthesis(CyclicTaskState state, Photosynthetic photosynthetic) {
        super(state);
        this.photosynthetic = photosynthetic;
    }

    @Override
    public void executeCyclicStep() {
        photosynthetic.collectSolarEnergy();
    }
}
