package barsuift.simLife.j3d.universe.physic;


public class MockPhysics implements Physics {

    private Gravity gravity = new MockGravity();

    @Override
    public Gravity getGravity() {
        return gravity;
    }

    public void setGravity(Gravity gravity) {
        this.gravity = gravity;
    }

}
