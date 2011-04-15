package barsuift.simLife.universe.physic;

import barsuift.simLife.Persistent;
import barsuift.simLife.j3d.universe.physic.Physics3D;


public interface Physics extends Persistent<PhysicsState> {

    public Gravity getGravity();

    public Physics3D getPhysics3D();

}
