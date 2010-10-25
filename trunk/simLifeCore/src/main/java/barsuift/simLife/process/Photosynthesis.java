package barsuift.simLife.process;

import java.util.Collection;
import java.util.List;

import barsuift.simLife.tree.Tree;
import barsuift.simLife.tree.TreeBranch;
import barsuift.simLife.tree.TreeBranchPart;
import barsuift.simLife.tree.TreeLeaf;

// TODO 008. create Photosynthetic interface and make leaf, branch, and tree implement it
// TODO 009. create missing methods in branch and tree, and make the loop from photosynthesis in these methods (create
// unit tests)
// TODO 010. update the Photosynthesis class to use the new method
// TODO 011. update the methods to collect free energy from sub parts (update unit tests)
public class Photosynthesis extends UnfrequentRunnable {

    private final Tree tree;

    public Photosynthesis(UnfrequentRunnableState state, Tree tree) {
        super(state);
        this.tree = tree;
    }

    @Override
    public void executeUnfrequentStep() {
        System.out.println("Executing Photosynthesis");
        List<TreeBranch> branches = tree.getBranches();
        for (TreeBranch branch : branches) {
            List<TreeBranchPart> parts = branch.getParts();
            for (TreeBranchPart part : parts) {
                Collection<TreeLeaf> leaves = part.getLeaves();
                for (TreeLeaf leaf : leaves) {
                    leaf.collectSolarEnergy();
                }
            }
        }
    }
}
