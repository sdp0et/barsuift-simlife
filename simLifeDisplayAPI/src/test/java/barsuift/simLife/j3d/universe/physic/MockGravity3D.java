package barsuift.simLife.j3d.universe.physic;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Group;

import barsuift.simLife.j3d.Mobile;


public class MockGravity3D implements Gravity3D {

    private Gravity3DState state;

    private int synchronizeCalled;

    private List<Mobile> fallingMobiles;

    private List<Mobile> fallenMobiles;

    private Group group;

    public MockGravity3D() {
        reset();
    }

    public void reset() {
        state = new Gravity3DState();
        synchronizeCalled = 0;
        fallingMobiles = new ArrayList<Mobile>();
        fallenMobiles = new ArrayList<Mobile>();
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
    public void fall(Mobile mobile) {
        fallingMobiles.add(mobile);
    }

    public List<Mobile> getFallingMobiles() {
        return fallingMobiles;
    }

    @Override
    public void isFallen(Mobile mobile) {
        fallenMobiles.add(mobile);
    }

    public List<Mobile> getFallenMobiles() {
        return fallenMobiles;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
