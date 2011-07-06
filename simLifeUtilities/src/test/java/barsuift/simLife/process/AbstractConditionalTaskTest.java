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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.message.PublisherTestHelper;

import static org.fest.assertions.Assertions.assertThat;


public class AbstractConditionalTaskTest {

    private MockConditionalTask conditionalRun;

    private ConditionalTaskState state;

    private MockSingleSynchronizedTask barrierReleaser;

    @BeforeMethod
    protected void setUp() {
        // make sure the barrier will block the process as long as the other mock process is not run
        CyclicBarrier barrier = new CyclicBarrier(2);
        CyclicConditionState executionConditionState = new CyclicConditionState(3, 0);
        BoundConditionState endingConditionState = new BoundConditionState(5, 0);
        state = new ConditionalTaskState(executionConditionState, endingConditionState);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
        conditionalRun = new MockConditionalTask(state);
        conditionalRun.changeBarrier(barrier);
    }

    /**
     * Expected results are :
     * <ol>
     * <li>not executed</li>
     * <li>not executed</li>
     * <li>not executed</li>
     * <li>executed</li>
     * <li>not executed</li>
     * <li>not executed and stopped</li>
     * <li>not executed</li>
     * <li>
     * </ol>
     */
    @Test
    public void run() throws Exception {
        PublisherTestHelper publisherHelper = new PublisherTestHelper();
        publisherHelper.addSubscriberTo(conditionalRun);

        (new Thread(conditionalRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(conditionalRun.isRunning()).isTrue(); // run 1
        assertThat(conditionalRun.getNbExecuted()).isEqualTo(0);
        assertThat(conditionalRun.getState().getExecutionCondition().getCount()).isEqualTo(1);
        assertThat(conditionalRun.getState().getEndingCondition().getCount()).isEqualTo(1);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // test we can not run the same task again
        try {
            conditionalRun.run();
            Assert.fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        conditionalRun.stop();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertThat(conditionalRun.isRunning()).isFalse();

        // test we can start it again
        (new Thread(conditionalRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(conditionalRun.isRunning()).isTrue();
        // run 2
        assertThat(conditionalRun.getNbExecuted()).isEqualTo(0);
        assertThat(conditionalRun.getState().getExecutionCondition().getCount()).isEqualTo(2);
        assertThat(conditionalRun.getState().getEndingCondition().getCount()).isEqualTo(2);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(conditionalRun.isRunning()).isTrue();
        // run 3
        assertThat(conditionalRun.getNbExecuted()).isEqualTo(1);
        assertThat(conditionalRun.getState().getExecutionCondition().getCount()).isEqualTo(0);
        assertThat(conditionalRun.getState().getEndingCondition().getCount()).isEqualTo(3);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();


        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(conditionalRun.isRunning()).isTrue();
        // run 4
        assertThat(conditionalRun.getNbExecuted()).isEqualTo(1);
        assertThat(conditionalRun.getState().getExecutionCondition().getCount()).isEqualTo(1);
        assertThat(conditionalRun.getState().getEndingCondition().getCount()).isEqualTo(4);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();

        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(conditionalRun.isRunning()).isTrue();
        // run 5
        assertThat(conditionalRun.getNbExecuted()).isEqualTo(1);
        assertThat(conditionalRun.getState().getExecutionCondition().getCount()).isEqualTo(2);
        assertThat(conditionalRun.getState().getEndingCondition().getCount()).isEqualTo(5);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(1);
        assertThat(publisherHelper.getUpdateObjects().get(0)).isEqualTo(null);

        publisherHelper.reset();
        // release the barrier
        (new Thread(barrierReleaser)).start();
        // make sure the thread has time to execute
        Thread.sleep(100);
        assertThat(conditionalRun.isRunning()).isFalse();
        // run 6
        assertThat(conditionalRun.getNbExecuted()).isEqualTo(1);
        assertThat(conditionalRun.getState().getExecutionCondition().getCount()).isEqualTo(2);
        assertThat(conditionalRun.getState().getEndingCondition().getCount()).isEqualTo(5);
        assertThat(publisherHelper.nbUpdated()).isEqualTo(0);
        assertThat(publisherHelper.getUpdateObjects()).isEmpty();
    }

    @Test
    public void getState() throws InterruptedException {
        assertThat(conditionalRun.getState()).isEqualTo(state);
        assertThat(conditionalRun.getState()).isSameAs(state);
        assertThat(conditionalRun.getState().getEndingCondition().getCount()).isEqualTo(0);
        assertThat(conditionalRun.getState().getExecutionCondition().getCount()).isEqualTo(0);
        (new Thread(conditionalRun)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(conditionalRun.getState()).isEqualTo(state);
        assertThat(conditionalRun.getState()).isSameAs(state);
        assertThat(conditionalRun.getState().getEndingCondition().getCount()).isEqualTo(1);
        assertThat(conditionalRun.getState().getExecutionCondition().getCount()).isEqualTo(1);
    }

}
