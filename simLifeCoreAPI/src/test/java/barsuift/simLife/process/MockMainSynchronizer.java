package barsuift.simLife.process;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;


public class MockMainSynchronizer extends BasicPublisher implements MainSynchronizer {

    private Speed speed;

    private boolean running;

    private int startCalled;

    private int oneStepCalled;

    private int stopCalled;

    private int stopAndWaitCalled;

    private MainSynchronizerState state;

    private int synchronizeCalled;

    public MockMainSynchronizer() {
        super(null);
        reset();
    }

    public void reset() {
        speed = Speed.NORMAL;
        running = false;
        startCalled = 0;
        oneStepCalled = 0;
        stopCalled = 0;
        stopAndWaitCalled = 0;
        state = CoreDataCreatorForTests.createSpecificMainSynchronizerState();
        synchronizeCalled = 0;
    }

    @Override
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public Speed getSpeed() {
        return speed;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void start() throws IllegalStateException {
        startCalled++;
    }

    public int getNbStartCalled() {
        return startCalled;
    }

    @Override
    public void oneStep() {
        oneStepCalled++;
    }

    public int getNbOneStepCalled() {
        return oneStepCalled;
    }

    @Override
    public void stop() {
        stopCalled++;
    }

    public int getNbStopCalled() {
        return stopCalled;
    }

    @Override
    public void stopAndWait() {
        stopAndWaitCalled++;
    }

    public int getNbStopAndWaitCalled() {
        return stopAndWaitCalled;
    }

    @Override
    public MainSynchronizerState getState() {
        return state;
    }

    public void setState(MainSynchronizerState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

}
