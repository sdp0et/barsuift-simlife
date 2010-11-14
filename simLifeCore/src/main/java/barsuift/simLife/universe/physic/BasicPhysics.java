package barsuift.simLife.universe.physic;

import barsuift.simLife.j3d.universe.physic.BasicPhysics3D;
import barsuift.simLife.j3d.universe.physic.Physics3D;
import barsuift.simLife.universe.Universe;


public class BasicPhysics implements Physics {

    private final PhysicsState state;

    private final Gravity gravity;

    private final Physics3D physics3D;

    public BasicPhysics(Universe universe, PhysicsState state) {
        this.state = state;
        this.gravity = new BasicGravity(state.getGravity(), universe);
        this.physics3D = new BasicPhysics3D(state.getPhysics3D(), this);
    }

    @Override
    public PhysicsState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        physics3D.synchronize();
        gravity.synchronize();
    }

    @Override
    public Physics3D getPhysics3D() {
        return physics3D;
    }

    @Override
    public Gravity getGravity() {
        return gravity;
    }

}
