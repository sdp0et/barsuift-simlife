package barsuift.simLife.j3d.tree;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BranchGroup;


public class MockTree3D implements Tree3D {

    private BranchGroup bg = new BranchGroup();

    private List<TreeBranch3D> branches3D = new ArrayList<TreeBranch3D>();

    private Tree3DState state = new Tree3DState();

    private TreeTrunk3D trunk3D = new MockTreeTrunk3D();

    @Override
    public BranchGroup getBranchGroup() {
        return bg;
    }

    public void setBranchGroup(BranchGroup bg) {
        this.bg = bg;
    }

    @Override
    public List<TreeBranch3D> getBranches() {
        return branches3D;
    }

    public void addBranch3D(TreeBranch3D branch3D) {
        branches3D.add(branch3D);
    }

    public void removeBranch3D(TreeBranch3D branch3D) {
        branches3D.remove(branch3D);
    }

    @Override
    public Tree3DState getState() {
        return state;
    }

    public void setState(Tree3DState state) {
        this.state = state;
    }

    @Override
    public TreeTrunk3D getTrunk() {
        return trunk3D;
    }

    public void setTrunk(TreeTrunk3D trunk3D) {
        this.trunk3D = trunk3D;
    }

}
