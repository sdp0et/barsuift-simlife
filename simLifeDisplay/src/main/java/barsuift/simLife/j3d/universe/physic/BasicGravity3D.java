package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;


public class BasicGravity3D implements Gravity3D {

    private final Gravity3DState state;

    private final Group group;

    public BasicGravity3D(Gravity3DState state) {
        this.state = state;
        group = new BranchGroup();
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void fall(BranchGroup groupToFall) {
        // TODO 001. put here the code extracted from GravityInerpolator
        // put the fallen leaves and interpolator in this group
    }

    @Override
    public Gravity3DState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        // nothing to do
    }

}
