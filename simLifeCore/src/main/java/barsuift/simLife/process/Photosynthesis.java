package barsuift.simLife.process;

import java.util.List;

import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeBranch;
import barsuift.simLife.tree.TreeBranchPart;
import barsuift.simLife.tree.TreeLeaf;


public class Photosynthesis extends UnfrequentRunnable {

    private final Tree tree;

    public Photosynthesis(UnfrequentRunnableState state, Tree tree) {
        super(state);
        this.tree = tree;
    }

    @Override
    public void executeUnfrequentStep() {
        List<TreeBranch> branches = tree.getBranches();
        for (TreeBranch branch : branches) {
            List<TreeBranchPart> parts = branch.getParts();
            for (TreeBranchPart part : parts) {
                List<TreeLeaf> leaves = part.getLeaves();
                for (TreeLeaf leaf : leaves) {
                    leaf.collectSolarEnergy();
                }
            }
        }
    }
}
