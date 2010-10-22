package barsuift.simLife.time;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;


public class MockTimeController extends BasicPublisher implements TimeController {

    private SimLifeDate date;

    private TimeControllerState state;

    private int synchronizeCalled;

    private int speed;

    private int startCalled;

    private int oneStepCalled;

    private int stopCalled;

    private boolean running;

    public MockTimeController() {
        super(null);
        reset();
    }

    public void reset() {
        date = new BasicSimLifeDate();
        state = CoreDataCreatorForTests.createSpecificTimeControllerState();
        synchronizeCalled = 0;
        speed = 1;
        startCalled = 0;
        oneStepCalled = 0;
        stopCalled = 0;
        running = false;
    }

    @Override
    public SimLifeDate getDate() {
        return date;
    }

    public void setDate(SimLifeDate date) {
        this.date = date;
    }

    @Override
    public TimeControllerState getState() {
        return state;
    }

    public void setTimeControllerState(TimeControllerState state) {
        this.state = state;
    }

    @Override
    public void synchronize() {
        synchronizeCalled++;
    }

    public int getNbSynchronizeCalled() {
        return synchronizeCalled;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getSpeed() {
        return speed;
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
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}
