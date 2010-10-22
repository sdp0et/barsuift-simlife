package barsuift.simLife.process;

import barsuift.simLife.tree.Tree;


public class Photosynthesis extends UnfrequentRunnable {

    private final Tree tree;

    public Photosynthesis(UnfrequentRunnableState state, Tree tree) {
        super(state);
        this.tree = tree;
    }

    @Override
    public void executeUnfrequentStep() {
        System.out.println("Execute Photosynthesis on tree " + tree);
        // TODO implement photosynthesis
    }

}
