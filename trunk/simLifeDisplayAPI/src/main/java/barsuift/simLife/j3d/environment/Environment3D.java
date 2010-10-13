package barsuift.simLife.j3d.environment;

import barsuift.simLife.Persistent;


public interface Environment3D extends Persistent<Environment3DState> {

    public Sun3D getSun3D();

}
