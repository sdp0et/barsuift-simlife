package barsuift.simLife.universe.physic;

import barsuift.simLife.j3d.universe.physic.Gravity3D;
import barsuift.simLife.j3d.universe.physic.MockGravity3D;


public class MockGravity implements Gravity {

    private GravityState state;

    private int synchronizedCalled;

    private Gravity3D gravity3D;

    public MockGravity() {
        reset();
    }

    public void reset() {
        this.state = new GravityState();
        this.synchronizedCalled = 0;
        this.gravity3D = new MockGravity3D();
    }

    @Override
    public GravityState getState() {
        return state;
    }

    public void setState(GravityState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

    @Override
    public Gravity3D getGravity3D() {
        return gravity3D;
    }

    public void setGravity3D(Gravity3D gravity3D) {
        this.gravity3D = gravity3D;
    }

}
