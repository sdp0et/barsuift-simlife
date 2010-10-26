package barsuift.simLife.universe.physic;

import barsuift.simLife.j3d.universe.physic.BasicGravity3D;
import barsuift.simLife.j3d.universe.physic.Gravity3D;


public class BasicGravity implements Gravity {

    private final GravityState state;

    private final Gravity3D gravity3D;

    public BasicGravity(GravityState state) {
        this.state = state;
        this.gravity3D = new BasicGravity3D(state.getGravity3D());
    }

    @Override
    public GravityState getState() {
        synchronize();
        return state;
    }

    @Override
    public void synchronize() {
        gravity3D.synchronize();
    }

    @Override
    public Gravity3D getGravity3D() {
        return gravity3D;
    }

}
