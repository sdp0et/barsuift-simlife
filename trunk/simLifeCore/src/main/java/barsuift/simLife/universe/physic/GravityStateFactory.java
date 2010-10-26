package barsuift.simLife.universe.physic;

import barsuift.simLife.j3d.universe.physic.Gravity3DState;
import barsuift.simLife.j3d.universe.physic.Gravity3DStateFactory;


public class GravityStateFactory {

    public GravityState createGravityState() {
        Gravity3DStateFactory gravity3DFactory = new Gravity3DStateFactory();
        Gravity3DState gravity3D = gravity3DFactory.createGravity3DState();
        return new GravityState(gravity3D);
    }

}
