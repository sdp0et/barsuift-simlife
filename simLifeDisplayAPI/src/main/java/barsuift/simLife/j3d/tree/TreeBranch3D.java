package barsuift.simLife.j3d.tree;

import java.util.List;

import javax.media.j3d.Group;
import javax.vecmath.Point3d;

public interface TreeBranch3D {

    public Point3d getEndPoint();

    public List<TreeBranchPart3D> getBranchParts();

    public TreeBranch3DState getState();

    public Group getGroup();

}
