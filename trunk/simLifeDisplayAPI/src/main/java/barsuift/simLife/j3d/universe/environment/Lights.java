package barsuift.simLife.j3d.universe.environment;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BranchGroup;



public interface Lights {

    public Sun3D getSun3D();

    public AmbientLight getAmbientLight();

    public BranchGroup getLightsGroup();

}