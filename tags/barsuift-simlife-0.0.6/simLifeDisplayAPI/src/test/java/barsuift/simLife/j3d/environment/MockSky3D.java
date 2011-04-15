package barsuift.simLife.j3d.environment;

import javax.media.j3d.Group;


public class MockSky3D implements Sky3D {

    private Sky3DState sky3DState;

    private int synchronizedCalled;

    private Group group;

    private Sun3D sun3D;

    public MockSky3D() {
        reset();
    }

    private void reset() {
        sky3DState = new Sky3DState();
        synchronizedCalled = 0;
        group = new Group();
        sun3D = new MockSun3D();
    }

    @Override
    public Sky3DState getState() {
        return sky3DState;
    }

    public void setState(Sky3DState sky3dState) {
        sky3DState = sky3dState;
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
    public Sun3D getSun3D() {
        return sun3D;
    }

    public void setSun3D(Sun3D sun3d) {
        sun3D = sun3d;
    }

}
