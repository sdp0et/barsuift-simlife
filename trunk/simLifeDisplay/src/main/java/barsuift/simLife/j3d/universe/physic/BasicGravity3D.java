package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.BranchGroup;


public class BasicGravity3D implements Gravity3D {

    private final Gravity3DState state;

    public BasicGravity3D(Gravity3DState state) {
        this.state = state;
    }

    @Override
    public void fall(BranchGroup groupToFall) {
        // TODO 001. put here the code extracted from GravityProcess
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
