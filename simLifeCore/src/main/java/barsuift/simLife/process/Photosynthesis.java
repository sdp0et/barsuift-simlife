package barsuift.simLife.process;

import barsuift.simLife.tree.Photosynthetic;

public class Photosynthesis extends CyclicRunnable {

    private final Photosynthetic photosynthetic;

    public Photosynthesis(CyclicRunnableState state, Photosynthetic photosynthetic) {
        super(state);
        this.photosynthetic = photosynthetic;
    }

    @Override
    public void executeCyclicStep() {
        photosynthetic.collectSolarEnergy();
    }
}
