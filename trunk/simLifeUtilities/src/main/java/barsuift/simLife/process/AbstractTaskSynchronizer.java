package barsuift.simLife.process;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import barsuift.simLife.message.BasicPublisher;
import barsuift.simLife.message.Publisher;
import barsuift.simLife.message.Subscriber;

/**
 * This is a common implementation of TaskSynchronizer.
 * <p>
 * This synchronizer allows to run the list of given {@link SynchronizedTask} at a given rate. A
 * {@link CyclicBarrier} is used to synchronized all the tasks, and a {@link Temporizer}, in a
 * {@link ScheduledExecutorService}, is used to ensure there is always the same delay between two runs.
 * </p>
 * 
 * @param <E> the sub-type of SynchronizedTask to use
 */
public abstract class AbstractTaskSynchronizer<E extends SynchronizedTask> implements TaskSynchronizer<E> {

    private boolean running;

    private boolean isStopAsked;


    private final ScheduledExecutorService scheduledThreadPool;

    private final Temporizer temporizer;

    private ScheduledFuture<?> temporizerFuture;


    private CyclicBarrier innerBarrier;

    private CyclicBarrier barrierForTasks;

    private final ExecutorService standardThreadPool;

    private final List<E> tasks = new ArrayList<E>();


    private final ConcurrentLinkedQueue<E> newTasksToSchedule = new ConcurrentLinkedQueue<E>();

    private final ConcurrentLinkedQueue<E> tasksToUnschedule = new ConcurrentLinkedQueue<E>();


    private final Publisher publisher = new BasicPublisher(this);


    public AbstractTaskSynchronizer() {
        this.running = false;
        this.isStopAsked = false;
        this.barrierForTasks = new CyclicBarrier(1, createBarrierTask());
        this.temporizer = new Temporizer(barrierForTasks);
        this.scheduledThreadPool = Executors.newScheduledThreadPool(1);
        this.standardThreadPool = Executors.newCachedThreadPool();
    }

    public void setBarrier(CyclicBarrier barrier) {
        if (this.innerBarrier != null) {
            throw new IllegalStateException("The synchronizer already has a barrier to synchronize on");
        }
        if (barrier == null) {
            throw new IllegalArgumentException("The given barrier is null");
        }
        this.innerBarrier = barrier;
    }

    protected void synchronizeWithOthers() {
        try {
            innerBarrier.await();
        } catch (InterruptedException e) {
            internalStop();
        } catch (BrokenBarrierException e) {
            internalStop();
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    protected List<E> getTasks() {
        return tasks;
    }

    @Override
    public void schedule(E task) {
        newTasksToSchedule.add(task);
    }

    @Override
    public void unschedule(E task) {
        // first try to remove it from the list of tasks to add
        if (!newTasksToSchedule.remove(task)) {
            if (!tasks.contains(task)) {
                throw new IllegalStateException("The task to unschedule is not acutally scheduled. task=" + task);
            }
            // if not present in the list to add, add it to the list to remove
            tasksToUnschedule.add(task);
        }
    }

    @Override
    public synchronized void start() {
        isStopAsked = false;
        if (running == true) {
            throw new IllegalStateException("The synchronizer is already running");
        }
        updateTaskList(false);
        running = true;

        temporizerFuture = scheduledThreadPool.scheduleWithFixedDelay(temporizer, 0, getTemporizerPeriod(),
                TimeUnit.MILLISECONDS);

        for (SynchronizedTask task : tasks) {
            standardThreadPool.submit(task);
        }
        setChanged();
        notifySubscribers();
    }

    protected abstract int getTemporizerPeriod();

    @Override
    public synchronized void stop() {
        if (running == false) {
            throw new IllegalStateException("The synchronizer is not running");
        }
        isStopAsked = true;
    }

    public synchronized void stopAndWait() {
        stop();
        while (isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                return;
            }
        }
    }

    protected boolean isStopAsked() {
        return isStopAsked;
    }

    protected void internalStop() {
        if (running == false) {
            throw new IllegalStateException("The synchronizer is not running");
        }
        running = false;

        temporizerFuture.cancel(false);
        for (SynchronizedTask task : tasks) {
            task.stop();
        }
        setChanged();
        notifySubscribers();
    }


    protected void updateTaskList(boolean startNewTasks) {
        int nbNewTasksToAdd = newTasksToSchedule.size();
        int nbTasksToRemove = tasksToUnschedule.size();
        // if there are new tasks to schedule or to unschedule
        if (nbNewTasksToAdd > 0 || nbTasksToRemove > 0) {
            // 1. update the new barrier
            barrierForTasks = new CyclicBarrier(barrierForTasks.getParties() + nbNewTasksToAdd
                    - nbTasksToRemove, createBarrierTask());
            // 2. update the list of executed tasks
            tasks.addAll(newTasksToSchedule);
            tasks.removeAll(tasksToUnschedule);
            // 3. update the barrier for everyone
            for (SynchronizedTask task : tasks) {
                task.changeBarrier(barrierForTasks);
            }
            // 4. also change the temporizer barrier
            temporizer.changeBarrier(barrierForTasks);
            // 5. start the new tasks if needed, or simply purge the list
            while (!newTasksToSchedule.isEmpty()) {
                E taskToSchedule = newTasksToSchedule.poll();
                if (startNewTasks) {
                    standardThreadPool.submit(taskToSchedule);
                }
            }
            // 6. stop the old tasks
            while (!tasksToUnschedule.isEmpty()) {
                E taskToUnschedule = tasksToUnschedule.poll();
                if (taskToUnschedule.isRunning()) {
                    taskToUnschedule.stop();
                }
            }
        }
    }

    protected abstract Runnable createBarrierTask();

    public void addSubscriber(Subscriber subscriber) {
        publisher.addSubscriber(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {
        publisher.deleteSubscriber(subscriber);
    }

    public void notifySubscribers() {
        publisher.notifySubscribers();
    }

    public void notifySubscribers(Object arg) {
        publisher.notifySubscribers(arg);
    }

    public void deleteSubscribers() {
        publisher.deleteSubscribers();
    }

    public boolean hasChanged() {
        return publisher.hasChanged();
    }

    public int countSubscribers() {
        return publisher.countSubscribers();
    }

    public void setChanged() {
        publisher.setChanged();
    }

    public void clearChanged() {
        publisher.clearChanged();
    }

}
