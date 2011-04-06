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
package barsuift.simLife;

import java.math.BigDecimal;

import barsuift.simLife.condition.BoundConditionState;
import barsuift.simLife.condition.CyclicConditionState;
import barsuift.simLife.process.ConditionalTaskState;
import barsuift.simLife.process.MainSynchronizerState;
import barsuift.simLife.process.Speed;
import barsuift.simLife.process.SplitConditionalTaskState;
import barsuift.simLife.process.SynchronizerFastState;
import barsuift.simLife.process.SynchronizerSlowState;
import barsuift.simLife.time.SimLifeDateState;


public final class UtilDataCreatorForTests {

    private UtilDataCreatorForTests() {
        // private constructor to enforce static access
    }

    /**
     * Creates a random BigDecimal, between 0 and 1.
     */
    public static BigDecimal createRandomBigDecimal() {
        return new BigDecimal(Math.random());
    }

    public static boolean createRandomBoolean() {
        return Math.random() >= 0.5;
    }

    /**
     * Creates a random rotation angle in radian, between 0 and 2 * Pi
     */
    public static double createRandomRotation() {
        return Math.random() * 2 * Math.PI;
    }

    /**
     * Creates a random date state, between 0 and 100 seconds.
     */
    public static SimLifeDateState createRandomDateState() {
        return new SimLifeDateState(Randomizer.randomBetween(0, 100) * 1000);
    }

    /**
     * Creates a specific date state, at 100 seconds.
     */
    public static SimLifeDateState createSpecificDateState() {
        return new SimLifeDateState(100000);
    }


    /*
     * ************* CONDTIONS *************
     */

    /**
     * Creates a random Cyclic condition state with
     * <ul>
     * <li>cycle=[3-10]</li>
     * <li>count=[0-2]</li>
     * </ul>
     */
    public static CyclicConditionState createRandomCyclicConditionState() {
        return new CyclicConditionState(Randomizer.randomBetween(3, 10), Randomizer.randomBetween(0, 2));
    }

    /**
     * Creates a specific CyclicConditionState with
     * <ul>
     * <li>cycle=5</li>
     * <li>count=2</li>
     * </ul>
     */
    public static CyclicConditionState createSpecificCyclicConditionState() {
        return new CyclicConditionState(5, 2);
    }

    /**
     * Creates a random BoundConditionState with
     * <ul>
     * <li>bound=[3-10]</li>
     * <li>count=[0-2]</li>
     * </ul>
     */
    public static BoundConditionState createRandomBoundConditionState() {
        return new BoundConditionState(Randomizer.randomBetween(3, 10), Randomizer.randomBetween(0, 2));
    }

    /**
     * Creates a specific BoundConditionState with
     * <ul>
     * <li>bound=5</li>
     * <li>count=2</li>
     * </ul>
     */
    public static BoundConditionState createSpecificBoundConditionState() {
        return new BoundConditionState(5, 2);
    }



    /*
     * ************* TASKS *************
     */

    /**
     * Creates a random ConditionalTaskState with
     * <ul>
     * <li>executionCondition made through {@link #createRandomCyclicConditionState()}</li>
     * <li>endingCondition made through {@link #createRandomBoundConditionState()}</li>
     * </ul>
     */
    public static ConditionalTaskState createRandomConditionalTaskState() {
        CyclicConditionState executionCondition = createRandomCyclicConditionState();
        BoundConditionState endingCondition = createRandomBoundConditionState();
        return new ConditionalTaskState(executionCondition, endingCondition);
    }

    /**
     * Creates a specific ConditionalTaskState with
     * <ul>
     * <li>executionCondition made through {@link #createSpecificCyclicConditionState()}</li>
     * <li>endingCondition made through {@link #createSpecificBoundConditionState()}</li>
     * </ul>
     */
    public static ConditionalTaskState createSpecificConditionalTaskState() {
        CyclicConditionState executionCondition = createSpecificCyclicConditionState();
        BoundConditionState endingCondition = createSpecificBoundConditionState();
        return new ConditionalTaskState(executionCondition, endingCondition);
    }

    /**
     * Creates a random SplitConditionalTaskState with
     * <ul>
     * <li>conditionalTask made through {@link #createRandomConditionalTaskState()}</li>
     * <li>stepSize=[2-5]</li>
     * </ul>
     */
    public static SplitConditionalTaskState createRandomSplitConditionalTaskState() {
        ConditionalTaskState conditionalTask = createRandomConditionalTaskState();
        int stepSize = Randomizer.randomBetween(2, 5);
        return new SplitConditionalTaskState(conditionalTask, stepSize);
    }

    /**
     * Creates a specific SplitConditionalTaskState with
     * <ul>
     * <li>conditionalTask made through {@link #createSpecificConditionalTaskState()}</li>
     * <li>stepSize=3</li>
     * </ul>
     */
    public static SplitConditionalTaskState createSpecificSplitConditionalTaskState() {
        ConditionalTaskState conditionalTask = createSpecificConditionalTaskState();
        int stepSize = 3;
        return new SplitConditionalTaskState(conditionalTask, stepSize);
    }

    public static SynchronizerSlowState createRandomSynchronizerSlowState() {
        return new SynchronizerSlowState(Speed.values()[Randomizer.randomBetween(0, 2)]);
    }

    public static SynchronizerSlowState createSpecificSynchronizerSlowState() {
        return new SynchronizerSlowState(Speed.NORMAL);
    }

    public static MainSynchronizerState createRandomMainSynchronizerState() {
        SynchronizerSlowState synchronizerSlowState = createRandomSynchronizerSlowState();
        SynchronizerFastState synchronizerFastState = createRandomSynchronizerFastState();
        return new MainSynchronizerState(synchronizerSlowState, synchronizerFastState);
    }

    public static MainSynchronizerState createSpecificMainSynchronizerState() {
        SynchronizerSlowState synchronizerSlowState = createSpecificSynchronizerSlowState();
        SynchronizerFastState synchronizerFastState = createSpecificSynchronizerFastState();
        return new MainSynchronizerState(synchronizerSlowState, synchronizerFastState);
    }

    public static SynchronizerFastState createRandomSynchronizerFastState() {
        return new SynchronizerFastState(Randomizer.randomBetween(1, 20));
    }

    public static SynchronizerFastState createSpecificSynchronizerFastState() {
        return new SynchronizerFastState(1);
    }

}
