package barsuift.simLife.universe.physic;

import barsuift.simLife.j3d.universe.physic.Physics3DState;
import barsuift.simLife.j3d.universe.physic.Physics3DStateFactory;


public class PhysicsStateFactory {

    public PhysicsState createPhysicsState() {
        GravityStateFactory gravityFactory = new GravityStateFactory();
        GravityState gravity = gravityFactory.createGravityState();
        Physics3DStateFactory physics3DFactory = new Physics3DStateFactory();
        Physics3DState physics3D = physics3DFactory.createPhysics3DState();
        return new PhysicsState(gravity, physics3D);
    }

}
