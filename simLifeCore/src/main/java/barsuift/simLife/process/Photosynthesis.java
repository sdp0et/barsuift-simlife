package barsuift.simLife.process;

import barsuift.simLife.tree.Photosynthetic;

public class Photosynthesis extends UnfrequentRunnable {

    private final Photosynthetic photosynthetic;

    public Photosynthesis(UnfrequentRunnableState state, Photosynthetic photosynthetic) {
        super(state);
        this.photosynthetic = photosynthetic;
    }

    @Override
    public void executeUnfrequentStep() {
        photosynthetic.collectSolarEnergy();
    }
}
