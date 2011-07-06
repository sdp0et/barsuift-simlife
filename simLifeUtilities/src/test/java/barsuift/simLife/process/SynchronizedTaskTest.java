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

import static org.fest.assertions.Assertions.assertThat;


public class SynchronizedTaskTest {

    private MockSingleSynchronizedTask barrierReleaser;

    private MockSynchronizedTask synchroTask;

    private CyclicBarrier barrier;

    @BeforeMethod
    protected void setUp() {
        barrier = new CyclicBarrier(2);
        synchroTask = new MockSynchronizedTask();
        synchroTask.changeBarrier(barrier);
        barrierReleaser = new MockSingleSynchronizedTask();
        barrierReleaser.changeBarrier(barrier);
    }

    @Test
    public void run() throws InterruptedException {
        (new Thread(synchroTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(synchroTask.isRunning()).isTrue();
        assertThat(synchroTask.getNbExecuted()).isEqualTo(1);

        // test we can not run the same task again
        try {
            synchroTask.run();
            Assert.fail("Should throw an IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK expected exception
        }

        // test we can stop it now
        synchroTask.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertThat(synchroTask.isRunning()).isFalse();

        synchroTask.resetNbExecuted();
        // test we can start it again
        (new Thread(synchroTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(synchroTask.isRunning()).isTrue();
        assertThat(synchroTask.getNbExecuted()).isEqualTo(1);
    }

    @Test
    public void sSetBarrier() throws Exception {
        // the process is not running, so it is still OK to change the barrier
        synchroTask.changeBarrier(barrier);

        // start the process
        (new Thread(synchroTask)).start();
        // make sure the thread has time to start
        Thread.sleep(100);
        assertThat(synchroTask.isRunning()).isTrue();
        // now, the process is running, but we can still change the barrier
        synchroTask.changeBarrier(barrier);

        // stop the process
        synchroTask.stop();
        barrierReleaser.run();
        // make sure the thread has time to stop
        Thread.sleep(100);
        assertThat(synchroTask.isRunning()).isFalse();
        // now, the process is stopped, so we can change again the barrier
        synchroTask.changeBarrier(barrier);

        // test with null parameter
        try {
            synchroTask.changeBarrier(null);
            Assert.fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            // OK expected exception
        }
    }

}
