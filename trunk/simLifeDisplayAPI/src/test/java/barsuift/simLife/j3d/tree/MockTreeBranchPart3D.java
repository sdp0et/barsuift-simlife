package barsuift.simLife.j3d.tree;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Group;
import javax.vecmath.Point3d;


public class MockTreeBranchPart3D implements TreeBranchPart3D {

    private Point3d endPoint = new Point3d();

    private Group group = new Group();

    private List<TreeLeaf3D> leaves = new ArrayList<TreeLeaf3D>();

    private TreeBranchPart3DState part3DState = new TreeBranchPart3DState();

    @Override
    public Point3d getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point3d endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public List<TreeLeaf3D> getLeaves() {
        return leaves;
    }

    @Override
    public void addLeaf(TreeLeaf3D leaf3D) {
        leaves.add(leaf3D);
    }

    public void removeLeaf(TreeLeaf3D leaf3D) {
        leaves.remove(leaf3D);
    }

    @Override
    public TreeBranchPart3DState getState() {
        return part3DState;
    }

    public void setState(TreeBranchPart3DState part3DState) {
        this.part3DState = part3DState;
    }

}
