package barsuift.simLife.j3d.tree;

import java.util.List;

import javax.media.j3d.Group;
import javax.vecmath.Point3d;


public interface TreeBranchPart3D {

    public void addLeaf(TreeLeaf3D leaf);

    public Point3d getEndPoint();

    public List<TreeLeaf3D> getLeaves();

    public TreeBranchPart3DState getState();

    public Group getGroup();

}
