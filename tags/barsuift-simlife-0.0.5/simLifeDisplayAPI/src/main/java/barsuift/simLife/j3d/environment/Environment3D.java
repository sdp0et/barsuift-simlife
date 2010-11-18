package barsuift.simLife.j3d.environment;

import javax.media.j3d.Group;

import barsuift.simLife.Persistent;


public interface Environment3D extends Persistent<Environment3DState> {

    public Group getGroup();

    public Sun3D getSun3D();

}
