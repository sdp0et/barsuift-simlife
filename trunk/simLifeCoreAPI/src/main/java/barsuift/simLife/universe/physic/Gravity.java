package barsuift.simLife.universe.physic;

import barsuift.simLife.Persistent;
import barsuift.simLife.j3d.universe.physic.Gravity3D;


public interface Gravity extends Persistent<GravityState> {

    public Gravity3D getGravity3D();

}
