/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife.process;

import java.util.concurrent.CyclicBarrier;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;

import static org.fest.assertions.Assertions.assertThat;


public class BasicSynchronizerSlowTest {

    private BasicSynchronizerSlow synchro;

    private SynchronizerSlowState state;

    private MockConditionalTask task;

    private SynchronizedTask barrierReleaser;

    @BeforeMethod
    protected void setUp() {
        setUpWithBound(0);
    }

    private void setUpWithBound(int bound) {
        state = new SynchronizerSlowState(Speed.VERY_FAST);
        synchro = new BasicSynchronizerSlow(state);
        task = new MockConditionalTask(new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(bound, 0)));
        synchro.schedule(task);

        CyclicBarrier barrier = new CyclicBarrier(2);
        synchro.setBarrier(barrier);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
    }

    @AfterMethod
    protected void tearDown() {
        state = null;
        synchro = null;
        task = null;
    }

    @Test
    public void setBarrier() {
        try {
            synchro.setBarrier(new CyclicBarrier(1));
            Assert.fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }
        synchro = new BasicSynchronizerSlow(state);
        try {
            synchro.setBarrier(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

    @Test
    public void setSpeed() {
        assertThat(synchro.getSpeed()).isEqualTo(Speed.VERY_FAST);
        synchro.setSpeed(Speed.FAST);
        assertThat(synchro.getSpeed()).isEqualTo(Speed.FAST);
        synchro.setSpeed(Speed.NORMAL);
        assertThat(synchro.getSpeed()).isEqualTo(Speed.NORMAL);
    }

    @Test
    public void start() throws InterruptedException {
        assertThat(synchro.isRunning()).isFalse();
        assertThat(task.getNbExecuted()).isEqualTo(0);

        synchro.start();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        assertThat(synchro.isRunning()).isTrue();
        assertThat(task.getNbExecuted() > 0).isTrue();

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        assertThat(synchro.isRunning()).isFalse();
        int nbExecuted = task.getNbExecuted();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        // assert the task is not called anymore
        assertThat(task.getNbExecuted()).isEqualTo(nbExecuted);
        // even if we release the barrier once more
        new Thread(barrierReleaser).start();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        assertThat(task.getNbExecuted()).isEqualTo(nbExecuted);
    }

    @Test
    public void startWithBoundedTask() throws InterruptedException {
        // with this bound, the tasks should execute only once
        setUpWithBound(1);

        assertThat(synchro.isRunning()).isFalse();
        assertThat(task.getNbExecuted()).isEqualTo(0);
        // the task in not yet in the list because the synchronizer is not running
        assertThat(synchro.getTasks().contains(task)).isFalse();

        synchro.start();
        // the synchronizer should be in the list now
        assertThat(synchro.getTasks().contains(task)).isTrue();
        Thread.sleep(2 * BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        assertThat(synchro.isRunning()).isTrue();
        assertThat(task.getNbExecuted()).isEqualTo(1);

        synchro.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(BasicSynchronizerFast.CYCLE_LENGTH_FAST_MS + 100);
        assertThat(synchro.isRunning()).isFalse();
        // the task has already stopped anyway
        assertThat(task.getNbExecuted()).isEqualTo(1);
        // the task is not even in the list of tasks
        assertThat(synchro.getTasks().contains(task)).isFalse();
    }

    @Test
    public void publisher() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(synchro);

        synchro.start();
        assertThat(publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat(publisherHelper.getUpdateObjects().get(0)).isNull();
        publisherHelper.reset();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat(publisherHelper.getUpdateObjects().get(0)).isNull();
    }


    @Test(expectedExceptions = { IllegalStateException.class })
    public void illegalStateException_onStop() {
        synchro.stop();
    }

    @Test(expectedExceptions = { IllegalStateException.class })
    public void illegalStateException_onAlreadyStarted() throws InterruptedException {
        synchro.start();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.start();
    }

    @Test
    public void getState() {
        assertThat(synchro.getState()).isEqualTo(state);
        assertThat(synchro.getState()).isSameAs(state);
        assertThat(synchro.getState().getSpeed()).isEqualTo(Speed.VERY_FAST);
        synchro.setSpeed(Speed.FAST);
        assertThat(synchro.getState()).isEqualTo(state);
        assertThat(synchro.getState()).isSameAs(state);
        assertThat(synchro.getState().getSpeed()).isEqualTo(Speed.FAST);
    }

    @Test
    public void schedule() throws Exception {
        // create mocks with no bound to be sure they won't stop before the end of the test
        ConditionalTaskState conditionalTaskState = new ConditionalTaskState(new CyclicConditionState(1, 0),
                new BoundConditionState(0, 0));
        MockConditionalTask mockRun1 = new MockConditionalTask(conditionalTaskState);
        MockConditionalTask mockRun2 = new MockConditionalTask(conditionalTaskState);
        MockConditionalTask mockRun3 = new MockConditionalTask(conditionalTaskState);

        try {
            synchro.unschedule(mockRun1);
            Assert.fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected Exception
        }

        synchro.schedule(mockRun1);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);

        assertThat(mockRun1.getNbExecuted() >= 1).isTrue();
        assertThat(mockRun2.getNbExecuted() == 0).isTrue();
        assertThat(mockRun3.getNbExecuted() == 0).isTrue();

        mockRun1.resetNbExecuted();
        synchro.schedule(mockRun2);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);

        assertThat(mockRun1.getNbExecuted() >= 1).isTrue();
        assertThat(mockRun2.getNbExecuted() == 0).isTrue();
        assertThat(mockRun3.getNbExecuted() == 0).isTrue();

        mockRun1.resetNbExecuted();
        synchro.schedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);
        synchro.unschedule(mockRun2);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);

        assertThat(mockRun1.getNbExecuted() >= 1).isTrue();
        assertThat(mockRun2.getNbExecuted() >= 1).isTrue();
        assertThat(mockRun3.getNbExecuted() == 0).isTrue();
        assertThat(mockRun1.getNbExecuted() > mockRun2.getNbExecuted()).isTrue();

        mockRun1.resetNbExecuted();
        mockRun2.resetNbExecuted();
        synchro.schedule(mockRun3);
        synchro.unschedule(mockRun1);
        synchro.start();
        synchro.stop();
        barrierReleaser.run();
        Thread.sleep(BasicSynchronizerSlow.CYCLE_LENGTH_SLOW_MS / synchro.getSpeed().getSpeed() + 100);

        assertThat(mockRun1.getNbExecuted() == 0).isTrue();
        assertThat(mockRun2.getNbExecuted() == 0).isTrue();
        assertThat(mockRun3.getNbExecuted() >= 1).isTrue();

    }

}
