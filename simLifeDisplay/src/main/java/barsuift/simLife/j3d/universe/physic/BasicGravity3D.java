package barsuift.simLife.j3d.universe.physic;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;

import barsuift.simLife.j3d.universe.Universe3D;
import barsuift.simLife.process.GravityTask;
import barsuift.simLife.process.Synchronizer3D;


public class BasicGravity3D implements Gravity3D {

    private final Gravity3DState state;

    private final Group group;

    private GravityTask gravityTask;

    public BasicGravity3D(Gravity3DState state, Universe3D universe3D) {
        this.state = state;
        group = new BranchGroup();
        group.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        Synchronizer3D synchronizer3D = universe3D.getSynchronizer();
        gravityTask = new GravityTask(state.getGravityTask());
        synchronizer3D.schedule(gravityTask);
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public void fall(BranchGroup groupToFall) {
        gravityTask.fall(groupToFall);
        group.addChild(groupToFall);
    }

    @Override
    public Gravity3DState getState() {
        synchronize();
        return state;
    }

    // FIXME unit test
    @Override
    public void synchronize() {
        gravityTask.synchronize();
    }

}
