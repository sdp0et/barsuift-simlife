package barsuift.simLife.process;

import java.util.Collection;
import java.util.List;

import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeBranch;
import barsuift.simLife.tree.TreeBranchPart;
import barsuift.simLife.tree.TreeLeaf;


public class TreeGrowth extends UnfrequentRunnable {

    private final Tree tree;

    public TreeGrowth(UnfrequentRunnableState state, Tree tree) {
        super(state);
        this.tree = tree;
    }

    @Override
    public void executeUnfrequentStep() {
        System.out.println("Executing TreeGrowth");
        List<TreeBranch> branches = tree.getBranches();
        for (TreeBranch branch : branches) {
            List<TreeBranchPart> parts = branch.getParts();
            for (TreeBranchPart part : parts) {
                Collection<TreeLeaf> leaves = part.getLeaves();
                for (TreeLeaf leaf : leaves) {
                    leaf.improveEfficiency();
                }
            }
        }
    }
}
