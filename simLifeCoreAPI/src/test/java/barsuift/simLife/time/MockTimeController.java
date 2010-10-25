package barsuift.simLife.time;

import java.util.ArrayList;
import java.util.List;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.process.SynchronizedRunnable;


public class MockTimeController extends BasicPublisher implements TimeController {

    private SimLifeDate date;

    private TimeControllerState state;

    private int synchronizeCalled;

    private int speed;

    private int startCalled;

    private int oneStepCalled;

    private int stopCalled;

    private boolean running;

    private int scheduleCalled;

    private List<Runnable> runnables;

    public MockTimeController() {
        super(null);
        reset();
    }

    public void reset() {
        date = new SimLifeDate();
        state = CoreDataCreatorForTests.createSpecificTimeControllerState();
        synchronizeCalled = 0;
        speed = 1;
        startCalled = 0;
        oneStepCalled = 0;
        stopCalled = 0;
        scheduleCalled = 0;
        runnables = new ArrayList<Runnable>();
        running = false;
    }

    @Override
    public SimLifeDate getDate() {
        return date;
    }

    public void setDate(SimLifeDate date) {
        this.date = date;
    }

    public void schedule(SynchronizedRunnable runnable) {
        scheduleCalled++;
        runnables.add(runnable);
    }

    public int getNbScheduleCalled() {
        return scheduleCalled;
    }

    public List<Runnable> getScheduledRunnables() {
        return runnables;
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
