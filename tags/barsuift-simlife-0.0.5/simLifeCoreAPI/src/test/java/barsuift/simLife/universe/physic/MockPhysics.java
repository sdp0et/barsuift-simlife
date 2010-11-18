package barsuift.simLife.universe.physic;

import barsuift.simLife.j3d.universe.physic.MockPhysics3D;
import barsuift.simLife.j3d.universe.physic.Physics3D;


public class MockPhysics implements Physics {

    private PhysicsState state;

    private int synchronizedCalled;

    private Gravity gravity;

    private Physics3D physics3D;


    public MockPhysics() {
        reset();
    }

    public void reset() {
        this.state = new PhysicsState();
        this.synchronizedCalled = 0;
        this.gravity = new MockGravity();
        this.physics3D = new MockPhysics3D();
    }

    @Override
    public PhysicsState getState() {
        return state;
    }

    public void setState(PhysicsState state) {
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
    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

    @Override
    public Physics3D getPhysics3D() {
        return physics3D;
    }

    public void setPhysics3D(Physics3D physics3D) {
        this.physics3D = physics3D;
    }

}
