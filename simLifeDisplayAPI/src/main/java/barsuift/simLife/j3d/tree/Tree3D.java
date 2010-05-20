package barsuift.simLife.j3d.tree;

import java.util.List;

import javax.media.j3d.BranchGroup;

public interface Tree3D {

    public TreeTrunk3D getTrunk();

    public List<TreeBranch3D> getBranches();

    public Tree3DState getState();

    public BranchGroup getBranchGroup();

}
