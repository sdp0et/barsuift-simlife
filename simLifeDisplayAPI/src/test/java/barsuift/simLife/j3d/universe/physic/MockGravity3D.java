package barsuift.simLife.j3d.universe.physic;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Group;


public class MockGravity3D implements Gravity3D {

    private Gravity3DState state;

    private int synchronizeCalled;

    private List<BranchGroup> fallenGroups;

    private Group group;

    public MockGravity3D() {
        reset();
    }

    public void reset() {
        state = new Gravity3DState();
        synchronizeCalled = 0;
        fallenGroups = new ArrayList<BranchGroup>();
        group = new Group();
    }

    @Override
    public Gravity3DState getState() {
        return state;
    }

    public void setState(Gravity3DState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

    @Override
    public void fall(BranchGroup groupToFall) {
        fallenGroups.add(groupToFall);
    }

    public List<BranchGroup> getFallenGroups() {
        return fallenGroups;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}