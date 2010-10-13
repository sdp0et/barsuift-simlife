package barsuift.simLife.j3d.environment;

import javax.media.j3d.Group;


public class MockEnvironment3D implements Environment3D {

    private Environment3DState env3DState = new Environment3DState();

    private int synchronizedCalled = 0;

    private Group group = new Group();

    private Sun3D sun3D = new MockSun3D();

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
    public Sun3D getSun3D() {
        return sun3D;
    }

    public void setSun3D(Sun3D sun3d) {
        sun3D = sun3d;
    }


}
