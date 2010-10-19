package barsuift.simLife.time;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.universe.MockUniverse;
import barsuift.simLife.universe.Universe;


public class MockTimeController extends BasicPublisher implements TimeController {

    private TimeControllerState state;

    private int synchronizeCalled;

    private int speed;

    private int startCalled;

    private int oneStepCalled;

    private int pauseCalled;

    private boolean running;

    private SimLifeCalendar calendar;

    private Universe universe;

    public MockTimeController() {
        super(null);
        reset();
    }

    public void reset() {
        state = CoreDataCreatorForTests.createSpecificTimeControllerState();
        synchronizeCalled = 0;
        speed = 1;
        startCalled = 0;
        oneStepCalled = 0;
        pauseCalled = 0;
        running = false;
        calendar = new SimLifeCalendar();
        universe = new MockUniverse();
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
    public void pause() {
        pauseCalled++;
    }

    public int getNbPauseCalled() {
        return pauseCalled;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public SimLifeCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(SimLifeCalendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public Universe getUniverse() {
        return universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

}
