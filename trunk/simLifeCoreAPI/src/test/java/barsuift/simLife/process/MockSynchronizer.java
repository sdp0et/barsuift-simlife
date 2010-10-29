package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;

import barsuift.simLife.CoreDataCreatorForTests;
import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.time.SimLifeDate;


public class MockSynchronizer extends BasicPublisher implements Synchronizer {

    private SimLifeDate date;

    private Speed speed;

    private boolean running;

    private int scheduleCalled;

    private List<Runnable> runnablesToSchedule;

    private int unscheduleCalled;

    private List<Runnable> runnablesToUnschedule;

    private int startCalled;

    private int oneStepCalled;

    private int stopCalled;

    private int stopAndWaitCalled;

    private SynchronizerState state;

    private int synchronizeCalled;

    public MockSynchronizer() {
        super(null);
        reset();
    }

    public void reset() {
        date = new SimLifeDate();
        speed = Speed.NORMAL;
        running = false;
        scheduleCalled = 0;
        runnablesToSchedule = new ArrayList<Runnable>();
        unscheduleCalled = 0;
        runnablesToUnschedule = new ArrayList<Runnable>();
        startCalled = 0;
        oneStepCalled = 0;
        stopCalled = 0;
        stopAndWaitCalled = 0;
        state = CoreDataCreatorForTests.createSpecificSynchronizerState();
        synchronizeCalled = 0;
    }

    @Override
    public SimLifeDate getDate() {
        return date;
    }

    public void setDate(SimLifeDate date) {
        this.date = date;
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

    public void schedule(SynchronizedRunnable runnable) {
        scheduleCalled++;
        runnablesToSchedule.add(runnable);
    }

    public int getNbScheduleCalled() {
        return scheduleCalled;
    }

    public List<Runnable> getScheduledRunnables() {
        return runnablesToSchedule;
    }

    public void unschedule(SynchronizedRunnable runnable) {
        unscheduleCalled++;
        runnablesToUnschedule.add(runnable);
    }

    public int getNbUnscheduleCalled() {
        return unscheduleCalled;
    }

    public List<Runnable> getUnscheduledRunnables() {
        return runnablesToUnschedule;
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
    public SynchronizerState getState() {
        return state;
    }

    public void setState(SynchronizerState state) {
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
