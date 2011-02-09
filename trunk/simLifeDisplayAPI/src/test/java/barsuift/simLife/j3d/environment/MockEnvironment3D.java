package barsuift.simLife.j3d.environment;

import javax.media.j3d.Group;

import barsuift.simLife.j3d.landscape.Landscape3D;
import barsuift.simLife.j3d.landscape.MockLandscape3D;


public class MockEnvironment3D implements Environment3D {

    private Environment3DState env3DState;

    private int synchronizedCalled;

    private Group group;

    private Sky3D sky3D;

    private Landscape3D landscape3D;

    public MockEnvironment3D() {
        reset();
    }

    public void reset() {
        env3DState = new Environment3DState();
        synchronizedCalled = 0;
        group = new Group();
        sky3D = new MockSky3D();
        landscape3D = new MockLandscape3D();
    }

    @Override
    public Environment3DState getState() {
        return env3DState;
    }

    public void setState(Environment3DState env3dState) {
        env3DState = env3dState;
    }

    @Override
    public void synchronize() {
        this.synchronizedCalled++;
    }

    public int getNbSynchronize() {
        return synchronizedCalled;
    }

    public void setNbSynchronize(int nbSynchronize) {
        this.synchronizedCalled = nbSynchronize;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public Sky3D getSky3D() {
        return sky3D;
    }

    public void setSky3D(Sky3D sky3d) {
        sky3D = sky3d;
    }

    @Override
    public Landscape3D getLandscape3D() {
        return landscape3D;
    }

    public void setLandscape3D(Landscape3D landscape3D) {
        this.landscape3D = landscape3D;
    }

}
