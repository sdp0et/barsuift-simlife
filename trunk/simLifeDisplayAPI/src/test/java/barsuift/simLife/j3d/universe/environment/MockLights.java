package barsuift.simLife.j3d.universe.environment;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BranchGroup;


public class MockLights implements Lights {

    private AmbientLight ambientLight = new AmbientLight();

    private BranchGroup lightsGroup = new BranchGroup();

    private Sun3D sun3D = new MockSun3D();

    @Override
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
    }

    @Override
    public BranchGroup getLightsGroup() {
        return lightsGroup;
    }

    public void setLightsGroup(BranchGroup lightsGroup) {
        this.lightsGroup = lightsGroup;
    }

    @Override
    public Sun3D getSun3D() {
        return sun3D;
    }

    public void setSun3D(Sun3D sun3D) {
        this.sun3D = sun3D;
    }

}
