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
        System.out.println("Executing Photosynthesis");
        photosynthetic.collectSolarEnergy();
    }
}
