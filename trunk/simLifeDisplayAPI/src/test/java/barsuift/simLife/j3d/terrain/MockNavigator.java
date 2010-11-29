package barsuift.simLife.j3d.terrain;


public class MockNavigator implements Navigator {

    private int nbResetToOriginalPositionCalled;

    private int nbResetToNominalViewAngleCalled;

    public MockNavigator() {
        reset();
    }

    public void reset() {
        this.nbResetToOriginalPositionCalled = 0;
        this.nbResetToNominalViewAngleCalled = 0;
    }

    @Override
    public void resetToOriginalPosition() {
        nbResetToOriginalPositionCalled++;
    }

    public int getNbResetToOriginalPositionCalled() {
        return nbResetToOriginalPositionCalled;
    }

    @Override
    public void resetToNominalViewAngle() {
        nbResetToNominalViewAngleCalled++;
    }

    public int getNbResetToNominalViewAngleCalled() {
        return nbResetToNominalViewAngleCalled;
    }

}
