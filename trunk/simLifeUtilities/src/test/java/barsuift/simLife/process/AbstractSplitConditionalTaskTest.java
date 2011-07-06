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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;

import static org.fest.assertions.Assertions.assertThat;


public class AbstractSplitConditionalTaskTest {

    private MockSplitConditionalTask splitTask;

    private SplitConditionalTaskState state;

    private MockSingleSynchronizedTask barrierReleaser;

    @BeforeMethod
    protected void setUp() {
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        CyclicConditionState executionCondition = new CyclicConditionState(3, 0);
        BoundConditionState endingCondition = new BoundConditionState(60, 6);
        ConditionalTaskState conditionalTaskState = new ConditionalTaskState(executionCondition, endingCondition);
        state = new SplitConditionalTaskState(conditionalTaskState, 2);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
        splitTask = new MockSplitConditionalTask(state);
        splitTask.changeBarrier(barrier);
    }

    @Test
    public void run() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(splitTask);

        (new Thread(splitTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue(); // executed once
        assertThat(splitTask.getNbExecuted()).isEqualTo(0);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(0);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(2);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(8);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // test we can not run the same task again
        try {
            splitTask.run();
            Assert.fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        splitTask.stop();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to stop
        Thread.sleep(100);
        AssertJUnit.assertFalse(splitTask.isRunning());

        // test we can start it again
        (new Thread(splitTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue();
        // executed once again
        assertThat(splitTask.getNbExecuted()).isEqualTo(1);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(2);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(1);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(10);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue();
        // executed once again
        assertThat(splitTask.getNbExecuted()).isEqualTo(2);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(4);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(0);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(12);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        splitTask.setStepSize(3);

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue();
        // executed once again
        assertThat(splitTask.getNbExecuted()).isEqualTo(3);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(7);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(0);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(15);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue();
        // executed once again
        assertThat(splitTask.getNbExecuted()).isEqualTo(4);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(10);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(0);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(18);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        splitTask.setStepSize(20);

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue();
        // executed once again
        assertThat(splitTask.getNbExecuted()).isEqualTo(5);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(30);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(2);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(38);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue();
        // executed once again
        assertThat(splitTask.getNbExecuted()).isEqualTo(6);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(50);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(1);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(58);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        splitTask.setStepSize(1);

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue();
        // executed once again
        assertThat(splitTask.getNbExecuted()).isEqualTo(6);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(50);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(2);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(59);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(splitTask.isRunning()).isTrue();
        // executed once again
        assertThat(splitTask.getNbExecuted()).isEqualTo(7);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(51);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(0);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(60);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat(publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        AssertJUnit.assertFalse(splitTask.isRunning());
        // this time, the split task should have stopped
        assertThat(splitTask.getNbExecuted()).isEqualTo(7);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(51);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(0);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(60);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        // still stopped
        AssertJUnit.assertFalse(splitTask.isRunning());
        assertThat(splitTask.getNbExecuted()).isEqualTo(7);
        assertThat(splitTask.getNbIncrementExecuted()).isEqualTo(51);
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(0);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(60);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();
    }

    @Test
    public void getState() throws InterruptedException {
        assertThat(splitTask.getState()).isEqualTo(state);
        AssertJUnit.assertSame(state, splitTask.getState());
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(0);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(6);
        (new Thread(splitTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(splitTask.getState()).isEqualTo(state);
        AssertJUnit.assertSame(state, splitTask.getState());
        // 2 because the counter is reseted every time it is greater than the cycle size
        assertThat(splitTask.getState().getConditionalTask().getExecutionCondition().getCount()).isEqualTo(2);
        assertThat(splitTask.getState().getConditionalTask().getEndingCondition().getCount()).isEqualTo(8);
    }

}
