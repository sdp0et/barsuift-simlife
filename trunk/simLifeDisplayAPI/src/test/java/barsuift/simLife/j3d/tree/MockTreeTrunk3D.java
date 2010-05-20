package barsuift.simLife.j3d.tree;

import javax.media.j3d.Group;

import com.sun.j3d.utils.geometry.Cylinder;


public class MockTreeTrunk3D implements TreeTrunk3D {

    private Group group = new Group();

    private TreeTrunk3DState state = new TreeTrunk3DState();

    private Cylinder cylinder = new Cylinder();

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public TreeTrunk3DState getState() {
        return state;
    }

    public void setState(TreeTrunk3DState state) {
        this.state = state;
    }

    @Override
    public Cylinder getTrunk() {
        return cylinder;
    }

    public void setTrunk(Cylinder cylinder) {
        this.cylinder = cylinder;
    }

}
